package spotifoo;

public class Song {
    private String name;
    private String artist;
    private String album;
    private Genre genre;
    private String mp3FileName;
    private String pngFileName;

    public Song() {
    }

    public Song(String name, String artist, String album, String genre, String mp3FileName, String pngFileName) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.genre = Genre.valueOf(genre);  // What if this fails?
        this.mp3FileName = mp3FileName;
        this.pngFileName = pngFileName;
    }

    public Song(String[] stringArray) {
        this.name = stringArray[0];
        this.artist = stringArray[1];
        this.album = stringArray[2];
        this.genre = Genre.valueOf(stringArray[3].toUpperCase());  // What if this fails?
        this.mp3FileName = stringArray[4];
        this.pngFileName = stringArray[5];
    }

    @Override
    public String toString() {
        return getName();
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

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
