package model;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

public class Animal {

    private String kind;
    private String name;
    private Integer age;

    public Animal(String kind, String name, Integer age) {
        this.kind=kind;
        this.name=name;
        this.age=age;
    }

    public Animal() {
    }


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind=kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age=age;
    }

    @Override
    public String toString() {
        return "Animal{" +
                ", kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
