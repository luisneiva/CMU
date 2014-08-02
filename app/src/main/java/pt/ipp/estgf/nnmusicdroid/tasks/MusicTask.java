package pt.ipp.estgf.nnmusicdroid.tasks;

import android.os.AsyncTask;

import pt.ipp.estgf.cmu.musicdroidlib.parsers.TopTrackParser;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

public class MusicTask extends AsyncTask<Long, Void, Void> {

    private BasicHandler handler;

    public MusicTask(BasicHandler handler) {
        this.handler = handler;
    }

    @Override
    protected Void doInBackground(Long... placeID) {
        MyDbAccess myDbAccess = new MyDbAccess();

        // Atualiza a base de dados
        // TODO: É necessario limitar o numero de chamadas à API
        TopTrackParser topTrackParser = new TopTrackParser(myDbAccess.getWritableDatabase());
        topTrackParser.getTopTracks(placeID[0]);

        // Executar o handler
        if (this.handler != null) {
            this.handler.run();
        }

        return null;
    }
}
