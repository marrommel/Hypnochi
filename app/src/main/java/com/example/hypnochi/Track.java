package com.example.hypnochi;

public class Track {

    private String artist;
    private String title;
    private int image;

    public Track(String artist, String title, int image) {
        this.artist = artist;
        this.title = title;
        this.image = image;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
