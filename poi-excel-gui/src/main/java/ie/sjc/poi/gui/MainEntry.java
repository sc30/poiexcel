package ie.sjc.poi.gui;

import ie.sjc.poi.ExcelUrlValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainEntry extends JPanel {

    JFileChooser chooser;

    char columnValue = 'A';
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
        JFrame frame = new JFrame("Excel处理器，制作 by QQ:79836305有相关需求请联系为您制作");
        frame.setSize(700, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainEntry());
        frame.setVisible(true);
    }

    public MainEntry() {
        setLayout(null);
        final JLabel excelLocationLabel = new JLabel("Excel文件位置:");
        excelLocationLabel.setBounds(10,20,260,25);
        add(excelLocationLabel);

        final JTextField excelLocationField = new JTextField(20);
        excelLocationField.setBounds(300,20,350,25);
        add(excelLocationField);

        final JLabel columnLabel = new JLabel("Excel包含网络地址的列，仅支持A-Z列:");
        columnLabel.setBounds(10,50,260,25);
        add(columnLabel);

        final JComboBox columnComboBox = new JComboBox(columns);
        columnComboBox.setBounds(300,50,350,25);
        add(columnComboBox);

        final JLabel processRowLabel = new JLabel("每处理:");
        processRowLabel.setBounds(10,80,50,25);
        add(processRowLabel);

        final JTextField processRowField = new JTextField(20);
        processRowField.setBounds(70,80,50,25);
        processRowField.setText("10");
        add(processRowField);

        final JLabel restLabel = new JLabel("行，休息时间为秒(s)：");
        restLabel.setBounds(130,80,150,25);
        add(restLabel);

        final JTextField waitTimeField = new JTextField(20);
        waitTimeField.setBounds(290,80,30,25);
        waitTimeField.setText("3");
        add(waitTimeField);

        JButton processButton = new JButton("开始处理");
        processButton.setBounds(10, 110, 100, 25);
        add(processButton);

        JButton selectExcelButton = new JButton("选择Excel文件");
        selectExcelButton.setBounds(140, 110, 140, 25);
        add(selectExcelButton);

        JTextArea textArea = new JTextArea(2, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.append("1. 此版本仅支持处理单个xlsx文件，如果不为xlsx文件请先转换为xlsx格式。 \n" +
                "2. 此版本仅能查找k3与sooxie网站的商品是否可以下架，如有需求，后续版本可以添加。 \n" +
                "3. 请确保xlsx文件的最后一列为网页链接，不然程序会出错。\n" +
                "4. 已有[文件前缀].处理完成.xlsx文件的话，再次处理此xlsx文件会更新已经存在的处理完成.xlsx。");
        scrollPane.setBounds(50, 140, 600, 100);
        add(scrollPane);

        chooser = new JFileChooser();
        chooser.setDialogTitle("请选择xlsx结尾的文件");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new ExtensionFileFilter("xlsx"));
        chooser.setApproveButtonText("确定选择Excel文件");
        chooser.setApproveButtonMnemonic('e');
        chooser.setApproveButtonToolTipText("确定选择Excel文件");

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

                int processRow = Integer.parseInt(processRowField.getText());
                int waitTimeInSeconds = Integer.parseInt(waitTimeField.getText());
                ExcelUrlValidator excelUrlValidator = new ExcelUrlValidator(processRow, waitTimeInSeconds);
                try {
                    String newFileLocation = excelUrlValidator.check(excelLocation, col);
                    JOptionPane.showMessageDialog(null, "处理完成！新文件在\n" + newFileLocation);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
