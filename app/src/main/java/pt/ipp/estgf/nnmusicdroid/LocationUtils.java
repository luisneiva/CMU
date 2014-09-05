package pt.ipp.estgf.nnmusicdroid;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import pt.ipp.estgf.cmu.musicdroidlib.Place;

/**
 * Lição 07, ficheiro: 17_internet_2013_11_08
 *
 * -- ConnectivityManager, Serviço responsável por obter o estado da
 * conectividade à internet. Também tem a capacidade para notificar
 * as aplicações quando existem alterações no estado da conectividade.
 *
 *
 */
public class LocationUtils {

    private final static int MIN_TIME_UPDATE = 1000 * 60 * 30; // 60 minutos
    private final static int MIN_DISTANCE_CHANGE_FOR_UPDATE = 1000; // 1 KM

    private Context context;

    public LocationUtils(Context context) {
        this.context = context;
    }

    /**
     * Pergunta ao sistema se o GPS está ativo.
     *
     * @return
     */
    public boolean hasGPSConnection() {
        LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (manager != null) {
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        return false;
    }

    /**
     * Verifica se existe uma ligação à internet.
     *
     * @return
     */
    public boolean hasInternetCOnnection() {
        // Obtem o manager responsavel pela conectividade, slide 3
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Obtem a ultima localização conhecida.
     *
     * @return
     */
    public Location getCurrentLocation() {
        Location location = null;

        // Obtem o manager responsavel pela localização
        LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        boolean internetConnection = this.hasInternetCOnnection();
        boolean gpsConnection = this.hasGPSConnection();

        try {
            if (!internetConnection && !gpsConnection) {
                // Não existe internet nem GPS
            } else {
                if (internetConnection && !gpsConnection && manager != null) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });

                    location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } else if (gpsConnection && manager != null) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });

                    location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return location;
    }

    /**
     * Obtem o place correspondente a localização atual.
     *
     * @return
     */
    public Place getCurrentPlace() {
        Place place = null;

        // Geocoder (usado para a morada)
        //O Geocoding Converte a morada em coordenadas de latitude e longitude
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        Address address = null;

        // Obtem a localização atual
        Location curLocation = this.getCurrentLocation();

        if (curLocation != null) {
            try {
                // Obtem a morada da localização atual
                address = geocoder.getFromLocation(curLocation.getLatitude(), curLocation.getLongitude(), 1).get(0);

                place = new Place(-1l, address.getCountryName(), (float)curLocation.getLatitude(), (float)curLocation.getLongitude());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return place;
    }

    private Context getContext() {
        return this.context;
    }

}
