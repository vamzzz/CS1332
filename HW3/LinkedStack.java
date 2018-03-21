import java.util.NoSuchElementException;
/**
 * Your implementation of a linked stack.
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("The stack is empty.");
        }
        LinkedNode<T> popped = new LinkedNode<>(head.getData());
        head = head.getNext();
        size--;
        return popped.getData();
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (size == 0) {
            LinkedNode<T> pushed = new LinkedNode<T>(data);
            head = pushed;
        } else {
            LinkedNode<T> pushed = new LinkedNode<T>(data, head);
            head = pushed;
        }
        size++;
    }

    @Override
    public int size() {
        return size;

    }

    /**
     * Returns the head of this stack.
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
}