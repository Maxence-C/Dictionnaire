package com.dictionnaire.maxence.dictionnaire;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {

    ListView listeDictionnaires;
    int page = 0;
    int dictio = 0;
    int grp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Accueil a = this;

        /*
        Click sur le bouton d'ajout d'un élément
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                FrameLayout ll = (FrameLayout)a.findViewById(R.id.Overlay);
                ll.setVisibility(View.VISIBLE);
            }

        });

        /*
        Clic sur "Annuler" de l'interface d'ajout
         */
        final FrameLayout ll = (FrameLayout)a.findViewById(R.id.Overlay);
        Button b = (Button) ll.findViewById(R.id.annuler);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout ll = (FrameLayout)a.findViewById(R.id.Overlay);
                ll.setVisibility(View.INVISIBLE);
            }
        });

        final DictionnaireDB dico = new DictionnaireDB(this);
        dico.open();
        Button b2 = (Button) ll.findViewById(R.id.Valider);

        /*
        Gestion du clic valider de l'interface d'ajout d'une élément
         */
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText ed1 = (EditText) ll.findViewById(R.id.editText);
                EditText ed2 = (EditText) ll.findViewById(R.id.editText2);
                if(page == 0){
                    dico.insertDictionnaire(new Dictionnaire(0, ed1.getText().toString(), ed2.getText().toString()));
                }else if(page == 1){
                    dico.insertGroupe(new Groupe(0,dictio,ed1.getText().toString(), ed2.getText().toString()));
                }else if(page == 2){
                    dico.insertMot(new Mot(0, grp, ed1.getText().toString()));
                }

                ll.setVisibility(View.INVISIBLE);

                LinearLayout ll = (LinearLayout)a.findViewById(R.id.Everything);
                ListView liste = (ListView) ll.findViewById(R.id.listeDictionnaires);
                AdapterDeuxLignes adl = null;
                if(page != 2)
                    adl = (AdapterDeuxLignes)liste.getAdapter();

                if(page == 0){
                    adl.updateResults(dico.getDictionnairesList());
                }else if(page == 1){
                    adl.updateResults(dico.getGroupeList(dictio));
                }else if(page == 2){
                    liste.setAdapter(new AdapterUneLigne(a, dico.getMotList(grp)));
                }


            }
        });

        listeDictionnaires = (ListView) findViewById(R.id.listeDictionnaires);

        /*
        Clic sur un élement de la liste, change les données, met à jour les variables globales et le titre
        Modifie aussi l'interface d'ajout en rendant invible le deuxième champs texte quand c'est un mot qui va être ajouté
         */
        listeDictionnaires.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(page == 0){
                    Dictionnaire d = (Dictionnaire)listeDictionnaires.getItemAtPosition(position);
                    dictio = d.getId();
                    AdapterDeuxLignes adl = (AdapterDeuxLignes)listeDictionnaires.getAdapter();
                    adl.updateResults(dico.getGroupeList(d.getId()));
                    a.setTitle("Groupe" + d.getId());
                    page++;
                }else if(page == 1){
                    Groupe d = (Groupe)listeDictionnaires.getItemAtPosition(position);
                    grp = d.getId();
                    listeDictionnaires.setAdapter(new AdapterUneLigne(a,dico.getMotList(d.getId())));
                    a.setTitle("Mot " + d.getId() + " " + d.getIdDictionnaire());

                    EditText ed2 = (EditText) ll.findViewById(R.id.editText2);
                    ed2.setVisibility(View.INVISIBLE);

                    page++;
                }else if(page == 2) {
                    //affichage vue mot
                }

                TextView v = (TextView)view.findViewById(android.R.id.text1);
                v.setText("clicked");

            }
        });


        dico.clearDataBase();
        listeDictionnaires.setAdapter(new AdapterDeuxLignes(this, dico.getDictionnairesList()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    Clic sur le bouton retour
     */
    @Override
    public void onBackPressed() {
        final FrameLayout ll = (FrameLayout)this.findViewById(R.id.Overlay);
        if(ll.getVisibility() == View.INVISIBLE) {
            final DictionnaireDB dico = new DictionnaireDB(this);
            dico.open();

            if (page == 1) {
                this.setTitle("Dictionnaire");
                AdapterDeuxLignes adl = (AdapterDeuxLignes) listeDictionnaires.getAdapter();
                adl.updateResults(dico.getDictionnairesList());
            } else if (page == 2) {
                this.setTitle("Groupe");


                EditText ed2 = (EditText) ll.findViewById(R.id.editText2);
                ed2.setVisibility(View.VISIBLE);
                listeDictionnaires.setAdapter(new AdapterDeuxLignes(this, dico.getGroupeList(dictio)));
            }

            if (page > 0) page--;
        }else{
            ll.setVisibility(View.INVISIBLE);
        }
    }
}
