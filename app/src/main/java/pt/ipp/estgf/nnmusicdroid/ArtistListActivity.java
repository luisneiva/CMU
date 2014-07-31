package pt.ipp.estgf.nnmusicdroid;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.DatabaseHelper;
import pt.ipp.estgf.cmu.musicdroidlib.TopArtist;
import pt.ipp.estgf.nnmusicdroid.adapter.ArtistAdapter;
import pt.ipp.estgf.nnmusicdroid.tasks.ArtistTask;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;

public class ArtistListActivity extends ListActivity {

    private SQLiteDatabase database;
    private TopArtist topArtistAccess;
    private DatabaseHelper dbHelper;
    private ArtistAdapter artistAdapter = null;
    private ArrayList<TopArtist> topArtists = new ArrayList<TopArtist>();

    private Long placeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        //Instanciar o listAdapter
        artistAdapter = new ArtistAdapter(this, topArtists);

        //Adicionar ao place adapter
        setListAdapter(artistAdapter);

        //Obtem o id do place a mostrar
        this.placeID = getIntent().getLongExtra("id", -1);
    }


    private void reloadListArtists(){
        this.dbHelper = new DatabaseHelper(getApplicationContext());

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
        task.execute(this.placeID);

        this.updateList();
    }

    private void updateList(){
        //Carrega os topArtists de um place
        TopArtist.getForPlace(this.placeID, topArtists, dbHelper.getReadableDatabase());

        //Notifica o adapter que os dados foram alterados
        this.artistAdapter.notifyDataSetChanged();
    }

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
