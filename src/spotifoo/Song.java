// A command line music player.
// Java summer course 2022.
// Author: Malla Grönqvist
// Song.java
//*****************************************************************************

package spotifoo;

public class Song {
    private final static String SONGS_PATH = "assets/songs/";
    private final static String PNG_PATH = "assets/albums/";
    private final static String PLACEHOLDER_FILE = "assets/no-picture.png";
    private String name;
    private String artist;
    private String album;
    private Genre genre;
    private String mp3FileName;
    private String pngFileName;

    public Song(String[] stringArray) {
        try {
            this.name = stringArray[0];
            this.artist = stringArray[1];
            this.album = stringArray[2];
            this.genre = Genre.valueOf(stringArray[3].toUpperCase());
            this.mp3FileName = stringArray[4];
            this.pngFileName = stringArray[5];
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("A problem has occurred while loading songs from file. " +
                    "This may cause errors in playing some song(s).");
        }
    }

    @Override
    public String toString() {
        return name + ", " +
                artist + ", " +
                album;
    }

    public String getSearchableString() {
        String songInfo = name + artist + album;
        return songInfo.replaceAll("\\s", "");
    }

    public String getMp3FileName() {
        return mp3FileName;
    }

    public String getPngFileName() {
        return pngFileName;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getPathToMP3File() {
        return SONGS_PATH + getMp3FileName();
    }

    public String getPathToPNGFile() {
        return PNG_PATH + getPngFileName();
    }

    public String getPathToPlaceHolderFile() {
        return PLACEHOLDER_FILE;
    }
}
