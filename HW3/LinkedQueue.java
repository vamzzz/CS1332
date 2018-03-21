import java.util.NoSuchElementException;
/**
 * Your implementation of a linked queue.
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The quene is empty.");
        }
        LinkedNode<T> temp = new LinkedNode<>(head.getData());
        head = head.getNext();
        size--;
        return temp.getData();
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        LinkedNode<T> start = new LinkedNode<>(data);
        LinkedNode<T> cur = head;
        if (size == 0) {
            head = start;
        } else {
            while (cur.getNext() != null) {
                cur = cur.getNext();
            }
            cur.setNext(start);
        }
        tail = start;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;

    }

    @Override
    public int size() {
        return size;

    }

    /**
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}