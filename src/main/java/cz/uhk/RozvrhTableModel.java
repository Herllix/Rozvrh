package cz.uhk;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RozvrhTableModel extends AbstractTableModel {
    private List<RozvrhovaAkce> data = new ArrayList<>();
    private final String[] columnNames = {"Předmět", "Den", "Od", "Do", "Učitel"};

    public void setData(List<RozvrhovaAkce> newData) {
        this.data = (newData != null) ? newData : Collections.emptyList();
        fireTableDataChanged();
    }

    public int getRowCount() {
        return data.size();
    }
    public int getColumnCount() {
        return columnNames.length;
    }
    public String getColumnName(int column) {
        return columnNames[column];
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount()) {
            return null;
        }
        RozvrhovaAkce akce = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return akce.getPredmet();
            case 1: return akce.getDen();
            case 2: return akce.getHodinaSkutOd();
            case 3: return akce.getHodinaSkutDo();
            case 4: return akce.getucitel();
            default: return null;
        }
    }
}
