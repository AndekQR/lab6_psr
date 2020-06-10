import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;
import model.Zoo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dao
public interface ZooDao {

    @Select
    Optional<Zoo> findById(Integer id);

    @Select
    PagingIterable<Zoo> getAll();

    @Insert
    void save(Zoo zoo);

    @Update
    void update(Zoo zoo);

    @Delete(entityClass=Zoo.class, ifExists=true)
    void delete(Integer id);

    @Select(customWhereClause="address = { street: :street1, houseNumber: :houseNumber1, postalCode: :postalCode }", allowFiltering=true)
    PagingIterable<Zoo> selectZooByAddress(String street1, int houseNumber1, String postalCode);

}
