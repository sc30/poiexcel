package ie.sjc.poi.gui;

import ie.sjc.poi.CheckUrlConnection;
import ie.sjc.poi.ExcelUrlValidator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainEntry {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Url in excel processor");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        final JLabel excelLocationLabel = new JLabel("excel location:");
        excelLocationLabel.setBounds(10,20,80,25);
        panel.add(excelLocationLabel);

        final JTextField excelLocationField = new JTextField(20);
        excelLocationField.setBounds(100,20,165,25);
        panel.add(excelLocationField);

        final JLabel columnLabel = new JLabel("Column:");
        columnLabel.setBounds(10,50,80,25);
        panel.add(columnLabel);

        final JTextField columnField = new JTextField(20);
        columnField.setBounds(100,50,165,25);
        panel.add(columnField);

        JButton processButton = new JButton("process");
        processButton.setBounds(10, 80, 80, 25);
        panel.add(processButton);

        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String excelLocation = excelLocationField.getText();
                int col = Integer.parseInt(columnField.getText());
                try {
                    ExcelUrlValidator.check(excelLocation, col);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
