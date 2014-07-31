package pt.ipp.estgf.cmu.musicdroidlib.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;

public class TopTrackParser extends DefaultParser {

	public static final String GETTOPTRACKS_URL = "geo.gettoptracks&country=";
		
	private boolean in_artist = false;

	private boolean in_image_small = false;
	private boolean in_image_medium = false;
	private boolean in_image_large = false;
	
	private String text = "";
	
	private SQLiteDatabase db = null;
	
	private boolean isStatusOk = false;
	
	private TopTrack mTopTrack;
	private long place_id;
	
	public TopTrackParser(SQLiteDatabase db) {
		this.db = db;		
	}

	public boolean getTopTracks(long place_id) {
		this.place_id = place_id;
		
		Place mPlace = Place.get(place_id, db);
		TopTrack.deleteAllForPlace(place_id, db);
	
		request(GETTOPTRACKS_URL + mPlace.getName(), this);
		
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
				mTopTrack = null;
			}
		}
		
		if (localName.equals("track")) {
			mTopTrack = new TopTrack();
			mTopTrack.setRank(Integer.valueOf(attributes.getValue("rank")));
		}
		
		
		if (localName.equals("artist")) {
			in_artist = true;
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
		
		if(localName.equals("track")) {
			if(mTopTrack != null) {
				mTopTrack.setIdPlace(place_id);
				mTopTrack.create(db);
			}
		}
		
		if (!in_artist && localName.equals("name")) {
			mTopTrack.setName(text);
		}
		
		if (localName.equals("duration")) {
			mTopTrack.setDuration(Long.valueOf(text));
		}
		
		if (localName.equals("url")) {
			mTopTrack.setURL(text);
		}
		
		if (localName.equals("artist")) {
			in_artist = false;
		}
		
		if (in_artist && localName.equals("name")) {
			mTopTrack.setArtistName(text);
		}
		
		if (in_artist && localName.equals("mbid")) {
			mTopTrack.setArtistMBID(text);
		}
		
		if (localName.equals("image") && in_image_small) {
			mTopTrack.setImageSmall(text);
			in_image_small = false;
		}
		
		if (localName.equals("image") && in_image_medium) {
			mTopTrack.setImageMedium(text);
			in_image_medium = false;
		}
		
		if (localName.equals("image") && in_image_large) {
			mTopTrack.setImageLarge(text);
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
