package Codecs;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import com.datastax.oss.driver.internal.core.type.codec.UdtCodec;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import model.Animal;
import model.Box;
import org.apache.tinkerpop.shaded.kryo.NotNull;

public class BoxCodec extends MappingCodec<UdtValue, Box> {

    public BoxCodec(@NonNull TypeCodec<UdtValue> innerCodec) {
        super(innerCodec, GenericType.of(Box.class));

    }

    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }

    @Nullable
    @Override
    protected Box innerToOuter(@Nullable UdtValue value) {
        return value == null ? null : new Box(value.getInt("boxNumber"), value.getList("animals", Animal.class), value.getString("boxName"));
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable Box value) {
        return value == null ? null : getCqlType().newValue().setInt("boxNumber", value.getBoxNumber()).setList("animals", value.getAnimals(), Animal.class).setString("boxName", value.getBoxName());
    }
}
