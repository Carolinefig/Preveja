package com.example.preveja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BD_Previsao extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_PREVISAO = "bd_previsao";

    private static final String TABELA_PREVISAO = "tb_previsao";

    private static final String COLUNA_TEMP = "temp";
    private static final String COLUNA_DATE = "date";
    private static final String COLUNA_TIME = "time";
    private static final String COLUNA_CONDITION_CODE = "condition_code";
    private static final String COLUNA_DESCRIPTION = "description";
    private static final String COLUNA_CURRENTLY = "currently";
    private static final String COLUNA_CITY = "city";
    private static final String COLUNA_HUMIDITY = "humidity";
    private static final String COLUNA_WIND_SPEEDY = "wind_speedy";
    private static final String COLUNA_SUNRISE = "sunrise";
    private static final String COLUNA_SUNSET = "sunset";
    private static final String COLUNA_CONDITION_SLUG = "condition_slug";
    private static final String COLUNA_CITY_NAME = "city_name";
    private static final String COLUNA_FORECAST = "forecast";


    public BD_Previsao(@Nullable Context context) {
        super(context, BANCO_PREVISAO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //-------------------------------TABELA PREVISAO-------------------------------//

        String QUERY_PREVISAO = "CREATE TABLE " + TABELA_PREVISAO + COLUNA_TEMP + " ( "
                + " STRING, " + COLUNA_DATE + " STRING, " + COLUNA_TIME + " STRING, " + COLUNA_CONDITION_CODE
                + " STRING, " + COLUNA_DESCRIPTION + " STRING, " + COLUNA_CURRENTLY + " STRING, " + COLUNA_CITY
                + " STRING PRIMARY KEY, " + COLUNA_HUMIDITY + " STRING, " + COLUNA_WIND_SPEEDY + " STRING, "
                + COLUNA_SUNRISE + " STRING, " + COLUNA_SUNSET + " STRING, " + COLUNA_CONDITION_SLUG
                + " STRING, " + COLUNA_CITY_NAME + " STRING, " + COLUNA_FORECAST + " STRING); ";

        db.execSQL(QUERY_PREVISAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PREVISAO + ";" );
        onCreate(db);
    }


    //---Método para adicionar valores, passando a constante e o atributo da classe em paralelo---//

    void addPrevisao (Previsao previsao){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_TEMP, previsao.getTemp());
        values.put(COLUNA_DATE, previsao.getDate());
        values.put(COLUNA_TIME, previsao.getTime());
        values.put(COLUNA_CONDITION_CODE, previsao.getCondition_code());
        values.put(COLUNA_DESCRIPTION, previsao.getDescription());
        values.put(COLUNA_CURRENTLY, previsao.getCurrently());
        values.put(COLUNA_CITY, previsao.getCity());
        values.put(COLUNA_HUMIDITY, previsao.getHumidity());
        values.put(COLUNA_WIND_SPEEDY, previsao.getWind_speedy());
        values.put(COLUNA_SUNRISE, previsao.getSunrise());
        values.put(COLUNA_SUNSET, previsao.getSunset());
        values.put(COLUNA_CONDITION_SLUG, previsao.getCondition_slug());
        values.put(COLUNA_CITY_NAME, previsao.getCity_name());
        values.put(COLUNA_FORECAST, previsao.getForecast());

        db.insert(TABELA_PREVISAO, null, values);
        db.close();
    }

    //-------------------------------LISTAR PREVISÃO-------------------------------//

    public List<Previsao> ListaTodasPrevisoes() {
        List<Previsao> ListaPrevisoes = new ArrayList<>();
        String query = "SELECT * FROM " + TABELA_PREVISAO;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do{
                Previsao previsao = new Previsao();

                previsao.setTemp(c.getString(0));
                previsao.setDate(c.getString(1));
                previsao.setTime(c.getString(2));
                previsao.setCondition_code(c.getString(3));
                previsao.setDescription(c.getString(4));
                previsao.setCurrently(c.getString(5));
                previsao.setCity(c.getString(6));
                previsao.setHumidity(c.getString(7));
                previsao.setWind_speedy(c.getString(8));
                previsao.setSunrise(c.getString(9));
                previsao.setSunset(c.getString(10));
                previsao.setCondition_slug(c.getString(11));
                previsao.setCity_name(c.getString(12));
                previsao.setForecast(c.getString(13));

                ListaPrevisoes.add(previsao);
            } while(c.moveToNext());
        }
        return ListaPrevisoes;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM tb_previsao WHERE city = "+id+"", null);
        return res;
    }
}