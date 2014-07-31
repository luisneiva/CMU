package pt.ipp.estgf.cmu.musicdroidlib.parsers;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.cmu.musicdroidlib.TopArtist;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TopArtistParser extends DefaultParser {
	
	public static final String GETTOPARTISTS_URL = "geo.gettopartists&country=";
	
	private boolean in_artist = false;

	private boolean in_image_small = false;
	private boolean in_image_medium = false;
	private boolean in_image_large = false;
	
	private String text = "";
	
	private SQLiteDatabase db = null;
	
	private boolean isStatusOk = false;
	
	private TopArtist mTopArtist;
	private long place_id;
		
	public TopArtistParser(SQLiteDatabase db) {
		this.db = db;
	}

	public boolean getTopArtists(long place_id) {
		this.place_id = place_id;
		
		Place mPlace = Place.get(place_id, db);
		TopArtist.deleteAllForPlace(place_id, db);
	
		request(GETTOPARTISTS_URL + mPlace.getName(), this);
		
		return isStatusOk;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(localName.equals("lfm")) {			
			if(attributes.getValue("status").equals("ok")) {
				Log.d("TAG", "Status: " + attributes.getValue("status"));
				isStatusOk = true;
			} else {
				Log.d("TAG", "Status: " + attributes.getValue("status"));
				isStatusOk = false;
				mTopArtist = null;
			}
		}
		
		if (localName.equals("artist")) {
			in_artist = true;
			mTopArtist = new TopArtist();
			mTopArtist.setRank(Integer.valueOf(attributes.getValue("rank")));
		}
			
		if (localName.equals("image") && attributes.getValue("size").equals("small")) {
			in_image_small = true;
		}
		
		if (localName.equals("image") && attributes.getValue("size").equals("medium")) {
			in_image_medium = true;
		}
		
		if (localName.equals("image") && attributes.getValue("size").equals("large")) {
			in_image_large = true;
		}
		
	}	
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(localName.equals("artist")) {
			if(mTopArtist != null) {
				mTopArtist.setIdPlace(place_id);
				mTopArtist.create(db);
			}
		}
		
		if (in_artist && localName.equals("name")) {
			mTopArtist.setArtistName(text);
		}
		
		if (in_artist && localName.equals("mbid")) {
			mTopArtist.setArtistMBID(text);
		}
		
		if (localName.equals("url")) {
			mTopArtist.setURL(text);
		}
		
		if (localName.equals("image") && in_image_small) {
			mTopArtist.setImageSmall(text);
			in_image_small = false;
		}
		
		if (localName.equals("image") && in_image_medium) {
			mTopArtist.setImageMedium(text);
			in_image_medium = false;
		}
		
		if (localName.equals("image") && in_image_large) {
			mTopArtist.setImageLarge(text);
			in_image_large = false;
		}		
		
		text = "";
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String tmp = new String(ch, start, length).trim();
		
		if(tmp == null || tmp.equals("") || tmp.equals("null"))
			return;
		else
			text += tmp;
	}
}
