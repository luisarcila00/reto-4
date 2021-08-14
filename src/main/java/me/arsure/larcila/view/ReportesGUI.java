package me.arsure.larcila.view;

import me.arsure.larcila.controller.ReportesController;
import me.arsure.larcila.model.vo.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ReportesGUI extends JFrame {

    private JTable tabla;
    private ReportesController controller;

    public ReportesGUI() {
        controller = new ReportesController();

        initUI();
        setSize(1024, 768);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setTitle("Reto 5 Misión TIC 2022 Ciclo 2 Grupo 18 Luis Arcila");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        var panel = new JPanel();
        getContentPane().add(panel, BorderLayout.PAGE_START);
        String[] optionsStrings = { "Selecciona una opción", "Ver los 10 lideres de proyecto que mas gastan",
                "Buscar proyectos financiados por un banco",
                "Ver total adeudado de cada proyecto filtrado por un límite inferior dado" };
        var comboBox = new JComboBox(optionsStrings);
        panel.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (comboBox.getSelectedIndex()) {
                    case 1:
                        cargarTablaLideresQueMasGastan();
                        break;
                    case 2:
                        cargarBancos();
                        break;
                    case 3:
                        cargarTotalAdeudado();
                        break;
                    default:
                        break;
                }                
            }
        });        
        tabla = new JTable();
        getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void cargarTablaLideresQueMasGastan() {
        try {
            var tableModel = new LideresQueMasGastanTableModel();

            var lista = controller.listarLideresQueMasGastan();

            tableModel.setData(lista);

            tabla.setModel(tableModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private class LideresQueMasGastanTableModel extends AbstractTableModel {
        private List<ComprasDeLiderVo> proyectos;

        public LideresQueMasGastanTableModel() {
            proyectos = new ArrayList<>();
        }

        public void setData(List<ComprasDeLiderVo> data) {
            proyectos = data;
            fireTableDataChanged();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 1:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Lider";
                case 1:
                    return "Valor";
            }

            return super.getColumnName(column);
        }

        @Override
        public int getRowCount() {
            return proyectos.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var proyecto = proyectos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return proyecto.getLider();
                case 1:
                    return String.format("$%,15.1f", proyecto.getValor());
            }
            return null;
        }

    }

    private void cargarBancos() {
        try {
            var tableModel = new BancosTableModel();
            var banco = JOptionPane.showInputDialog(this, "Ingrese nombre del banco:");
            var lista = controller.listarProyectosPorBanco(banco);
            tableModel.setData(lista);
            tabla.setModel(tableModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private class BancosTableModel extends AbstractTableModel {
        private List<ProyectoBancoVo> proyectos;

        public BancosTableModel() {
            proyectos = new ArrayList<>();
        }

        public void setData(List<ProyectoBancoVo> data) {
            proyectos = data;
            fireTableDataChanged();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Integer.class;
                case 5:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "ID";
                case 1:
                    return "Banco";
                case 2:
                    return "Constructora";
                case 3:
                    return "Ciudad";
                case 4:
                    return "Clasificación";
                case 5:
                    return "Estrato";
                case 6:
                    return "Lider";
            }

            return super.getColumnName(column);
        }

        @Override
        public int getRowCount() {
            return proyectos.size();
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var proyecto = proyectos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return proyecto.getId();
                case 1:
                    return proyecto.getBanco();
                case 2:
                    return proyecto.getConstructora();
                case 3:
                    return proyecto.getCiudad();
                case 4:
                    return proyecto.getClasificacion();
                case 5:
                    return proyecto.getEstrato();
                case 6:
                    return proyecto.getLider();
            }
            return null;
        }
    }

    private void cargarTotalAdeudado() {
        var valorIngresado = JOptionPane.showInputDialog(this, "Ingrese un limite:");
        try {
            var limite = Double.parseDouble(valorIngresado);
            try {
                var tableModel = new TotalAdeudadoTableModel();
                var lista = controller.listarTotalAdeudadoPorProyectoSuperioresAlLimite(limite);
                tableModel.setData(lista);
                tabla.setModel(tableModel);                                 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "El limite " + valorIngresado + " es un valor incorrecto", getTitle(),
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private class TotalAdeudadoTableModel extends AbstractTableModel {
        private List<DeudasPorProyectoVo> proyectos;

        public TotalAdeudadoTableModel() {
            proyectos = new ArrayList<>();
        }

        public void setData(List<DeudasPorProyectoVo> data) {
            proyectos = data;
            fireTableDataChanged();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return Integer.class;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "ID";
                case 1:
                    return "Valor";
            }

            return super.getColumnName(column);
        }

        @Override
        public int getRowCount() {
            return proyectos.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var proyecto = proyectos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return proyecto.getId();
                case 1:
                    return String.format("$%,15.1f", proyecto.getValor());
            }
            return null;
        }
    }
}