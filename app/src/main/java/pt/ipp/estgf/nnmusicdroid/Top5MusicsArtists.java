package pt.ipp.estgf.nnmusicdroid;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.cmu.musicdroidlib.TopArtist;
import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;
import pt.ipp.estgf.cmu.musicdroidlib.DatabaseHelper;
import pt.ipp.estgf.nnmusicdroid.adapter.ArtistAdapter;
import pt.ipp.estgf.nnmusicdroid.adapter.TrackAdapter;
import pt.ipp.estgf.nnmusicdroid.tasks.ArtistTask;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;
import pt.ipp.estgf.nnmusicdroid.tasks.MusicTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Lição 02, ficheiro: 4_interface_grafica
 */
public class Top5MusicsArtists extends Activity {

    private SQLiteDatabase database;
    private TopTrack topTrackAccess;
    private TopArtist topArtist;
    private DatabaseHelper dbHelper;

    //Variável para Adapter que coloca cada um dos elementos no layout:
    private TrackAdapter trackAdapterMusics = null;
    private ArtistAdapter artistAdapterArtists = null;

    //Variável com o ArrayList dos elementos da lista:
    private ArrayList<TopTrack> topTrack = new ArrayList<TopTrack>();
    private ArrayList<TopArtist> topArtists = new ArrayList<TopArtist>();

    ListView top_music_list;
    ListView top_artist_list;

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top5_musics_artists);

        // Obtem o id do place a mostrar
        final long placeID = getIntent().getLongExtra("id", -1);

        // Instancia o DbHelper
        this.dbHelper = new DatabaseHelper(getApplicationContext());

        // Obtem o place
        this.place = Place.get(placeID, this.dbHelper.getReadableDatabase());

        // COnfigura a lista de musicas
        this.configMusicList();

        // Configura a lista de artistas
        this.configArtistList();

        // Adiciona o click para a lista do top de musicas
        TextView textView = (TextView)findViewById(R.id.text_top5_musics);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Top5MusicsArtists.this, MusicListActivity.class);
                intent.putExtra("id", placeID);
                Top5MusicsArtists.this.startActivity(intent);
            }
        });

        // Adicina o click para a lista de top de artistas
        textView = (TextView)findViewById(R.id.text_top5_artists);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Top5MusicsArtists.this, ArtistListActivity.class);
                intent.putExtra("id", placeID);
                Top5MusicsArtists.this.startActivity(intent);
            }
        });

        // Atualiza a view com os dados
        updateViewData();
    }

    private void configMusicList() {
        // Obtem a instancia da lista
        this.top_music_list = (ListView)findViewById(R.id.top_music_list);

        // Cria o adapater
        this.trackAdapterMusics = new TrackAdapter(this, topTrack);

        // Assicia o adapater a lista
        this.top_music_list.setAdapter(this.trackAdapterMusics);

        // Cria o evento para o click
        this.top_music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Top5MusicsArtists.this, MusicDetails.class);
                TopTrack topTrack = Top5MusicsArtists.this.topTrack.get(position);

                intent.putExtra("ArtistName", topTrack.getArtistName());
                intent.putExtra("TrackName", topTrack.getName());

                //Lança a Activity
                Top5MusicsArtists.this.startActivity(intent);
            }
        });
    }

    private void configArtistList() {
        // Obtem a instancia da lista
        this.top_artist_list = (ListView)findViewById(R.id.top_artist_list);

        // Cria o adapater
        this.artistAdapterArtists = new ArtistAdapter(this, topArtists);

        // Assicia o adapater a lista
        this.top_artist_list.setAdapter(this.artistAdapterArtists);

        // Cria o evento para o click
        this.top_artist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Top5MusicsArtists.this, ArtistDetails.class);
                TopTrack topTrack = Top5MusicsArtists.this.topTrack.get(position);

                intent.putExtra("ID", topTrack.getArtistName());

                //Lança a Activity
                Top5MusicsArtists.this.startActivity(intent);
            }
        });
    }

    private void updateViewData() {
        TextView titulo = (TextView)findViewById(R.id.title_top5);
        titulo.setText(this.place.getName());
    }


    //Método reloadListTracks, inicia a task Top5MusicsArtists para mostrar
    //a lista do TOP 5 de músicas
    private void reloadListTracks(){
        MusicTask task = new MusicTask(new BasicHandler() {
            @Override
            public void run() {
                // Faz com que o codigo a seguir seja executado na UIThread
                Top5MusicsArtists.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Top5MusicsArtists.this.updateList();
                    }
                });
            }
        });

        // Inicia a task
        task.execute(this.place.getId());

        this.updateList();
    }

    //Método reloadListArtists, inicia a task Top5MusicsArtists para mostrar
    //a lista do TOP 5 de artistas
    private void reloadListArtists(){

        ArtistTask task = new ArtistTask(new BasicHandler() {
            @Override
            public void run() {
                //Faz com que o código a seguir seja executado na UIthread
                Top5MusicsArtists.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Top5MusicsArtists.this.updateList();
                    }
                });
            }
        });

        //Inicia a Task
        task.execute(this.place.getId());

        this.updateList();
    }

    private void updateList() {
        // Carrega as top tracks e os top artists de um place
        TopTrack.getForPlace(this.place.getId(), topTrack, dbHelper.getReadableDatabase());
        TopArtist.getForPlace(this.place.getId(), topArtists, dbHelper.getReadableDatabase());

        // --- LIMITA A 5 ELEMENTOS
        while (topTrack.size() > 5) {
            topTrack.remove(topTrack.size() - 1);
        }

        while (topArtists.size() > 5) {
            topArtists.remove(topArtists.size() - 1);
        }
        // ---

        //Notifica o adapter que os dados foram alterados, slide 5
        this.trackAdapterMusics.notifyDataSetChanged();
        this.artistAdapterArtists.notifyDataSetChanged();
    }

    //Método onResume preencher as listas de objetos com os dados
    //que queremos mostrar:
    @Override
    protected void onResume() {
        super.onResume();

        // Preenche a lista com os dados
        reloadListTracks();
        reloadListArtists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top5_musics_artists, menu);
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
