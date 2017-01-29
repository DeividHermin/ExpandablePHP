package com.example.deivi.expandablephp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private String IP_Server = "iesayala.ddns.net";
    private String url_consulta = "http://"+IP_Server+"/DeividHermin/Categorias.php";
    private JSONArray jSONArray;
    private DevuelveJSON devuelveJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devuelveJSON = new DevuelveJSON();
        new ListaUsuario().execute();
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
                parametrosPost.put("ins_sql","Select * from PELICULAS");
                jSONArray = devuelveJSON.sendRequest(url_consulta,
                        parametrosPost);
                if (jSONArray != null) {
                    return jSONArray;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        Vector aPeliculas;
        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
                aPeliculas = new Vector();
                Pelicula pelicula;
                JSONObject jsonObject;
                for (int i = 0; i < json.length(); i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        pelicula = new Pelicula();
                        pelicula.setCodigo(jsonObject.getInt("Codigo"));
                        pelicula.setCodigoGenero(jsonObject.getInt("Cod_Genero"));
                        pelicula.setNombre(jsonObject.getString("Nombre"));
                        pelicula.setDescripcion(jsonObject.getString("descripcion"));
                        aPeliculas.add(pelicula);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                for(int i=0; i < aPeliculas.size(); i++){
                    pelicula = (Pelicula)aPeliculas.get(i);
                    Log.e(pelicula.getNombre(), pelicula.getCodigo() + " | " + pelicula.getDescripcion() + " | " + pelicula.getCod_genero());
                }
            } else {
                Toast.makeText(MainActivity.this, "JSON Array nulo",Toast.LENGTH_LONG).show();
            }
        }
    }
}
