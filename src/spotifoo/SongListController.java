package spotifoo;

public class SongListController {

    SongListModel songList = new SongListModel();
    SongListView songListView = new SongListView();

    public void run() {

        songList.loadFromFile();

        boolean again = true;

        do {
            songListView.printMainMenu();
            switch(songListView.getMainMenuChoice()) {
                case 1:
                    songListView.printSongs(songList.getSongList());
                    break;
                case 2:
                    songListView.printArtists(songList);
                    break;
                case 3:
                    songListView.filterByAlbum(songList);
                    break;
                case 4:
                    songListView.filterByGenre(songList);
                    break;
                case 5:
                    System.out.println("Search");
                    break;
            }

        }while(again);

    }
}
