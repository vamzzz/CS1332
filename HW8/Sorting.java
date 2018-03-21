import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Try again. Array is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }

        boolean sorted = false;
        int count = 1;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < arr.length - count; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T removed = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = removed;
                    sorted = false;
                }
            }
            count++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Try again. Array is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T removed = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = removed;
                j--;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Try again. Array is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }
        if (rand == null) {
            throw new IllegalArgumentException("The random place is null.");
        }

        quickSort(0, arr.length - 1, arr, comparator, rand);
    }

    /**
     * Helper method to sort the entire array in a quick sort. This method
     * go through the list recursively until the array is sorted.
     *
     * @param <T> generic type that is held in the arr
     * @param x the left most element in the array
     * @param y the right most element in the array
     * @param arr the array that is being sorted
     * @param comparator the comparator that the object uses for the values
     * @param rand the random object that selects the pivot position
     */
    private static <T> void quickSort(int x, int y, T[] arr,
                                    Comparator<T> comparator, Random rand) {
        if (x >= y) {
            return;
        }

        int left = x + 1;
        int right = y;
        int pivot = rand.nextInt(y - x) + x;
        T pivotObject = arr[pivot];
        arr[pivot] = arr[x];
        arr[x] = pivotObject;
        T removed;

        while (left <= right) {
            while (left <= right && comparator.compare(arr[left], pivotObject)
                < 0) {
                left++;
            }

            while (left <= right && comparator.compare(arr[right], pivotObject)
                > 0) {
                right--;
            }

            if (left <= right) {
                removed = arr[left];
                arr[left] = arr[right];
                arr[right] = removed;
                left++;
                right--;
            }
        }
        removed = arr[right];
        arr[right] = arr[x];
        arr[x] = removed;

        quickSort(x, right - 1, arr, comparator, rand);
        quickSort(right + 1, y, arr, comparator, rand);
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Try again. Array is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null.");
        }

        T[] removed = (T[]) new Object[arr.length];
        mergeSort(arr, removed, 0, arr.length - 1, comparator);
    }

    /**
     * Helper method for mergeSort. It gets recursively called on both
     * the right and left side until the array is split into individual
     * units.
     *
     * @param <T> the type that is held inside the array
     * @param arr the array that needs to be sorted
     * @param removed a temporary array to store the elements in the array
     * @param left index on the left side of the array that needs to be merged
     * @param right index on the right side of array that needs to be merged
     * @param comparator the comparator object for the values
     */
    private static <T> void mergeSort(T[] arr, T[] removed, int left,
                                        int right, Comparator<T> comparator) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(arr, removed, left, middle, comparator);
            mergeSort(arr, removed, middle + 1, right, comparator);
            merge(arr, removed, left, middle + 1, right, comparator);
        }
    }

    /**
     * Helped method to combine the array back together after the array
     * gets split up. When the array gets back together, it will be in order
     * 
     * @param <T> the type of Object that is held in the array
     * @param arr the array where all the objects are held and sorted
     * @param removed a temporary array to store the elements in the array
     * @param left index on the left side of the array that needs to be merged
     * @param right index on the right side of array that needs to be merged
     * @param rightLast the index that right is incremented to
     * @param comparator the comparator object for the values
     */
    private static <T> void merge(T[] arr, T[] removed, int left,
                    int right, int rightLast, Comparator<T> comparator) {
        int length = rightLast - left + 1;
        int leftLast = right - 1;
        int pointer = left;

        while (right <= rightLast && left <= leftLast) {
            if (comparator.compare(arr[left], arr[right]) <= 0) {
                removed[pointer] = arr[left];
                pointer++;
                left++;
            } else {
                removed[pointer] = arr[right];
                pointer++;
                right++;
            }
        }

        while (left <= leftLast) {
            removed[pointer] = arr[left];
            pointer++;
            left++;
        }
        while (right <= rightLast) {
            removed[pointer] = arr[right];
            pointer++;
            right++;
        }
        for (int i = 0; i < length; i++, rightLast--) {
            arr[rightLast] = removed[rightLast];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Passed in array is null."
                + " Try again.");
        }
        arr = lsd(arr, 1);
        return arr;
    }

    /**
     * Helper method for lsd sort. It puts the elements into buckets and
     * re-adds to array.
     *
     * @param arr the array that needs to be sorted
     * @param aNum digit that is being compared
     * @return the sorted array
     */
    private static int[] lsd(int[] arr, int aNum) {
        LinkedList<Integer>[] buckets = new LinkedList[19];
        boolean stop = true;

        for (int i : arr) {
            if (i / pow(10, aNum) != 0) {
                stop = false;
            }

            int index = (i % pow(10, aNum)) / pow(10, aNum - 1) + 9;
            if (buckets[index] == null) {
                buckets[index] = new LinkedList<>();
            }
            buckets[index].add(i);
        }

        int index = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                for (Integer element : buckets[i]) {
                    arr[index] = element;
                    index++;
                }
            }
        }

        if (!stop) {
            arr = lsd(arr, aNum + 1);
        }
        return arr;
    }

    /**
     * Implement MSD (most significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should:
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] msdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot pass in a null"
            + " array Object.");
        }
        int large = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i]) > large) {
                large = Math.abs(arr[i]);
            }
        }
        int max = 0;

        while (large != 0) {
            large /= 10;
            max++;
        }
        arr = msd(arr, max);
        return arr;
    }

    /**
     * Helper method for msd sort, puts elements into buckets and
     * sorts each bucket
     *
     * @param arr the array to be sorted
     * @param n the digit being compared
     * @return the sorted array
     */
    private static int[] msd(int[] arr, int n) {

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i : arr) {
            int index = i % pow(10, n) / pow(10, n - 1) + 9;

            if (buckets[index] == null) {
                buckets[index] = new LinkedList<>();
            }
            buckets[index].add(i);
        }

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i] = rearrangeBucket(buckets[i], n - 1);
            }
        }

        int curr = 0;
        for (LinkedList<Integer> ll : buckets) {
            if (ll != null) {
                for (Integer i : ll) {
                    arr[curr] = i;
                    curr++;
                }
            }
        }
        return arr;
    }

    /**
     * Helper method for msd sort, recursively sorts a bucket
     *
     * @param bucket the bucket being sorted
     * @param n the digit being compared
     * @return the sorted bucket
     */
    private static LinkedList<Integer> rearrangeBucket(
            LinkedList<Integer> bucket, int n) {
        //end case: Bucket is empty or entire bucket has been sorted
        if (bucket.size() == 1 || n == 0) {
            return bucket;
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];

        for (Integer i : bucket) {
            int index = i % pow(10, n) / pow(10, n - 1) + 9;

            if (buckets[index] == null) {
                buckets[index] = new LinkedList<>();
            }
            buckets[index].add(i);
        }
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i] = rearrangeBucket(buckets[i], n - 1);
            }
        }
        bucket = new LinkedList<>();
        for (LinkedList<Integer> ll : buckets) {
            if (ll != null) {
                for (Integer i : ll) {

                    bucket.add(i);
                }
            }
        }
        return bucket;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }
}
