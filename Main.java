import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FreeMealList freeMealList = new FreeMealList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Tambah Korban");
            System.out.println("2. Tambah Korban dari File");
            System.out.println("3. Tampilkan Daftar Korban");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Masukkan nama korban: ");
                    String name = scanner.nextLine();
                    System.out.print("Masukkan umur korban: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan status korban (contoh: 'terdampak bencana'): ");
                    String status = scanner.nextLine();

                    Person person = new Person(name, age, status);
                    freeMealList.addRecipient(person);
                    System.out.println("Korban berhasil ditambahkan!");
                    break;

                case 2:
                    System.out.print("Masukkan path file D:\\Afrahh\\Semester 5\\OOP\\uas\\korban.txt: ");
                    String filePath = scanner.nextLine();
                    freeMealList.addRecipientsFromFile(filePath);
                    break;

                case 3:
                    freeMealList.printRecipients();
                    break;

                case 4:
                    System.out.println("Program selesai. Terima kasih!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }
}

