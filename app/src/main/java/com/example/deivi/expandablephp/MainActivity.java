package com.example.deivi.expandablephp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private String IP_Server = "iesayala.ddns.net";
    private String url_consulta = "http://"+IP_Server+"/DeividHermin/Categorias.php";
    private JSONArray jSONArray;
    private DevuelveJSON devuelveJSON;

    private ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = (ExpandableListView)findViewById(R.id.lvExp);
        //prepareListData();
        devuelveJSON = new DevuelveJSON();
        new ListaUsuario().execute();

        //listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        //expListView.setAdapter(listAdapter);
    }

    private void aplicaAdapter(){
        listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private void creaElementoLista(String header, List<String> peliculas){
        listDataHeader.add(header);
        listDataChild.put(header, peliculas);
    }

    private void limpiaElemento(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    class ListaUsuario extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONArray doInBackground(String... args) {
            try {
                HashMap<String, String> parametrosPost = new HashMap<>();
                parametrosPost.put("ins_sql","Select * from GENEROS");
                jSONArray = devuelveJSON.sendRequest(url_consulta, parametrosPost);

                if (jSONArray!= null) {

                    aGeneros = new Vector();
                    Genero genero;
                    JSONObject jsonObject;
                    for (int i = 0; i < jSONArray.length(); i++) {
                        try {
                            jsonObject = jSONArray.getJSONObject(i);
                            genero = new Genero();
                            genero.setCodigo(jsonObject.getInt("Codigo"));
                            genero.setNombre(jsonObject.getString("Nombre"));
                            aGeneros.add(genero);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    limpiaElemento();
                    for(int i=0; i < aGeneros.size(); i++) {
                        int codigo = ((Genero) aGeneros.get(i)).getCodigo();
                        parametrosPost = new HashMap<>();
                        parametrosPost.put("ins_sql","SELECT * FROM PELICULAS WHERE Cod_Genero="+codigo);
                        JSONArray jSONArray2 = devuelveJSON.sendRequest(url_consulta, parametrosPost);

                        List<String> peliculas = new ArrayList<String>();
                        //aPeliculas = new Vector();
                        //Pelicula pelicula;
                        try {
                            if (jSONArray2 != null) {
                                JSONObject jsonObject2;
                                for (int j = 0; j < jSONArray2.length(); j++) {
                                    try {
                                        jsonObject2 = jSONArray2.getJSONObject(j);
                                        //pelicula = new Pelicula();
                                        //pelicula.setCodigo(jsonObject2.getInt("Codigo"));
                                        //pelicula.setCodigoGenero(jsonObject2.getInt("Cod_Genero"));
                                        //pelicula.setNombre(jsonObject2.getString("Nombre"));
                                        //pelicula.setDescripcion(jsonObject2.getString("descripcion"));
                                        peliculas.add(jsonObject2.getString("Nombre"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //añade el genero y sus peliculas
                        creaElementoLista(((Genero)aGeneros.get(i)).getNombre(), peliculas);
                    }
                }


                if (jSONArray != null) {
                    return jSONArray;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        Vector aGeneros;
        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            aplicaAdapter();
        }
    }
}

/*

 */