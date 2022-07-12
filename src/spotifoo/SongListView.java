package spotifoo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SongListView {

    private Menu mainMenu = new Menu();
//    private static final Scanner keyboard = new Scanner(System.in);

    public SongListView() {
        mainMenu.setMenuTitle("***Main menu***");
        mainMenu.addItem("Songs");
        mainMenu.addItem("Artists");
        mainMenu.addItem("Albums");
        mainMenu.addItem("Genres");
        mainMenu.addItem("Search");
    }

    public void printMainMenu(){
        mainMenu.printMenuItems();
    }

    public int getMainMenuChoice(){
        return mainMenu.getMenuChoice();
    }

    public void printSongs(ArrayList<Song> songList){
        int i = 1;
        for (Song song : songList) {
            System.out.println( "[" + i + "] " + song.getName());
            i++;
        }

        int index = getChoice();
            Song song = songList.get(index);
            System.out.println("Now playing" + song.getName());
            System.out.println(song.getMp3FileName());
            File file = new File("assets/songs/" + song.getMp3FileName());
            File file2 = new File("assets/albums/" +song.getPngFileName());

            try {
                Desktop.getDesktop().open(file);
                Desktop.getDesktop().open(file2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
/*
    public void printSongsByArtist(SongListModel songListModel){
        System.out.println("Enter name of artist:");
        Scanner keyboard = new Scanner(System.in);
        String artist;
        artist = keyboard.nextLine();

        ArrayList<Song> songsByArtist= new ArrayList<>();
        for (Song song : songListModel.getSongList()) {
            if (song.getArtist().equalsIgnoreCase(artist)){
                songsByArtist.add(song);
            }
        }

        printSongs(songsByArtist);
    }
*/

    public void printArtists(SongListModel songListModel){
        ArrayList<String> artists = songListModel.getArtists();

        int i = 1;
        for(String artist : artists) {
            System.out.println("[" + i + "] " + artist);
            i++;
        }

        int index = getChoice();
        String chosenArtist = artists.get(index);

        ArrayList<Song>songsByArtist = new ArrayList<>();
        for(Song song : songListModel.getSongList()){
            if(song.getArtist().equals(chosenArtist)){
                songsByArtist.add(song);
            }
        }

        printSongs(songsByArtist);
    }
    public void filterByAlbum(SongListModel songListModel){
        ArrayList<String>albums = songListModel.getAlbums();

        int i = 1;
        for(String album : albums) {
            System.out.println("[" + i + "] " + album);
            i++;
        }

        int index = getChoice();
        String chosenAlbum = albums.get(index);

        ArrayList<Song>songsByAlbum = new ArrayList<>();
        for(Song song : songListModel.getSongList()){
            if(song.getAlbum().equals(chosenAlbum)){
                songsByAlbum.add(song);
            }
        }

        printSongs(songsByAlbum);
    }

    public void filterByGenre(SongListModel songListModel) {

    }

    private int getChoice(){
        System.out.println("[0] Back to main menu.");
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter your choice:");
        String choice = keyboard.nextLine();
        int index;
        if (choice.equals("0")){
            return 0;
        } else {
            index = Integer.parseInt(choice) - 1;
        }
        return index;
    }
}
