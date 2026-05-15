package util;

import model.Contact;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
// Utility class for exporting contact data
// Supports CSV format — compatible with Excel and Google Sheets
// Developed by Eman Ahmed

public class ExportUtil {

    public static void exportToCSV(JFrame parent, List<Contact> contacts) {

        if (contacts == null || contacts.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                    "No contacts to export.",
                    "Export CSV", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Contacts as CSV");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        chooser.setSelectedFile(new java.io.File("contacts_" + timestamp + ".csv"));

        int result = chooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new java.io.File(file.getAbsolutePath() + ".csv");
        }

        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Name,Phone Number\n");
            for (Contact c : contacts) {
                fw.write(c.getName() + "," + c.getPhoneNumber() + "\n");
            }
            JOptionPane.showMessageDialog(parent,
                    "Exported " + contacts.size() + " contact(s) to:\n" + file.getAbsolutePath(),
                    "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to export: " + ex.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
