package model;

import java.util.List;

public class Box {

    private Integer boxNumber;
    private List<Animal> animals;
    private String boxName;

    public Box() {
    }

    public Box(Integer boxNumber, List<Animal> animals, String boxName) {
        this.boxNumber=boxNumber;
        this.animals=animals;
        this.boxName=boxName;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber=boxNumber;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals=animals;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName=boxName;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + boxNumber +
                ", animals=" + animals +
                ", name='" + boxName + '\'' +
                '}';
    }
}
