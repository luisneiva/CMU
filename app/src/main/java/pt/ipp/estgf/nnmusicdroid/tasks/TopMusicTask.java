package pt.ipp.estgf.nnmusicdroid.tasks;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.os.AsyncTask;

import pt.ipp.estgf.cmu.musicdroidlib.parsers.TopTrackParser;
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
 *  * Esta task é para carregar os TOPS das músicas
 * a partir da API
 */
public class TopMusicTask extends AsyncTask<Long, Void, Void> {

    private BasicHandler handler;

    public TopMusicTask(BasicHandler handler) {
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
