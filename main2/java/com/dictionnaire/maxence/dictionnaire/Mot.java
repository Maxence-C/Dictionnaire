package com.dictionnaire.maxence.dictionnaire;

/**
 * Created by Maxence on 02/06/2017.
 */

public class Mot {

    public int id;
    public int idGroupe;
    public String mot;

    public Mot(int id, int idGroupe, String mot){
        this.mot = mot;
        this.id = id;
        this.idGroupe = idGroupe;
    }

    public String getMot(){
        return this.mot;
    }

    public int getId(){
        return this.id;
    }

    public int getIdGroupe(){
        return this.idGroupe;
    }
}
