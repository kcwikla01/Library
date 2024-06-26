package library.app;

import library.IO.ConsolePrinter;
import library.IO.DataReader;
import library.IO.file.FileManager;
import library.IO.file.FileManagerBuilder;
import library.exception.DataExportException;
import library.exception.DataImportException;
import library.exception.NoSuchOptionException;
import library.exception.UserAlreadyExistsException;
import library.model.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.InputMismatchException;

class LibraryControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private Library library;

    LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Zaimportowane dane z pliku");
        } catch (DataImportException | IOException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę.");
            library = new Library();
        }
    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK -> addBook();
                case ADD_MAGAZINE -> addMagazine();
                case PRINT_BOOKS -> printBooks();
                case PRINT_MAGAZINES -> printMagazines();
                case DELETE_BOOK -> deleteBook();
                case DELETE_MAGAZINE -> deleteMagazine();
                case ADD_USER -> addUser();
                case PRINT_USERS -> printUsers();
                case FIND_PUBLICATION -> findPublication();
                case EXIT -> exit();
                default -> printer.printLine("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        } while (option != Option.EXIT);
    }

    private void findPublication() {
        printer.printLine("Podaj tytuł publikacji");
        String title=dataReader.getString();
        String notFoundMessage="Brak publikacji o takim tytule";
        library.findPublicationByTitle(title)
                .map(Publication::toString)
                .ifPresentOrElse(System.out::println,()-> System.out.println(notFoundMessage));
    }

    private void printUsers() {
        printer.printUsers(library.getSortedUsers(
          //      (u1, u2) -> u1.getLastName().compareToIgnoreCase(u2.getLastName())
                Comparator.comparing(User::getLastName,String.CASE_INSENSITIVE_ORDER)
                )
        );
    }

    private void addUser() {

       LibraryUser user=dataReader.createLibraryUser();
       try {
           library.addUser(user);
       }catch (UserAlreadyExistsException e){
           printer.printLine(e.getMessage());
       }

        
    }


    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", podaj ponownie:");
            } catch (InputMismatchException ignored) {
                printer.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }

        return option;
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addBook(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnej książki");
        }
    }
    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book)) {
                printer.printLine("Usunięto książkę");
            } else {
                printer.printLine("Brak wskazanego książki");
            }
        }catch (InputMismatchException e){
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        }
    }

    private void printBooks() {
        printer.printBooks(library.getSortedPublications(
               Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
                ));
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagzine();
            library.addMagazine(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnego magazynu");
        }
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagzine();
            if (library.removePublication(magazine)) {
                printer.printLine("Usunięto magazyn");
            } else {
                printer.printLine("Brak wskazanego magazynu");
            }
        }catch (InputMismatchException e){
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        }
    }

    private void printMagazines() {
        printer.printMagazines(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakończony powodzeniem");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("Koniec programu, papa!");
    }

    private enum Option {
        EXIT(0, "Wyjście z programu"),
        ADD_BOOK(1, "Dodanie książki"),
        ADD_MAGAZINE(2,"Dodanie magazynu/gazety"),
        PRINT_BOOKS(3, "Wyświetlenie dostępnych książek"),
        PRINT_MAGAZINES(4, "Wyświetlenie dostępnych magazynów/gazet"),
        DELETE_BOOK(5,"Usuń książkę"),
        DELETE_MAGAZINE(6,"Usuń magazyn"),
        ADD_USER(7,"Dodaj czytelnika"),
        PRINT_USERS(8,"Wyswietl czytelników"),
        FIND_PUBLICATION(9,"Wyszukaj publikację ");


        private int value;
        private String description;

        Option(int value, String desc) {
            this.value = value;
            this.description = desc;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch(ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }
        }
    }
}
