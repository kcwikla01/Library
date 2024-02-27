package library.model;

public class Book extends Publication{
    public static final String TYPE="Książka";
    private String author;
    private int pages;
    private String isbn;


    public Book(String title, String author, int year, int pages, String publisher,String isbn) {
        super(year,title,publisher);
        this.author = author;
        this.pages = pages;
        this.isbn=isbn;
    }


    @Override
    public String toCSV() {
        return TYPE+";"+
                getTitle()+";"+
                getPublisher()+";" +
                getYear()+";" +
                author+";" +
                pages+";" +
                isbn;
    }

    @Override
    public String toString() {
        return super.toString()+"; "+author+"; "+pages+"; "+isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
