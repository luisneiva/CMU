package pt.ipp.estgf.cmu.musicdroidlib.parsers;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class DefaultParser extends DefaultHandler {

	public static final int HTTP_TIMEOUT = 6000;
	public static final String API_KEY = "";
	public static final String GENERIC_URL = "http://ws.audioscrobbler.com/2.0/?method=";
	
	public void request(String request_url, ContentHandler handler) {
		try {
			URL url = new URL(GENERIC_URL + request_url + "&api_key=" + API_KEY);
			Log.d("DefaultParser", url.toString());

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			// handler para lidar com o parsing do XML
			xr.setContentHandler(handler);

			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(HTTP_TIMEOUT);
			conn.setReadTimeout(HTTP_TIMEOUT);
			InputStream in = conn.getInputStream();
			xr.parse(new InputSource(in));

		} catch (Exception e) {
			StackTraceElement[] st = e.getStackTrace();
			Log.d("TopArtistParser", e.toString());
			for (StackTraceElement el : st)
				Log.d("TopArtistParser", el.toString());
		}
	}
}
