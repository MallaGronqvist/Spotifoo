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
                    songListView.printSongsByArtist(songList);
                    break;
                case 3:
                    System.out.println("Show songs by albums");
                    break;
                case 4:
                    System.out.println("Show songs by genre");
                    break;
                case 5:
                    System.out.println("Search");
                    break;
            }

        }while(again);

    }
}
