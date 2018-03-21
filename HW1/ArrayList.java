/**
 * Your implementation of an ArrayList.
 *
 * @author Vamshi Adimulam
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public void addAtIndex(int index, T data) throws IndexOutOfBoundsException,
        IllegalArgumentException {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The specified index" 
                + "is negative");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("The specified index"
                + "value is greater than the size of the array");
        }

        if (data == null) {
            throw new IllegalArgumentException("The data value is null");
        }
        T[] tempArray;

        if (size == backingArray.length) {
            tempArray = (T[]) new Object[size * 2];
        } else {
            tempArray = (T[]) new Object[backingArray.length];
        }
        int count = 0;
        
        if (size != index) {
            for (int x = 0; x < size + 1; x++) {
                if (x == index) {
                    tempArray[x] = data;
                    count--;
                } else {
                    tempArray[x] = backingArray[x + count];
                }
            }
            backingArray = tempArray;
        } else {
            backingArray[index] = data;
        }
        size++;
    }

    @Override
    public void addToFront(T data) throws IllegalArgumentException {
        addAtIndex(0, data);

    }

    @Override
    public void addToBack(T data) throws IllegalArgumentException {
        addAtIndex(size, data);

    }

    @Override
    public T removeAtIndex(int index) throws IndexOutOfBoundsException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("At this array index,"
                + "there is no data value");
        }
        T removed = backingArray[index];
        T[] tempArray = (T[]) new Object[backingArray.length];
        int count = 0;
        if (size - 1 == index) {
            backingArray[index] = null;
        } else {
            for (int x = 0; x < size; x++) {
                if (x == index) {
                    count++;
                    tempArray[x] = backingArray[x + count];
                } else {
                    tempArray[x] = backingArray[x + count];
                }    
            }
            backingArray = tempArray;
        } 
        size--;
        return removed;
    }

    @Override
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("At this array index,"
                + "there is no data value");
        }
        return backingArray[index];
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
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}
