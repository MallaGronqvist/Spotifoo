// Project: Spotifoo, a command line music player.
// Java summer course 2022 with Novare Potential.
// Author: Malla Grönqvist
// MusicPlayerView.java
//*****************************************************************************

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

    private static void clearConsoleScreen() {
        final String os = System.getProperty("os.name");

        if (os.contains("Windows")) {
            clearConsoleWindows();
        } else {
            clearConsoleMac();
        }
    }

    private static void clearConsoleMac() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void clearConsoleWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            String errorMessage = "An unexpected error has occurred, which may result in the program " +
                    "not displaying correctly.";
            System.out.println(errorMessage);
        }
    }

    private static String askForPlaylistName() {
        System.out.println("Enter a name for the playlist:");

        Scanner keyboard = new Scanner(System.in);

        return keyboard.nextLine();
    }

    private static void waitForEnter() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Press enter to continue...");

        keyboard.nextLine();
    }

    public void chooseSongToPlayFromList(ArrayList<Song> songList) {
        boolean displayOptions = true;

        while (displayOptions) {
            printOptions(SONGS_TITLE, songList, true);

            int index = getChoice(songList.size(), true);

            if (backToPreviousMenu(index)) {
                displayOptions = false;
                return;
            }

            final boolean canPlaySong = playSong(songList.get(--index));

            if (canPlaySong) {
                displayPicture(songList.get(index));
            }

            waitForEnter();
        }
    }

    private boolean playSong(Song song) {
        File mp3File = new File(song.getPathToMP3File());

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(mp3File);
                System.out.println("Now playing " + song.getName());
                return true;
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not play the song.");
        }

        return false;
    }

    private void displayPicture(Song song) {
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
        boolean displayOptions = true;

        while (displayOptions) {
            printOptions(ARTISTS_TITLE, musicPlayerModel.getArtists(), true);

            int index = getChoice(musicPlayerModel.getArtists().size(), true);

            if (backToPreviousMenu(index)) {
                displayOptions = false;
                return;
            }

            String chosenArtist = musicPlayerModel.getArtists().get(--index);

            chooseSongToPlayFromList(musicPlayerModel.getSongsByArtist(chosenArtist));
        }
    }

    public void filterByAlbum(MusicPlayerModel musicPlayerModel) {
        boolean displayOptions = true;

        while (displayOptions) {
            printOptions(ALBUMS_TITLE, musicPlayerModel.getAlbums(), true);

            int index = getChoice(musicPlayerModel.getAlbums().size(), true);

            if (backToPreviousMenu(index)) {
                displayOptions = false;
                return;
            }

            String chosenAlbum = musicPlayerModel.getAlbums().get((--index));

            chooseSongToPlayFromList(musicPlayerModel.getSongsByAlbum(chosenAlbum));
        }
    }

    public void filterByGenre(MusicPlayerModel musicPlayerModel) {
        boolean displayOptions = true;

        while (displayOptions) {
            printOptions(GENRES_TITLE, List.of(Genre.values()), true);

            Genre[] genres = Genre.values();
            int index = getChoice(genres.length, true);

            if (backToPreviousMenu(index)) {
                displayOptions = false;
                return;
            }

            Genre chosenGenre = genres[--index];

            chooseSongToPlayFromList(musicPlayerModel.getSongsByGenre(chosenGenre));
        }
    }

    private boolean backToPreviousMenu(int index) {
        return index == 0;
    }

    public int getChoice(int numberOfOptions, boolean returnEnabled) {
        Scanner keyboard = new Scanner(System.in);

        int index = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter your choice: ");
            String choice = keyboard.nextLine();

            try {
                index = Integer.parseInt(choice);
                if (!returnEnabled && index == 0) throw new IllegalArgumentException();
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

    public void printOptions(String title, Collection<?> options, boolean enableReturnToPreviousMenu) {
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

        if (enableReturnToPreviousMenu) {
            System.out.println("[0] Back to previous menu");
        }
    }

    public void superSearch(MusicPlayerModel musicPlayerModel) {
        String searchTerm = askForSearchTerm();

        System.out.println("Results with search term '" + searchTerm + "':");

        chooseSongToPlayFromList(musicPlayerModel.getSongsByAnySearchTerm(searchTerm));
    }

    private String askForSearchTerm() {
        System.out.println("Search for  a song by any search term.");
        System.out.println("Write the search term and press enter.");

        Scanner keyboard = new Scanner(System.in);
        String searchTerm;
        searchTerm = keyboard.nextLine();
        return searchTerm;
    }

    public void playSongFromPlaylist(MusicPlayerModel musicPlayerModel) {
        System.out.println(musicPlayerModel.getPlaylist().getName());

        chooseSongToPlayFromList(musicPlayerModel.getPlaylist().getPlaylist());
    }

    public void addSongToPlaylist(MusicPlayerModel musicPlayerModel) {
        printOptions(SONGS_TITLE, musicPlayerModel.getSongList(), true);

        System.out.println("Enter number of the song you want to add to playlist "
                + musicPlayerModel.getPlaylist().getName() + ":");

        int index = getChoice(musicPlayerModel.getSongList().size(), true);

        if (backToPreviousMenu(index)) return;

        musicPlayerModel.addSongToPlaylist(musicPlayerModel.getSongFromSongList(index));

        System.out.println(musicPlayerModel.getSongFromSongList(index).getName() + " was added to playlist.");

        waitForEnter();
    }

    public void namePlaylist(MusicPlayerModel musicPlayerModel) {
        musicPlayerModel.getPlaylist().setName(askForPlaylistName());

        System.out.println("Your playlist has been named '" + musicPlayerModel.getPlaylist().getName() + "'.");

        waitForEnter();
    }

    public void removeSongFromPlaylist(MusicPlayerModel musicPlayerModel) {
        printOptions(SONGS_TITLE, musicPlayerModel.getPlaylist().getPlaylist(), true);

        if (!musicPlayerModel.getPlaylist().getPlaylist().isEmpty()) {
            System.out.println("Enter number of the song you want to remove from playlist "
                    + musicPlayerModel.getPlaylist().getName() + ":");
        }

        int index = getChoice(musicPlayerModel.getPlaylist().getPlaylist().size(), true);

        if (backToPreviousMenu(index)) return;

        System.out.println(musicPlayerModel.getSongFromPlaylist(index).getName() + " was removed from playlist.");

        musicPlayerModel.removeSongFromPlaylist(musicPlayerModel.getSongFromPlaylist(index));

        waitForEnter();
    }
}


