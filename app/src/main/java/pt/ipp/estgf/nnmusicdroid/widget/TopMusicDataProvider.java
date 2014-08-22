package pt.ipp.estgf.nnmusicdroid.widget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;
import pt.ipp.estgf.cmu.musicdroidlib.Track;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

public class TopMusicDataProvider extends ContentProvider {

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
        // @TODO: Obter o ID do place a mostrar
        int placeID = 4;

        // Carrega os dados para a lista
        MyDbAccess dbHelper = new MyDbAccess(getContext());
        TopTrack.getForPlace(placeID, topMusics, dbHelper.getReadableDatabase());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        assert(uri.getPathSegments().isEmpty());

        final MatrixCursor c = new MatrixCursor(new String[]{"Name"});

        for (int i = 0; i < 5; i++) {
            c.addRow(new Object[]{this.topMusics.get(i).getName()});
        }

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.

        return 1;
    }
}
