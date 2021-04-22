package org.fis2021.models;

import org.dizitart.no2.objects.Id;

import java.util.ArrayList;
import java.util.List;

public class Tutor {

    @Id
    private String username;

    private String nume, parola, materie, specializare;


    public Tutor(String nume, String username, String parola, String materie,String specializare) {
        this.nume = nume;
        this.username = username;
        this.parola = parola;
        this.materie = materie;
        this.specializare = specializare;
    }

    public Tutor(){
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
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

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }
}
