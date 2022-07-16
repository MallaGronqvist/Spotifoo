package spotifoo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SongListModel {
    private ArrayList<Song> songList;
    private static final String FILE_NAME = "assets/data.txt";

    public SongListModel() {
        songList = new ArrayList<Song>();
    }

    public void loadFromFile() {
        BufferedReader reader;

        try {   // change this to try with resources
            reader = new BufferedReader(new FileReader("assets/data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String [] stringArray = line.split(",");
                Song song = new Song(stringArray);
                songList.add(song);
            }
            reader.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
    }

    public ArrayList<Song>getSongList() {
        return songList;
    }

    public ArrayList<String>getArtists() {
        ArrayList<String> artists = new ArrayList<>();
        for (Song song : songList) {
            String artist = song.getArtist();
            if(!artists.contains(artist)){
                artists.add(artist);
            }
        }
        Collections.sort(artists);
    return artists;
    }
    public ArrayList<String> getAlbums() {
        ArrayList<String> albums = new ArrayList<>();

        for (Song song : songList) {
            String album = song.getAlbum();
            if(!albums.contains(album)){
                albums.add(album);
            }
        }
        Collections.sort(albums);
        return albums;
    }
}
