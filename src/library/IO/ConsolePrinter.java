package library.IO;

import library.model.Book;
import library.model.LibraryUser;
import library.model.Magazine;
import library.model.Publication;

import java.util.Collection;

public class ConsolePrinter {

    public void printMagazines(Collection<Publication> publications){
        long count= publications.stream()
                .filter(p ->p instanceof Magazine)
                .map(Publication::toString)
                .peek(s -> printLine(s))
                .count();
        if (count==0){
           printLine("Brak magazynow w bilbiotece");
        }
    }

    public void printBooks(Collection<Publication> publications){



       long count= publications.stream()
               .filter(p ->p instanceof Book)
               .map(Publication::toString)
               .peek(s -> printLine(s))
               .count();
        if (count==0){
            printLine("Brak ksiazek w bilbiotece");
        }
    }

    public void printUsers(Collection<LibraryUser> users)
    {
        users.stream()
                .map(LibraryUser::toString)
                .forEach(this::printLine);
    }
    public void printLine(String text){
        System.out.println(text);
    }
}
