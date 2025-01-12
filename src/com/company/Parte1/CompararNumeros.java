package com.company.Parte1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompararNumeros extends JFrame {
    private JTextField campoA;
    private JTextField campoB;
    private JLabel resultadoLabel;
    private JButton compararButton;
    private JButton limpiarButton;

    public CompararNumeros() {
        setTitle("Comparador de Números");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputPanel.add(new JLabel("Valor A:"));
        campoA = new JTextField();
        campoA.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(campoA);

        inputPanel.add(new JLabel("Valor B:"));
        campoB = new JTextField();
        campoB.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(campoB);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        compararButton = new JButton("Comparar");
        compararButton.setFont(new Font("Arial", Font.BOLD, 14));
        compararButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                compararNumeros();
            }
        });

        limpiarButton = new JButton("Limpiar");
        limpiarButton.setFont(new Font("Arial", Font.BOLD, 14));
        limpiarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        buttonPanel.add(compararButton);
        buttonPanel.add(limpiarButton);

        resultadoLabel = new JLabel("Ingrese los números y presione Comparar");
        resultadoLabel.setHorizontalAlignment(JLabel.CENTER);
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultadoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultadoLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void compararNumeros() {
        try {
            double a = Double.parseDouble(campoA.getText());
            double b = Double.parseDouble(campoB.getText());

            String resultado;
            if (a > b) {
                resultado = String.format("%.2f es mayor que %.2f", a, b);
            } else if (a == b) {
                resultado = String.format("%.2f es igual a %.2f", a, b);
            } else {
                resultado = String.format("%.2f es menor que %.2f", a, b);
            }

            resultadoLabel.setText(resultado);
            resultadoLabel.setForeground(new Color(0, 100, 0)); // Verde oscuro para el resultado

        } catch (NumberFormatException ex) {
            resultadoLabel.setText("Por favor, ingrese números válidos");
            resultadoLabel.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        campoA.setText("");
        campoB.setText("");
        resultadoLabel.setText("Ingrese los números y presione Comparar");
        resultadoLabel.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        // Asegurar que la interfaz se cree en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CompararNumeros ventana = new CompararNumeros();
                ventana.setVisible(true);
            }
        });
    }
}