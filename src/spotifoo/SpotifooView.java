package spotifoo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SpotifooView {

    private Menu mainMenu = new Menu();

    public SpotifooView() {
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

    public void printSongs(ArrayList<Song> songList){  //How to name this?

        printOptions("Available songs:", songList);

        int index = getChoice(songList.size());
        if (index == 0) {
            return;
        }
        index--;
        Song song = songList.get(index);
        playSongAndShowPicture(song);
    }

    private void playSongAndShowPicture(Song song){
        File mp3File = new File("assets/songs/" + song.getMp3FileName());
        File pngFile = new File("assets/albums/" +song.getPngFileName());
        final String PLACEHOLDER_FILE = "assets/no-picture.png";
        File placeHolderImage = new File(PLACEHOLDER_FILE);

        try {
            if(Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(mp3File);
                System.out.println("Now playing " + song.getMp3FileName());
            }
            try {
                Desktop.getDesktop().open(pngFile);
            } catch (IOException | IllegalArgumentException e) {
                Desktop.getDesktop().open(placeHolderImage);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.exit(0);
        }
    }

    public void filterByArtist(SpotifooModel spotifooModel){
        ArrayList<String> artists = spotifooModel.getArtists();

        printOptions("Available artists:", artists);

        int index = getChoice(artists.size());
        if (index == 0){
            return;
        }
        index--;

        String chosenArtist = artists.get(index);
        System.out.println(chosenArtist);
        ArrayList<Song> songsByArtist = new ArrayList<>();
        for (Song song : spotifooModel.getSongList()) {
            if (song.getArtist().equals(chosenArtist)) {
                songsByArtist.add(song);
            }
        }
        printSongs(songsByArtist);
    }
    public void filterByAlbum(SpotifooModel spotifooModel){
        ArrayList<String>albums = spotifooModel.getAlbums();

        printOptions("Available albums:", albums);

        int index = getChoice(albums.size());
        if (index == 0){
            return;
        }
        index--;
        String chosenAlbum = albums.get(index);

        ArrayList<Song> songsByAlbum = new ArrayList<>();
        for (Song song : spotifooModel.getSongList()) {
            if (song.getAlbum().equals(chosenAlbum)) {
                songsByAlbum.add(song);
            }
        }

        printSongs(songsByAlbum);
    }

    public void filterByGenre(SpotifooModel spotifooModel) {
        Genre[] genres = Genre.values();

        printOptions("Available genres:", List.of(Genre.values()));

        int index = getChoice(genres.length);
        if (index == 0){
            return;
        }
        index--;
        Genre chosenGenre = genres[index];

        ArrayList<Song> songsByGenre = new ArrayList<>();
        for (Song song : spotifooModel.getSongList()) {
            if (song.getGenre() == chosenGenre) {
                songsByGenre.add(song);
            }
        }
        printSongs(songsByGenre);
    }

    public void searchByName(SpotifooModel spotifooModel) {
        System.out.println("Search for  a song by name.");
        System.out.println("Write the name of a song and press enter:");

        Scanner keyboard = new Scanner(System.in);
        String searchTerm;
        searchTerm = keyboard.nextLine();

        ArrayList<Song>songsBySearchName = new ArrayList<>();
        for(Song song : spotifooModel.getSongList()){
            String songName = song.getName();
            if(songName.toUpperCase().contains(searchTerm.toUpperCase())){
                songsBySearchName.add(song);
            }
        }

        System.out.println("Results with search term " + searchTerm + ":");
        printSongs(songsBySearchName);
    }

    private int getChoice(int numberOfOptions){
        Scanner keyboard = new Scanner(System.in);

        int index = 0;
        boolean validInput = false;
        while(!validInput){
            System.out.println("Enter your choice:");
            String choice = keyboard.nextLine();
            try {
                index = Integer.parseInt(choice);
                if(index >= 0 && index <= numberOfOptions) {
                    validInput = true;
                } else throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Try again.");
            }
        }
        clearConsoleScreen();
        return index;
    }

    private void printOptions(String title, Collection<?>options){
        if(!options.isEmpty()){
            System.out.println(title);
            int i = 1;
            for (var option : options) {
                System.out.println("[" + i + "] " + option);
                i++;
            }
        } else {
            System.out.println("No songs were found.");
        }
        System.out.println("[0] Back to main menu.");
    }

    private void clearConsoleScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
    }
}


