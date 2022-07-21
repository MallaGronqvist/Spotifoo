package spotifoo;

public class MusicPlayerController {
    private MusicPlayerModel musicPlayerModel;
    private MusicPlayerView musicPlayerView;
    private final Menu mainMenu = new Menu();
    private final Menu searchMenu = new Menu();
    private final Menu playListMenu = new Menu();

    public MusicPlayerController(MusicPlayerModel musicPlayerModel, MusicPlayerView musicPlayerView) {
        this.musicPlayerModel = musicPlayerModel;
        this.musicPlayerView = musicPlayerView;

        mainMenu.setMenuTitle("MAIN MENU");
        mainMenu.addItem("Songs");
        mainMenu.addItem("Artists");
        mainMenu.addItem("Albums");
        mainMenu.addItem("Genres");
        mainMenu.addItem("Search menu");
        mainMenu.addItem("Playlist menu");

        searchMenu.setMenuTitle("SEARCH MENU");
        searchMenu.addItem("Search by name");
        searchMenu.addItem("Search by any search term in song name, artist and album");

        playListMenu.setMenuTitle(("PLAYLIST MENU"));
        playListMenu.addItem("Name your playlist");
        playListMenu.addItem("Add a song to your playlist");
        playListMenu.addItem("Remove a song from your playlist");
        playListMenu.addItem("Play a song from your playlist");
    }

    public void run() {
        musicPlayerModel.loadFromFile();

        boolean again = true;

        do {
            mainMenu.printMenuItems();
            switch (mainMenu.getMenuChoice()) {
                case 1 -> musicPlayerView.chooseSongToPlayFromList(musicPlayerModel.getSongList());
                case 2 -> musicPlayerView.filterByArtist(musicPlayerModel);
                case 3 -> musicPlayerView.filterByAlbum(musicPlayerModel);
                case 4 -> musicPlayerView.filterByGenre(musicPlayerModel);
                case 5 -> {
                    boolean printSearchMenu = true;
                    while(printSearchMenu){
                        searchMenu.printMenuItems();
                        switch (searchMenu.getMenuChoice()){
                            case 1 -> musicPlayerView.searchByName(musicPlayerModel);
                            case 2 -> musicPlayerView.superSearch(musicPlayerModel);
                            case 0 -> printSearchMenu = false;
                        }
                    }
                }
                case 6 -> {
                    boolean printPlayListMenu = true;
                    while(printPlayListMenu){
                        playListMenu.printMenuItems();
                        switch (playListMenu.getMenuChoice()){
                            case 1 -> musicPlayerView.namePlaylist(musicPlayerModel);
                            case 2 -> musicPlayerView.addSongToPlaylist(musicPlayerModel);
                            case 3 -> musicPlayerView.removeSongFromPlaylist(musicPlayerModel);
                            case 4 -> musicPlayerView.playSongFromPlaylist(musicPlayerModel);
                            case 0 -> printPlayListMenu = false;
                    }

                    }
                }
            }
        } while (again);
    }
}
