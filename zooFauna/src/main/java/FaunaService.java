import com.faunadb.client.FaunaClient;
import com.faunadb.client.types.Value;
import model.Zoo;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;

public class FaunaService {

    private static final String DB_NAME="zooDB";
    private static final String ZOO_COLLECTION_NAME=Zoo.class.getSimpleName();
    private static final String POSTAL_CODE_INDEX="psotal_code_index";
    private static final String ALL_ZOO_INDEX="all_zoo_index";

    private FaunaClient client;
    private final FaunaClient adminClient;

    public FaunaService() {
        this.adminClient=FaunaClient.builder()
                .withSecret(FaunaKey.KEY)
                .build();

        try {
            this.createDBIfNotExists();
            this.getClient();
            this.createCollectionsIfNotExists();
            this.createIndexPostalCodeIfNotExists();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public Zoo saveZoo(Zoo zoo) throws ExecutionException, InterruptedException {
        Value data=this.client.query(
                Create(
                        Collection(ZOO_COLLECTION_NAME),
                        Obj("data", Value(zoo))
                )
        ).get();
        return data.to(Zoo.class).get();
    }

    public ZooRefPair getById(String ref) throws ExecutionException, InterruptedException {
        Value data=client.query(
                Get(Ref(Collection(ZOO_COLLECTION_NAME), Value(ref)))
        ).get();
        return new ZooRefPair(data.at("ref.id"), data.at("data").to(Zoo.class).get());
    }

    private void createCollectionsIfNotExists() {
        this.client.query(
                If(
                        Exists(Collection(ZOO_COLLECTION_NAME)),
                        Value(true),
                        CreateCollection(Obj("name", Value(ZOO_COLLECTION_NAME)))
                )
        );
    }

    private void createIndexPostalCodeIfNotExists() throws ExecutionException, InterruptedException {
        client.query(
                If(Exists(Index(POSTAL_CODE_INDEX)),
                        Value(true),
                        CreateIndex(
                                Obj("name", Value(POSTAL_CODE_INDEX),
                                        "source", Collection(Value(ZOO_COLLECTION_NAME)),
                                        "terms", Arr(Obj("field", Arr(Value("data"), Value("address"), Value("postalCode")))))
                        )
                ));
        client.query(
                If(Exists(Index(ALL_ZOO_INDEX)),
                        Value(true),
                        CreateIndex(
                                Obj("name", Value(ALL_ZOO_INDEX),
                                        "source", Collection(Value(ZOO_COLLECTION_NAME))
                                        )
                        )));

    }

    private void getClient() throws ExecutionException, InterruptedException {
        Value value=this.adminClient.query(
                CreateKey(Obj("database", Database(Value(DB_NAME)), "role", Value("server")))
        ).get();
        String key=value.at("secret").to(String.class).get();
        this.client=this.adminClient.newSessionClient(key);
    }

    private void createDBIfNotExists() throws ExecutionException, InterruptedException {
        this.adminClient.query(
                Do(
                        If(
                                Exists(Database(DB_NAME)),
                                Value(true),
                                CreateDatabase(Obj("name", Value(DB_NAME)))
                        )
                )
        );
    }

    public void updateZooStreet(String id, String newStreet) throws ExecutionException, InterruptedException {
        ZooRefPair zooref=this.getById(id);
        zooref.getZoo().ifPresent(zoo -> {
            zoo.getAddress().setStreet(newStreet);
            client.query(
                    Update(
                            Ref(Collection(ZOO_COLLECTION_NAME), Value(id)), Obj("data", Value(zoo))
                    )
            ).whenComplete((value, throwable) -> System.out.println("Zaktualizowano"));
        });

    }

    public void deleteZoo(String zooId) {
        client.query(
                Delete(
                        Ref(Collection(ZOO_COLLECTION_NAME), Value(zooId))
                )
        ).whenComplete((value, throwable) -> System.out.println("Usunięto: " + value.toString()));
    }

    public Optional<Zoo> getZoosByPostalCode(String postalCode) throws ExecutionException, InterruptedException {
        Value value=client.query(
                Get(
                        Match(
                                Index(Value(POSTAL_CODE_INDEX)),
                                Value(postalCode)
                        )
                )

        ).get();
        return value.at("data").to(Zoo.class).getOptional();
    }

    public int getZooCount() throws ExecutionException, InterruptedException {
        Value value=client.query(
                Count(
                        Match(
                                Index(Value(ALL_ZOO_INDEX))
                        )
                )
        ).get();
        return value.to(Integer.class).get();
    }
}
