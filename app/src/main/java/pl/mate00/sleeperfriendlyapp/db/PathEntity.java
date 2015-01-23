package pl.mate00.sleeperfriendlyapp.db;

public class PathEntity {

    private String path;

    private boolean played;

    public PathEntity(String path, boolean played) {
        this.path = path;
        this.played = played;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    @Override
    public String toString() {
        return path + "[" + (played ? "x" : " ") + "]";
    }
}
