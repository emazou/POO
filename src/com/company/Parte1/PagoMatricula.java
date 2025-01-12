package com.company.Parte1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.text.NumberFormat;
import java.util.Locale;

public class PagoMatricula extends JFrame {
    private JTextField numeroInscripcionField;
    private JTextField nombresField;
    private JTextField patrimonioField;
    private JComboBox estratoCombo;
    private JTextArea resultadoArea;

    public PagoMatricula() {
        setTitle("Calculadora de Pago de Matrícula");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Datos del Estudiante",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
        ));

        formPanel.add(crearLabelConEstilo("Número de Inscripción:"));
        numeroInscripcionField = crearCampoTexto();
        formPanel.add(numeroInscripcionField);

        formPanel.add(crearLabelConEstilo("Nombres:"));
        nombresField = crearCampoTexto();
        formPanel.add(nombresField);

        formPanel.add(crearLabelConEstilo("Patrimonio ($):"));
        patrimonioField = crearCampoTexto();
        formPanel.add(patrimonioField);

        formPanel.add(crearLabelConEstilo("Estrato Social:"));
        String[] estratos = {"1", "2", "3", "4", "5", "6"};
        estratoCombo = new JComboBox(estratos);
        estratoCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(estratoCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton calcularButton = new JButton("Calcular Pago");
        calcularButton.setFont(new Font("Arial", Font.BOLD, 14));
        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularPago();
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
                        "Resultado del Cálculo",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Arial", Font.BOLD, 14)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        resultadoArea.setText("Complete el formulario y presione Calcular Pago");

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

    private void calcularPago() {
        try {
            if (numeroInscripcionField.getText().trim().isEmpty() ||
                    nombresField.getText().trim().isEmpty() ||
                    patrimonioField.getText().trim().isEmpty()) {
                resultadoArea.setText("Error: Todos los campos son obligatorios");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            String numeroInscripcion = numeroInscripcionField.getText();
            String nombres = nombresField.getText();
            double patrimonio = Double.parseDouble(patrimonioField.getText());
            int estrato = Integer.parseInt((String)estratoCombo.getSelectedItem());

            double pagoBase = 50000;
            double incremento = 0;

            if (patrimonio > 2000000 && estrato > 3) {
                incremento = patrimonio * 0.03;
            }

            double pagoTotal = pagoBase + incremento;

            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

            StringBuilder resultado = new StringBuilder();
            resultado.append("RESUMEN DE PAGO DE MATRÍCULA\n");
            resultado.append("================================\n\n");
            resultado.append("Número de Inscripción: ").append(numeroInscripcion).append("\n");
            resultado.append("Nombre del Estudiante: ").append(nombres).append("\n");
            resultado.append("Patrimonio: ").append(formatoMoneda.format(patrimonio)).append("\n");
            resultado.append("Estrato Social: ").append(estrato).append("\n\n");
            resultado.append("Pago Base: ").append(formatoMoneda.format(pagoBase)).append("\n");

            if (incremento > 0) {
                resultado.append("Incremento (3%): ").append(formatoMoneda.format(incremento)).append("\n");
            }

            resultado.append("\nTOTAL A PAGAR: ").append(formatoMoneda.format(pagoTotal));

            resultadoArea.setText(resultado.toString());
            resultadoArea.setForeground(new Color(0, 100, 0));

        } catch (NumberFormatException ex) {
            resultadoArea.setText("Error: El patrimonio debe ser un número válido");
            resultadoArea.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        numeroInscripcionField.setText("");
        nombresField.setText("");
        patrimonioField.setText("");
        estratoCombo.setSelectedIndex(0);
        resultadoArea.setText("Complete el formulario y presione Calcular Pago");
        resultadoArea.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PagoMatricula ventana = new PagoMatricula();
                ventana.setVisible(true);
            }
        });
    }
}