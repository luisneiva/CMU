package pt.ipp.estgf.nnmusicdroid;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;
import pt.ipp.estgf.cmu.musicdroidlib.DatabaseHelper;
import pt.ipp.estgf.cmu.musicdroidlib.Track;
import pt.ipp.estgf.nnmusicdroid.adapter.TrackAdapter;

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

import java.util.ArrayList;

public class MusicListActivity extends ListActivity {

    private SQLiteDatabase database;
    private TopTrack topTrackAccess;
    private DatabaseHelper dbHelper;
    private TrackAdapter trackAdapter = null;
    private ArrayList<TopTrack> topTrack = new ArrayList<TopTrack>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        //Instanciar o TrackAdapter
        trackAdapter = new TrackAdapter(this, topTrack);

        //Adicionar ao place adapter
        setListAdapter(trackAdapter);
    }

    private void reloadListTracks(){
        this.dbHelper = new DatabaseHelper(getApplicationContext());

        // Carrega as top tracks de um place
        TopTrack.getForPlace(1, topTrack, dbHelper.getReadableDatabase());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_list, menu);
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
