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

    public void chooseSongToPlayFromSongList(ArrayList<Song> songList) {  //How to name this?
        printOptions(SONGS_TITLE, songList);

        int index = getChoice(songList.size());
        if (index == 0) {
            return;
        }
        index--;
        Song song = songList.get(index);

        boolean canPlaySong = playSong(song);
        if(canPlaySong){
            displayPicture(song);
        }
    }

    private boolean playSong(Song song){
        final String SONGS_PATH = "assets/songs/";
        File mp3File = new File(SONGS_PATH + song.getMp3FileName());

            try {
                if(Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(mp3File);
                    System.out.println("Now playing " + song.getName());
                    return true;
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Could not play the song.");;
            }
            return false;
    }

    private void displayPicture(Song song){
        final String PNG_PATH = "assets/albums/";
        File pngFile = new File(PNG_PATH + song.getPngFileName());
        final String PLACEHOLDER_FILE = "assets/no-picture.png";
        File placeHolderImage = new File(PLACEHOLDER_FILE);

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
        if (index == 0) {
            return;
        }
        index--;

        String chosenArtist = artists.get(index);

        ArrayList<Song> songsByArtist = new ArrayList<>();
        for (Song song : musicPlayerModel.getSongList()) {
            if (song.getArtist().equals(chosenArtist)) {
                songsByArtist.add(song);
            }
        }

        chooseSongToPlayFromSongList(songsByArtist);
    }

    public void filterByAlbum(MusicPlayerModel musicPlayerModel) {
        ArrayList<String> albums = musicPlayerModel.getAlbums();

        printOptions(ALBUMS_TITLE, albums);

        int index = getChoice(albums.size());
        if (index == 0) {
            return;
        }
        index--;
        String chosenAlbum = albums.get(index);

        ArrayList<Song> songsByAlbum = new ArrayList<>();
        for (Song song : musicPlayerModel.getSongList()) {
            if (song.getAlbum().equals(chosenAlbum)) {
                songsByAlbum.add(song);
            }
        }

        chooseSongToPlayFromSongList(songsByAlbum);
    }

    public void filterByGenre(MusicPlayerModel musicPlayerModel) {
        Genre[] genres = Genre.values();

        printOptions(GENRES_TITLE, List.of(Genre.values()));

        int index = getChoice(genres.length);
        if (index == 0) {
            return;
        }
        index--;
        Genre chosenGenre = genres[index];

        ArrayList<Song> songsByGenre = new ArrayList<>();
        for (Song song : musicPlayerModel.getSongList()) {
            if (song.getGenre() == chosenGenre) {
                songsByGenre.add(song);
            }
        }

        chooseSongToPlayFromSongList(songsByGenre);
    }

    public void searchByName(MusicPlayerModel musicPlayerModel) {
        System.out.println("Search for  a song by name.");
        System.out.println("Write the name of a song and press enter:");

        Scanner keyboard = new Scanner(System.in);
        String searchTerm;
        searchTerm = keyboard.nextLine();

        ArrayList<Song> songsBySearchName = new ArrayList<>();
        for (Song song : musicPlayerModel.getSongList()) {
            String songName = song.getName();
            if (songName.toUpperCase().contains(searchTerm.toUpperCase())) {
                songsBySearchName.add(song);
            }
        }

        System.out.println("Results with search term " + searchTerm + ":");
        chooseSongToPlayFromSongList(songsBySearchName);
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
            System.out.println("No songs were found.");
        }
        System.out.println("[0] Back to main menu.");
    }

    private void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void superSearch(MusicPlayerModel musicPlayerModel) {
        System.out.println("Search for  a song by any search term.");
        System.out.println("Write the search term and press enter:");

        Scanner keyboard = new Scanner(System.in);
        String searchTerm;
        searchTerm = keyboard.nextLine();

        ArrayList<Song> songsBySearchName = new ArrayList<>();
        for (Song song : musicPlayerModel.getSongList()) {
            String songInfo = song.getSearchableString();
            if (songInfo.toUpperCase().contains(searchTerm.toUpperCase())) {
                songsBySearchName.add(song);
            }
        }

        System.out.println("Results with search term " + searchTerm + ":");
        chooseSongToPlayFromSongList(songsBySearchName);
    }

    public void playSongFromPlaylist(MusicPlayerModel musicPlayerModel){
        System.out.println(musicPlayerModel.getPlaylist().getName());
        chooseSongToPlayFromSongList(musicPlayerModel.getPlaylist().getPlaylist());
    }

    public void addSongToPlaylist(MusicPlayerModel musicPlayerModel) {
        printOptions(SONGS_TITLE, musicPlayerModel.getSongList());

        System.out.println("Enter number of the song you want to add to playlist "
                + musicPlayerModel.getPlaylist().getName() + ":");

        int index = getChoice(musicPlayerModel.getSongList().size());
        if(index == 0){
            return;
        }
        index--;
        Song song = musicPlayerModel.getSongList().get(index);
        musicPlayerModel.getPlaylist().addSong(song);
        System.out.println(song.getName() + " was added to playlist.");
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
        if(index == 0){
            return;
        }
        index--;
        Song song = musicPlayerModel.getPlaylist().getPlaylist().get(index);
        musicPlayerModel.getPlaylist().removeSong(song);
        System.out.println(song.getName() + " was removed from playlist.");
    }

    public void waitForEnter(){
        Scanner s = new Scanner(System.in);

        System.out.println("Press enter to continue.....");

        s.nextLine();
    }
}


