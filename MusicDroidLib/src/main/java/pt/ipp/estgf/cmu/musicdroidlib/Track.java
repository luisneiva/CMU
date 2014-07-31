package pt.ipp.estgf.cmu.musicdroidlib;

import java.util.ArrayList;

public class Track {

    private String mbid;
    private String name;
    private String url;
    private long duration;
    private int playcount;

    private String artist_name;
    private String artist_id;

    private String album_title;
    private String album_mbid;
    private String album_image_small;
    private String album_image_medium;
    private String album_image_large;

    private ArrayList<String> tags = new ArrayList<String>();

    private String summary;

    public Track() { }

    public Track(String mbid, String name, String url, long duration, int playcount, String artist_id,
                 String artist_name, String album_title, String album_mbid, String album_image_small, String album_image_medium,
                 String album_image_large, ArrayList<String> tags, String summary) {

        this.mbid = mbid;
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.playcount = playcount;

        this.artist_id = artist_id;
        this.artist_name = artist_name;

        this.album_title = album_title;
        this.album_mbid = album_mbid;
        this.album_image_small = album_image_small;
        this.album_image_medium = album_image_medium;
        this.album_image_large = album_image_large;

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public String getArtistName() {
        return artist_name;
    }

    public void setArtistName(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getArtistId() {
        return artist_id;
    }

    public void setArtistId(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getAlbumMBID() {
        return album_mbid;
    }

    public void setAlbumMBID(String album_mbid) {
        this.album_mbid = album_mbid;
    }

    public String getAlbumTitle() {
        return album_title;
    }

    public void setAlbumTitle(String album_title) {
        this.album_title = album_title;
    }

    public String getAlbumImageSmall() {
        return album_image_small;
    }

    public void setAlbumImageSmall(String album_image_small) {
        this.album_image_small = album_image_small;
    }

    public String getAlbumImageMedium() {
        return album_image_medium;
    }

    public void setAlbumImageMedium(String album_image_medium) {
        this.album_image_medium = album_image_medium;
    }

    public String getAlbumImageLarge() {
        return album_image_large;
    }

    public void setAlbumImageLarge(String album_image_large) {
        this.album_image_large = album_image_large;
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
        tmp += duration + "\n";
        tmp += playcount + "\n";
        tmp += artist_id + "\n";
        tmp += artist_name + "\n";
        tmp += album_title + "\n";
        tmp += album_image_small + "\n";
        tmp += album_image_medium + "\n";
        tmp += album_image_large + "\n";
        tmp += summary + "\n";

        for(int i=0; i< tags.size(); i++) {
            tmp += tags.get(i) + "\n";
        }

        return tmp;
    }
}