package pl.mate00.sleeperfriendlyapp.audio.shuffler;

public class PathEntity {

    private Mp3Location mp3Location;

    private boolean played;

    public PathEntity(Mp3Location mp3Location) {
        this(mp3Location, false);
    }

    public PathEntity(Mp3Location mp3Location, boolean played) {
        this.mp3Location = mp3Location;
        this.played = played;
    }

    public Mp3Location getMp3Location() {
        return mp3Location;
    }

    public void setMp3Location(Mp3Location mp3Location) {
        this.mp3Location = mp3Location;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    @Override
    public String toString() {
        return mp3Location + "[" + (played ? "x" : " ") + "]";
    }
}
