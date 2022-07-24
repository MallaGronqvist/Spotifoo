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

        final boolean canPlaySong = playSong(song);

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
        printOptions(ARTISTS_TITLE, musicPlayerModel.getArtists());

        int index = getChoice(musicPlayerModel.getArtists().size());

        if (backToPreviousMenu(index)) return;

        String chosenArtist = musicPlayerModel.getArtists().get(--index);

        chooseSongToPlayFromList(musicPlayerModel.getSongsByArtist(chosenArtist));
    }

    public void filterByAlbum(MusicPlayerModel musicPlayerModel) {
        printOptions(ALBUMS_TITLE, musicPlayerModel.getAlbums());

        int index = getChoice(musicPlayerModel.getAlbums().size());

        if (backToPreviousMenu(index)) return;

        String chosenAlbum = musicPlayerModel.getAlbums().get((--index));

        chooseSongToPlayFromList(musicPlayerModel.getSongsByAlbum(chosenAlbum));
    }

    public void filterByGenre(MusicPlayerModel musicPlayerModel) {
        printOptions(GENRES_TITLE, List.of(Genre.values()));

        Genre[] genres = Genre.values();
        int index = getChoice(genres.length);

        if (backToPreviousMenu(index)) return;

        Genre chosenGenre = genres[--index];

        chooseSongToPlayFromList(musicPlayerModel.getSongsByGenre(chosenGenre));
    }

    private boolean backToPreviousMenu(int index) {
        return index == 0;
    }

    public void searchByName(MusicPlayerModel musicPlayerModel) {
        String searchTerm = askForSearchTerm("Search for  a song by name.",
                "Write the name of a song and press enter:");

        System.out.println("Results with search term " + searchTerm + ":");

        chooseSongToPlayFromList(musicPlayerModel.getSongsBySearchName(searchTerm));
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

        System.out.println("Results with search term " + searchTerm + ":");

        chooseSongToPlayFromList(musicPlayerModel.getSongsByAnySearchTerm(searchTerm));
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

        musicPlayerModel.addSongToPlaylist(musicPlayerModel.getSongFromSongList(index));

        System.out.println(musicPlayerModel.getSongFromSongList(index).getName() + " was added to playlist.");

        waitForEnter();
    }

    public void namePlaylist(MusicPlayerModel musicPlayerModel) {
        System.out.println("Enter a name for the playlist:");
        Scanner keyboard = new Scanner(System.in);
        String name = keyboard.nextLine();

        musicPlayerModel.getPlaylist().setName(name);

        System.out.println("Your playlist has been named '" + name + "'.");

        waitForEnter();
    }

    public void removeSongFromPlaylist(MusicPlayerModel musicPlayerModel) {
        printOptions(SONGS_TITLE, musicPlayerModel.getPlaylist().getPlaylist());

        System.out.println("Enter number of the song you want to remove from playlist "
        + musicPlayerModel.getPlaylist().getName() + ":");

        int index = getChoice(musicPlayerModel.getPlaylist().getPlaylist().size());

        if (backToPreviousMenu(index)) return;

        System.out.println(musicPlayerModel.getSongFromPlaylist(index).getName() + " was removed from playlist.");

        musicPlayerModel.removeSongFromPlaylist(musicPlayerModel.getSongFromPlaylist(index));

        waitForEnter();
    }

    public void waitForEnter(){
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Press enter to continue...");

        keyboard.nextLine();
    }
}


