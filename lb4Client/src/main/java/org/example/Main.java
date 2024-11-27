package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    /*public static void main(String[] args) {
        try
        {
            ServiceService client = new ServiceService();

            Service ws = client.getServicePort();
            List<String> files=ws.getFiles().getItem();
            files.forEach(System.out::println);

            System.out.println(ws.getFileContent("bio.txt"));
        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }*/
    private JFrame frame;
    private JList<String> list;
    private JTextArea textArea;
    private DefaultListModel<String> listModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().initialize());
    }

    public void initialize() {
        frame = new JFrame("Лб2Общее");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        list.addListSelectionListener(e -> onListItemSelected());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(list), BorderLayout.WEST);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.getContentPane().add(panel);

        fillList();

        frame.setVisible(true);
    }

    private void fillList() {
        try
        {
            ServiceService client = new ServiceService();

            Service ws = client.getServicePort();
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

    private void onListItemSelected() {
        String selectedItem = list.getSelectedValue();
        if (selectedItem != null) {
            try
            {
                ServiceService client = new ServiceService();

                Service ws = client.getServicePort();
                textArea.setText(ws.getFileContent(selectedItem));
            }
            catch(Exception exp) {
                textArea.setText(exp.getMessage());
                System.out.println(exp.getMessage());
            }
        }
    }
}