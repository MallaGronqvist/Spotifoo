// Project: Spotifoo, a command line music player.
// Java summer course 2022.
// Author: Malla Grönqvist
// Main.java
//*****************************************************************************

package spotifoo;

public class Main {
    public static void main(String[] args) {
        MusicPlayerModel musicPlayerModel = new MusicPlayerModel();
        MusicPlayerView musicPlayerView = new MusicPlayerView();
        MusicPlayerController musicPlayerController =
                new MusicPlayerController(musicPlayerModel, musicPlayerView);

        musicPlayerController.run();
    }
}
