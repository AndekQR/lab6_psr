import model.Zoo;

import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class EntryPoint {

    public static void main(String[] args) {

        FaunaService faunaService=new FaunaService();

        Scanner scanner=new Scanner(System.in);
        int choose;

        do {
            System.out.println("[0] - wyjście,\n " +
                    "[1] - Zapisz randomowe zoo,\n " +
                    "[2] - aktualizuj nazwę ulicy,\n " +
                    "[3] - usuń zoo,\n " +
                    "[4] - pobierz zoo po id,\n " +
                    "[5] - pobieranie złożone,\n " +
                    "[6] - przetwarzanie,\n ");
            try {
                choose=Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choose=999;
            }

            switch (choose) {
                case 1: {
                    try {
                        Zoo zoo=faunaService.saveZoo(ExampleData.getRandomZoo());
                        System.out.println("Dodano nowe zoo!");
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("Błąd zapisywania: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    try {
                        System.out.println("Id zoo do aktualizacji: ");
                        String id=scanner.nextLine();
                        System.out.println("Nowa nazwa ulicy: ");
                        String street=scanner.nextLine();
                        faunaService.updateZooStreet(id, street);
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("Błąd zapisywania: " + e.getMessage());
                    }
                    break;
                }
                case 3: {
                    System.out.println("Id zoo do usunięcia: ");
                    String id=scanner.nextLine();
                    faunaService.deleteZoo(id);
                    break;
                }
                case 4: {
                    System.out.println("Zoo id: ");
                    String reference=scanner.nextLine();
                    try {
                        ZooRefPair byId=faunaService.getById(reference);
                        if (byId.getRef().isPresent() && byId.getZoo().isPresent()) {
                            System.out.println(byId.getRef().get());
                            System.out.println(byId.getZoo().get());
                        } else {
                            System.out.println("Błąd odczytu");
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("Błąd odczytywania: " + e.getMessage());
                    }
                    break;
                }
                case 5: {
                    System.out.println("Postal code: ");
                    String code=scanner.nextLine();
                    try {
                        Optional<Zoo> zoosByPostalCode=faunaService.getZoosByPostalCode(code);
                        if (zoosByPostalCode.isPresent()) {
                            System.out.println(zoosByPostalCode.get());
                        } else {
                            System.out.println("Brak takiego zoo");
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("Błąd szukania: " + e.getMessage());
                    }
                    break;
                }
                case 6: {
                    try {
                        System.out.println("Ilość wszystkich dokumentów: "+faunaService.getZooCount());
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("Błąd szukania: " + e.getMessage());
                    }
                    break;
                }
                default: {
                    System.out.println("Nieprawidłowa akcja");
                }
            }
        } while (choose != 0);
    }
}
