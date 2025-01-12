package com.company.Parte1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.text.NumberFormat;
import java.util.Locale;

public class SalarioEmpleadoRetencion extends JFrame {
    private JTextField codigoField;
    private JTextField nombresField;
    private JTextField horasField;
    private JTextField valorHoraField;
    private JTextField retencionField;
    private JTextArea resultadoArea;

    public SalarioEmpleadoRetencion() {
        setTitle("Calculadora de Salario con Retención");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Datos del Empleado",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
        ));

        formPanel.add(crearLabelConEstilo("Código:"));
        codigoField = crearCampoTexto();
        formPanel.add(codigoField);

        formPanel.add(crearLabelConEstilo("Nombres:"));
        nombresField = crearCampoTexto();
        formPanel.add(nombresField);

        formPanel.add(crearLabelConEstilo("Horas Trabajadas:"));
        horasField = crearCampoTexto();
        formPanel.add(horasField);

        formPanel.add(crearLabelConEstilo("Valor Hora ($):"));
        valorHoraField = crearCampoTexto();
        formPanel.add(valorHoraField);

        formPanel.add(crearLabelConEstilo("Retención (%):"));
        retencionField = crearCampoTexto();
        formPanel.add(retencionField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton calcularButton = new JButton("Calcular");
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

        resultadoArea = new JTextArea(8, 40);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultadoArea.setEditable(false);
        resultadoArea.setBackground(new Color(240, 240, 240));
        resultadoArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Resultados",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Arial", Font.BOLD, 14)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        resultadoArea.setText("Complete el formulario y presione Calcular");

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
            if (codigoField.getText().trim().isEmpty() ||
                    nombresField.getText().trim().isEmpty() ||
                    horasField.getText().trim().isEmpty() ||
                    valorHoraField.getText().trim().isEmpty() ||
                    retencionField.getText().trim().isEmpty()) {
                resultadoArea.setText("Error: Todos los campos son obligatorios");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            String codigo = codigoField.getText();
            String nombres = nombresField.getText();
            int horas = Integer.parseInt(horasField.getText());
            double valorHora = Double.parseDouble(valorHoraField.getText());
            double porcentajeRetencion = Double.parseDouble(retencionField.getText());

            if (horas <= 0 || valorHora <= 0 || porcentajeRetencion < 0 || porcentajeRetencion > 100) {
                resultadoArea.setText("Error: Los valores ingresados no son válidos.\n" +
                        "- Las horas y el valor por hora deben ser positivos\n" +
                        "- La retención debe estar entre 0 y 100%");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            double salarioBruto = horas * valorHora;
            double retencion = salarioBruto * (porcentajeRetencion / 100);
            double salarioNeto = salarioBruto - retencion;

            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

            StringBuilder resultado = new StringBuilder();
            resultado.append("INFORMACIÓN DEL EMPLEADO\n");
            resultado.append("=======================\n\n");
            resultado.append("Código: ").append(codigo).append("\n");
            resultado.append("Nombre: ").append(nombres).append("\n\n");
            resultado.append("DETALLE DE SALARIO\n");
            resultado.append("==================\n");
            resultado.append("Salario Bruto: ").append(formatoMoneda.format(salarioBruto)).append("\n");
            resultado.append("Retención (").append(porcentajeRetencion).append("%): ");
            resultado.append(formatoMoneda.format(retencion)).append("\n");
            resultado.append("Salario Neto: ").append(formatoMoneda.format(salarioNeto));

            resultadoArea.setText(resultado.toString());
            resultadoArea.setForeground(new Color(0, 100, 0));

        } catch (NumberFormatException ex) {
            resultadoArea.setText("Error: Por favor, ingrese valores numéricos válidos");
            resultadoArea.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        codigoField.setText("");
        nombresField.setText("");
        horasField.setText("");
        valorHoraField.setText("");
        retencionField.setText("");
        resultadoArea.setText("Complete el formulario y presione Calcular");
        resultadoArea.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SalarioEmpleadoRetencion ventana = new SalarioEmpleadoRetencion();
                ventana.setVisible(true);
            }
        });
    }
}