package com.company.Parte2;

import javax.swing.JOptionPane;

public class PruebaFiguras {

    public static void main(String[] args) {
        String[] opciones = {"Círculo", "Rectángulo", "Cuadrado", "Triángulo Rectángulo"};
        String figuraSeleccionada = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione una figura geométrica:",
                "Figuras Geométricas",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if ("Círculo".equals(figuraSeleccionada)) {
            double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el radio del círculo:"));
            Circulo circulo = new Circulo(radio);
            JOptionPane.showMessageDialog(null, "Área: " + circulo.calcularArea() +
                    "\nPerímetro: " + circulo.calcularPerimetro());
        } else if ("Rectángulo".equals(figuraSeleccionada)) {
            double baseR = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la base del rectángulo:"));
            double alturaR = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la altura del rectángulo:"));
            Rectangulo rectangulo = new Rectangulo(baseR, alturaR);
            JOptionPane.showMessageDialog(null, "Área: " + rectangulo.calcularArea() +
                    "\nPerímetro: " + rectangulo.calcularPerimetro());
        } else if ("Cuadrado".equals(figuraSeleccionada)) {
            double lado = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el lado del cuadrado:"));
            Cuadrado cuadrado = new Cuadrado(lado);
            JOptionPane.showMessageDialog(null, "Área: " + cuadrado.calcularArea() +
                    "\nPerímetro: " + cuadrado.calcularPerimetro());
        } else if ("Triángulo Rectángulo".equals(figuraSeleccionada)) {
            double baseT = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la base del triángulo rectángulo:"));
            double alturaT = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la altura del triángulo rectángulo:"));
            TrianguloRectangulo triangulo = new TrianguloRectangulo(baseT, alturaT);
            JOptionPane.showMessageDialog(null, "Área: " + triangulo.calcularArea() +
                    "\nPerímetro: " + triangulo.calcularPerimetro() +
                    "\nHipotenusa: " + triangulo.calcularHipotenusa());
        } else {
            JOptionPane.showMessageDialog(null, "Opción no válida.");
        }
    }
}