package com.dictionnaire.maxence.dictionnaire;

/**
 * Created by Maxence on 02/06/2017.
 */

public class Groupe extends Element {

    public int id;
    public String nom;
    public String description;
    public int idDictionnaire;

    public Groupe(int id, int idDictionnaire, String description, String nom){

        super(nom, description);
        this.id = id;
        this.idDictionnaire = idDictionnaire;
    }

    public String getNom(){
        return this.nom;
    }

    public String getDesc(){
        return this.description;
    }

    public int getId(){
        return this.id;
    }

    public int getIdDictionnaire(){
        return this.idDictionnaire;
    }

}
