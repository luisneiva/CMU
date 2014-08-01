package pt.ipp.estgf.nnmusicdroid;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.security.Key;
import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.adapter.PlaceAdapter;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

public class MyCountriesActivity extends ListActivity {

    private SQLiteDatabase database;
    private MyDbAccess myDbAccess;
    private PlaceAdapter placeAdapter = null;
    private ArrayList<Place> placesList = new ArrayList<Place>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        //Instanciar o CountryAdapter
        placeAdapter = new PlaceAdapter(this, placesList);

        //Adicionar ao placeAdapter
        setListAdapter(placeAdapter);

        // Obtem a lista
        ListView listView = this.getListView();


        //Cria o intent
        String m = getIntent().getStringExtra("id");

        if (m.equals("musica")) {

            // Adiciona o evento do click
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                    // Obtem o place para esta posição
                    Place place = MyCountriesActivity.this.placesList.get(index);

                    // Cria o intent
                    Intent intent = new Intent(MainActivity.globalContext, MusicListActivity.class);

                    // Adiciona o id do place
                    intent.putExtra("id", place.getId());

                    // Lança a Activity
                    MyCountriesActivity.this.startActivity(intent);
                }
            });
        } else {
            // Adiciona o evento do click
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                    // Obtem o place para esta posição
                    Place place = MyCountriesActivity.this.placesList.get(index);

                    // Cria o intent
                    Intent intent = new Intent(MainActivity.globalContext, ArtistListActivity.class);

                    // Adiciona o id do place
                    intent.putExtra("id", place.getId());

                    // Lança a Activity
                    MyCountriesActivity.this.startActivity(intent);
                }
            });
        }
    }

    private void reloadListCountries(){
        this.myDbAccess = new MyDbAccess();
        Place.getAll(placesList, this.myDbAccess.getReadableDatabase());

        // Confirmação das alterações no country adapter
        placeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();

        //Preencimento dos paises no arrayList
        reloadListCountries();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.countries, menu);
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
