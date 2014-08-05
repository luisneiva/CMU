package pt.ipp.estgf.nnmusicdroid;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.security.Key;
import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.adapter.PlaceAdapter;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;
import pt.ipp.estgf.nnmusicdroid.model.Country;

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
                public void onItemClick(AdapterView<?> adapterView, final View view, final int index, final long l) {
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
                public void onItemClick(AdapterView<?> adapterView, final View view, final int index, final long l) {
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

        //Define o handler para o longPress
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int index, final long l) {
                //Obtem o plave para a posição
                final Place place = MyCountriesActivity.this.placesList.get(index);

                //Questionar e eliminar o pais selecionado
                AlertDialog.Builder builder = new AlertDialog.Builder(MyCountriesActivity.this);
                builder.setMessage("Emilinar o país selecionado da sua lista?");
                builder.setCancelable(false);

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whish) {

                        try {
                            //Elimina o Place
                            MyDbAccess myDbAccess1 = new MyDbAccess();
                            Place.delete(place.getId(), myDbAccess1.getWritableDatabase());

                            //Mensagem de confirmação do pais eliminado
                            Toast.makeText(MyCountriesActivity.this, ""  + "País eliminado com sucesso!",Toast.LENGTH_LONG).show();

                            reloadListCountries();
                        }catch (Exception ex){
                            Log.d("MyCountriesActivity", ex.getMessage());
                        }
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int wish){
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

        //obter o id do xml da activity Country list activity
        Button button_list_Countries = (Button) findViewById(R.id.button_AddCountry);

        //Adicionar i Listner ao botão addPais
        button_list_Countries.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                //Abrir a activity da lista de paises
                Intent intent = new Intent(MyCountriesActivity.this, CountryListActivity.class);
                startActivity(intent);
            }
        });


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
