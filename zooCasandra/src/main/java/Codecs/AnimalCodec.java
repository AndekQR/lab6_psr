package Codecs;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import model.Animal;

public class AnimalCodec extends MappingCodec<UdtValue, Animal> {

    public AnimalCodec(@NonNull TypeCodec<UdtValue> innerCodec) {
        super(innerCodec, GenericType.of(Animal.class));
    }

    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }

    @Nullable
    @Override
    protected Animal innerToOuter(@Nullable UdtValue value) {
        return value == null ? null : new Animal(value.getString("kind"), value.getString("name"), value.getInt("age"));
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable Animal value) {
        return value == null ? null : getCqlType().newValue().setString("kind", value.getKind()).setString("name", value.getName()).setInt("age", value.getAge());
    }
}
