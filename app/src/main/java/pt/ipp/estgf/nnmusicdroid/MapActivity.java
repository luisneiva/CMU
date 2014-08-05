package pt.ipp.estgf.nnmusicdroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

import java.util.ArrayList;
import java.util.HashMap;

import pt.ipp.estgf.cmu.musicdroidlib.Place;

public class MapActivity extends FragmentActivity {

    private SQLiteDatabase database;
    private MyDbAccess myDbAccess;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private AlertDialog onlAlertDialog = null;
    LocationManager mLocationManager;
    private final LatLng LOCATION_EUROPE = new LatLng(48.136355, 11.584408);
    ArrayList<Place> placesList = new ArrayList<Place>();

    private Place place;

    //HashMap para associar ao marker o id e o place (key-value)
    HashMap<String, Long> markers = new HashMap<String, Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        loadPlaces();
    }

    /**
     * Lição 06, slides 14 Mapas
     *Método onStart que carrega o mapa inicial, o mapa é do tipo normal,
     * tem os botões do zoom e a bússula ativos, e mostra +/- a localizção
     * do centro da europa com o zoom5, este zoom pode ir de 1 a 21.
     */
    @Override
    protected void onStart(){
        super.onStart();
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.myMap);
        mMap = mapFragment.getMap();

        if (mMap != null) {
            // identifica a nossa localização
            mMap.setMyLocationEnabled(true);

            //Mostra o mapa do tipo normal
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            // Mostra os botões de zoom do mapa
            mMap.getUiSettings().setZoomControlsEnabled(true);

            //Mostra a bússula
            mMap.getUiSettings().setCompassEnabled(true);

            //Metodo para colcoar a câmara a mostrar o centro da europa com aproximação5
            mMap.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(LOCATION_EUROPE, 3));

            //Método que mostra o Top de músicas do marker seleccionado (setOnWindow)
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    //Obtem o ID do plce que está na hashMap Markers
                    Long placeID = MapActivity.this.markers.get(marker.getId());

                    Intent intent = new Intent(MapActivity.this, Top5MusicsArtists.class);
                    intent.putExtra("id", placeID);
                    MapActivity.this.startActivity(intent);

                    return false;
                }
            });

            //Adiciona os markers
            addMarkers();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * Método que carrega os places da placesList
     */
    private void loadPlaces() {
        MyDbAccess myDbAccess = new MyDbAccess();
        SQLiteDatabase dbAccess = myDbAccess.getReadableDatabase();
        placesList.clear();
        Place.getAll(placesList, dbAccess);
        dbAccess.close();
    }

    /**
     * Lição 06 - Ficheiro --> 14_mapas_2013_11_01.pdf, página 17
     * Método que cria os markers conforme os países que temos nos "Meus Países"
     */
    public void addMarkers(){
        this.markers.clear();
        this.mMap.clear();

        if(!placesList.isEmpty()){

            for(Place place : placesList){
                MarkerOptions myOptions = new MarkerOptions();

                double latitude = place.getLatitude();
                double longitude = place.getLongitude();

                LatLng COORDENADAS = new LatLng(latitude, longitude);
                myOptions.position(COORDENADAS);
                myOptions.title(place.getName());
                myOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


                Marker marker = mMap.addMarker(myOptions);

                // Adiciona o id do marker e do place a HashMap
                this.markers.put(marker.getId(), place.getId());

                // Showing InfoWindow on the GoogleMap
                marker.showInfoWindow();
            }
        }
    }


    /**
     * Método que altera o tipo de mapa para NORMAL
     * @param view
     */
    public void onClick_Normal(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_EUROPE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_EUROPE, 5));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.animateCamera(cameraUpdate);
    }

    /**
     * Método que altera o tipo de mapa para TERRAIN
     * @param view
     */
    public void onClick_Terrain(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_EUROPE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_EUROPE, 5));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.animateCamera(cameraUpdate);
    }

    /**
     * Método que altera o tipo de mapa para SATELLITE
     * @param view
     */
    public void onClick_Satellite(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_EUROPE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_EUROPE, 5));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.animateCamera(cameraUpdate);
    }

    /**
     * Método que altera o tipo de mapa para HYBRID
     * @param view
     */
    public void onClick_Hybrid(View view){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOCATION_EUROPE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_EUROPE, 5));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.animateCamera(cameraUpdate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
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
