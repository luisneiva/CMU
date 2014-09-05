package pt.ipp.estgf.nnmusicdroid;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.adapter.CountryAdapter;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;
import pt.ipp.estgf.nnmusicdroid.model.Country;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Lição 03, ficheiro: 6_listas_2013_10_11
 *
 * -- LISTAS É um grupo de view que apresenta uma lista através
 * de uma fonte de dados como um array ou o Cursor, têm layout
 * próprio e setendem a super class ListActivity
 *
 * Classe para suporte à base de dados
 *
 * * slide 2
 */

public class CountryListActivity extends ListActivity {

    // Variável com o ArrayList dos elementos da lista:
    private ArrayList<Country> countryList = new ArrayList<Country>();

    // Variável para Adapter que coloca cada um dos elementos no layout:
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        // Cria o adapter e define o conjunto de dados
        this.adapter = new CountryAdapter(this.getApplicationContext(), this.countryList);
        this.setListAdapter(this.adapter);

        // Carrega os dados na adapter
        this.updateListView();

        // Obtem a ListView
        final ListView listView = this.getListView();

        // Define o handler para o LongPress
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                // Obtem o Country para a posição
                final Country country = CountryListActivity.this.countryList.get(index);

                // Verifica se este pais já é favorito
                MyDbAccess myDB = new MyDbAccess();
                ArrayList<Place> myPlaces = Place.getAll(null, myDB.getReadableDatabase());

                for (Place place : myPlaces) {
                    if (place.getName().equals(country.getName())) {
                        Toast.makeText(CountryListActivity.this, "" + "País já existe na sua lista!", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }

                // Questionar e adicionar o pais à lista de preferidos. Adicionar o Place (Dialog)
                AlertDialog.Builder builder1 = new AlertDialog.Builder(CountryListActivity.this);
                builder1.setMessage("Adicionar o País selecionado à sua lista?");
                builder1.setCancelable(false);

                builder1.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whish) {
                        // Obtem as coordenadas
                        try {
                            Geocoder geocoder = new Geocoder(MainActivity.globalContext, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocationName(country.getName(), 1);
                            Address address = addresses.get(0);
                            float longitude = (float) address.getLongitude();
                            float latitude = (float) address.getLatitude();

                            // Cria o place
                            MyDbAccess myDbAccess = new MyDbAccess();
                            Place.create(country.getName(), latitude, longitude, myDbAccess.getWritableDatabase());

                            //Mensagem de confirmação do país adicionado
                            Toast.makeText(CountryListActivity.this, "" + "País adicionado à sua lista de preferidos!", Toast.LENGTH_LONG).show();

                        } catch (Exception ex) {
                            Log.d("CountryListActivity", ex.getMessage());
                        }
                    }
                });

                builder1.setNegativeButton("Não", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int wish){
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder1.create();
                alert.show();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.country_list, menu);
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

    /**
     * Atualiza o conjunto de dados da list
     * view e notifica o adapter.
     */
    private void updateListView() {
        Country.getAll(this.countryList);
        this.adapter.notifyDataSetChanged();
    }

}
