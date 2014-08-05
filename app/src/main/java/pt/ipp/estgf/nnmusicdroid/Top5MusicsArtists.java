package pt.ipp.estgf.nnmusicdroid;

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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Top5MusicsArtists extends ListActivity {

    private SQLiteDatabase database;
    private TopTrack topTrackAccess;
    private TopArtist topArtist;
    private DatabaseHelper dbHelper;
    private TrackAdapter trackAdapterMusics = null;
    private ArtistAdapter artistAdapterArtists = null;
    private ArrayList<TopTrack> topTrack = new ArrayList<TopTrack>();
    private ArrayList<TopArtist> topArtists = new ArrayList<TopArtist>();

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top5_musics_artists);

        //Instanciar o TrackAdapter
        trackAdapterMusics = new TrackAdapter(this, topTrack);
        artistAdapterArtists = new ArtistAdapter(this, topArtists);

        //Adicionar ao place adapter
        setListAdapter(trackAdapterMusics);
        setListAdapter(artistAdapterArtists);

        // Obtem o id do place a mostrar
        long placeID = getIntent().getLongExtra("id", -1);

        // Instancia o DbHelper
        this.dbHelper = new DatabaseHelper(getApplicationContext());

        // Obtem o place
        this.place = Place.get(placeID, this.dbHelper.getReadableDatabase());


        // Atualiza a view com os dados
        updateViewData();
    }

    private void updateViewData() {
        TextView titulo = (TextView)findViewById(R.id.title);
        titulo.setText(this.place.getName());
    }

    //Clique para passar para a activity da lista de musicas
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(Top5MusicsArtists.this, MusicListActivity.class);
        TopTrack topTrack = this.topTrack.get(position);

        //Lança a Activity
        Top5MusicsArtists.this.startActivity(intent);
    }


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

        // Notifica o adpater que os dados foram alterados
        this.trackAdapterMusics.notifyDataSetChanged();
    }

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
