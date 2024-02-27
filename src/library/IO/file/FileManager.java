package library.IO.file;

import library.model.Library;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileManager {
    Library importData() throws IOException;
    void exportData(Library library);
}
