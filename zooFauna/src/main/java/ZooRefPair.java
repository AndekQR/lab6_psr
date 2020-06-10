import com.faunadb.client.types.Value;
import model.Zoo;

import java.util.Optional;

public class ZooRefPair {

    private Value ref;
    private Zoo zoo;


    public ZooRefPair(Value ref, Zoo zoo) {
        this.ref=ref;
        this.zoo=zoo;
    }

    public Optional<Value> getRef() {
        return Optional.ofNullable(ref);
    }

    public void setRef(Value ref) {
        this.ref=ref;
    }

    public Optional<Zoo> getZoo() {
        return Optional.ofNullable(zoo);
    }

    public void setZoo(Zoo zoo) {
        this.zoo=zoo;
    }
}
