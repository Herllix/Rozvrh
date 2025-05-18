package cz.uhk;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.stream.Collectors;

public class RozvrhApp extends JFrame {
    private RozvrhTableModel tableModel;
    private JTable rozvrhTable;
    private JButton fetchButton;
    private RozvrhApi apiClient;
    private JComboBox budova;
    private JComboBox mistnost;

    private  String[] budovy = {"J", "A", "S"};
    private  Map<String, String[]> mistnostiBudov;

    private static final String DEFAULT_BUDOVA = "J";


    public RozvrhApp() {
        apiClient = new RozvrhApi();
        tableModel = new RozvrhTableModel();
        rozvrhTable = new JTable(tableModel);

        mistnostiBudov = new HashMap<>();
        mistnostiBudov.put("J", new String[]{"J1", "J10", "J11", "J12", "J13"});
        mistnostiBudov.put("A", new String[]{"A1", "AULA", "A10", "A11", "A12"});
        mistnostiBudov.put("S", new String[]{"S1", "S10", "S11", "S12", "S15"});

        budova = new JComboBox<>(budovy);
        mistnost = new JComboBox<>();

        budova.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selected = (String) budova.getSelectedItem();
                if(selected != null){
                    updateMistnosti(selected);
                }
            }
        });

        budova.setSelectedItem(DEFAULT_BUDOVA);
        updateMistnosti(DEFAULT_BUDOVA);


        fetchButton = new JButton("Načíst Rozvrh!");

        rozvrhTable.setFillsViewportHeight(true);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(rozvrhTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel upperPanel = new JPanel();
        upperPanel.add(budova);
        upperPanel.add(mistnost);
        upperPanel.add(fetchButton);
        add(upperPanel, BorderLayout.NORTH);

        fetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadScheduleData();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void loadScheduleData() {
        fetchButton.setEnabled(false);

        List<RozvrhovaAkce> fetchedData = apiClient.fetchScheduleData("https://stag-demo.uhk.cz/ws/services/rest2/rozvrhy/getRozvrhByMistnost?semestr=%25&budova="+budova.getSelectedItem()+"&mistnost="+mistnost.getSelectedItem()+"&outputFormat=JSON");
        List<RozvrhovaAkce> filteredData = new ArrayList<>();

        if(fetchedData != null){
            filteredData = fetchedData.stream()
                    .filter(akce ->{
                String typ = akce.gettypAkce();
                return "Cvičení".equals(typ) || "Přednáška".equals(typ);

            }).collect(Collectors.toList());
        }
        tableModel.setData(filteredData);
        fetchButton.setEnabled(true);
    }
    private void updateMistnosti(String selectedBuilding){
        String [] rooms = mistnostiBudov.get(selectedBuilding);
        mistnost.setModel(new DefaultComboBoxModel<>(rooms));
    }

    public static void main(String[] args) {
        RozvrhApp app = new RozvrhApp();
        app.setVisible(true);
    }

}
