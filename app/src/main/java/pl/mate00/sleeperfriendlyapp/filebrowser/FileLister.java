package pl.mate00.sleeperfriendlyapp.filebrowser;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mamy on 16.04.15.
 */
public class FileLister {


    public List<String> listContents(String directory, final String extension) {
        List<String> result = listDirectories(directory);

        final File file = new File(directory);
        String[] list = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                File name = new File(dir, filename);
                return filename.endsWith(extension) && !name.isDirectory();
            }
        });

        Arrays.sort(list);
        result.addAll(Arrays.asList(list));

        return result;
    }

    private List<String> listDirectories(String directory) {
        File file = new File(directory);
        File[] directories = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        Arrays.sort(directories);
        List<String> result = new ArrayList<>();
        for (File f : directories) {
            result.add(f.getName());
        }
        return result;
    }
}
