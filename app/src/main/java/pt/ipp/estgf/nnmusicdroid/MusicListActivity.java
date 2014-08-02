package pt.ipp.estgf.nnmusicdroid;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;
import pt.ipp.estgf.cmu.musicdroidlib.DatabaseHelper;
import pt.ipp.estgf.nnmusicdroid.adapter.TrackAdapter;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;
import pt.ipp.estgf.nnmusicdroid.tasks.MusicTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicListActivity extends ListActivity {

    private SQLiteDatabase database;
    private TopTrack topTrackAccess;
    private DatabaseHelper dbHelper;
    private TrackAdapter trackAdapter = null;
    private ArrayList<TopTrack> topTrack = new ArrayList<TopTrack>();

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        //Instanciar o TrackAdapter
        trackAdapter = new TrackAdapter(this, topTrack);

        //Adicionar ao place adapter
        setListAdapter(trackAdapter);

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

    private void reloadListTracks(){
        MusicTask task = new MusicTask(new BasicHandler() {
            @Override
            public void run() {
                // Faz com que o codigo a seguir seja executado na UIThread
                MusicListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MusicListActivity.this.updateList();
                    }
                });
            }
        });

        // Inicia a task
        task.execute(this.place.getId());

        this.updateList();
    }

    private void updateList() {
        // Carrega as top tracks de um place
        TopTrack.getForPlace(this.place.getId(), topTrack, dbHelper.getReadableDatabase());

        // Notifica o adpater que os dados foram alterados
        this.trackAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Preenche a lista com os dados
        reloadListTracks();
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
