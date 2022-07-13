package spotifoo;

public enum Genre {
    METAL, POP, REGGAETON, ROCK;
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
