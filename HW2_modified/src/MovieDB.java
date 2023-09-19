import java.util.Iterator;
import java.util.NoSuchElementException;


public class MovieDB {
    private MyLinkedList <MovieDBItem> DB;
    public MovieDB() {
        DB = new MyLinkedList<>();

    }

    public void insert(MovieDBItem item) {
        Node<MovieDBItem> last = DB.head;
        Node<MovieDBItem> value = new Node<>(item);
        if (last.getNext() == null) {
            last.setNext(value);
            DB.numItems++;
        } else {
            while (last.getNext() != null) {
                if (last.getNext().getItem().getGenre().compareTo(item.getGenre())>0){
                    value.setNext(last.getNext());
                    last.setNext(value);
                    DB.numItems++;
                    break;
                } else if (last.getNext().getItem().getGenre().compareTo(item.getGenre())==0) {
                    if (last.getNext().getItem().getTitle().compareTo(item.getTitle())>0){
                        value.setNext(last.getNext());
                        last.setNext(value);
                        DB.numItems++;
                        break;
                    } else if (last.getNext().getItem().getTitle().compareTo(item.getTitle())==0) {
                        break;
                    }
                }
                last = last.getNext();
                if(last.getNext() == null) {
                    last.setNext(value);
                    DB.numItems++;
                    break;
                }
            }
        }
    }

    public void delete(MovieDBItem item) {
        Node<MovieDBItem> last = DB.head;
        while (last.getNext() != null) {
            if(item.equals(last.getNext().getItem())){
                if(last.getNext().getNext() != null) {
                    last.setNext(last.getNext().getNext());
                    DB.numItems--;
                }else{
                    last.setNext(null);
                    DB.numItems--;
                    break;
                }
            }
            last = last.getNext();
        }

    }

    public MyLinkedList<MovieDBItem> search(String term) {

        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();

        for(MovieDBItem item : DB) {
            if(item.getTitle().contains(term)) {
                MovieDBItem search = item;
                results.add(search);
            }
        }

        return results;
    }

    public MyLinkedList<MovieDBItem> items() {

        return DB;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
    public Genre(String name) {
        super(name);
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public int compareTo(Genre o) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}

class MovieList implements ListInterface<String> {
    public MovieList() {
    }

    @Override
    public Iterator<String> iterator() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void add(String item) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String first() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void removeAll() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}