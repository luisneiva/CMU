package pt.ipp.estgf.nnmusicdroid.widget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;
import pt.ipp.estgf.nnmusicdroid.LocationUtils;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;
import pt.ipp.estgf.nnmusicdroid.model.MyPlace;
import pt.ipp.estgf.nnmusicdroid.other.Utils;
import pt.ipp.estgf.nnmusicdroid.tasks.BasicHandler;
import pt.ipp.estgf.nnmusicdroid.tasks.MusicTask;

public class TopMusicDataProvider extends ContentProvider {

    public static Context globalContext;

    public static final Uri CONTENT_URI = Uri.parse("content://pt.ipp.estgf.nnmusicdroid.widget.provider");
    private final ArrayList<TopTrack> topMusics = new ArrayList<TopTrack>();

    public TopMusicDataProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        // DIsponibiliza o contexto
        globalContext = getContext();

        // Update data
        updateData();

        return true;
    }

    private void updateData() {
        MyDbAccess dbHelper = new MyDbAccess(Utils.getContext());

        MyPlace tempPlace = null;
        long placeID = 0l;

        if (Utils.isToUserCurrentPlace()) {
            // Obtem o Place correspondente à localização atual
            LocationUtils locationUtils = new LocationUtils(getContext());
            tempPlace = locationUtils.getCurrentPlace();

            // Guarda o place na base de dados
            MyPlace.delete(0l, dbHelper.getWritableDatabase());

            tempPlace.create(dbHelper.getWritableDatabase());

            placeID = 0l;
        } else {
            placeID = Utils.getSelectedDefaultPlace();
        }

        final long placeIDF = placeID;

        dbHelper.close();


        // Carrega os dados para a lista
        //MyDbAccess dbHelper = new MyDbAccess(getContext());
        //TopTrack.getForPlace(place.getId(), topMusics, dbHelper.getReadableDatabase());
        //dbHelper.close();

        MusicTask task = new MusicTask(new BasicHandler() {
            @Override
            public void run() {
                // Carrega os dados para a lista
                MyDbAccess dbHelper = new MyDbAccess(Utils.getContext());
                TopTrack.getForPlace(placeIDF, topMusics, dbHelper.getReadableDatabase());
                dbHelper.close();
            }
        });

        // Inicia a task
        task.execute(placeID);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        assert(uri.getPathSegments().isEmpty());

        final MatrixCursor c = new MatrixCursor(new String[]{TopTrack.NAME, TopTrack.ARTIST_NAME});

        for (int i = 0; i < 5; i++) {
            if (this.topMusics.size() <= i) {
                return c;
            }

            final TopTrack data = this.topMusics.get(i);
            c.addRow(new Object[]{data.getName(), data.getArtistName()});
        }

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        assert(uri.getPathSegments().size() == 1);

        // Update data
        updateData();

        getContext().getContentResolver().notifyChange(uri, null);

        return 1;
    }
}
