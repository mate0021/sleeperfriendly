package pl.mate00.sleeperfriendlyapp.audio.shuffler;

/**
 * Created by mamy on 20.04.15.
 */
public class Mp3Location {

    private String path;

    private String track;

    public Mp3Location(String path, String track) {
        this.path = path;
        this.track = track;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getFullPath() {
        return path + "/" + track;
    }

    @Override
    public String toString() {
        return "Mp3Location{" +
                path + "/" + track + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mp3Location that = (Mp3Location) o;

        if (!path.equals(that.path)) return false;
        if (!track.equals(that.track)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + track.hashCode();
        return result;
    }
}
