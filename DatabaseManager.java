import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Base64;

public class DatabaseManager {

    private static final String DATA_FOLDER = "NEXORA_Data";

    // Create storage folder automatically
    static {
        try {
            Files.createDirectories(Paths.get(DATA_FOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get file for each module
    private static Path getFile(String module) {

        String fileName = module.replaceAll(
                "[^a-zA-Z0-9]", "_");

        return Paths.get(
                DATA_FOLDER,
                fileName + ".csv");
    }

    // Save table data permanently
    public static void saveTable(
            String module,
            DefaultTableModel model) throws IOException {

        Path file = getFile(module);

        try (BufferedWriter writer = Files.newBufferedWriter(
                file,
                StandardCharsets.UTF_8)) {

            for (int row = 0;
                    row < model.getRowCount();
                    row++) {

                StringBuilder line = new StringBuilder();

                for (int col = 0;
                        col < model.getColumnCount();
                        col++) {

                    Object value = model.getValueAt(row, col);

                    String text = value == null
                            ? ""
                            : value.toString();

                    String encoded =
                            Base64.getEncoder().encodeToString(
                                    text.getBytes(
                                            StandardCharsets.UTF_8));

                    if (col > 0) {
                        line.append(",");
                    }

                    line.append(encoded);
                }

                writer.write(line.toString());
                writer.newLine();
            }
        }

        System.out.println(
                module + " saved successfully!");
    }

    // Load previously saved data
    public static boolean loadTable(
            String module,
            DefaultTableModel model) throws IOException {

        Path file = getFile(module);

        if (!Files.exists(file)) {
            return false;
        }

        model.setRowCount(0);

        try (BufferedReader reader = Files.newBufferedReader(
                file,
                StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] encodedValues =
                        line.split(",", -1);

                int expectedColumns =
                        model.getColumnCount();

                // Skip records with more columns than the new table
                if (encodedValues.length > expectedColumns) {
                    continue;
                }

                // Create a row with the required number of columns
                Object[] row = new Object[expectedColumns];

                for (int i = 0; i < expectedColumns; i++) {
                    row[i] = "";
                }

                int columnsToLoad = encodedValues.length;

                /*
                 * Faculty migration:
                 *
                 * Old Faculty format:
                 * ID, Name, Department, Status
                 *
                 * New Faculty format:
                 * Faculty ID, Full Name, Department,
                 * Designation, Email, Phone
                 *
                 * Preserve the first three fields.
                 * Leave the new fields blank.
                 */
                if (module.equals("Faculty Management")
                        && encodedValues.length == 4
                        && expectedColumns == 6) {

                    columnsToLoad = 3;
                }

                // Decode and load saved values
                for (int i = 0; i < columnsToLoad; i++) {

                    byte[] decoded =
                            Base64.getDecoder().decode(
                                    encodedValues[i]);

                    row[i] = new String(
                            decoded,
                            StandardCharsets.UTF_8);
                }

                model.addRow(row);
            }
        }

        System.out.println(
                module + " loaded successfully!");

        return true;
    }
}