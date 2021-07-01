package com.example.preveja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import static android.hardware.Sensor.TYPE_LIGHT;

public class GeoLocalizacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_localizacao);
    }

    public void buscarInformacoesGPS(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(GeoLocalizacao.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(GeoLocalizacao.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(GeoLocalizacao.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            return;
        }

        LocationManager mLocManager = (LocationManager) getSystemService(GeoLocalizacao.this.LOCATION_SERVICE);
        LocationListener mLocListener = new MinhaLocalizacaoListener();

        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            String texto = "Latitude: " + MinhaLocalizacaoListener.Latitude + "\n" +
                    "Longitude: " + MinhaLocalizacaoListener.Longitude + "\n";
            Toast.makeText(GeoLocalizacao.this, texto, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(GeoLocalizacao.this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
        }

        this.mostrarGoogleMaps(MinhaLocalizacaoListener.Latitude, MinhaLocalizacaoListener.Longitude);
    }

    public void mostrarGoogleMaps(double Latitude, double Longitude) {
        WebView wv = findViewById(R.id.webv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.google.com.br/maps/search/?api=1&query=" + Latitude + "," + Longitude);
    }

    public void voltar(View v) {
        Intent voltar = new Intent(GeoLocalizacao.this, MainActivity.class);
        startActivity(voltar);
    }

}