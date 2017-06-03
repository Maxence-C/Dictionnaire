package com.dictionnaire.maxence.dictionnaire;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maxence on 25/05/2017.
 */

public class BDDCREATE extends SQLiteOpenHelper {

    private static final String Dictionnaire = "table_livres";
    private static final String COL_ID = "Id";
    private static final String COL_NOM = "nom";
    private static final String COL_DESCRIPTION = "description";

    private static final String CREATE_BDD = "CREATE TABLE Dictionnaire ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOM + " VARCHAR(50) NOT NULL, "
            + COL_DESCRIPTION + " VARCHAR(100) NOT NULL);";

    private static final String CREATE_GROUPE_BDD = "CREATE TABLE Groupe ("
            + " idGroupe INTEGER NOT NULL, idDictionnaire INTEGER NOT NULL,"
            + " nomGroupe VARCHAR(50) NOT NULL, descGroupe VARCHAR(100) NOT NULL,"
            + " PRIMARY KEY (idGroupe, idDictionnaire));";

    private static final String CREATE_MOT_BDD = "CREATE TABLE Mot ("
            + "idMot INTEGER NOT NULL, idGroupe INTEGER NOT NULL,"
            + "mot VARCHAR(50) NOT NULL,"
            + " PRIMARY KEY (idMot));";

    public BDDCREATE(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        //db.execSQL(CREATE_BDD);
        db.execSQL(CREATE_GROUPE_BDD);
        db.execSQL(CREATE_MOT_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
