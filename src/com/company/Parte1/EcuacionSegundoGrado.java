package com.company.Parte1;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EcuacionSegundoGrado extends JFrame {
    private JTextField campoA, campoB, campoC;
    private JTextArea resultadoArea;
    private JLabel ecuacionLabel;

    public EcuacionSegundoGrado() {
        setTitle("Calculadora - Ecuación de Segundo Grado");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        ecuacionLabel = new JLabel("ax² + bx + c = 0", JLabel.CENTER);
        ecuacionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(ecuacionLabel, BorderLayout.CENTER);

        JPanel coeficientesPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        coeficientesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Coeficientes",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
        ));

        coeficientesPanel.add(crearLabelConEstilo("Coeficiente a (x²):"));
        campoA = crearCampoTexto();
        coeficientesPanel.add(campoA);

        coeficientesPanel.add(crearLabelConEstilo("Coeficiente b (x):"));
        campoB = crearCampoTexto();
        coeficientesPanel.add(campoB);

        coeficientesPanel.add(crearLabelConEstilo("Coeficiente c:"));
        campoC = crearCampoTexto();
        coeficientesPanel.add(campoC);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton calcularButton = new JButton("Calcular");
        calcularButton.setFont(new Font("Arial", Font.BOLD, 14));
        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularSoluciones();
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
                        "Resultados",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Arial", Font.BOLD, 14)
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        resultadoArea.setText("Ingrese los coeficientes y presione Calcular");

        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.add(coeficientesPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(resultadoArea, BorderLayout.SOUTH);

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

    private void calcularSoluciones() {
        try {
            double a = Double.parseDouble(campoA.getText());
            double b = Double.parseDouble(campoB.getText());
            double c = Double.parseDouble(campoC.getText());

            if (a == 0) {
                resultadoArea.setText("Error: El coeficiente 'a' no puede ser cero.\n" +
                        "La ecuación debe ser de segundo grado.");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            double discriminante = Math.pow(b, 2) - 4 * a * c;

            StringBuilder resultado = new StringBuilder();
            resultado.append(String.format("Ecuación: %.2fx² + %.2fx + %.2f = 0\n\n", a, b, c));
            resultado.append(String.format("Discriminante = %.2f\n\n", discriminante));

            if (discriminante > 0) {
                double x1 = (-b + Math.sqrt(discriminante)) / (2 * a);
                double x2 = (-b - Math.sqrt(discriminante)) / (2 * a);
                resultado.append("La ecuación tiene dos soluciones reales:\n");
                resultado.append(String.format("x₁ = %.4f\n", x1));
                resultado.append(String.format("x₂ = %.4f", x2));
                resultadoArea.setForeground(new Color(0, 100, 0));
            } else if (discriminante == 0) {
                double x = -b / (2 * a);
                resultado.append("La ecuación tiene una solución real única:\n");
                resultado.append(String.format("x = %.4f", x));
                resultadoArea.setForeground(new Color(0, 100, 0));
            } else {
                double parteReal = -b / (2 * a);
                double parteImaginaria = Math.sqrt(-discriminante) / (2 * a);
                resultado.append("La ecuación tiene dos soluciones complejas:\n");
                resultado.append(String.format("x₁ = %.4f + %.4fi\n", parteReal, parteImaginaria));
                resultado.append(String.format("x₂ = %.4f - %.4fi", parteReal, parteImaginaria));
                resultadoArea.setForeground(new Color(0, 0, 150));
            }

            resultadoArea.setText(resultado.toString());

        } catch (NumberFormatException ex) {
            resultadoArea.setText("Error: Por favor, ingrese números válidos en todos los campos.");
            resultadoArea.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        campoA.setText("");
        campoB.setText("");
        campoC.setText("");
        resultadoArea.setText("Ingrese los coeficientes y presione Calcular");
        resultadoArea.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EcuacionSegundoGrado ventana = new EcuacionSegundoGrado();
                ventana.setVisible(true);
            }
        });
    }
}