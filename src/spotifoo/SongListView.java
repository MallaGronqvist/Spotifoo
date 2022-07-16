package spotifoo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

        int index = getChoice(songList.size());
        if (index == 0) {
            clearConsoleScreen();
            return;
        }

            index--;
            Song song = songList.get(index);
            playSongAndShowPicture(song);

            /*
            try {
                if(Desktop.isDesktopSupported() && mp3File.exists() && mp3File.canRead()){
                    Desktop.getDesktop().open(mp3File);
                    if(pngFile.exists() && pngFile.canRead()){
                        Desktop.getDesktop().open(pngFile);
                    }else{
                        Desktop.getDesktop().open(placeHolderImage);
                    }
                }else{
                    System.exit(0);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
*/

    }

    private void playSongAndShowPicture(Song song){
        System.out.println("Now playing " + song.getMp3FileName());
        File mp3File = new File("assets/songs/" + song.getMp3FileName());
        File pngFile = new File("assets/albums/" +song.getPngFileName());
        final String placeHolderFileName = "assets/no-picture.png";
        File placeHolderImage = new File(placeHolderFileName);

        try {
            if(Desktop.isDesktopSupported())
                Desktop.getDesktop().open(mp3File);
            try {
                Desktop.getDesktop().open(pngFile);
            } catch (IOException e) {
                Desktop.getDesktop().open(placeHolderImage);
            } catch (IllegalArgumentException e) {
                Desktop.getDesktop().open(placeHolderImage);
            }
        } catch (IOException e) {
            System.exit(0);
        } catch (IllegalArgumentException e) {
            System.exit(0);
        }
    }

    public void filterByArtist(SongListModel songListModel){
        ArrayList<String> artists = songListModel.getArtists();

        printOptions("Available artists:", artists);

        int index = getChoice(artists.size());
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

        printOptions("Available albums:", albums);

        int index = getChoice(albums.size());
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

        printOptions("Available genres:", List.of(Genre.values()));

        int index = getChoice(genres.length);
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

    private int getChoice(int numberOfOptions){
        Scanner keyboard = new Scanner(System.in);

        int index = 0;
        boolean again = true;
        do {
            System.out.println("Enter your choice:");
            String choice = keyboard.nextLine();
            try {
                index = Integer.parseInt(choice);
                if(index >= 0 && index <= numberOfOptions) {
                    again = false;
                } else {
                    throw new Exception();      //Type of exception?
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        } while (again);

        return index;
    }

    private void printOptions(String title, Collection<?>options){
        if(!options.isEmpty()){
            System.out.println(title);
            int i = 1;
            Iterator<?>iterator = options.iterator();
            while(iterator.hasNext()){
                System.out.println("[" + i + "] " + iterator.next());
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


