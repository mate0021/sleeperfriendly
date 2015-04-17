package pl.mate00.sleeperfriendlyapp.filebrowser;

public class DirectoryItem extends SelectableItem {

    public DirectoryItem(String fileName) {
        super(fileName);
    }

    @Override
    public void onTap() {
        System.out.println("will enter directory " + getFileName());
    }

    @Override
    public boolean isSelectedVisible() {
        return false;
    }
}
