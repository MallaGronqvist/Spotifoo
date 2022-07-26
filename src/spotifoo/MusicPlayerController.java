package spotifoo;

public class MusicPlayerController {
    private final MusicPlayerModel musicPlayerModel;
    private final MusicPlayerView musicPlayerView;


    public MusicPlayerController(MusicPlayerModel musicPlayerModel, MusicPlayerView musicPlayerView) {
        this.musicPlayerModel = musicPlayerModel;
        this.musicPlayerView = musicPlayerView;
    }

    public void run() {
        musicPlayerModel.loadFromFile();

        printWelcomeMessage();

        boolean again = true;

        do {
            musicPlayerView.printOptions(musicPlayerModel.getMainMenu().getMenuTitle(),
                    musicPlayerModel.getMainMenu().getOptions(), false);
            switch (musicPlayerView.getChoice(musicPlayerModel.getMainMenu().getNumberOfOptions(),
                    false)) {
                case 1 -> musicPlayerView.chooseSongToPlayFromList(musicPlayerModel.getSongList());
                case 2 -> musicPlayerView.filterByArtist(musicPlayerModel);
                case 3 -> musicPlayerView.filterByAlbum(musicPlayerModel);
                case 4 -> musicPlayerView.filterByGenre(musicPlayerModel);
                case 5 -> musicPlayerView.superSearch(musicPlayerModel);
                case 6 -> {
                    boolean printPlayListMenu = true;
                    while(printPlayListMenu){
                        musicPlayerView.printOptions(musicPlayerModel.getPlayListMenu().getMenuTitle(),
                                musicPlayerModel.getPlayListMenu().getOptions(), true);
                        switch (musicPlayerView.getChoice(musicPlayerModel.getPlayListMenu().getNumberOfOptions(),
                                true)){
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

    private void printWelcomeMessage() {
        System.out.println("Welcome to Spotifoo Music Player!");
        System.out.println();
    }
}
