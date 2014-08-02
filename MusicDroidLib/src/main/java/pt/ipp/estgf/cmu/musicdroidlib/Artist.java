package pt.ipp.estgf.cmu.musicdroidlib;

import java.util.ArrayList;

public class Artist {
	
	private String mbid;
	private String name;
	private String url;
	
	private String image_small;
	private String image_medium;
	private String image_large;

	private ArrayList<String> similar_artists = new ArrayList<String>();
	private ArrayList<String> tags = new ArrayList<String>();
	
	private String summary;
	
	public Artist() { }
	
	public Artist(String mbid, String name, String url, String image_small, String image_medium, String image_large, ArrayList<String> similar_artists, ArrayList<String> tags, String summary) {
		this.mbid = mbid;
		this.name = name;
		this.url = url;
		
		this.image_small = image_small;
		this.image_medium = image_medium;
		this.image_large = image_large;

		this.similar_artists = similar_artists;
        this.tags = tags;
		
		this.summary = summary;		
	}

	public String getMBID() {
		return mbid;
	}

	public void setMBID(String mbid) {
		this.mbid = mbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageSmall() {
		return image_small;
	}

	public void setImageSmall(String image_small) {
		this.image_small = image_small;
	}

	public String getImageMedium() {
		return image_medium;
	}

	public void setImageMedium(String image_medium) {
		this.image_medium = image_medium;
	}

	public String getImageLarge() {
		return image_large;
	}

	public void setImageLarge(String image_large) {
		this.image_large = image_large;
	}

	public ArrayList<String> getSimilarArtists() {
		return similar_artists;
	}

	public void setSimilarArtists(ArrayList<String> similar_artists) {
		this.similar_artists = similar_artists;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Override
	public String toString() {
		String tmp = "";
		
		tmp += mbid + "\n";
		tmp += name + "\n";
		tmp += url + "\n";
		tmp += image_small + "\n";
		tmp += image_medium + "\n";
		tmp += image_large + "\n";
		tmp += summary + "\n";
		
		for(int i=0; i< similar_artists.size(); i++) {
			tmp += similar_artists.get(i) + "\n";
		}
		
		for(int i=0; i< tags.size(); i++) {
			tmp += tags.get(i) + "\n";
		}
		
		return tmp;
	}
}
