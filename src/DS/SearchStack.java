package DS;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SearchStack<T> implements Iterable<T> {
    private Node top;
    private int size;

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    public SearchStack() {
        top = null;
        size = 0;
    }

    public void push(T data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new SearchStackIterator();
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Index out of bounds");
        }
        Node current = top;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    private class SearchStackIterator implements Iterator<T> {
        private Node current = top;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new EmptyStackException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}
