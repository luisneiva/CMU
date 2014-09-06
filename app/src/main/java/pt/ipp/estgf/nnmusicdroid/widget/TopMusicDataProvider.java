package pt.ipp.estgf.nnmusicdroid.widget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;
import pt.ipp.estgf.cmu.musicdroidlib.Track;
import pt.ipp.estgf.nnmusicdroid.MainActivity;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;
import pt.ipp.estgf.nnmusicdroid.tasks.TopArtistTask;

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
        // TODO: Implement this to handle requests for the MIME type of the data
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
        int placeID = 1;

        // Carrega os dados para a lista
        MyDbAccess dbHelper = new MyDbAccess(getContext());
        TopTrack.getForPlace(placeID, topMusics, dbHelper.getReadableDatabase());
        dbHelper.close();
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        assert(uri.getPathSegments().isEmpty());

        final MatrixCursor c = new MatrixCursor(new String[]{TopTrack.NAME});

        for (int i = 0; i < 5; i++) {
            if (this.topMusics.size() <= i) {
                return c;
            }

            final TopTrack data = this.topMusics.get(i);
            c.addRow(new Object[]{data.getName()});
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
