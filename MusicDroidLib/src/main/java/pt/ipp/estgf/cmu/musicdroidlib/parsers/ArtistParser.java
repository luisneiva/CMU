package pt.ipp.estgf.cmu.musicdroidlib.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;
import pt.ipp.estgf.cmu.musicdroidlib.Artist;

public class ArtistParser extends DefaultParser {
	
	public static final String GETARTIST_BY_NAME_URL = "artist.getinfo&artist=";
	public static final String GETARTIST_BY_MBID_URL = "artist.getinfo&mbid=";
			
	private boolean in_image_small = false;
	private boolean in_image_medium = false;
	private boolean in_image_large = false;
	
	private boolean in_similar = false;
	private boolean in_similar_artists = false;

	private boolean in_tags = false;
	private boolean in_tag = false;
	private boolean in_bio = false;
	
	private Artist mArtist;
	
	private String text = "";

	public Artist getArtist(String artist_name, String artist_mbid) {
	
		if(artist_name != null)
			request(GETARTIST_BY_NAME_URL + artist_name, this);
		else
			request(GETARTIST_BY_MBID_URL + artist_mbid, this);
		
		return mArtist;
	}	
	
	@Override
	public void startDocument() throws SAXException {
		mArtist = new Artist();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(localName.equals("lfm") && !attributes.getValue("status").equals("ok")) {
			Log.d("TAG", "Status: " + attributes.getValue("status"));
			mArtist = null;
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
		
		if (localName.equals("similar")) {
			in_similar = true;
		}
		
		if (in_similar && localName.equals("artist")) {
			in_similar_artists = true;
		}
		
		if (localName.equals("tags")) {
			in_tags = true;
		}
		
		if (in_tags && localName.equals("tag")) {
			in_tag = true;
		}
		
		if (localName.equals("bio")) {
			in_bio = true;
		}
	}	
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!in_similar && !in_tags && localName.equals("name")) {
			mArtist.setName(text);
		}
		
		if (localName.equals("mbid")) {
			mArtist.setMBID(text);
		}
		
		if (localName.equals("url")) {
			mArtist.setUrl(text);
		}
		
		if (localName.equals("image") && in_image_small) {
			mArtist.setImageSmall(text);
			in_image_small = false;
		}
		
		if (localName.equals("image") && in_image_medium) {
			mArtist.setImageMedium(text);
			in_image_medium = false;
		}
		
		if (localName.equals("image") && in_image_large) {
			mArtist.setImageLarge(text);
			in_image_large = false;
		}
		
		if (localName.equals("similar")) {
			in_similar = false;
		}
		
		if (in_similar && localName.equals("artist")) {
			in_similar_artists = false;
		}
		
		if (in_similar_artists && localName.equals("name")) {
			mArtist.getSimilarArtists().add(text);
		}
		
		if (localName.equals("tags")) {
			in_tags = false;
		}
		
		if (in_tags && localName.equals("tag")) {
			in_tag = false;
		}
		
		if (in_tag && localName.equals("name")) {
			mArtist.getTags().add(text);
		}
		
		if (localName.equals("bio")) {
			in_bio = false;
		}
		
		if (in_bio && localName.equals("summary")) {
			mArtist.setSummary(text);
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
