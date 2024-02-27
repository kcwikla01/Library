package library.app;

public class LibraryApp {
    private final static String APP_NAME ="Biblioteka v1.7";
    public static void main(String[] args) {
        System.out.println(APP_NAME);
        LibraryControl libraryControll=new LibraryControl();
        libraryControll.controlLoop();
    }

}
