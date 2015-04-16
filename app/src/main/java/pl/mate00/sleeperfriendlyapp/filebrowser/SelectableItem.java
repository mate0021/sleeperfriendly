package pl.mate00.sleeperfriendlyapp.filebrowser;

public abstract class SelectableItem {

    private boolean isSelected;
    private String fileName;

    public SelectableItem(String fileName) {
        this(fileName, false);
    }

    public SelectableItem(String fileName, boolean isSelected) {
        this.fileName = fileName;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public abstract void onTap();

    public abstract boolean isSelectedVisible();
}
