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

    public String getSearchableString(){
        return new String (name + artist + album);
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
