package com.example.preveja;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    Button btnLocalizacao;
    EditText editCidade;
    Button btnPesquisar;
    Button btnLuminosidade;
    ListView listViewCidades;
    TextView resultTxt;

    BD_Previsao db = new BD_Previsao(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLocalizacao = (Button) findViewById(R.id.btnLocalizacao);    //ok                                        //criar tela com informações completas ??????
        editCidade = (EditText) findViewById(R.id.editCidade);          //ok
        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);        //enviar valor da edit p/ API
        btnLuminosidade = (Button) findViewById(R.id.btnLuminosidade);  //ok
        listViewCidades = (ListView) findViewById(R.id.listViewCidades);//listar resultado da API
        resultTxt = findViewById(R.id.resultTXT);                       //+ adicionar informações no banco após a busca de dados

        btnLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                Intent localizacao = new Intent(MainActivity.this, GeoLocalizacao.class);
                startActivity(localizacao);
            }
        });

        btnLuminosidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                Intent luminosidade = new Intent(MainActivity.this, Sensor.class);
                startActivity(luminosidade);
            }
        });

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void buscaPrevisao(View view) {
        // Recupera a string de busca.
        String queryString = editCidade.getText().toString();
        // esconde o teclado qdo o botão é clicado
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Verifica o status da conexão de rede
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        /* Se a rede estiver disponivel e o campo de busca não estiver vazio
         iniciar o Loader CarregaLivros */
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            editCidade.setText(R.string.str_empty);
            resultTxt.setText(R.string.loading);
        }
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (queryString.length() == 0) {
                editCidade.setText(R.string.str_empty);
                resultTxt.setText(R.string.no_search_term);
            } else {
                editCidade.setText(" ");
                resultTxt.setText(R.string.no_network);
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new CarregaPrevisao(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);
            // Obtem o JSONArray dos itens de livros
            JSONArray itemsArray = jsonObject.getJSONArray("results");
            // inicializa o contador
            int i = 0;
            String titulo = null;
            // String autor = null;
            // Procura pro resultados nos itens do array
            while (i < itemsArray.length() &&
                    (titulo == null)) {
                // Obtem a informação
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("results");
                //  Obter autor e titulo para o item,
                // erro se o campo estiver vazio
                try {
                    titulo = volumeInfo.getString("title");
                    // autor = volumeInfo.getString("authors");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // move para a proxima linha
                i++;
            }
            //mostra o resultado qdo possivel.
            if (titulo != null) {
                resultTxt.setText(titulo);
                // nmAutor.setText(autor);
                //nmLivro.setText(R.string.str_empty);
            } else {
                // If none are found, update the UI to show failed results.
                resultTxt.setText(R.string.no_results);
                // nmAutor.setText(R.string.str_empty);
            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
            resultTxt.setText(R.string.no_results);
            // nmAutor.setText(R.string.str_empty);
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}

//    int temp;
//    Date date;
//    Time time;
//    int condition_code;
//    String description;
//    String currently;
//    String city;
//    int humidity;
//    String wind_speedy;
//    String sunrise;
//    String sunset;
//    String condition_slug;
//    String city_name;
//    String forecast;


    /*Button btnLocalizacao;
    EditText editCidade;
    Button btnPesquisar;
    Button btnLuminosidade;
    ListView listViewCidades;
    TextView resultTxt;

    private RequestQueue mQueue;

    BD_Previsao db = new BD_Previsao(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLocalizacao = (Button) findViewById(R.id.btnLocalizacao);
        editCidade = (EditText) findViewById(R.id.editCidade);
        btnPesquisar = (Button)findViewById(R.id.btnPesquisar);
        btnLuminosidade = (Button)findViewById(R.id.btnLuminosidade);
        listViewCidades = (ListView)findViewById(R.id.listViewCidades);
        resultTxt = findViewById(R.id.resultTXT);

        mQueue = Volley.newRequestQueue(this);


        btnLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                Intent localizacao = new Intent(MainActivity.this, GeoLocalizacao.class);
                startActivity(localizacao);
            }
        });

        btnLuminosidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a) {
                Intent luminosidade = new Intent(MainActivity.this, Sensor.class);
                startActivity(luminosidade);
            }
        });

        listenersButtons();
    }

    public void list(View v){
        Intent intent = new Intent(MainActivity.this, CarregaPrevisao.class);
        startActivity(intent);
    }

    public void listenersButtons() {
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCidade.getText();
                jsonParse();
            }
        });
    }

    private void jsonParse() {

        String cidade = editCidade.getText().toString();

        String url = "https://api.hgbrasil.com/weather?key=77eb3f8a&city_name=" + cidade;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObject = response.getJSONObject("results");

                            for (int i = 0; i < 10; i++) {

                                JSONObject results = jsonObject.getJSONObject(String.valueOf(i));

                                String temp = results.getString("temp");
                                String date = results.getString("date");
                                String time = results.getString("time");
                                String condition_code = results.getString("condition_code");
                                String description = results.getString("description");
                                String currently = results.getString("currently");
                                String city = results.getString("city");
                                String humidity = results.getString("humidity");
                                String wind_speedy = results.getString("wind_speedy");
                                String sunrise = results.getString("sunrise");
                                String sunset = results.getString("sunset");
                                String condition_slug = results.getString("condition_slug");
                                String city_name = results.getString("city_name");
                                String forecast = results.getString("forecast");

                                resultTxt.append(city + "\n");

                                try{
                                    new BD_Previsao(new Previsao(temp, date, time, condition_code, description, currently, city, humidity, wind_speedy, sunrise, sunset, condition_slug, city_name, forecast));
                                } catch (Exception e){

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }*/





