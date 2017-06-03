package com.dictionnaire.maxence.dictionnaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Maxence on 25/05/2017.
 */

public class DictionnaireDB {

    private SQLiteDatabase bdd;

    private BDDCREATE maBaseSQLite;

    public DictionnaireDB(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new BDDCREATE(context, "Dictionnaire.db", null, 1);

    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
        //maBaseSQLite.onCreate(bdd);
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertDictionnaire(Dictionnaire d){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put("nom", d.getNom());
        values.put("description", d.getDescription());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert("Dictionnaire", null, values);
    }

    public long insertGroupe(Groupe g){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put("idGroupe", getIdGroupeToInsert()+1);
        values.put("idDictionnaire", g.getIdDictionnaire());
        values.put("nomGroupe", g.getLibelle());
        values.put("descGroupe", g.getDescription());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert("Groupe", null, values);
    }

    public long insertMot(Mot m){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put("idMot", getIdMotToInsert()+1);
        values.put("idGroupe", m.getIdGroupe());
        values.put("mot", m.getMot());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert("Mot", null, values);
    }

    public int getIdGroupeToInsert(){
        String query = "SELECT MAX(idGroupe) FROM Groupe";
        int res = 0;
        Cursor c = bdd.rawQuery(query, null);
        while(c.moveToNext()) {
            res = c.getInt(0);
        }
        c.close();

        return res;
    }

    public int getIdMotToInsert(){
        String query = "SELECT MAX(idMot) FROM Mot";
        int res = 0;
        Cursor c = bdd.rawQuery(query, null);
        while(c.moveToNext()) {
            res = c.getInt(0);
        }
        c.close();

        return res;
    }

    public int updateDictionnaire(int id, Dictionnaire d){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put("nom", d.getNom());
        values.put("description", d.getDescription());
        return bdd.update("Dictionnaire", values, " Id = " + id, null);
    }

    public int removeLivreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete("Dictionnaire", " Id = " +id, null);
    }

    public Dictionnaire getDictionnaireFromNom(String nom){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query("Dictionnaire", new String[] {"Id", "nom", "description"}, " nom " + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToLivre(c);
    }

    private Dictionnaire cursorToLivre(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Dictionnaire dictionnaire = new Dictionnaire(c.getInt(0), c.getString(1),c.getString(2));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return dictionnaire;
    }

    public ArrayList<? extends Element> getDictionnairesList(){
        ArrayList<Element> arraylist = new ArrayList<Element>();

        String query = "SELECT * FROM Dictionnaire";

        Cursor c = bdd.rawQuery(query, null);
        while(c.moveToNext()) {
            arraylist.add(new Dictionnaire(c.getInt(0), c.getString(1), c.getString(2)));
        }
        c.close();

        return arraylist;
    }

    public void clearDataBase(){
        String query = "DELETE FROM Dictionnaire";
        Cursor c = bdd.rawQuery(query, null);
        c.moveToFirst();

        query = "DELETE FROM Groupe";
        c = bdd.rawQuery(query, null);
        c.moveToFirst();

        query = "DELETE FROM Mot";
        c = bdd.rawQuery(query, null);
        c.moveToFirst();
    }

    public ArrayList<? extends Element> getGroupeList(int idDictionnaire ){
        ArrayList<Element> arraylist = new ArrayList<Element>();
        String query = "SELECT * FROM Groupe WHERE idDictionnaire = " + idDictionnaire;

        Cursor c = bdd.rawQuery(query, null);
        while(c.moveToNext()) {
            arraylist.add(new Groupe(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3)));
        }
        c.close();

        return arraylist;
    }

    public ArrayList<Mot> getMotList(int idGroupe){
        ArrayList<Mot> arraylist = new ArrayList<Mot>();
        String query = "SELECT * FROM Mot WHERE idGroupe = " + idGroupe;

        Cursor c = bdd.rawQuery(query, null);
        while(c.moveToNext()) {
            arraylist.add(new Mot(c.getInt(0), c.getInt(1), c.getString(2)));
        }
        c.close();

        return arraylist;
    }

}
