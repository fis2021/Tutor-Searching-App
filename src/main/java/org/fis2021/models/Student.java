package org.fis2021.models;

import org.dizitart.no2.objects.Id;

public class Student {

    @Id
    String username;

    private String nume, facultate, specializare, nrMatricol, parola;

    public Student(String nume, String facultate, String specializare, String nrMatricol, String username, String parola) {
        this.nume = nume;
        this.facultate = facultate;
        this.specializare = specializare;
        this.nrMatricol = nrMatricol;
        this.username = username;
        this.parola = parola;
    }

    public Student(){
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public String getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}