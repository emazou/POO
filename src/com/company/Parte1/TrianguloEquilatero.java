package com.company.Parte1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.text.DecimalFormat;

public class TrianguloEquilatero extends JFrame {
    private JTextField ladoField;
    private JTextArea resultadoArea;
    private TrianguloPanel trianguloPanel;

    public TrianguloEquilatero() {
        setTitle("Calculadora - Triángulo Equilátero");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Datos del Triángulo",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
        ));

        formPanel.add(crearLabelConEstilo("Lado del Triángulo:"));
        ladoField = crearCampoTexto();
        formPanel.add(ladoField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton calcularButton = new JButton("Calcular");
        calcularButton.setFont(new Font("Arial", Font.BOLD, 14));
        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularPropiedades();
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

        trianguloPanel = new TrianguloPanel();
        trianguloPanel.setPreferredSize(new Dimension(300, 300));
        trianguloPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Visualización",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
        ));

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
        resultadoArea.setText("Ingrese el lado del triángulo y presione Calcular");

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(trianguloPanel, BorderLayout.CENTER);
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

    private void calcularPropiedades() {
        try {
            if (ladoField.getText().trim().isEmpty()) {
                resultadoArea.setText("Error: Debe ingresar el valor del lado");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            double lado = Double.parseDouble(ladoField.getText());

            if (lado <= 0) {
                resultadoArea.setText("Error: El lado debe ser un valor positivo");
                resultadoArea.setForeground(Color.RED);
                return;
            }

            double perimetro = 3 * lado;
            double altura = (Math.sqrt(3) / 2) * lado;
            double area = (lado * altura) / 2;

            DecimalFormat df = new DecimalFormat("#,##0.00");

            StringBuilder resultado = new StringBuilder();
            resultado.append("PROPIEDADES DEL TRIÁNGULO EQUILÁTERO\n");
            resultado.append("==================================\n\n");
            resultado.append("Lado: ").append(df.format(lado)).append(" unidades\n");
            resultado.append("Perímetro: ").append(df.format(perimetro)).append(" unidades\n");
            resultado.append("Altura: ").append(df.format(altura)).append(" unidades\n");
            resultado.append("Área: ").append(df.format(area)).append(" unidades²");

            resultadoArea.setText(resultado.toString());
            resultadoArea.setForeground(new Color(0, 100, 0));

            trianguloPanel.actualizarTriangulo(lado);

        } catch (NumberFormatException ex) {
            resultadoArea.setText("Error: Por favor, ingrese un valor numérico válido");
            resultadoArea.setForeground(Color.RED);
        }
    }

    private void limpiarCampos() {
        ladoField.setText("");
        resultadoArea.setText("Ingrese el lado del triángulo y presione Calcular");
        resultadoArea.setForeground(Color.BLACK);
        trianguloPanel.limpiar();
    }

    private class TrianguloPanel extends JPanel {
        private double lado = 0;

        public void actualizarTriangulo(double lado) {
            this.lado = lado;
            repaint();
        }

        public void limpiar() {
            this.lado = 0;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (lado > 0) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth() - 40;
                int height = getHeight() - 40;
                double escala = Math.min(width / lado, height / (lado * Math.sqrt(3) / 2));

                int ladoPixeles = (int)(lado * escala);
                int alturaPixeles = (int)(ladoPixeles * Math.sqrt(3) / 2);

                int[] xPoints = new int[3];
                int[] yPoints = new int[3];

                xPoints[0] = getWidth() / 2;
                yPoints[0] = 20;

                xPoints[1] = getWidth() / 2 - ladoPixeles / 2;
                yPoints[1] = 20 + alturaPixeles;

                xPoints[2] = getWidth() / 2 + ladoPixeles / 2;
                yPoints[2] = 20 + alturaPixeles;

                g2d.setColor(new Color(200, 230, 255));
                g2d.fillPolygon(xPoints, yPoints, 3);
                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawPolygon(xPoints, yPoints, 3);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TrianguloEquilatero ventana = new TrianguloEquilatero();
                ventana.setVisible(true);
            }
        });
    }
}