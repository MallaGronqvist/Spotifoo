package spotifoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SpotifooModel {
    private final ArrayList<Song> songList;
    private static final String FILE_NAME = "assets/data.txt";

    public SpotifooModel() {
        songList = new ArrayList<>();
    }

    public void loadFromFile() {
        try (FileReader fileReader = new FileReader(FILE_NAME);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] stringArray = line.split(",");
                Song song = new Song(stringArray);
                songList.add(song);
            }
        } catch (IOException e) {
            System.out.println("List of songs could not be loaded from file.");
            System.exit(0);
        }
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public ArrayList<String> getArtists() {
        ArrayList<String> artists = new ArrayList<>();
        for (Song song : songList) {
            String artist = song.getArtist();
            if (!artists.contains(artist)) {
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
            if (!albums.contains(album)) {
                albums.add(album);
            }
        }
        Collections.sort(albums);
        return albums;
    }
}
