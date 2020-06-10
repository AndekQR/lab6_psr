package model;


import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;

import java.util.List;

public class Zoo {

    @FaunaField
    private List<Box> boxes;
    @FaunaField
    private Address address;
    @FaunaField
    private Integer numberOfWorkers;
    public Zoo() {
    }

    @FaunaConstructor
    public Zoo(@FaunaField("boxes") List<Box> boxes, @FaunaField("address") Address address, @FaunaField("numberOfWorkers") Integer numberOfWorkers) {
        this.boxes=boxes;
        this.address=address;
        this.numberOfWorkers=numberOfWorkers;
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
