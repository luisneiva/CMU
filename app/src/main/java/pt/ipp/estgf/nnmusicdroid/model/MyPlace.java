package pt.ipp.estgf.nnmusicdroid.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MyPlace extends pt.ipp.estgf.cmu.musicdroidlib.Place {

    public MyPlace(long id, String name, float latitude, float longitude) {
        super(id, name, latitude, longitude);
    }

    public long create(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(super.ID, this.getId());
        cv.put(NAME, this.getName());
        cv.put(LATITUDE, this.getLatitude());
        cv.put(LONGITUDE, this.getLongitude());

        long rowId = db.insertOrThrow(TBL_NAME, null, cv);
        if(rowId < 0) throw new RuntimeException("Erro ao inserir Place na base de dados!");

        return this.getId();
    }

}
