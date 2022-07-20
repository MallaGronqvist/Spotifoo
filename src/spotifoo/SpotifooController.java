package spotifoo;

public class SpotifooController {
    private SpotifooModel spotifooModel;
    private SpotifooView spotifooView;
    private final Menu mainMenu = new Menu();
    private final Menu searchMenu = new Menu();
    private final Menu playListMenu = new Menu();

    public SpotifooController(SpotifooModel spotifooModel, SpotifooView spotifooView) {
        this.spotifooModel = spotifooModel;
        this.spotifooView = spotifooView;

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
        playListMenu.addItem("Create a playlist");
        playListMenu.addItem("Add a song to your playlist");
        playListMenu.addItem("Remove a song from your playlist");
        playListMenu.addItem("Play a song from your playlist");
    }

    public void run() {
        spotifooModel.loadFromFile();

        boolean again = true;

        do {
            mainMenu.printMenuItems();
            switch (mainMenu.getMenuChoice()) {
                case 1 -> spotifooView.chooseSongToPlayFromSongList(spotifooModel.getSongList());
                case 2 -> spotifooView.filterByArtist(spotifooModel);
                case 3 -> spotifooView.filterByAlbum(spotifooModel);
                case 4 -> spotifooView.filterByGenre(spotifooModel);
                case 5 -> {
                        searchMenu.printMenuItems();
                        switch (searchMenu.getMenuChoice()){
                            case 1 -> spotifooView.searchByName(spotifooModel);
                            case 2 -> spotifooView.superSearch(spotifooModel);
                        }
                }
                case 6 -> {
                    boolean printPlayListMenu = true;
                    while(printPlayListMenu){
                        playListMenu.printMenuItems();
                        switch (playListMenu.getMenuChoice()){
                            case 1 -> spotifooView.createPlaylist(spotifooModel);
                            case 2 -> spotifooView.addSongToPlaylist(spotifooModel);
                            case 3 -> spotifooView.removeSongFromPlaylist(spotifooModel);
                            case 4 -> spotifooView.playSongFromPlaylist(spotifooModel);
                            case 0 -> printPlayListMenu = false;
                    }

                    }
                }
            }
        } while (again);
    }
}
