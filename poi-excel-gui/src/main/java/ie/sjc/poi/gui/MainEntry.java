package ie.sjc.poi.gui;

import ie.sjc.poi.ExcelUrlValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainEntry extends JPanel {

    JFileChooser chooser;

    char columnValue;
    String[] columns = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
        "O","P","Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Url in excel processor");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainEntry());
        frame.setVisible(true);
    }

    public MainEntry() {
        setLayout(null);
        final JLabel excelLocationLabel = new JLabel("excel location:");
        excelLocationLabel.setBounds(10,20,160,25);
        add(excelLocationLabel);

        final JTextField excelLocationField = new JTextField(20);
        excelLocationField.setBounds(100,20,165,25);
        add(excelLocationField);

        final JLabel columnLabel = new JLabel("Column:");
        columnLabel.setBounds(10,50,160,25);
        add(columnLabel);

        final JComboBox columnComboBox = new JComboBox(columns);
        columnComboBox.setBounds(100,50,165,25);
        add(columnComboBox);

        JButton processButton = new JButton("process");
        processButton.setBounds(10, 80, 80, 25);
        add(processButton);

        JButton selectExcelButton = new JButton("select excel");
        selectExcelButton.setBounds(120, 80, 100, 25);
        add(selectExcelButton);

        chooser = new JFileChooser();
        chooser.setDialogTitle("select excel file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new ExtensionFileFilter("xlsx"));
        chooser.setApproveButtonText("approve button text");
        chooser.setApproveButtonMnemonic('e');
        chooser.setApproveButtonToolTipText("approve button tool tip");

        selectExcelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int retVal = chooser.showOpenDialog(MainEntry.this);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    String file = chooser.getSelectedFile().toString();
                    excelLocationField.setText(file);
                }
            }
        });

        columnComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                columnValue = String.class.cast(cb.getSelectedItem()).charAt(0);
            }
        });

        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String excelLocation = excelLocationField.getText();
                int col = columnValue - 65;
                try {
                    ExcelUrlValidator.check(excelLocation, col);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
