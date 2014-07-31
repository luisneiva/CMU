package pt.ipp.estgf.cmu.musicdroidlib;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TopTrack {
	
//	id_top_artist INTEGER PRIMARY KEY AUTOINCREMENT, id_place INTEGER, rank INTEGER,
//	name VARCHAR(75), duration INTEGER, url VARCHAR(255), artist_mbid VARCHAR(60), artist_name VARCHAR(75), image_small VARCHAR(200), image_medium VARCHAR(200), 
//  image_large VARCHAR(200)
	
	private long id;
	private long id_place;
	private int rank;
	private Track track;

	public final static String TBL_NAME = "tbl_top_track";
	public final static String ID = "id_top_track";
	public final static String ID_PLACE = "id_place";
	public final static String RANK = "rank";
	
	public final static String NAME = "name";
	public final static String DURATION = "duration";
	public final static String URL = "url";
	public final static String ARTIST_MBID = "artist_mbid";
	public final static String ARTIST_NAME = "artist_name";
	public final static String IMAGE_SMALL = "image_small";
	public final static String IMAGE_MEDIUM = "image_medium";
	public final static String IMAGE_LARGE = "image_large";
	
	public final static String[] FIELDS = { ID, ID_PLACE, RANK, NAME, DURATION, URL, ARTIST_MBID, ARTIST_NAME, IMAGE_SMALL, IMAGE_MEDIUM, IMAGE_LARGE };
	
	public static ArrayList<TopTrack> getForPlace(long place_id, ArrayList<TopTrack> alist, SQLiteDatabase db) {
		ArrayList<TopTrack> tlist = (alist == null ? new ArrayList<TopTrack>() : alist);

		tlist.clear();
		Cursor c = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + ID_PLACE + "=" + place_id + " ORDER BY " + RANK + " ASC", null);
		if (c.moveToFirst()) {
			do {
				tlist.add(new TopTrack(c.getLong(0), c.getLong(1), c.getInt(2), c.getString(3), c.getLong(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9),
						 c.getString(10)));						
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}
	
	public void create(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		cv.put(ID_PLACE, id_place);
		cv.put(RANK, rank);
		cv.put(NAME, track.getName());
		cv.put(DURATION, track.getDuration());
		cv.put(URL, track.getUrl());
		cv.put(ARTIST_MBID, track.getArtistId());
		cv.put(ARTIST_NAME, track.getArtistName());
		cv.put(IMAGE_SMALL, track.getAlbumImageSmall());
		cv.put(IMAGE_MEDIUM, track.getAlbumImageMedium());
		cv.put(IMAGE_LARGE, track.getAlbumImageLarge());

		long rowId = db.insert(TBL_NAME, null, cv);
		if(rowId < 0) throw new RuntimeException("Erro ao inserir TopTrack na base de dados!");
	}

	public static void create(SQLiteDatabase db, long id_place, int rank, String name, long duration, String url, String artist_mbid, String artist_name, String image_small, String image_medium, String image_large) {
		ContentValues cv = new ContentValues();
		cv.put(ID_PLACE, id_place);
		cv.put(RANK, rank);
		cv.put(NAME, name);
		cv.put(DURATION, duration);
		cv.put(URL, url);
		cv.put(ARTIST_MBID, artist_mbid);
		cv.put(ARTIST_NAME, artist_name);
		cv.put(IMAGE_SMALL, image_small);
		cv.put(IMAGE_MEDIUM, image_medium);
		cv.put(IMAGE_LARGE, image_large);

		long rowId = db.insert(TBL_NAME, null, cv);
		if(rowId < 0) throw new RuntimeException("Erro ao inserir TopTrack na base de dados!");
	}

	public static void delete(long id, SQLiteDatabase db) {
		db.delete(TBL_NAME, ID + "=" + id, null);
	}
	
	public static void deleteAllForPlace(long id_place, SQLiteDatabase db) {
		db.delete(TBL_NAME, ID_PLACE + "=" + id_place, null);
	}
	
	public TopTrack() {
		track = new Track();
	}
	
	public TopTrack(long id, long id_place, int rank, String name, long duration, String url, String artist_mbid, String artist_name, String image_small, String image_medium, String image_large) {
		this.id = id;
		this.id_place = id_place;
		this.rank = rank;
		
		track = new Track();
		track.setName(name);
		track.setDuration(duration);
		track.setUrl(url);
		track.setArtistId(artist_mbid);
		track.setArtistName(artist_name);
		track.setAlbumImageSmall(image_small);
		track.setAlbumImageMedium(image_medium);
		track.setAlbumImageLarge(image_large);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPlace() {
		return id_place;
	}

	public void setIdPlace(long id_place) {
		this.id_place = id_place;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public String getName() {
		return track.getName();
	}
	
	public void setName(String name) {
		track.setName(name);
	}
	
	public long getDuration() {
		return track.getDuration();
	}
	
	public void setDuration(long duration) {
		track.setDuration(duration);
	}
	
	public String getURL() {
		return track.getUrl();
	}
	
	public void setURL(String url) {
		track.setUrl(url);
	}
	
	public String getArtistMBID() {
		return track.getArtistId();
	}
	
	public void setArtistMBID(String artist_mbid) {
		track.setArtistId(artist_mbid);
	}
	
	public String getArtistName() {
		return track.getArtistName();
	}
	
	public void setArtistName(String artist_name) {
		track.setArtistName(artist_name);
	}
	
	public String getImageSmall() {
		return track.getAlbumImageSmall();
	}
	
	public void setImageSmall(String image_small) {
		track.setAlbumImageSmall(image_small);
	}
	
	public String getImageMedium() {
		return track.getAlbumImageMedium();
	}
	
	public void setImageMedium(String image_medium) {
		track.setAlbumImageMedium(image_medium);
	}
	
	public String getImageLarge() {
		return track.getAlbumImageLarge();
	}
	
	public void setImageLarge(String image_large) {
		track.setAlbumImageLarge(image_large);
	}	
	
	@Override
	public String toString() {
		String tmp = "";
	
		tmp += id + "\n";
		tmp += id_place + "\n";
		tmp += rank + "\n";
		tmp += track.getName() + "\n";
		tmp += track.getDuration() + "\n";
		tmp += track.getUrl() + "\n";
		tmp += track.getArtistId() + "\n";
		tmp += track.getArtistName() + "\n";
		tmp += track.getAlbumImageSmall() + "\n";
		tmp += track.getAlbumImageMedium() + "\n";
		tmp += track.getAlbumImageLarge() + "\n";
		
		return tmp;
	}
}
