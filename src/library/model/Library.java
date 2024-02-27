package library.model;

import library.exception.PublicationAlreadyExistException;
import library.exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {

    private Map<String,Publication> publications=new HashMap<>();
    private Map<String,LibraryUser> users=new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Collection<Publication> getSortedPublications(Comparator<Publication> comparator){
        List<Publication> list = new ArrayList<>(publications.values());
        list.sort(comparator);
        return list;
    }
    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public Collection<LibraryUser> getSortedUsers(Comparator<LibraryUser> comparator)
    {
        List<LibraryUser> list =new ArrayList<>(users.values());
        list.sort(comparator);
        return list;
    }

    public void addBook(Book book){
        addPublication(book);
    }


    public void addMagazine(Magazine magazine){
        addPublication(magazine);
    }

    public void addPublication(Publication publication){
            if (publications.containsKey(publication.getTitle())){
                throw new PublicationAlreadyExistException("Publikacja o takim tytule juz istnieje");
            }
            publications.put(publication.getTitle(),publication);

    }

    public void addUser(LibraryUser user){
        if (users.containsKey(user.getPesel())){
            throw new UserAlreadyExistsException("Uzytkownik o takim peselu juz istnieje");
        }
        users.put(user.getPesel(),user);
    }

    public boolean removePublication(Publication publication){
        if (publications.containsValue(publication)){
            publications.remove(publication.getTitle());
            return true;
        }
       else {
           return false;
        }
    }
}
