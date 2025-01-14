import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FreeMealList {
    private ArrayList<Person> recipients;

    public FreeMealList() {
        recipients = new ArrayList<>();
    }

    public void addRecipient(Person person) {
        recipients.add(person);
    }

    public void addRecipientsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0].trim();
                    int age = Integer.parseInt(data[1].trim());
                    String status = data[2].trim();
                    recipients.add(new Person(name, age, status));
                } else {
                    System.out.println("Baris tidak valid: " + line);
                }
            }
            System.out.println("Data dari file berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }

    public void printRecipients() {
        if (recipients.isEmpty()) {
            System.out.println("Belum ada korban yang terdaftar.");
        } else {
            System.out.println("Daftar korban yang akan mendapatkan makan gratis:");
            for (Person person : recipients) {
                System.out.println(person);
            }
        }
    }
}
