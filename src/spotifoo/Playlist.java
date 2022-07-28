// Project: Spotifoo, a command line music player.
// Java summer course 2022.
// Author: Malla Grönqvist
// Playlist.java
//*****************************************************************************

package spotifoo;

import java.util.ArrayList;

public class Playlist {
    ArrayList<Song> playlist;
    private String name;

    public Playlist() {
        name = "";
        playlist = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Song> getPlaylist() {
        return playlist;
    }

    public void addSong(Song song) {
        playlist.add(song);
    }

    public void removeSong(Song song) {
        playlist.remove(song);
    }

    public Song getSong(int index) {
        Song song = null;
        try {
            song = playlist.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Song could not be fetched from the playlist.");
        }
        return song;
    }
}
