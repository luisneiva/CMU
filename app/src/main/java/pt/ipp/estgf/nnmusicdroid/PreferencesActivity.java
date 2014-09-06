package pt.ipp.estgf.nnmusicdroid;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.R;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

/**
 * Lição 04, ficheiro: 8_preferencias_2013_10_13
 *
 * PreferenceActivity, tilizada para modificar o comportamento
 * das aplicações, Activity -> PreferenceActivity
 * Ao contrário da Activity, a PreferenceActivity não necessita
 * de um layout em XML, mas sim de um XML com a especificação das
 * preferências.
 */
public class PreferencesActivity extends PreferenceActivity {

    private SQLiteDatabase database;
    private MyDbAccess myDbAccess;
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private ListPreference prefTimeUpdate;

    private final LatLng LOCATION_DEFAULT = new LatLng(39.654532, -8.238181);

    ArrayList<Place> placesList = new ArrayList<Place>();
    private ArrayList<String> timeUpdate_item = new ArrayList<String>();

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //setContentView(R.layout.activity_preferences);
        loadPlaces();
    }

    //Método que faz o load dos Places
    private void loadPlaces() {
        MyDbAccess myDbAccess = new MyDbAccess();
        SQLiteDatabase dbAccess = myDbAccess.getReadableDatabase();
        placesList.clear();
        Place.getAll(placesList, dbAccess);

        // ---

        ListPreference listPreferenceCategory = (ListPreference) findPreference("pref_default_country_key");
        if (listPreferenceCategory != null) {

            List<CharSequence> entries = new ArrayList<CharSequence>();
            List<CharSequence> entryValues = new ArrayList<CharSequence>();

            for (Place place : placesList) {
                // Ignora o place temporário
                if (place.getId() == 0) {
                    continue;
                }

                entries.add(place.getName());
                entryValues.add(String.valueOf(place.getId()));
            }

            CharSequence entriesArray[] = new String[entries.size()];
            CharSequence entryValuesArray[] = new String[entries.size()];

            // Copia a lista para um array
            System.arraycopy(entries.toArray(), 0, entriesArray, 0, entries.size());
            System.arraycopy(entryValues.toArray(), 0, entryValuesArray, 0, entries.size());

            listPreferenceCategory.setEntries(entriesArray);
            listPreferenceCategory.setEntryValues(entryValuesArray);
        }
    }

    /**
     * Método que adiciona os intervalos de atualização da widget
     */
    private void PrefTimeUpdateWidget() {

        // adicionar os items
        timeUpdate_item.clear();
        if (timeUpdate_item != null) {
            timeUpdate_item.add("1 Dia");
            timeUpdate_item.add("2 Dias");
            timeUpdate_item.add("3 Dias");
            timeUpdate_item.add("5 Dias");
            timeUpdate_item.add("7 Dias");
        }
    }

    /**
     * Método que altera o tipo de mapa para NORMAL
     * @param view
     *//*
    public void onClick_Normal(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_DEFAULT);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_DEFAULT, 6));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.animateCamera(cameraUpdate);
    }

    *//**
     * Método que altera o tipo de mapa para TERRAIN
     * @param view
     *//*
    public void onClick_Terrain(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_DEFAULT);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_DEFAULT, 6));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.animateCamera(cameraUpdate);
    }

    *//**
     * Método que altera o tipo de mapa para SATELLITE
     * @param view
     *//*
    public void onClick_Satellite(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_DEFAULT);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_DEFAULT, 6));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.animateCamera(cameraUpdate);
    }

    *//**
     * Método que altera o tipo de mapa para HYBRID
     * @param view
     *//*
    public void onClick_Hybrid(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_DEFAULT);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_DEFAULT, 6));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.animateCamera(cameraUpdate);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
