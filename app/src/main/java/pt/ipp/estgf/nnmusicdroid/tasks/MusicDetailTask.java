package pt.ipp.estgf.nnmusicdroid.tasks;

import android.os.AsyncTask;

import pt.ipp.estgf.cmu.musicdroidlib.Track;
import pt.ipp.estgf.cmu.musicdroidlib.parsers.TrackParser;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

/**
 * Created by luisteixeira on 02/08/14.
 */
public class MusicDetailTask extends AsyncTask<String, Void, Void>{

    private BasicHandler handler;
    private Track track = null;

    public MusicDetailTask(BasicHandler handler) { this.handler = handler; }


@Override
    protected Void doInBackground(String... data){
    MyDbAccess myDbAccess = new MyDbAccess();

    String artistName = data[0].replace(" ", "%20");
    String trackname = data[1].replace(" ", "%20");

    //Atualiza a base de dados
    // TODO: É necessario limitar o numero de chamadas à API
    TrackParser trackParser = new TrackParser();
    this.track = trackParser.getTrack(null, artistName, trackname);

    // Executar o handler
    if (this.handler != null){
        this.handler.run();
    }
    return null;
}
    public  Track getTrack() {return this.track; }
}
