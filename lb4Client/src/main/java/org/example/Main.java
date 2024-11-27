package org.example;

import org.example.cont.Service;
import org.example.cont.ServiceService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    private JFrame frame;
    private JList<String> list;
    private JTextArea textArea;
    private DefaultListModel<String> listModel;
    private JTextField expressionField; // Для ввода математического выражения
    private JLabel resultLabel; // Для отображения результата вычислений
    private final ServiceService client;
    private final Service ws;

    public Main(){
        client = new ServiceService();
        ws = client.getServicePort();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().initialize());
    }

    public void initialize() {
        frame = new JFrame("Лб2Общее");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        list.addListSelectionListener(e -> onListItemSelected());

        // Создание текстового поля для ввода математического выражения
        expressionField = new JTextField();
        expressionField.setPreferredSize(new Dimension(200, 30));

        // Создание метки для отображения результата вычислений
        resultLabel = new JLabel("Результат: ");

        // Создание кнопки для вычисления
        JButton calculateButton = new JButton("Вычислить");
        calculateButton.addActionListener(e -> calculateExpression());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(list), BorderLayout.WEST);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Введите выражение:"));
        inputPanel.add(expressionField);
        inputPanel.add(calculateButton);
        inputPanel.add(resultLabel);

        // Добавление панелей в окно
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);

        fillList();

        frame.setVisible(true);
    }

    private void fillList() {
        try
        {
            List<String> files=ws.getFiles().getItem();
            for(String fileName : files){
                listModel.addElement(fileName);
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
            listModel.addElement(exp.getMessage());
        }
    }

    private void calculateExpression() {
        String expression = expressionField.getText();
        try {
            double result = ws.calc(expression);
            resultLabel.setText("Результат: " + result);
        } catch (Exception e) {
            resultLabel.setText("Ошибка в выражении: "+e.getMessage());
        }
    }

    private void onListItemSelected() {
        String selectedItem = list.getSelectedValue();
        if (selectedItem != null) {
            try
            {
                textArea.setText(ws.getFileContent(selectedItem));
            }
            catch(Exception exp) {
                textArea.setText(exp.getMessage());
                System.out.println(exp.getMessage());
            }
        }
    }
}