package pt.ipp.estgf.nnmusicdroid.tasks;

import android.os.AsyncTask;

import pt.ipp.estgf.cmu.musicdroidlib.Artist;
import pt.ipp.estgf.cmu.musicdroidlib.parsers.ArtistParser;
import pt.ipp.estgf.cmu.musicdroidlib.parsers.TopArtistParser;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

public class ArtistDetailTask extends AsyncTask<String, Void, Void> {

    private BasicHandler handler;
    private Artist artist = null;

    public ArtistDetailTask(BasicHandler handler) {
        this.handler = handler;
    }

    @Override
    protected Void doInBackground(String... artistID) {
        MyDbAccess myDbAccess = new MyDbAccess();

        artistID[0] = artistID[0].replace(" ", "%20");

        // Atualiza a base de dados
        // TODO: É necessario limitar o numero de chamadas à API
        ArtistParser artistParser = new ArtistParser();
        this.artist = artistParser.getArtist(artistID[0], null);

        // Executar o handler
        if (this.handler != null) {
            this.handler.run();
        }

        return null;
    }

    public Artist getArtist() {
        return this.artist;
    }
}
