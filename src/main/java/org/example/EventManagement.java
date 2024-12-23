import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.util.Units;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventManagement {

    // Data structure to store events
    static class Event {
        String name;
        String date;
        String location;
        String posterPath;
        String description;

        public Event(String name, String date, String location, String posterPath, String description) {
            this.name = name;
            this.date = date;
            this.location = location;
            this.posterPath = posterPath;
            this.description = description;
        }

        public Object[] toRow() {
            return new Object[]{name, date, location};
        }

        public String getFormattedDate() {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date parsedDate = inputFormat.parse(date);
                SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
                return outputFormat.format(parsedDate);
            } catch (Exception e) {
                return date; // fallback to original format if parsing fails
            }
        }
    }

    private ArrayList<Event> events = new ArrayList<>();
    private DefaultTableModel tableModel;
    private XWPFDocument document = new XWPFDocument();
    private String wordFilePath = "Event_Details.docx";

    public EventManagement() {
        // Create the GUI
        JFrame frame = new JFrame("Event Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Table to display events
        String[] columnNames = {"Event Name", "Date", "Location"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField descriptionField = new JTextField();
        JLabel imageLabel = new JLabel();
        JButton uploadImageButton = new JButton("Upload Poster");
        JButton saveToWordButton = new JButton("Save to Word");

        inputPanel.add(new JLabel("Nama Event:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Tanggal (DD-MM-YYYY):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Lokasi Event:"));
        inputPanel.add(locationField);
        inputPanel.add(new JLabel("Deskripsi Singkat:"));
        inputPanel.add(descriptionField);
        inputPanel.add(uploadImageButton);
        inputPanel.add(imageLabel);
        inputPanel.add(saveToWordButton);

        // Buttons
        JButton addButton = new JButton("Tambah Event");
        JButton deleteButton = new JButton("Hapus Event");
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        // Add event listener for adding events
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String date = dateField.getText();
                    String location = locationField.getText();
                    String description = descriptionField.getText();
                    String posterPath = imageLabel.getText();

                    if (name.isEmpty() || date.isEmpty() || location.isEmpty() || posterPath.isEmpty()) {
                        throw new Exception("Semua kolom harus diisi, termasuk poster!");
                    }

                    // Validate date format
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.setLenient(false);
                    try {
                        dateFormat.parse(date);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Format tanggal salah! Harus dalam format DD-MM-YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Stop the event addition process
                    }

                    // Add to table
                    Event event = new Event(name, date, location, posterPath, description);
                    events.add(event);
                    tableModel.addRow(event.toRow());

                    nameField.setText("");
                    dateField.setText("");
                    locationField.setText("");
                    descriptionField.setText("");
                    imageLabel.setIcon(null);
                    imageLabel.setText("");

                    updateWordDocument();

                    JOptionPane.showMessageDialog(frame, "Event berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add event listener for deleting events
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    events.remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                    updateWordDocument();
                    JOptionPane.showMessageDialog(frame, "Event berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Pilih event yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add event listener for uploading image
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getPath();
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize image
                    imageLabel.setIcon(new ImageIcon(image));
                    imageLabel.setText(imagePath);
                    imageLabel.revalidate();
                    imageLabel.repaint();
                }
            }
        });

        // Save to Word Button Action
        saveToWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateWordDocument();
                JOptionPane.showMessageDialog(frame, "Dokumen Word berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void updateWordDocument() {
        try {
            document = new XWPFDocument();

            for (Event event : events) {
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(24);
                titleRun.setFontFamily("Calibri");
                titleRun.setColor("4A90E2"); // Set color to blue
                titleRun.setText(event.name);

                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("Kapan Eventnya: " + event.getFormattedDate());
                run.addBreak();
                run.setText("Lokasi Event: " + event.location);
                run.addBreak();
                run.setText("Deskripsi: " + event.description);
                run.addBreak();

                if (!event.posterPath.isEmpty()) {
                    File imageFile = new File(event.posterPath);
                    try (FileInputStream is = new FileInputStream(imageFile)) {
                        XWPFRun imageRun = document.createParagraph().createRun();
                        imageRun.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imageFile.getName(), Units.toEMU(400), Units.toEMU(300));
                    }
                }
            }

            try (FileOutputStream out = new FileOutputStream(wordFilePath)) {
                document.write(out);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EventManagement::new);
    }
}
