package com.dictionnaire.maxence.dictionnaire;

/**
 * Created by Maxence on 02/06/2017.
 */

public class Element {

    public String libelle;
    public String description;

    public Element(String libelle, String description){
        this.libelle = libelle;
        this.description = description;
    }

    public String getLibelle(){
        return this.libelle;
    }

    public String getDescription(){
        return this.description;
    }

}
