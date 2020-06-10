import com.github.javafaker.Faker;
import model.Address;
import model.Animal;
import model.Box;
import model.Zoo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExampleData {

    public static Zoo getRandomZoo() {
        Faker faker =  new Faker();
        Zoo zoo = new Zoo();
        List<Box> boxes = new ArrayList<>();
        for (int i=0; i < faker.number().numberBetween(10, 40); i++) {
            boxes.add(getRandomBox(faker));
        }
        zoo.setAddress(getRandomAddress(faker));
        zoo.setBoxes(boxes);
        zoo.setId(faker.number().numberBetween(1,10000));
        zoo.setNumberOfWorkers(faker.number().numberBetween(4, 15));
        return zoo;
    }

    private static Address getRandomAddress(Faker faker) {
        Address address = new Address();
        address.setHouseNumber(faker.number().numberBetween(1, 100));
        address.setPostalCode(faker.address().zipCode());
        address.setStreet(faker.address().streetName());
        return address;
    }

    private static Animal getRandomAnimal(Faker faker) {
        Animal animal = new Animal();
        animal.setAge(faker.number().numberBetween(1, 35));
        animal.setKind(faker.medical().diseaseName());
        animal.setName(faker.animal().name());
        return animal;
    }

    private static Box getRandomBox(Faker faker){
        Box box = new Box();
        List<Animal> animals = new ArrayList<>();
        for (int i=0; i < faker.number().numberBetween(1, 5); i++) {
            animals.add(getRandomAnimal(faker));
        }
        box.setAnimals(animals);
        box.setBoxName(faker.name().username());
        box.setBoxNumber(faker.number().numberBetween(1, 300));
        return box;
    }
}
