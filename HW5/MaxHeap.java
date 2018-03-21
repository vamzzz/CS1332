import java.util.NoSuchElementException;
/**
 * Your implementation of a max heap.
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial length of {@code INITIAL_CAPACITY} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The passed in item is null.");
        }
        if (size == backingArray.length - 1) {
            T[] tempArray = (T[]) new Comparable[(backingArray.length * 2) + 1];
            for (int x = 0; x < backingArray.length; x++) {
                tempArray[x] = backingArray[x];
            }
            backingArray = tempArray;
        }
        backingArray[++size] = item;
        int pos = size;
        for (; pos > 1 && item.compareTo(backingArray[pos / 2]) > 0;
            pos = pos / 2) {
            T temp = backingArray[pos];
            backingArray[pos] = backingArray[pos / 2];
            backingArray[pos / 2] = temp;
        }
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;

        int pos = 1;

        boolean doing = true;

        while ((pos * 2 <= size) && doing) {
            int child = pos * 2;

            if (backingArray[child + 1] != null) {
                if (backingArray[child + 1].
                    compareTo(backingArray[child]) > 0) {
                    child++;
                }
            }
            if (backingArray[pos].compareTo(backingArray[child]) < 0) {
                T temp = backingArray[pos];
                backingArray[pos] = backingArray[child];
                backingArray[child] = temp;
            } else {
                doing = false;
            }
            pos = child;
        }

        return removed;
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
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }
}
