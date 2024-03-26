import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cantidad_Ventas_Por_Producto {

    public static void main(String[] args) {
        String ventasFilePath = "C:\\Users\\Administrator\\Documents\\Entrega_1\\Consolidado_Ventas.csv";
        String diccionarioFilePath = "C:\\Users\\Administrator\\Documents\\Entrega_1\\Base_Products.csv";
        String outputFilePath = "C:\\Users\\Administrator\\Documents\\Entrega_1\\Consolidado_Ventas_Con_Producto.csv";

        Map<String, String> productMap = readProductMap(diccionarioFilePath);
        List<String[]> ventasData = readCSVFile(ventasFilePath);

        for (String[] venta : ventasData) {
            String idProducto = venta[2];
            String producto = productMap.get(idProducto);
            venta[venta.length - 1] = producto != null ? producto : "PRODUCTO NO ENCONTRADO";
        }

        System.out.println("Tabla final:");
        for (String[] row : ventasData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }

        saveToCSV(outputFilePath, ventasData);
    }

    public static Map<String, String> readProductMap(String filePath) {
        Map<String, String> productMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    productMap.put(fields[0], fields[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productMap;
    }

    public static List<String[]> readCSVFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                data.add(fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveToCSV(String filePath, List<String[]> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    writer.append(row[i]);
                    if (i < row.length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            System.out.println("Archivo guardado en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

