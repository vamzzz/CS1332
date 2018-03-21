import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Your implementation of a binary search tree.
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Try again. Passed in data is"
                + " null");
        }
        for (T x: data) {
            add(x);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Try agian. Passed in data that"
                + " is null.");
        }
        root = addHelper(root, data);
    }

    /**
     * Adds the data in the right location of the BST recursively. It will
     * compare the data with the root node whether it is greater than and
     * less than the data. To hit level one case, the next data must be
     * equal to null, which let the code know that there is an empty space
     * in the BST. It will add the data by creating a new BSTNode to that
     * location.
     * 
     * @param cur current Node that is being traversed
     * @param data that is passed in the add method
     * @return the BSTNode where the data can be added.
     */
    private BSTNode<T> addHelper(BSTNode<T> cur, T data) {
        if (cur == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(cur.getData()) < 0) {
            cur.setLeft(addHelper(cur.getLeft(), data));
        } else if (data.compareTo(cur.getData()) > 0) {
            cur.setRight(addHelper(cur.getRight(), data));
        }
        return cur;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Try again. Passed in data"
                + " that is null.");
        }
        if (root == null) {
            throw new NoSuchElementException("Data doesn't exist in the BST.");
        }
        BSTNode<T> temp = new BSTNode<>(null);
        root = removeHelper(root, data, temp);
        if (temp.getData() == null) {
            throw new NoSuchElementException("Data doesn't exist in the BST.");
        }
        return temp.getData();
    }

    /**
     * The delete method finds the data that is passed recursively throughout
     * the BST. It takes care of 3 different cases when a node has no children,
     * one child, and two children). When there is a node with two children,
     * this method calls a helper method to find the successor to find the next
     * smallest value on the right subtree.
     *
     * @param cur The current node where the traversal is occuring
     * @param data The data that is passed in that needs to removed from the
     * BST
     * @param removed A dummy node that can be used to return the method
     * @return the node that will be removed
     */
    private  BSTNode<T> removeHelper(BSTNode<T> cur, T data,
        BSTNode<T> dummy) {
        if (cur == null) {
            return null;
        } else if (data.compareTo(cur.getData()) > 0) {
            cur.setRight(removeHelper(cur.getRight(), data, dummy));
        } else if (data.compareTo(cur.getData()) < 0) {
            cur.setLeft(removeHelper(cur.getLeft(), data, dummy));
        } else {
            dummy.setData(cur.getData());
            if (cur.getLeft() == null && cur.getRight() == null) {
                size--;
                return null;
            } else if (cur.getLeft() == null) {
                size--;
                return cur.getRight();
            } else if (cur.getRight() == null) {
                size--;
                return cur.getLeft();
            } else {
                BSTNode<T> temp = new BSTNode<>(null);
                T successor = getSuccessor(cur).getData();
                cur.setData(successor);
                cur.setRight(removeHelper(cur.getRight(), successor, temp));
            }
        }
        return cur;
    }

    /**
     * Helper method that finds the successor. The successor is the minimum
     * value on the right side of the tree.
     *
     * @param cur The current Node we are traversing to find the successor Node.
     * @return The Node where the successor is
     */
    private BSTNode<T> getSuccessor(BSTNode<T> cur) {
        BSTNode<T> successor = cur.getRight();
        while (successor.getLeft() != null) {
            successor = successor.getLeft();
        }
        return successor;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Passed in null. Try again.");
        }
        BSTNode<T> cur = getHelper(root, data);
        if (size == 0) {
            throw new NoSuchElementException("BST has no elements. Data"
                + " cannot be found.");
        } else {
            return cur.getData();
        }
    }

    /**
     * This method get recursively called to find the Node with the data in the
     * BST. Compars the data with the compareTo method.
     *
     * @throws java.util.NoSuchElementException if the Node is null
     * @param cur The current Node that you are traversing the BST with
     * @param data The data that we have to find in the BST
     * @return returns the the Node where the node exists
     * 
     */
    private BSTNode<T> getHelper(BSTNode<T> cur, T data) {
        if (cur == null) {
            throw new NoSuchElementException("The element does not exist.");
        } else if (data.equals(cur.getData())) {
            return cur;
        } else if (data.compareTo(cur.getData()) < 0) {
            return getHelper(cur.getLeft(), data);
        } else if (data.compareTo(cur.getData()) > 0) {
            return getHelper(cur.getRight(), data);
        }
        throw new NoSuchElementException("The element does not exist in BST.");
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Passed in data that is null."
                + " Try again.");
        } else if (size == 0) {
            return false;
        }
        return getContains(root, data) != null;
    }

    /**
     * This method checks recursively throughout the BST to see if the data that
     * is being passed is in the BST. 
     * 
     * @throws java.lang.IllegalArgumentException if the data is passed is null
     * @param cur The current Node where we are traversing
     * @param data The data that we are checking whether it exists in the BST
     * @return the data that the BST contains
     */
    private T getContains(BSTNode<T> cur, T data) {
        if (cur == null) {
            return null;
        } else if (data.compareTo(cur.getData()) == 0) {
            return cur.getData();
        } else if (data.compareTo(cur.getData()) < 0) {
            return getContains(cur.getLeft(), data);
        } else if (data.compareTo(cur.getData()) > 0) {
            return getContains(cur.getRight(), data);
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<>();
        helpPreorder(root, list);
        return list;
    }
    /**
     * This method will be going through the BST that will allow to order
     * the BST according to preorder format.
     *
     * @param cur is the current node that we traversing through the BST
     * @param list The arraylist that is passed in will include all the data
     * in the order of perorder
     */
    private void helpPreorder(BSTNode<T> cur, ArrayList<T> list) {
        if (cur != null) {
            list.add(cur.getData());
            helpPreorder(cur.getLeft(), list);
            helpPreorder(cur.getRight(), list);
        }
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<>();
        helpPostorder(root, list);
        return list;
    }

    /**
     * The helpPostorder method goes through the tree. It checks the left
     * subtree and adds the node to the list, and then checks the right
     * and adds them to the list
     *
     * @param cur the current Node we are at when traversing throuhg the BST
     * @param list The array list that we will placing all the ordered data into
     */
    private void helpPostorder(BSTNode<T> cur, ArrayList<T> list) {
        if (cur != null) {
            helpPostorder(cur.getLeft(), list);
            helpPostorder(cur.getRight(), list);
            list.add(cur.getData());
        }
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<>();
        helpInOrder(root, list);
        return list;
    }

    /**
     * This method will traverse through the entire BST. It checks the left
     * subtree and adds those nodes to the list, then adding the root. It
     * then check the right nodes and adds them to the list.
     *
     * @param cur the current node that is used to traverse the BST
     * @param list the list that will contain the inorder data (ascending order)
     */
    private void helpInOrder(BSTNode<T> cur, ArrayList<T> list) {
        if (cur != null) {
            helpInOrder(cur.getLeft(), list);
            list.add(cur.getData());
            helpInOrder(cur.getRight(), list);
        }
    }

    @Override
    public List<T> levelorder() {
        LinkedList<BSTNode<T>> aList = new LinkedList<>();
        ArrayList<T> daList = new ArrayList<>();
        aList.add(root);
        BSTNode<T> cur;
        while (!(aList.isEmpty())) {
            cur = aList.removeFirst();
            if (cur != null) {
                daList.add(cur.getData());
                if (cur.getLeft() != null) {
                    aList.add(cur.getLeft());
                }
                if (cur.getRight() != null) {
                    aList.add(cur.getRight());
                }
            }
        }
        return daList;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;

    }

    @Override
    public int height() {
        return helpHeight(root);

    }
    /**
     * A helper method to figure out the height of the BST. This method
     * gets called recursively. When each return happens of helpHeight,
     * the Math.max compares each return to deliver the max number.
     *
     * @param cur the Node that is being called recursively to go throughout
     * BST
     * @return an integer of the max height of the tree
     */
    private int helpHeight(BSTNode<T> cur) {
        if (cur == null) {
            return -1;
        } else {
            return (Math.max(helpHeight(cur.getLeft()),
                helpHeight(cur.getRight())) + 1);
        }
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
