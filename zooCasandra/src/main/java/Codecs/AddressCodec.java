package Codecs;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import model.Address;

public class AddressCodec extends MappingCodec<UdtValue, Address> {

    public AddressCodec(@NonNull TypeCodec<UdtValue> innerCodec){
        super(innerCodec, GenericType.of(Address.class));
    }

    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }

    @Nullable
    @Override
    protected Address innerToOuter(@Nullable UdtValue value) {
        return value == null ? null : new Address(value.getString("street"), value.getInt("houseNumber"), value.getString("postalCode"));
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable Address value) {
        return value == null ? null : getCqlType().newValue().setString("street", value.getStreet()).setInt("houseNumber", value.getHouseNumber()).setString("postalCode", value.getPostalCode());
    }
}
