import java.util.NoSuchElementException;
/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The declared index is "
                + "negative. You cannot add data at negative indices");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("The declared index is"
                + " greater than the total number of elements inside"
                + " the list.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Passed in data that is"
                + " null. Please include data that is not null");
        }
        if (index == 0) {
            addToFront(data);    
        } else {
            int count = 1;
            LinkedListNode<T> cur = head;
            while (count != index) {
                cur = cur.getNext();
                count++;
            }
            LinkedListNode adding = new LinkedListNode<T>(data, cur.getNext());
            cur.setNext(adding);
            if (adding.getNext() == null) {
                tail = adding;
            }
            size++;
        }
        
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Passed in data that is null."
                + " Please include data that is not null");
        }
        if (size() == 0) {
            LinkedListNode<T> adding = new LinkedListNode<T>(data);
            head = adding;
            tail = adding;
        } else {
            LinkedListNode<T> adding = new LinkedListNode<T>(data, head);
            head = adding;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Passed in data that is null."
                + " Please include data that is not null");
        }
        LinkedListNode<T> adding = new LinkedListNode<T>(data);
        if (size == 0) {
            head = adding;
            tail = adding;
        } else {
            tail.setNext(adding);
            tail = adding;
        } 
        size++;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The declared index is "
                + "negative. You cannot add data at negative indices");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("The declared index is "
                + "greater than the total number of elements inside "
                + "the list.");
        }
        if (index == 0) {
            return removeFromFront();
        }
        if (index == size - 1) {
            return removeFromBack();
        }
        int count = 1;
        LinkedListNode<T> cur = head;
        while (count != index) {
            cur = cur.getNext();
            count++;
        }
        LinkedListNode<T> removed = new LinkedListNode<T>(
            cur.getNext().getData());
        cur.setNext(cur.getNext().getNext());
        if (cur.getNext() == null) {
            tail = cur;
        }
        size--;
        return removed.getData();
    }

    @Override
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }
        LinkedListNode<T> removed = new LinkedListNode<T>(head.getData());
        T removing = removed.getData();

        head = head.getNext();
        if (size == 1) {
            tail = null;
        }
        size--;
        return removing;
    }

    @Override
    public T removeFromBack() {
        LinkedListNode cur = head;
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            LinkedListNode<T> removed = new LinkedListNode<T>(head.getData());
            head = null;
            tail = null;
            size--;
            return removed.getData();
        }
        if (size == 2) {
            LinkedListNode<T> removed = new LinkedListNode<T>(
                head.getNext().getData());
            tail = head;
            size--;
            return removed.getData();
        }
        
        while (cur.getNext().getNext() != null) {
            cur = cur.getNext();
        }
        LinkedListNode<T> removed = new LinkedListNode<T>(tail.getData()); 
        cur.setNext(null);
        tail = cur;
        size--;
        return removed.getData();
    }

    @Override
    public T removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Passed in data that is null. "
                + "Please include data that is not null");
        }
        int count = 0;
        LinkedListNode<T> cur = head;
        if (cur.getData() == data) {
            return removeFromFront();
        }
        while (cur.getNext() != null) {
            if (data == cur.getNext().getData()) {
                LinkedListNode<T> removed = new LinkedListNode<T>(
                    cur.getNext().getData());
                if (count == size - 2) {
                    return removeFromBack();
                }
                cur.setNext(cur.getNext().getNext());
                size--;
                return removed.getData();

            } else {
                cur = cur.getNext();
                count++;
            }
        }
        throw new NoSuchElementException("There is no such data in the list. "
            + "Try again with a different data");
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("You tried to pass a negative "
                + "number. Only positive numbers are accepted");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("You tried to pass a number "
                + "greater than or equal to the total number of elements"
                + " in the list. Try again with a smaller number");
        }
        int count = 0;
        LinkedListNode<T> cur = head;
        while (count != index) {
            cur = cur.getNext();
            count++;
        }
        return cur.getData();

    }

    @Override
    public Object[] toArray() {
        Object[] anArray = new Object[size];
        LinkedListNode<T> curr = head;
        for (int x = 0; x < size; x++) {
            anArray[x] = curr.getData();
            curr = curr.getNext();
        }
        return anArray;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
