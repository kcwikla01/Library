package library.model;

import java.util.ArrayList;
import java.util.List;

public class LibraryUser extends User  {
    private List<Publication> publicationHistory=new ArrayList<>();
    private List<Publication> borrowedPublications=new ArrayList<>();

    @Override
    public String toCSV() {
        return getFirstName()+";"+getLastName()+";"+getPesel();
    }

    public LibraryUser(String firstName, String lastName, String pesel) {
        super(firstName, lastName, pesel);
    }

    public List<Publication> getPublicationHistory() {
        return publicationHistory;
    }

    public List<Publication> getBorrowedPublications() {
        return borrowedPublications;
    }

    public void addPublicationToHistory(Publication pub){
        publicationHistory.add(pub);
    }

    public void borrowPublication(Publication pub){
        borrowedPublications.add(pub);
    }

    public boolean returnPublications(Publication pub){
        boolean result=false;
        if (borrowedPublications.contains(pub)){
            borrowedPublications.remove(pub);
            addPublicationToHistory(pub);
            result=true;
        }
        return result;
    }
}
