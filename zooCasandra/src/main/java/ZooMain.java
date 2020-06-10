import com.datastax.oss.driver.api.core.CqlSession;
import model.Zoo;

import java.util.List;
import java.util.Scanner;

public class ZooMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choose;

        try(CqlSession session = CqlSession.builder().build()){
            ZooService zooService = new ZooService(session);
            do{
                System.out.println("[0] - wyjście, " +
                        "[1] - Zapisz randomowe zoo,\n " +
                        "[2] - aktualizuj nazwę ulicy,\n " +
                        "[3] - usuń zoo,\n " +
                        "[4] - pobierz zoo po id,\n " +
                        "[5] - pobieranie złożone,\n " +
                        "[6] - przetwarzanie,\n " +
                        "[7] - pobierz wszystkie zoo");
                try{
                    choose = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    choose = 999;
                }

                switch(choose) {
                    case 1: {
                        Zoo zoo=zooService.saveZoo(ExampleData.getRandomZoo());
                        System.out.println("Zapisano: "+zoo);
                        break;
                    }
                    case 2: {
                        System.out.println("ID zoo do aktualizacji");
                        int id = 0;
                        try{
                            id = Integer.parseInt(scanner.nextLine());
                        }catch (NumberFormatException e) {
                            System.out.println("Nieprawidłowe id");
                        }
                        System.out.println("Nowa nazwa ulicy");
                        String strett = scanner.nextLine();
                        Zoo zoo=zooService.updateZooStreet(strett, id);
                        System.out.println("Zaukatualizowano: "+zoo);
                        break;
                    }
                    case 3: {
                        System.out.println("Id zoo do usunięcia");
                        int id = 0;
                        try{
                            id = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Nieprawidłowe id");
                        }
                        zooService.deleteZoo(id);
                        break;
                    }
                    case 4: {
                        System.out.println("Podaj id zoo do pobrania");
                        int id = scanner.nextInt();
                        try{
                            Zoo zoo=zooService.getZoo(id);
                            System.out.println("Pobrano: "+zoo);
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case 5: {
                        System.out.println("Nazwa ulicy");
                        String street = scanner.nextLine();
                        System.out.println("Numer budynku");
                        int number = 0;
                        try{
                           number = Integer.parseInt(scanner.nextLine());
                        }catch (NumberFormatException e){
                            break;
                        }
                        System.out.println("Podaj kod pocztwoy szukanego zoo");
                        String code = scanner.nextLine();
                        List<Zoo> zooByPostalCode=zooService.selectZooByAddress(street, number, code);
                        zooByPostalCode.forEach(System.out::println);
                        break;
                    }
                    case 6: {
                        int id = 0;
                        int number = 0;
                        try{
                            System.out.println("Zoo id");
                            id = Integer.parseInt(scanner.nextLine());
                            System.out.println("Nowa liczba pracowników");
                            number = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Nie wykonano");
                            break;
                        }
                        zooService.updateNumberOfWorkers(number, id);
                        break;
                    }
                    case 7: {
                        List<Zoo> allZoo=zooService.getAllZoo();
                        allZoo.forEach(System.out::println);
                        break;
                    }
                    default:{
                        System.out.println("Nieprawidłowa akcja");
                    }
                }
            }while(choose != 0);
        }



    }
}
