package pt.ipp.estgf.nnmusicdroid.tasks;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.os.AsyncTask;

import pt.ipp.estgf.cmu.musicdroidlib.Track;
import pt.ipp.estgf.cmu.musicdroidlib.parsers.TrackParser;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

/**
 * Lição 07, ficheiro: 16_asynctask_threads_2013_11_08
 *
 * -- Em Android, existem pelo menos duas formas de lançarmos
 * novas linhas de execução paralelas (Threads)
 *     Classe AsyncTask (abstração que faz parte da API Android)
 *     Classe Thread (tradicionalmente utilizada em Java)
 * -- Na AsyncTask o código que executa na nova linha de execução
 * está no método doInBackground()
 *
 * slide 10
 *
 * Esta task é para carregar os detalhes da música seleccionada
 * a partir da API
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
