package com.main.java.controller;

import com.main.java.model.Person;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonManager {
    private final String filePath = "src/com/main/resources/data.txt";

    public void createPerson(Person person) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(person.toString());
        writer.newLine();
        writer.close();
    }

    public List<Person> readPersons() throws IOException {
        List<Person> persons = new ArrayList<Person>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            persons.add(Person.fromString(line));
        }
        reader.close();
        return persons;
    }

    public void updatePerson(Person updatedPerson) throws IOException {
        List<Person> persons = readPersons();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Person person : persons) {
            if (person.getId() == updatedPerson.getId()) {
                writer.write(updatedPerson.toString());
            } else {
                writer.write(person.toString());
            }
            writer.newLine();
        }
        writer.close();
    }

    public void deletePerson(int id) throws IOException {
        List<Person> persons = readPersons();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Person person : persons) {
            if (person.getId() != id) {
                writer.write(person.toString());
                writer.newLine();
            }
        }
        writer.close();
    }
}
