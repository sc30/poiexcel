package ie.sjc.poi.gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ExtensionFileFilter extends FileFilter {
    private String extension;

    public ExtensionFileFilter(String extension) {
        this.extension = extension;
    }

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String name = f.getName();
        int i = name.lastIndexOf('.');
        String ext = null;
        if (i > 0 && i < name.length() - 1) {
            ext = name.substring(i + 1);
        }
        if (ext != null) {
            return extension.equalsIgnoreCase(ext);
        }
        return false;
    }

    public String getDescription() {
        return "." + extension;
    }
}
