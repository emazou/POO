package com.company.Parte1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.text.NumberFormat;
import java.util.Locale;

public class SalarioEmpleado extends JFrame {
    private JTextField nombreField;
    private JTextField salarioHoraField;
    private JTextField horasField;
    private JTextArea resultadoArea;

    public SalarioEmpleado() {
        setTitle("Calculadora de Salario Mensual");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Datos del Empleado",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
        ));

        formPanel.add(crearLabelConEstilo("Nombre:"));
        nombreField = crearCampoTexto();
        formPanel.add(nombreField);

        formPanel.add(crearLabelConEstilo("Salario por Hora ($):"));
        salarioHoraField = crearCampoTexto();
        formPanel.add(salarioHoraField);

        formPanel.add(crearLabelConEstilo("Horas Trabajadas:"));
        horasField = crearCampoTexto();
        formPanel.add(horasField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton calcularButton = new JButton("Calcular Salario");
        calcularButton.setFont(new Font("Arial", Font.BOLD, 14));
        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularSalario();
            }
        });

        JButton limpiarButton = new JButton("Limpiar");
        limpiarButton.setFont(new Font("Arial", Font.BOLD, 14));
        limpiarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        buttonPanel.add(calcularButton);
        buttonPanel.add(limpiarButton);

        resultadoArea = new JTextArea(6, 40);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultadoArea.setEditable(false);
        resultadoArea.setBackground(new Color(240, 240, 240));
        resultadoArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Resultado",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Arial", Font.BOLD, 14)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        resultadoArea.setText("Complete el formulario y presione Calcular Salario");

        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JLabel crearLabelConEstilo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        return campo;
    }

    private void calcularSalario() {
        try {
            if (nombreField.getText().trim().isEmpty() ||
                    salarioHoraField.getText().trim().isEmpty() ||
                    horasField.getText().trim().isEmpty()) {
                resultadoArea.setText("Error: Todos los campos son obligatorios");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            String nombre = nombreField.getText();
            double salarioHora = Double.parseDouble(salarioHoraField.getText());
            int horas = Integer.parseInt(horasField.getText());

            if (salarioHora <= 0 || horas <= 0) {
                resultadoArea.setText("Error: El salario por hora y las horas trabajadas deben ser valores positivos");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            double salarioMensual = salarioHora * horas;

            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

            StringBuilder resultado = new StringBuilder();
            resultado.append("RESUMEN DE SALARIO\n");
            resultado.append("==================\n\n");
            resultado.append("Nombre del Empleado: ").append(nombre).append("\n");

            if (salarioMensual > 450000) {
                resultado.append("Salario Mensual: ").append(formatoMoneda.format(salarioMensual)).append("\n");
                resultado.append("\nDetalles del cálculo:\n");
                resultado.append("Salario por Hora: ").append(formatoMoneda.format(salarioHora)).append("\n");
                resultado.append("Horas Trabajadas: ").append(horas).append("\n");
            }

            resultadoArea.setText(resultado.toString());
            resultadoArea.setForeground(new Color(0, 100, 0));

        } catch (NumberFormatException ex) {
            resultadoArea.setText("Error: Por favor, ingrese valores numéricos válidos para el salario y las horas");
            resultadoArea.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        nombreField.setText("");
        salarioHoraField.setText("");
        horasField.setText("");
        resultadoArea.setText("Complete el formulario y presione Calcular Salario");
        resultadoArea.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SalarioEmpleado ventana = new SalarioEmpleado();
                ventana.setVisible(true);
            }
        });
    }
}