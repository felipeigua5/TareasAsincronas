package com.example.jonmid.tareasasincronas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jonmid.tareasasincronas.Adapters.PostAdapter;
import com.example.jonmid.tareasasincronas.Models.Post;
import com.example.jonmid.tareasasincronas.Parser.JsonCountry;
import com.example.jonmid.tareasasincronas.Parser.JsonPost;
import com.example.jonmid.tareasasincronas.URL.HttpManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    // Atributos de clase iniciales
    ProgressBar progressBar;
    Button button;
    //TextView textView;
    RecyclerView recyclerView;

    List<Post> postList = new ArrayList<>();

    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        progressBar = (ProgressBar) findViewById(R.id.id_pb_data);
        button = (Button) findViewById(R.id.id_btn_loaddata);
        //textView = (TextView) findViewById(R.id.id_tv_data);

        recyclerView = (RecyclerView) findViewById(R.id.id_rcv_data);

        // Establcer la orientacion de RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void showCountries(View view){
        Intent intent = new Intent(PostActivity.this, MainActivity.class);
        startActivity(intent);
    }


    // Metodo para validar la conexion a internet
    public Boolean isOnLine(){
        // Hacer llamado al servicio de conectividad utilizando el ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Obtener el estado de la conexion a internet en el dispositivo
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Validar el estado obtenido de la conexion
        if (networkInfo != null){
            return true;
        }else {
            return false;
        }
    }

    // Evento del boton
    public void loadData(View view){
        if (isOnLine()){
            TaskPost taskPost = new TaskPost();
            taskPost.execute("https://jsonplaceholder.typicode.com/posts");
        }else {
            Toast.makeText(this, "Sin conexion", Toast.LENGTH_SHORT).show();
        }
    }

    // *************************************************************************************

    public void processData(){

        postAdapter = new PostAdapter(postList, getApplicationContext());
        recyclerView.setAdapter(postAdapter);
    }

    // Tarea para traer los datos de post
    public class TaskPost extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {


            String content = null;
            try {
                content = HttpManager.getDataJson(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                postList = JsonPost.getData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            processData();

            progressBar.setVisibility(View.GONE);
        }
    }




}
