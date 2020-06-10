package model;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.util.List;

@Entity
public class Zoo {

    @PartitionKey
    private Integer id;
    private List<Box> boxes;
    private Address address;
    private Integer numberOfWorkers;

    public Zoo() {
    }

    public Zoo(Integer id, List<Box> boxes, Address address, Integer numberOfWorkers) {
        this.id=id;
        this.boxes=boxes;
        this.address=address;
        this.numberOfWorkers=numberOfWorkers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes=boxes;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address=address;
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "id=" + id +
                "\n, boxes=" + boxes +
                "\n, address=" + address +
                "\n, numberOfWorkers=" + numberOfWorkers +
                '}';
    }

    public Integer getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(Integer numberOfWorkers) {
        this.numberOfWorkers=numberOfWorkers;
    }
}
