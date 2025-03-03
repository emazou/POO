package com.main.java.view;

import com.main.java.controller.PersonManager;
import com.main.java.model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class CRUDForm extends JFrame {
    private final PersonManager manager;
    private final JTextField fieldId;
    private final JTextField fieldName;
    private final JTextField fieldAge;
    private final JTextArea textArea;

    public CRUDForm() {
        manager = new PersonManager();

        setTitle("CRUD Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        fieldId = new JTextField(5);
        fieldName = new JTextField(10);
        fieldAge = new JTextField(5);
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);

        JButton btnCreate = new JButton("Create");
        JButton btnRead = new JButton("Read");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        add(new JLabel("ID:"));
        add(fieldId);
        add(new JLabel("Name:"));
        add(fieldName);
        add(new JLabel("Age:"));
        add(fieldAge);
        add(btnCreate);
        add(btnRead);
        add(btnUpdate);
        add(btnDelete);
        add(new JScrollPane(textArea));

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRUDForm.this.handleCreate(e);
            }
        });
        btnRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRUDForm.this.handleRead(e);
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                CRUDForm.this.handleUpdate(e1);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRUDForm.this.handleDelete(e);
            }
        });
    }

    private void handleCreate(ActionEvent e) {
        try {
            int id = Integer.parseInt(fieldId.getText());
            String name = fieldName.getText();
            int age = Integer.parseInt(fieldAge.getText());
            Person person = new Person(id, name, age);
            manager.createPerson(person);
            showMessage("Person created successfully.");
            clearFields();
        } catch (IOException ex) {
            showMessage("Error creating person.");
        }
    }

    private void handleRead(ActionEvent e) {
        try {
            List<Person> persons = manager.readPersons();
            textArea.setText("");
            for (Person person : persons) {
                textArea.append(person.toString() + "\n");
            }
        } catch (IOException ex) {
            showMessage("Error reading data.");
        }
    }

    private void handleUpdate(ActionEvent e) {
        try {
            int id = Integer.parseInt(fieldId.getText());
            String name = fieldName.getText();
            int age = Integer.parseInt(fieldAge.getText());
            Person person = new Person(id, name, age);
            manager.updatePerson(person);
            showMessage("Person updated successfully.");
            clearFields();
        } catch (IOException ex) {
            showMessage("Error updating person.");
        }
    }

    private void handleDelete(ActionEvent e) {
        try {
            int id = Integer.parseInt(fieldId.getText());
            manager.deletePerson(id);
            showMessage("Person deleted successfully.");
            clearFields();
        } catch (IOException ex) {
            showMessage("Error deleting person.");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void clearFields() {
        fieldId.setText("");
        fieldName.setText("");
        fieldAge.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CRUDForm().setVisible(true);
            }
        });
    }
}
