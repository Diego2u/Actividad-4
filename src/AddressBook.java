import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contactos;
    private String archivo;

    public AddressBook(String archivo) {
        this.contactos = new HashMap<>();
        this.archivo = archivo;
        load(); // Carga los contactos al iniciar
    }

    // Cargar contactos del archivo en formato CSV (Elaborado con videos de youtube)
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    contactos.put(partes[0].trim(), partes[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar contactos: " + e.getMessage());
        }
    }

    // archivo CSV (Obtenido de youtube)
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Map.Entry<String, String> entry : contactos.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar contactos: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contactos.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String numero, String nombre) {
        contactos.put(numero, nombre);
        save();
        System.out.println("Contacto creado: " + numero + " : " + nombre);
    }

    public void delete(String numero) {
        if (contactos.remove(numero) != null) {
            save();
            System.out.println("Contacto eliminado: " + numero);
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook("contactos.csv");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
                addressBook.list();
            } else if (opcion.equals("2")) {
                System.out.print("Ingrese el número telefónico del contacto: ");
                String numero = scanner.nextLine();
                System.out.print("Ingrese el nombre del contacto: ");
                String nombre = scanner.nextLine();
                addressBook.create(numero, nombre);
            } else if (opcion.equals("3")) {
                System.out.print("Ingrese el número de contacto a eliminar: ");
                String numero = scanner.nextLine();
                addressBook.delete(numero);
            } else if (opcion.equals("4")) {
                break;
            } else {
                System.out.println("Opción no válida.");
            }
        }

        scanner.close();
        System.out.println("Programa cerrado.");
    }
}