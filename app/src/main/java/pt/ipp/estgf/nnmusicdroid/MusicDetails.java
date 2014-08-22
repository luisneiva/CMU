package pt.ipp.estgf.nnmusicdroid;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pt.ipp.estgf.cmu.musicdroidlib.Track;
import pt.ipp.estgf.nnmusicdroid.other.DateTools;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;
import pt.ipp.estgf.nnmusicdroid.tasks.ImageLoaderTask;
import pt.ipp.estgf.nnmusicdroid.tasks.MusicDetailTask;

/**
 * Lição 02, ficheiro: 4_interface_grafica
 */
public class MusicDetails extends ActionBarActivity {

    private MusicDetailTask task = null;
    private Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);

        String artistName = getIntent().getStringExtra("ArtistName");
        String musicName = getIntent().getStringExtra("TrackName");

        task = new MusicDetailTask(new BasicHandler() {
            @Override
            public void run() {
                MusicDetails.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MusicDetails.this.track = MusicDetails.this.task.getTrack();
                        updateViewData();
                    }
                });
            }
        });
        task.execute(artistName, musicName);
    }

    // Lista os detalhes da musica
    private void updateViewData() {
        //Lista o nome da musica
        TextView nameMusic = (TextView) findViewById(R.id.music_name);
        nameMusic.setText(track.getName());

        // Imagem
        ImageView image = (ImageView)findViewById(R.id.music_image);
        (new ImageLoaderTask(image)).execute(this.track.getAlbumImageLarge());

        //Detalhes - Titulo do album
        TextView albumTitle = (TextView) findViewById(R.id.music_album_title);
        albumTitle.setText(track.getAlbumTitle());

        //Detalhes - Nome do artista relativo à musica
        TextView nameArtist = (TextView) findViewById(R.id.music_artist_name);
        nameArtist.setText(track.getArtistName());

        //Detalhes - URL
        TextView url = (TextView) findViewById(R.id.music_url);
        url.setText(track.getUrl());
        Linkify.addLinks(url, Linkify.ALL);

        //Detalhes - duração da musica
        TextView musicDuration = (TextView) findViewById(R.id.music_duration);
        musicDuration.setText(DateTools.format(this.track.getDuration()));

        //Detalhes - summary
        TextView summary = (TextView) findViewById(R.id.music_summary);
        summary.setText(track.getSummary());
    }

    //Método onCreateOptionsMenu, mostra o ficheiro .xml "music_details"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_details, menu);
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
