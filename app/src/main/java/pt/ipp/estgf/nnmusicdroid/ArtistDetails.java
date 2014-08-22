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

import pt.ipp.estgf.cmu.musicdroidlib.Artist;
import pt.ipp.estgf.nnmusicdroid.tasks.ArtistDetailTask;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;
import pt.ipp.estgf.nnmusicdroid.tasks.ImageLoaderTask;

/**
 * Lição 02, ficheiro: 4_interface_grafica
 */
public class ArtistDetails extends ActionBarActivity {

    private ArtistDetailTask task = null;
    private Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        //Obtem o ID do artist através do intent
        String artistName = getIntent().getStringExtra("ID");

        task = new ArtistDetailTask(new BasicHandler() {
            @Override
            public void run() {
                ArtistDetails.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArtistDetails.this.artist = ArtistDetails.this.task.getArtist();
                        updateViewData();
                    }
                });
            }
        });

        task.execute(artistName);
    }

    //Lista os detalhes do Artista
    private void updateViewData() {
        Artist artist = new Artist();

        //Lista o nome do artista
        TextView name = (TextView)findViewById(R.id.artist_name);
        name.setText(this.artist.getName());

        //Lista a imagem do artista
        ImageView image = (ImageView)findViewById(R.id.artist_image);
        new ImageLoaderTask(image).execute(this.artist.getImageLarge());

        //Lista o URL do artista
        TextView url = (TextView)findViewById(R.id.artist_url);
        url.setText(this.artist.getUrl());
        Linkify.addLinks(url, Linkify.ALL);

        //Lista o sumário do artista
        TextView sumario = (TextView)findViewById(R.id.artist_summary);
        sumario.setText(this.artist.getSummary());
    }


    //Método onCreateOptionsMenu, mostra o ficheiro .xml "artist_details"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.artist_details, menu);
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
