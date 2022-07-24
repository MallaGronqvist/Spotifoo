package spotifoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MusicPlayerModel {
    private final ArrayList<Song> songList;
    private final Playlist playlist;
    private static final String FILE_NAME = "assets/data.txt";

    public MusicPlayerModel() {
        songList = new ArrayList<>();
        playlist = new Playlist();
        playlist.setName("Unnamed");
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

    public Song getSongFromSongList(int index){
        index--;
        return songList.get(index);
    }

    public void addSongToPlaylist(Song song){
        playlist.addSong(song);
    }

    public Song getSongFromPlaylist(int index){
        index--;
        return playlist.getSong(index);
    }

    public void removeSongFromPlaylist(Song song){
        playlist.removeSong(song);
    }

    public ArrayList<String> getArtists() {
        ArrayList<String> artists = new ArrayList<>();

        for (Song song : songList) {
            if (!artists.contains(song.getArtist())) {
                artists.add(song.getArtist());
            }
        }
        Collections.sort(artists);
        return artists;
    }

    public ArrayList<String> getAlbums() {
        ArrayList<String> albums = new ArrayList<>();

        for (Song song : songList) {
            if (!albums.contains(song.getAlbum())) {
                albums.add(song.getAlbum());
            }
        }
        Collections.sort(albums);
        return albums;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public ArrayList<Song> getSongsByArtist(String chosenArtist){
        ArrayList<Song> songsByArtist = new ArrayList<>();
        for (Song song : songList) {
            if (song.getArtist().equals(chosenArtist)) {
                songsByArtist.add(song);
            }
        }
        return songsByArtist;
    }

    public ArrayList<Song> getSongsByAlbum(String chosenAlbum){
        ArrayList<Song> songsByAlbum = new ArrayList<>();

        for (Song song : songList) {
            if (song.getAlbum().equals(chosenAlbum)) {
                songsByAlbum.add(song);
            }
        }
        return songsByAlbum;
    }

    public ArrayList<Song> getSongsByGenre(Genre chosenGenre){
        ArrayList<Song> songsByGenre = new ArrayList<>();

        for (Song song : songList) {
            if (song.getGenre() == chosenGenre) {
                songsByGenre.add(song);
            }
        }
        return songsByGenre;
    }

    public ArrayList<Song> getSongsBySearchName(String searchTerm) {
        ArrayList<Song> songsBySearchName = new ArrayList<>();

        for (Song song : songList) {
            if (song.getName().toUpperCase().contains(searchTerm.toUpperCase())) {
                songsBySearchName.add(song);
            }
        }
        return songsBySearchName;
    }

    public ArrayList<Song> getSongsByAnySearchTerm(String searchTerm) {
        ArrayList<Song> songsByAnySearchTerm = new ArrayList<>();

        for (Song song : songList) {
            if (song.getSearchableString().toUpperCase().contains(searchTerm.toUpperCase())) {
                songsByAnySearchTerm.add(song);
            }
        }
        return songsByAnySearchTerm;
    }
}
