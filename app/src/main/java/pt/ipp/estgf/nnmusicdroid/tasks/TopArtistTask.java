package pt.ipp.estgf.nnmusicdroid.tasks;

import android.os.AsyncTask;

import pt.ipp.estgf.cmu.musicdroidlib.Artist;
import pt.ipp.estgf.cmu.musicdroidlib.parsers.TopArtistParser;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

public class TopArtistTask extends AsyncTask<Long, Void, Void> {

    private BasicHandler handler;

    public TopArtistTask(BasicHandler handler) {
        this.handler = handler;
    }

    @Override
    protected Void doInBackground(Long... placeID) {
        MyDbAccess myDbAccess = new MyDbAccess();

        // Atualiza a base de dados
        // TODO: É necessario limitar o numero de chamadas à API
        TopArtistParser topArtistParser = new TopArtistParser(myDbAccess.getWritableDatabase());
        topArtistParser.getTopArtists(placeID[0]);

        // Executar o handler
        if (this.handler != null) {
            this.handler.run();
        }

        return null;
    }
}

