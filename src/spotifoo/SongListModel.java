package spotifoo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SongListModel {
    private ArrayList<Song> songList;
    private static final String FILE_NAME = "assets/data.txt";

    public SongListModel() {
        songList = new ArrayList<Song>();
    }

    public void addSong(String name, String artist, String album, String genre, String mp3FileName, String pngFileName) {
        songList.add((new Song(name, artist, album, genre, mp3FileName, pngFileName)));
    }

    public void loadFromFile() {
        BufferedReader reader;

        try {   // change this to try with resources
            reader = new BufferedReader(new FileReader("assets/data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
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
}
