package spotifoo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SongListView {

    private Menu mainMenu = new Menu();

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

        printOptions("Available songs:", songList);

        int index = getChoice();
        if (index == 0) {
            return;
        }

            index--;
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

    public void filterByArtist(SongListModel songListModel){
        ArrayList<String> artists = songListModel.getArtists();

        printOptions("Available artists:", artists);

        int index = getChoice();
        if (index == 0){
            return;
        }else {
            index--;
            String chosenArtist = artists.get(index);

            ArrayList<Song> songsByArtist = new ArrayList<>();
            for (Song song : songListModel.getSongList()) {
                if (song.getArtist().equals(chosenArtist)) {
                    songsByArtist.add(song);
                }
            }
            printSongs(songsByArtist);
        }
    }
    public void filterByAlbum(SongListModel songListModel){
        ArrayList<String>albums = songListModel.getAlbums();
/*
        int i = 1;
        for(String album : albums) {
            System.out.println("[" + i + "] " + album);
            i++;
        }

 */
        printOptions("Available albums:", albums);

        int index = getChoice();
        if (index == 0){
            return;
        }else {
            index--;
            String chosenAlbum = albums.get(index);

            ArrayList<Song> songsByAlbum = new ArrayList<>();
            for (Song song : songListModel.getSongList()) {
                if (song.getAlbum().equals(chosenAlbum)) {
                    songsByAlbum.add(song);
                }
            }

            printSongs(songsByAlbum);
        }
    }

    public void filterByGenre(SongListModel songListModel) {
        Genre[] genres = Genre.values();
 /*       int i = 1;
        for(Genre genre : genres){
            String stringGenre = genre.toString().toLowerCase();
            System.out.println("[" + i + "] " + stringGenre);
            i++;
        }
  */
        printOptions("Available genres:", List.of(Genre.values()));

        int index = getChoice();
        if (index == 0){
            return;
        }else {
            index--;
            Genre chosenGenre = genres[index];

            ArrayList<Song> songsByGenre = new ArrayList<>();
            for (Song song : songListModel.getSongList()) {
                if (song.getGenre() == chosenGenre) {
                    songsByGenre.add(song);
                }
            }
            // If not empty!
            printSongs(songsByGenre);
        }
    }

    public void searchByName(SongListModel songListModel) {
        System.out.println("Search for  a song by name.");
        System.out.println("Write the name of a song and press enter:");

        Scanner keyboard = new Scanner(System.in);
        String searchTerm;
        searchTerm = keyboard.nextLine();

        ArrayList<Song>songsBySearchName = new ArrayList<>();
        for(Song song : songListModel.getSongList()){
            String songName = song.getName();
            if(songName.toUpperCase().contains(searchTerm.toUpperCase())){
                songsBySearchName.add(song);
            }
        }

        System.out.println("Results with search term " + searchTerm + ":");
        printSongs(songsBySearchName);
    }

    private int getChoice(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter your choice:");
        String choice = keyboard.nextLine();
        int index = Integer.parseInt(choice);

        // Add input validation here.

        return index;
    }

    private void printOptions(String title, Collection<?>options){
        System.out.println(title);
        int i = 1;
        Iterator<?>iterator = options.iterator();
        while(iterator.hasNext()){
            System.out.println("[" + i + "] " + iterator.next());
            i++;
        }
        System.out.println("[0] Back to main menu.");
    }
}
