package com.example.webservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText entrada;
    private Button enviar;
    private TextView saida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText) findViewById(R.id.entrada);
        saida = (TextView) findViewById(R.id.saida);
        enviar = (Button) findViewById(R.id.enviar);
        enviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    if(view.getId()==enviar.getId()){
        String url = "https://swapi.dev/api/people/"+entrada.getText().toString().trim();
        tarefa t = new tarefa();
        t.execute(url);
    }
    }
    class tarefa extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer = new StringBuffer();
            try{
                URL url2 = new URL(strings[0]);
                HttpsURLConnection conexao = (HttpsURLConnection) url2.openConnection();
                InputStream input = conexao.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader leitura = new BufferedReader(reader);
                String aux;
                if((aux= leitura.readLine())!=null){
                    buffer.append(aux);
                }
                System.out.println(buffer.toString());
            }   catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String p) {
            super.onPostExecute(p);
            JSONObject j = null;
            try{
                j = new JSONObject(p.toString());


                saida.setText("Nome:"+j.getString("name")+"  Cor dos olhos:"+j.getString("eye_color"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}