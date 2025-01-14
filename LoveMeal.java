import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Person {
    private String name;
    private int age;
    private String status;

    public Person(String name, int age, String status) {
        this.name = name;
        this.age = age;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Nama: " + name + ", Umur: " + age + ", Status: " + status;
    }
}

class FreeMealList {
    private Queue<Person> recipients;

    public FreeMealList() {
        recipients = new LinkedList<>();
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

    public Person processRecipient() {
        return recipients.poll();
    }

    public int getRecipientCount() {
        return recipients.size();
    }

    public boolean isEmpty() {
        return recipients.isEmpty();
    }
}

 class Main {
    public static Map<String, Integer> readDataFromFile(String fileName) {
        Map<String, Integer> donasiData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || !line.contains(":")) {
                    continue;
                }

                String[] data = line.split(":");
                if (data.length != 2) {
                    System.out.println("Format tidak valid: " + line);
                    continue;
                }

                String name = data[0].trim(); // Mengambil nama
                String amountStr = data[1].replace("Rp", "").replace(".", "").trim();

                try {
                    int amount = Integer.parseInt(amountStr); // Parsing jumlah donasi
                    donasiData.put(name, amount);
                } catch (NumberFormatException e) {
                    System.out.println("Format jumlah donasi tidak valid untuk: " + name);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return donasiData;
    }

    public static void saveDataToFile(Map<String, Integer> donasiData, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : donasiData.entrySet()) {
                bw.write(entry.getKey() + ": Rp " + String.format("%,d", entry.getValue()).replace(",", "."));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    public static String formatRupiah(int amount) {
        return "Rp " + String.format("%,d", amount).replace(",", ".");
    }

    public static void main(String[] args) {
        FreeMealList freeMealList = new FreeMealList();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama file untuk membaca data donasi (contoh: list_donatur.txt): ");
        String donasiFileName = scanner.nextLine();
        Map<String, Integer> donasiData = readDataFromFile(donasiFileName);
        int totalDonasi = donasiData.values().stream().mapToInt(Integer::intValue).sum();
        int costPerMeal = 35000;

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Menu Korban Kelaparan");
            System.out.println("2. Menu Donasi");
            System.out.println("3. Penyaluran Donasi");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Menu Korban Kelaparan ---");
                    System.out.println("1. Tambah Korban");
                    System.out.println("2. Tambah Korban dari File");
                    System.out.println("3. Tampilkan Daftar Korban");
                    System.out.print("Pilih opsi: ");
                    int subChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (subChoice) {
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
                            System.out.print("Masukkan path file (contoh: D:\\Afrahh\\Semester 5\\OOP\\uas\\korban.txt): ");
                            String filePath = scanner.nextLine();
                            freeMealList.addRecipientsFromFile(filePath);
                            break;

                        case 3:
                            freeMealList.printRecipients();
                            break;

                        default:
                            System.out.println("Opsi tidak valid di menu korban kelaparan.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- Menu Donasi ---");
                    System.out.println("1. Tampilkan Data Donasi");
                    System.out.println("2. Tambah Donasi");
                    System.out.print("Pilih opsi: ");
                    int donasiChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (donasiChoice) {
                        case 1:
                            System.out.println("Data Donasi Saat Ini:");
                            donasiData.forEach((donorName, amount) ->
                                System.out.println(donorName + " sudah mendonasikan " + formatRupiah(amount))
                            );
                            break;

                        case 2:
                            System.out.print("Masukkan nama Anda: ");
                            String donorName = scanner.nextLine();
                            System.out.print("Masukkan jumlah donasi Anda: Rp ");
                            int donasiAmount = scanner.nextInt();
                            donasiData.put(donorName, donasiData.getOrDefault(donorName, 0) + donasiAmount);
                            totalDonasi += donasiAmount;
                            saveDataToFile(donasiData, donasiFileName);
                            System.out.println("Terima kasih, " + donorName + "! Donasi Anda sebesar " + formatRupiah(donasiAmount) + " telah diterima.");
                            break;

                        default:
                            System.out.println("Opsi tidak valid di menu donasi.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Penyaluran Donasi ---");
                    System.out.println("Total donasi tersedia: " + formatRupiah(totalDonasi));

                    if (freeMealList.isEmpty()) {
                        System.out.println("Tidak ada korban dalam daftar untuk menerima makanan.");
                    } else {
                        int availableMeals = totalDonasi / costPerMeal;
                        int mealsProvided = Math.min(availableMeals, freeMealList.getRecipientCount());

                        if (mealsProvided > 0) {
                            for (int i = 0; i < mealsProvided; i++) {
                                Person servedPerson = freeMealList.processRecipient();
                                System.out.println("Orang berikut telah mendapatkan makanan: " + servedPerson);
                            }

                            int amountUsed = mealsProvided * costPerMeal;
                            totalDonasi -= amountUsed;
                            saveDataToFile(donasiData, donasiFileName);

                            System.out.println("Sebanyak " + mealsProvided + " makanan telah disalurkan kepada para korban.");
                            System.out.println("Sisa donasi: " + formatRupiah(totalDonasi));
                        } else {
                            System.out.println("Donasi tidak mencukupi untuk memberikan makanan kepada para korban.");
                        }
                    }
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
