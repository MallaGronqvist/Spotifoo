package spotifoo;

public class Main {
    public static void main(String[] args) {
        SpotifooModel spotifooModel = new SpotifooModel();
        SpotifooView spotifooView = new SpotifooView();
        SpotifooController spotifooController = new SpotifooController(spotifooModel, spotifooView);

        spotifooController.run();
    }
}
