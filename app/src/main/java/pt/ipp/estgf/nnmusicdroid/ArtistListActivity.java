package pt.ipp.estgf.nnmusicdroid;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.cmu.musicdroidlib.TopArtist;
import pt.ipp.estgf.cmu.musicdroidlib.DatabaseHelper;
import pt.ipp.estgf.nnmusicdroid.adapter.ArtistAdapter;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;
import pt.ipp.estgf.nnmusicdroid.tasks.ArtistTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Lição 03, ficheiro: 6_listas_2013_10_11
 *
 * -- LISTAS É um grupo de view que apresenta uma lista através
 * de uma fonte de dados como um array ou o Cursor, têm layout
 * próprio e setendem a super class ListActivity
 *
 * slide 2
 */
public class ArtistListActivity extends ListActivity {

    private SQLiteDatabase database;
    private TopArtist topArtistAccess;
    private DatabaseHelper dbHelper;

    //Variável para Adapter que coloca cada um dos elementos no layout:
    private ArtistAdapter artistAdapter = null;

    //Variável com o ArrayList dos elementos da lista:
    private ArrayList<TopArtist> topArtists = new ArrayList<TopArtist>();

    private Place place;


    //Método onCreate, slide 4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        //Instanciar o listAdapter
        artistAdapter = new ArtistAdapter(this, topArtists);

        //Adicionar ao place adapter
        setListAdapter(artistAdapter);

        //Obtem o id do place a mostrar
        long placeID = getIntent().getLongExtra("id", -1);

        //Instancia o DBHelper
        this.dbHelper = new DatabaseHelper(getApplicationContext());

        //Obtém o place
        this.place = Place.get(placeID, this.dbHelper.getReadableDatabase());

        //Atualiza a view dos dados
        updateViewData();
    }

    private void updateViewData(){
        TextView titulo = (TextView)findViewById(R.id.title);
        titulo.setText(this.place.getName());
    }

    //Método onListItemClick para passar para mostrar detalhes do artista
    //seleccionado
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(ArtistListActivity.this, ArtistDetails.class);
        TopArtist topArtist = this.topArtists.get(position);
        intent.putExtra("ID", topArtist.getArtistName());

        // Lança a Activity
        ArtistListActivity.this.startActivity(intent);
    }

    //Método reloadArtists, inicia a task ArtistListActivity para mostrar
    //a lista de artistas
    private void reloadListArtists(){

        ArtistTask task = new ArtistTask(new BasicHandler() {
            @Override
            public void run() {
                //Faz com que o código a seguir seja executado na UIthread
                ArtistListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArtistListActivity.this.updateList();
                    }
                });
            }
        });

        //Inicia a Task
        task.execute(this.place.getId());

        this.updateList();
    }

    //Método updateList, carrega os tops de uma place e atualiza a interface
    private void updateList(){
        //Carrega os topArtists de um place
        TopArtist.getForPlace(this.place.getId(), topArtists, dbHelper.getReadableDatabase());

        //Notifica o adapter que os dados foram alterados, slide 5
        this.artistAdapter.notifyDataSetChanged();
    }

    //Método onResume preencher a lista de objetos com os dados
    //que queremos mostrar:
    @Override
    protected void onResume(){
        super.onResume();

        //Preenche a lista com os dados
        reloadListArtists();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.artist_list, menu);
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
