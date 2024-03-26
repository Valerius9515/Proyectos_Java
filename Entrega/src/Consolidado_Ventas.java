import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Consolidado_Ventas {

    public static void main(String[] args) {

        String folderPath = "C:\\Users\\Administrator\\Documents\\Entrega_1\\Vendor";
        String outputPath = "C:\\Users\\Administrator\\Documents\\Entrega_1\\Consolidado_Ventas.csv";

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        List<String[]> consolidatedData = new ArrayList<>();
        boolean isFirstFile = true;

        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                if (isFirstFile) {
                    readCSVFile(file.getAbsolutePath(), consolidatedData);
                    isFirstFile = false;
                } else {
                    readCSVFileWithoutHeader(file.getAbsolutePath(), consolidatedData);
                }
            }
        }


        for (String[] row : consolidatedData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }


        saveToCSV(outputPath, consolidatedData);
    }

    public static void readCSVFile(String filePath, List<String[]> consolidatedData) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;


            if ((line = br.readLine()) != null) {
                String[] header = line.split(",");
                consolidatedData.add(header);
            }


            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                consolidatedData.add(fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCSVFileWithoutHeader(String filePath, List<String[]> consolidatedData) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;


            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (!isFirstLine) {
                    String[] fields = line.split(",");
                    consolidatedData.add(fields);
                }
                isFirstLine = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToCSV(String filePath, List<String[]> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)))) {
            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(row[i]);
                    if (i < row.length - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            System.out.println("Tabla consolidada guardada en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
