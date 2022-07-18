package spotifoo;

public class SpotifooController {
    SpotifooModel spotifooModel;
    SpotifooView spotifooView;

    public SpotifooController(SpotifooModel spotifooModel, SpotifooView spotifooView) {
        this.spotifooModel = spotifooModel;
        this.spotifooView = spotifooView;
    }

    public void run() {
        spotifooModel.loadFromFile();

        boolean again = true;

        do {
            spotifooView.printMainMenu();
            switch (spotifooView.getMainMenuChoice()) {
                case 1 -> spotifooView.printSongs(spotifooModel.getSongList());
                case 2 -> spotifooView.filterByArtist(spotifooModel);
                case 3 -> spotifooView.filterByAlbum(spotifooModel);
                case 4 -> spotifooView.filterByGenre(spotifooModel);
                case 5 -> spotifooView.searchByName(spotifooModel);
                case 6 -> spotifooView.superSearch(spotifooModel);
            }
        }while(again);
    }
}
