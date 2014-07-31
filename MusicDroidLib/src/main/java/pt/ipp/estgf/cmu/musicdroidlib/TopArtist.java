package pt.ipp.estgf.cmu.musicdroidlib;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TopArtist {
	//id_top_artist INTEGER PRIMARY KEY AUTOINCREMENT, id_place INTEGER, rank INTEGER, artist_mbid VARCHAR(60), 
	//name VARCHAR(75) NOT NULL, url VARCHAR(255), image_small VARCHAR(200), image_medium VARCHAR(200), image_large VARCHAR(200));
	
	private long id;
	private long id_place;
	private int rank;
	private Artist artist;

	public final static String TBL_NAME = "tbl_top_artist";
	public final static String ID = "id_top_artist";
	public final static String ID_PLACE = "id_place";
	public final static String RANK = "rank";

	public final static String ARTIST_MBID = "artist_mbid";
	public final static String ARTIST_NAME = "artist_name";
	public final static String URL = "url";
	public final static String IMAGE_SMALL = "image_small";
	public final static String IMAGE_MEDIUM = "image_medium";
	public final static String IMAGE_LARGE = "image_large";
	
	public final static String[] FIELDS = { ID, ID_PLACE, RANK, ARTIST_MBID, ARTIST_NAME, URL, IMAGE_SMALL, IMAGE_MEDIUM, IMAGE_LARGE };
	
	public static ArrayList<TopArtist> getForPlace(long place_id, ArrayList<TopArtist> alist, SQLiteDatabase db) {
		ArrayList<TopArtist> tlist = (alist == null ? new ArrayList<TopArtist>() : alist);

		tlist.clear();
		Cursor c = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + ID_PLACE + "=" + place_id + " ORDER BY " + RANK + " ASC", null);
		if (c.moveToFirst()) {
			do {
				tlist.add(new TopArtist(c.getLong(0), c.getLong(1), c.getInt(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8)));						
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}
	
	public void create(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		cv.put(ID_PLACE, id_place);
		cv.put(RANK, rank);
		cv.put(ARTIST_MBID, artist.getMBID());
		cv.put(ARTIST_NAME, artist.getName());
		cv.put(URL, artist.getUrl());
		cv.put(IMAGE_SMALL, artist.getImageSmall());
		cv.put(IMAGE_MEDIUM, artist.getImageMedium());
		cv.put(IMAGE_LARGE, artist.getImageLarge());

		long rowId = db.insert(TBL_NAME, null, cv);
		if(rowId < 0) throw new RuntimeException("Erro ao inserir TopArtist na base de dados!");
	}

	public static void create(SQLiteDatabase db, long id_place, int rank, String artist_mbid, String artist_name, String url, String image_small, String image_medium, String image_large) {
		ContentValues cv = new ContentValues();
		cv.put(ID_PLACE, id_place);
		cv.put(RANK, rank);		
		cv.put(ARTIST_MBID, artist_mbid);
		cv.put(ARTIST_NAME, artist_name);
		cv.put(URL, url);
		cv.put(IMAGE_SMALL, image_small);
		cv.put(IMAGE_MEDIUM, image_medium);
		cv.put(IMAGE_LARGE, image_large);

		long rowId = db.insert(TBL_NAME, null, cv);
		if(rowId < 0) throw new RuntimeException("Erro ao inserir TopArtist na base de dados!");
	}

	public static void delete(long id, SQLiteDatabase db) {
		db.delete(TBL_NAME, ID + "=" + id, null);
	}
	
	public static void deleteAllForPlace(long id_place, SQLiteDatabase db) {
		db.delete(TBL_NAME, ID_PLACE + "=" + id_place, null);
	}
	
	public TopArtist() {
		artist = new Artist();
	}
	
	public TopArtist(long id, long id_place, int rank, String artist_mbid, String artist_name, String url, String image_small, String image_medium, String image_large) {
		this.id = id;
		this.id_place = id_place;
		this.rank = rank;
		
		artist = new Artist();		
		artist.setMBID(artist_mbid);
		artist.setName(artist_name);
		artist.setUrl(url);
		artist.setImageSmall(image_small);
		artist.setImageMedium(image_medium);
		artist.setImageLarge(image_large);
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
	
	public String getArtistMBID() {
		return artist.getMBID();
	}
	
	public void setArtistMBID(String artist_mbid) {
		artist.setMBID(artist_mbid);
	}
	
	public String getArtistName() {
		return artist.getName();
	}
	
	public void setArtistName(String artist_name) {
		artist.setName(artist_name);
	}
	
	public String getURL() {
		return artist.getUrl();
	}
	
	public void setURL(String url) {
		artist.setUrl(url);
	}
	
	public String getImageSmall() {
		return artist.getImageSmall();
	}
	
	public void setImageSmall(String image_small) {
		artist.setImageSmall(image_small);
	}
	
	public String getImageMedium() {
		return artist.getImageMedium();
	}
	
	public void setImageMedium(String image_medium) {
		artist.setImageMedium(image_medium);
	}
	
	public String getImageLarge() {
		return artist.getImageLarge();
	}
	
	public void setImageLarge(String image_large) {
		artist.setImageLarge(image_large);
	}
	
	@Override
	public String toString() {
		String tmp = "";
	
		tmp += id + "\n";
		tmp += id_place + "\n";
		tmp += rank + "\n";
		tmp += artist.getMBID() + "\n";
		tmp += artist.getName() + "\n";
		tmp += artist.getUrl() + "\n";
		tmp += artist.getImageSmall() + "\n";
		tmp += artist.getImageMedium() + "\n";
		tmp += artist.getImageLarge() + "\n";
		
		return tmp;
	}
}
