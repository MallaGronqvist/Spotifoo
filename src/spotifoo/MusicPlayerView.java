package spotifoo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class MusicPlayerView {

    private static final String SONGS_TITLE = "Available songs:";
    private static final String ALBUMS_TITLE = "Available albums:";
    private static final String ARTISTS_TITLE = "Available artists:";
    private static final String GENRES_TITLE = "Available genres:";

    public void chooseSongToPlayFromList(ArrayList<Song> songList) {
        printOptions(SONGS_TITLE, songList);

        int index = getChoice(songList.size());
        if (backToPreviousMenu(index)) return;

        Song song = songList.get(--index);

        boolean canPlaySong = playSong(song);
        if(canPlaySong){
            displayPicture(song);
        }

        waitForEnter();
    }

    private boolean playSong(Song song){

        File mp3File = new File(song.getPathToMP3File());

            try {
                if(Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(mp3File);
                    System.out.println("Now playing " + song.getName());
                    return true;
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Could not play the song.");
            }
            return false;
    }

    private void displayPicture(Song song){

        File pngFile = new File(song.getPathToPNGFile());

        File placeHolderImage = new File(song.getPathToPlaceHolderFile());

        try {
            Desktop.getDesktop().open(pngFile);
        } catch (IOException | IllegalArgumentException e) {
            try {
                Desktop.getDesktop().open(placeHolderImage);
            } catch (IOException | IllegalArgumentException ex) {
                System.out.println("Could not display album art.");
            }
        }
    }

    public void filterByArtist(MusicPlayerModel musicPlayerModel) {
        ArrayList<String> artists = musicPlayerModel.getArtists();

        printOptions(ARTISTS_TITLE, artists);

        int index = getChoice(artists.size());
        if (backToPreviousMenu(index)) return;

        String chosenArtist = artists.get(--index);

        ArrayList<Song> songsByArtist = musicPlayerModel.getSongsByArtist(chosenArtist);

        chooseSongToPlayFromList(songsByArtist);
    }

    public void filterByAlbum(MusicPlayerModel musicPlayerModel) {
        ArrayList<String> albums = musicPlayerModel.getAlbums();

        printOptions(ALBUMS_TITLE, albums);

        int index = getChoice(albums.size());

        if (backToPreviousMenu(index)) return;

        String chosenAlbum = albums.get((--index));

        ArrayList<Song> songsByAlbum = musicPlayerModel.getSongsByAlbum(chosenAlbum);

        chooseSongToPlayFromList(songsByAlbum);
    }

    public void filterByGenre(MusicPlayerModel musicPlayerModel) {
        Genre[] genres = Genre.values();

        printOptions(GENRES_TITLE, List.of(Genre.values()));

        int index = getChoice(genres.length);

        if (backToPreviousMenu(index)) return;

        Genre chosenGenre = genres[--index];

        ArrayList<Song> songsByGenre = musicPlayerModel.getSongsByGenre(chosenGenre);

        chooseSongToPlayFromList(songsByGenre);
    }

    private boolean backToPreviousMenu(int index) {
        return index == 0;
    }

    public void searchByName(MusicPlayerModel musicPlayerModel) {
        String searchTerm = askForSearchTerm("Search for  a song by name.",
                "Write the name of a song and press enter:");

        ArrayList<Song> songsBySearchName = musicPlayerModel.getSongsBySearchName(searchTerm);

        System.out.println("Results with search term " + searchTerm + ":");
        chooseSongToPlayFromList(songsBySearchName);
    }

    private int getChoice(int numberOfOptions) {
        Scanner keyboard = new Scanner(System.in);

        int index = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Enter your choice:");
            String choice = keyboard.nextLine();
            try {
                index = Integer.parseInt(choice);
                if (index >= 0 && index <= numberOfOptions) {
                    validInput = true;
                } else throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Try again.");
            }
        }

        clearConsoleScreen();
        return index;
    }

    private void printOptions(String title, Collection<?> options) {
        if (!options.isEmpty()) {
            System.out.println(title);
            int i = 1;
            for (var option : options) {
                System.out.println("[" + i + "] " + option);
                i++;
            }
        } else {
            System.out.println("No results were found.");
        }
        System.out.println("[0] Back to previous menu.");
    }

    private void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void superSearch(MusicPlayerModel musicPlayerModel) {
        String searchTerm = askForSearchTerm("Search for  a song by any search term.",
                "Write the search term and press enter:");

        ArrayList<Song> songsByAnySearchTerm = musicPlayerModel.getSongsByAnySearchTerm(searchTerm);

        System.out.println("Results with search term " + searchTerm + ":");
        chooseSongToPlayFromList(songsByAnySearchTerm);
    }

    private String askForSearchTerm(String x, String x1) {
        System.out.println(x);
        System.out.println(x1);

        Scanner keyboard = new Scanner(System.in);
        String searchTerm;
        searchTerm = keyboard.nextLine();
        return searchTerm;
    }

    public void playSongFromPlaylist(MusicPlayerModel musicPlayerModel){
        System.out.println(musicPlayerModel.getPlaylist().getName());
        chooseSongToPlayFromList(musicPlayerModel.getPlaylist().getPlaylist());
    }

    public void addSongToPlaylist(MusicPlayerModel musicPlayerModel) {
        printOptions(SONGS_TITLE, musicPlayerModel.getSongList());

        System.out.println("Enter number of the song you want to add to playlist "
                + musicPlayerModel.getPlaylist().getName() + ":");

        int index = getChoice(musicPlayerModel.getSongList().size());
        if (backToPreviousMenu(index)) return;

        Song song = musicPlayerModel.getSongFromSongList(index);
        musicPlayerModel.addSongToPlaylist(song);

        System.out.println(song.getName() + " was added to playlist.");

        waitForEnter();
    }

    public void namePlaylist(MusicPlayerModel musicPlayerModel) {
        System.out.println("Enter a name for the playlist:");
        Scanner keyboard = new Scanner(System.in);
        String name = keyboard.nextLine();

        musicPlayerModel.getPlaylist().setName(name);

        System.out.println("Your playlist has been named '" + name + "'");

        waitForEnter();
    }

    public void removeSongFromPlaylist(MusicPlayerModel musicPlayerModel) {
        printOptions(SONGS_TITLE, musicPlayerModel.getPlaylist().getPlaylist());

        System.out.println("Enter number of the song you want to remove from playlist "
        + musicPlayerModel.getPlaylist().getName() + ":");

        int index = getChoice(musicPlayerModel.getPlaylist().getPlaylist().size());
        if (backToPreviousMenu(index)) return;

        Song song = musicPlayerModel.getSongFromPlaylist(index);
        musicPlayerModel.removeSongFromPlaylist(song);

        System.out.println(song.getName() + " was removed from playlist.");

        waitForEnter();
    }

    public void waitForEnter(){
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Press enter to continue...");

        keyboard.nextLine();
    }
}


