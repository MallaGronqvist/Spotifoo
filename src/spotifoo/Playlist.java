package spotifoo;

import java.util.ArrayList;

public class Playlist {
    private String name;
    ArrayList<Song> playlist;

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

    public void setPlaylist(ArrayList<Song> playlist) {
        this.playlist = playlist;
    }

    public void addSong(Song song){
        playlist.add(song);
    }

    public void removeSong(Song song){
        playlist.remove(song);
    }
}
