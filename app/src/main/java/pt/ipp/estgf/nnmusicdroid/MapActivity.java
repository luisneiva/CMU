package pt.ipp.estgf.nnmusicdroid;

import android.app.AlertDialog;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.database.sqlite.SQLiteDatabase;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

import org.apache.http.ConnectionReuseStrategy;

import java.sql.Connection;
import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.R;

public class MapActivity extends FragmentActivity {

    private SQLiteDatabase database;
    private MyDbAccess myDbAccess;

    private final LatLng LOCATION_PORTUGAL = new LatLng(38.71667, -9.13333);
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private AlertDialog onlAlertDialog = null;
    LocationManager mLocationManager;
    ArrayList<Place> placesList = new ArrayList<Place>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        loadPlaces();
    }

    @Override
    protected void onStart(){
        super.onStart();
        mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        mMap = mapFragment.getMap();

        if (mMap != null) {
            // identifica a nossa localização
            mMap.setMyLocationEnabled(true);
            // permite mostrar o + e o - do zoom
            mMap.getUiSettings().setZoomControlsEnabled(true);
            // para focar o centro de portugal com aproximação 6, assim, aparece
            // portugal em toda a janela
            mMap.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(LOCATION_PORTUGAL, 5));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void loadPlaces() {
        MyDbAccess myDbAccess = new MyDbAccess();
        SQLiteDatabase dbAccess = myDbAccess.getReadableDatabase();
        placesList.clear();
        Place.getAll(placesList, dbAccess);
        dbAccess.close();
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
