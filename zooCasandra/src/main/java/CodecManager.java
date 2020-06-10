import Codecs.AddressCodec;
import Codecs.AnimalCodec;
import Codecs.BoxCodec;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import model.Address;
import model.Animal;
import model.Box;

public class CodecManager {

    private CqlSession cqlSession;

    public CodecManager(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
    }

    public void registerCodec(Class codecClass) {
        CodecRegistry codecRegistry = this.cqlSession.getContext().getCodecRegistry();
        UserDefinedType userDefinedType = cqlSession
                .getMetadata()
                .getKeyspace(ZooService.KEYSPACE)
                .flatMap(ks -> ks.getUserDefinedType(codecClass.getSimpleName()))
                .orElseThrow(IllegalStateException::new);
        TypeCodec<UdtValue> typeCodec = codecRegistry.codecFor(userDefinedType);

        if (codecClass.getSimpleName().equals(Address.class.getSimpleName())) {
            AddressCodec addressCodec = new AddressCodec(typeCodec);
            ((MutableCodecRegistry) codecRegistry).register(addressCodec);
        } else if(codecClass.getSimpleName().equals(Animal.class.getSimpleName())) {
            AnimalCodec animalCodec = new AnimalCodec(typeCodec);
            ((MutableCodecRegistry) codecRegistry).register(animalCodec);
        } else if(codecClass.getSimpleName().equals(Box.class.getSimpleName())) {
            BoxCodec boxCodec = new BoxCodec(typeCodec);
            ((MutableCodecRegistry) codecRegistry).register(boxCodec);
        }
    }
}
