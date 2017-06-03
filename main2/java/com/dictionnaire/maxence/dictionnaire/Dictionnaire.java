package com.dictionnaire.maxence.dictionnaire;

/**
 * Created by Maxence on 25/05/2017.
 */

public class Dictionnaire extends Element{

    private int id;


    public Dictionnaire(int id, String nom, String description){

        super(nom, description);
        this.id = id;
    }

    public String getNom(){
        return this.libelle;
    }

    public String getDescription(){
        return this.description;
    }

    public int getId(){
        return this.id;
    }

}
