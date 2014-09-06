package pt.ipp.estgf.nnmusicdroid.other;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;
import pt.ipp.estgf.nnmusicdroid.tasks.TopArtistTask;
import pt.ipp.estgf.nnmusicdroid.tasks.TopMusicTask;

public final class UpdateService extends Service {

    private static final String TAG = "UpdateService";
    private final Handler handler = new Handler();

    public UpdateService() {
    }

    private Runnable updateAllPlacesData = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "A atualizar os dados...");

            // Abre a conecção à base de dados
            MyDbAccess db = new MyDbAccess(Utils.getContext());

            // Obtem a lista de todos os places
            ArrayList<Place> allPlaces = Place.getAll(null, db.getReadableDatabase());

            // Atualiza todos os places
            for (Place place : allPlaces) {
                // Atualiza o top de artistas
                TopArtistTask topArtistTask = new TopArtistTask(null);
                topArtistTask.execute(place.getId());

                // Atualiza o top de musicas
                TopMusicTask topMusicTask = new TopMusicTask(null);
                topMusicTask.execute(place.getId());
            }

            // Fecha a conhecção
            db.close();

            // Regista novamente o serviço
            handler.postDelayed(updateAllPlacesData, 50000);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Remove algum calback existente
        handler.removeCallbacks(updateAllPlacesData);

        // Adiciona de novo o callback (primeira vez, apenas 1 segundo)
        handler.postDelayed(updateAllPlacesData, 1000);

        // Assim o serviço corre até que seja mandado parar
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
