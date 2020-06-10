import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import model.Address;
import model.Animal;
import model.Box;
import model.Zoo;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class ZooService {

    final private static Duration TIMEOUT=Duration.ofSeconds(10);

    public static final String KEYSPACE="ZOO_KEYSPACE";
    private static final String ZOO_TABLE_NAME="zoo";
    private static final String ANIMAL_TYPE_NAME="animal";
    private static final String BOX_TYPE_NAME="box";
    private static final String ADDRESS_TYPE_NAME="address";

    private final CqlSession session;
    private final ZooDao zooDao;


    public ZooService(CqlSession session) {
        this.session=session;
        this.initKeySpace();
        this.createTables();
        this.registerCodecs();

        ZooMapper zooMapper = new ZooMapperBuilder(this.session).build();
        this.zooDao = zooMapper.zooDao(CqlIdentifier.fromCql(KEYSPACE));

    }

    private void registerCodecs() {
        CodecManager codecManager = new CodecManager(this.session);
        codecManager.registerCodec(Animal.class);
        codecManager.registerCodec(Box.class);
        codecManager.registerCodec(Address.class);
    }

    private void createTables() {
//        dropZooTable();

        SimpleStatement statement=new SimpleStatementBuilder("CREATE TYPE IF NOT EXISTS " + ANIMAL_TYPE_NAME + " (\n" +
                "kind text,\n" +
                "name text,\n" +
                "age int\n" +
                ");").build();
        this.executeSimpleStatement(statement);
        statement=new SimpleStatementBuilder("CREATE TYPE IF NOT EXISTS " + BOX_TYPE_NAME + " (\n" +
                "boxNumber int,\n" +
                "animals list<frozen<" + ANIMAL_TYPE_NAME + ">>,\n" +
                "boxName text\n" +
                ");").build();
        this.executeSimpleStatement(statement);
        statement=new SimpleStatementBuilder("CREATE TYPE IF NOT EXISTS " + ADDRESS_TYPE_NAME + " (\n" +
                "street text,\n" +
                "houseNumber int,\n" +
                "postalCode text\n" +
                ");").build();
        this.executeSimpleStatement(statement);
        statement=new SimpleStatementBuilder("CREATE TABLE IF NOT EXISTS " + ZOO_TABLE_NAME + " (\n" +
                "id int PRIMARY KEY,\n" +
                "boxes list<frozen<" + BOX_TYPE_NAME + ">>,\n" +
                "address frozen<" + ADDRESS_TYPE_NAME + ">,\n" +
                "number_of_workers int,\n" +
                ");").build();
        this.executeSimpleStatement(statement);
    }

    public void dropZooTable() {
        SimpleStatement statement = new SimpleStatementBuilder("DROP TABLE "+ZOO_TABLE_NAME+";").build();
        this.executeSimpleStatement(statement);
    }

    private boolean isTableExists(String tableName) {
        return this.session.getMetadata()
                .getKeyspace(KEYSPACE)
                .flatMap(ks -> ks.getTable(tableName))
                .isPresent();
    }


    private void initKeySpace() {
        KeySpaceManager keySpaceManager=new KeySpaceManager(this.session, KEYSPACE);
        keySpaceManager.createKeySpace();
        keySpaceManager.useKeySpace();
    }

    public Zoo saveZoo(Zoo zoo) {
        this.zooDao.save(zoo);
        return zoo;
    }

    public Zoo updateZooStreet(String newStreet, Integer zooToUpdateId) {
        Zoo zoo=getZoo(zooToUpdateId);
        zoo.getAddress().setStreet(newStreet);
        this.zooDao.update(zoo);
        return zoo;
    }

    public List<Zoo> getAllZoo() {
        return zooDao.getAll().all();
    }

    public Zoo getZoo(Integer uid) {
        Optional<Zoo> optionalZoo=zooDao.findById(uid);
        if (optionalZoo.isPresent()) {
            return optionalZoo.get();
        } else {
            throw new IllegalArgumentException("Brak zoo o podanym id");
        }
    }

    public void deleteZoo(int zooId){
        this.zooDao.delete(zooId);
    }

    public List<Zoo> selectZooByAddress(String street1, int houseNumber1, String postalCode) {
        return this.zooDao.selectZooByAddress(street1, houseNumber1, postalCode).all();
    }

    public void updateNumberOfWorkers(int workers, int zooId){
        SimpleStatement st=QueryBuilder.update(ZOO_TABLE_NAME)
                .usingTtl(0)
                .setColumn("number_of_workers", QueryBuilder.literal(workers))
                .whereColumn("id").isEqualTo(QueryBuilder.literal(zooId)).build();
        this.executeSimpleStatement(st);
    }

    private ResultSet executeSimpleStatement(SimpleStatement simpleStatement) {
        return this.session.execute(simpleStatement.setTimeout(TIMEOUT));
    }
}
