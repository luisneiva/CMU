package pt.ipp.estgf.nnmusicdroid;

import android.app.AlertDialog;
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

import pt.ipp.estgf.nnmusicdroid.adapter.PlaceAdapter;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

import org.apache.http.ConnectionReuseStrategy;

import java.sql.Connection;
import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.R;

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
        mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.buttonMap));
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
                    .newLatLngZoom(LOCATION_EUROPE, 5));

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
     * Método que cria os markers conforme os países que temos nos "Meus Países"
     */
    public void addMarkers(){

        if(!placesList.isEmpty()){

            for(Place place : placesList){
                MarkerOptions myOptions = new MarkerOptions();

                double latitude = place.getLatitude();
                double longitude = place.getLongitude();

                LatLng COORDENADAS = new LatLng(latitude, longitude);
                myOptions.position(COORDENADAS);
                myOptions.title(place.getName());
                myOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                mMap.addMarker(myOptions);
                Marker marker = mMap.addMarker(myOptions);

                //GoogleMap.setOnMarkerClickListener(OnMarkerClickListener(marker));

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
