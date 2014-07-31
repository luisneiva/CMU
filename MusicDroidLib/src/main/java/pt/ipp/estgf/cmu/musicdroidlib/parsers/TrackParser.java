package pt.ipp.estgf.cmu.musicdroidlib.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pt.ipp.estgf.cmu.musicdroidlib.Track;

public class TrackParser extends DefaultParser {
	
	public static final String GETTRACK_BY_NAME_URL = "track.getInfo&artist=";
	public static final String GETTRACK_BY_MBID_URL = "track.getInfo&mbid=";
		
	private boolean in_artist = false;
	
	private boolean in_album = false;
	private boolean in_album_image_small = false;
	private boolean in_album_image_medium = false;
	private boolean in_album_image_large = false;

	private boolean in_top_tags = false;
	private boolean in_tag = false;
	private boolean in_wiki = false;
	
	private Track mTrack;
	
	private String text = "";

	public Track getTrack(String track_mbid, String artist_name, String track_name) {
	
		if(track_mbid != null)
			request(GETTRACK_BY_MBID_URL + track_mbid, this);
		else
			request(GETTRACK_BY_NAME_URL + artist_name + "&track=" + track_name, this);
		
		return mTrack;
	}	
	
	@Override
	public void startDocument() throws SAXException {
		mTrack = new Track();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(localName.equals("lfm") && !attributes.getValue("status").equals("ok")) {
			mTrack = null;
		}	
		
		if (localName.equals("artist")) {
			in_artist = true;
		}
		
		if (localName.equals("album")) {
			in_album = true;
		}
		
		if (in_album && localName.equals("image") && attributes.getValue("size").equals("small")) {
			in_album_image_small = true;
		}
		
		if (in_album && localName.equals("image") && attributes.getValue("size").equals("medium")) {
			in_album_image_medium = true;
		}
		
		if (in_album && localName.equals("image") && attributes.getValue("size").equals("large")) {
			in_album_image_large = true;
		}
		
		if(localName.equals("toptags")) {
			in_top_tags = true;
		}
		
		if(in_top_tags && localName.equals("tag")) {
			in_tag = true;
		}
				
		if (localName.equals("wiki")) {
			in_wiki = true;
		}
	}	
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!in_artist && !in_top_tags && localName.equals("name")) {
			mTrack.setName(text);
		}
		
		if (localName.equals("mbid")) {
			mTrack.setMBID(text);
		}
		
		if (localName.equals("url")) {
			mTrack.setUrl(text);
		}
		
		if (localName.equals("duration")) {
			mTrack.setDuration(Long.valueOf(text));
		}
		
		if (localName.equals("playcount")) {
			mTrack.setPlaycount(Integer.valueOf(text));
		}
		
		if (localName.equals("artist")) {
			in_artist = false;
		}
		
		if (in_artist && localName.equals("mbid")) {
			mTrack.setArtistId(text);
		}
		
		if (in_artist && localName.equals("name")) {
			mTrack.setArtistName(text);
		}
		
		if (localName.equals("album")) {
			in_album = false;
		}
		
		if (in_album && localName.equals("title")) {
			mTrack.setAlbumTitle(text);
		}
		
		if (in_album && localName.equals("mbid")) {
			mTrack.setAlbumMBID(text);
		}
		
		if (in_album && localName.equals("image") && in_album_image_small) {
			mTrack.setAlbumImageSmall(text);
			in_album_image_small = false;
		}
		
		if (in_album && localName.equals("image") && in_album_image_medium) {
			mTrack.setAlbumImageMedium(text);
			in_album_image_medium = false;
		}
		
		if (in_album && localName.equals("image") && in_album_image_large) {
			mTrack.setAlbumImageLarge(text);
			in_album_image_large = false;
		}
		
		if(localName.equals("toptags")) {
			in_top_tags = false;
		}
		
		if(in_top_tags && localName.equals("tag")) {			
			in_tag = false;
		}
		
		if (in_tag && localName.equals("name")) {
			mTrack.getTags().add(text);
		}
		
		if (localName.equals("wiki")) {
			in_wiki = false;
		}
		
		if (in_wiki && localName.equals("summary")) {
			mTrack.setSummary(text);
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
