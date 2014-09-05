package pt.ipp.estgf.nnmusicdroid.tasks;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.os.AsyncTask;

import pt.ipp.estgf.cmu.musicdroidlib.Artist;
import pt.ipp.estgf.cmu.musicdroidlib.parsers.TopArtistParser;
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
 *  * Esta task é para carregar os TOPS dos artistas
 * a partir da API
 */
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

