import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        for (T i : data) {
            add(i);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        root = add(data, root);
    }

    /**
     * Helper method to add a node to the AVL
     *
     * @param data the data to be added to the tree
     * @param root the root to compare data to
     * @return A node representing the recursive state of the new tree
     */
    private AVLNode<T> add(T data, AVLNode<T> root) {
        if (root == null) {
            root = new AVLNode<>(data);
            root.setHeight(1);
            root.setBalanceFactor(0);
            size++;
        }
        if (data.compareTo(root.getData()) < 0) {
            AVLNode<T> added = add(data, root.getLeft());
            root.setLeft(added);
        }
        if (data.compareTo(root.getData()) > 0) {
            AVLNode<T> added = add(data, root.getRight());
            root.setRight(added);
        }
        updateHeight(root);
        updateBalanceFactor(root);
        root = balance(root);
        return root;
    }

    /**
     * Helper method to balance the AVL tree. This is primarily
     * used after any addition or remove method to make sure
     * the AVL tree stays balanced.
     *
     * @param root the root that needs balancing
     * @return The new root node after the balancing
     */
    private AVLNode<T> balance(AVLNode<T> root) {
        if (root.getBalanceFactor() == -2) {
            if (root.getRight().getBalanceFactor() == -1
                || root.getRight().getBalanceFactor() == 0) {
                root = rotateLeft(root);
            } else if (root.getRight().getBalanceFactor() == 1) {
                root = rotateRightLeft(root);
            }
        }

        if (root.getBalanceFactor() == 2) {
            if (root.getLeft().getBalanceFactor() == 1
                || root.getLeft().getBalanceFactor() == 0) {
                root = rotateRight(root);
            } else if (root.getLeft().getBalanceFactor() == -1) {
                root = rotateLeftRight(root);
            }
        }
        return root;
    }

    /**
     * Method to update the balance factor after 
     * things get changed in the AVL tree
     * @param root the node that needs to be updated
     */
    private void updateBalanceFactor(AVLNode<T> root) {
        if (root.getRight() != null && root.getLeft() != null) {
            root.setBalanceFactor(root.getLeft().getHeight()
                - root.getRight().getHeight());
        } else if (root.getLeft() != null) {
            root.setBalanceFactor(1 + root.getLeft().getHeight());
        } else if (root.getRight() != null) {
            root.setBalanceFactor(-1 - root.getRight().getHeight());
        } else {
            root.setBalanceFactor(0);
        }
    }

    /**
     * Method to update the height as things change
     * inside the AVL tree.
     *
     * @param root that needs to be updated
     */
    private void updateHeight(AVLNode<T> root) {
        int heightLeft = -1;
        int heightRight = -1;
        if (root.getLeft() != null) {
            heightLeft = root.getLeft().getHeight();
        }
        if (root.getRight() != null) {
            heightRight = root.getRight().getHeight();
        }
        root.setHeight(1 + Math.max(heightLeft, heightRight));   
    }

    /**
     * Helper method to rotate clockwise around a node
     *
     * @param root the node that needs to be rotated right
     * @return the new node after the rotation
     */
    private AVLNode<T> rotateRight(AVLNode<T> root) {
        AVLNode<T> parent = root.getLeft();
        root.setLeft(parent.getRight());
        parent.setRight(root);
        updateHeight(root);
        updateBalanceFactor(root);

        if (parent.getLeft() != null) {
            updateBalanceFactor(parent.getLeft());
            updateHeight(parent.getLeft());
        }
        updateHeight(parent);
        updateBalanceFactor(parent);
        return parent;
    }

    /**
     * Helper method to rotate left
     *
     * @param root the node that needs to be rotated left
     * @return the new node after the left rotation
     */
    private AVLNode<T> rotateLeft(AVLNode<T> root) {
        AVLNode<T> parent = root.getRight();
        root.setRight(parent.getLeft());
        parent.setLeft(root);
        updateHeight(root);
        updateBalanceFactor(root);

        if (parent.getRight() != null) {
            updateHeight(parent.getRight());
            updateBalanceFactor(parent.getRight());
        }
        updateHeight(parent);
        updateBalanceFactor(parent);
        return parent;
    }

    /**
     * Helper method to rotate right and then left
     * about a node
     *
     * @param root the root to be rotated right
     * @return the new parent node after the rotation
     */
    private AVLNode<T> rotateRightLeft(AVLNode<T> root) {
        root.setRight(rotateRight(root.getRight()));
        return rotateLeft(root);
    }

    /**
     * Helper method to rotate left and then right
     * about a node
     *
     * @param root the node to be rotated right
     * @return the new node after the rotation
     */
    private AVLNode<T> rotateLeftRight(AVLNode<T> root) {
        root.setLeft(rotateLeft(root.getLeft()));
        return rotateRight(root);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = remove(data, root, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * Helper method to remove an element from the tree. This method
     * needs to be separate to allow for a recursive call.
     *
     * @param data the data that we are looking for to remove
     * @param root the Node at which we are looking to compare to
     * @param removed a dummy node that can be used to return the data
     * @return A node that holds the data that is required to be removed
     * @throws NoSuchElementException when the data does not exist in the tree
     */
    private AVLNode<T> remove(T data, AVLNode<T> root, AVLNode<T> removed) {
        if (root == null) {
            throw new NoSuchElementException("Element does not exist in"
            + " the AVL");
        }
        if (data.compareTo(root.getData()) < 0) {
            root.setLeft(remove(data, root.getLeft(), removed));
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(remove(data, root.getRight(), removed));
        } else {
            if (removed != null) {
                removed.setData(root.getData());
            }
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getLeft() == null) {
                root = root.getRight();
            } else if (root.getRight() == null) {
                root = root.getLeft();
            } else {
                root = succeed(root, root, root.getRight());
                updateHeight(root);
                updateBalanceFactor(root);
                root = balance(root);
            }
        }
        if (root != null) {
            updateHeight(root);
            updateBalanceFactor(root);
            root = balance(root);
        }
        return root;
    }

    /**
     * Helper method to remove an element when there are two children
     *
     * @param parent node above of the successor node
     * @param successor successor of the root node
     * @param root the node that needs to be removed
     * @return A node that displays the state of the tree after removal
     */
    private AVLNode<T> succeed(AVLNode<T> parent, AVLNode<T> root,
        AVLNode<T> successor) {
        if (successor.getLeft() != null) {
            if (parent != root) {
                parent.setLeft(succeed(successor, root, successor.getLeft()));
            } else {
                parent.setRight(succeed(successor, root, successor.getLeft()));
            }
        } else {
            root.setData(successor.getData());
            if (parent != root) {
                parent.setLeft(successor.getRight());
            } else {
                parent.setRight(successor.getRight());
            }
        }
        updateHeight(parent);
        updateBalanceFactor(parent);
        parent = balance(parent);
        return parent;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        return get(data, root);
    }

    /**
     * Helper method to find the specified data in an AVL tree
     *
     * @param data the data that we are trying to find
     * @param root the node that we are comparing inside the AVL
     * @return the data inside the AVL tree. Not the original data
     */
    private T get(T data, AVLNode<T> root) {
        if (root == null) {
            throw new NoSuchElementException("Passed in data does not exist in"
                + " the tree.");
        }
        if (data.compareTo(root.getData()) < 0) {
            return get(data, root.getLeft());
        } else if (data.compareTo(root.getData()) > 0) {
            return get(data, root.getRight());
        }
        return root.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find a null element.");
        }
        return contains(data, root);
    }

    /**
     * Helper method to check if the passed in data is in the AVL tree
     * Additional method require to called recrusively.
     *
     * @param data the data being validated in the AVL
     * @param root the node being compared to data
     * @return boolean of whether data is in the AVL
     */
    private boolean contains(T data, AVLNode<T> root) {
        if (root == null) {
            return false;
        }
        if (data.compareTo(root.getData()) > 0) {
            return contains(data, root.getRight());
        }
        if (data.compareTo(root.getData()) < 0) {
            return contains(data, root.getLeft());
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    /**
     * Method to get the preorder of the AVL in an Arraylist format
     *
     * @param root the root being added to the result
     * @param result the list that stores the AVL's elements
     */
    private void preorder(AVLNode<T> root, List<T> result) {
        if (root != null) {
            result.add(root.getData());
            preorder(root.getLeft(), result);
            preorder(root.getRight(), result);
        }
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    /**
     * Helper method to get the postorder of the AVL.
     *
     * @param root the root being added to the result
     * @param result the list that stores the AVL's elements
     */
    private void postorder(AVLNode<T> root, List<T> result) {
        if (root != null) {
            postorder(root.getLeft(), result);
            postorder(root.getRight(), result);
            result.add(root.getData());
        }
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    /**
     * Helper method to get the inorder of the AVL in a list
     * Needs to be a separate method to be called recursively
     *
     * @param root the root being added to the result
     * @param result the list that stores the AVL's elements
     */
    private void inorder(AVLNode<T> root, List<T> result) {
        if (root != null) {
            inorder(root.getLeft(), result);
            result.add(root.getData());
            inorder(root.getRight(), result);
        }
    }

    @Override
    public List<T> levelorder() {
        ArrayList<T> result = new ArrayList<>();
        if (root != null) {
            levelorder(root, result);
        }
        return result;
    }

    /**
     * Helper method to get the levelorder of the AVL
     * Need to be a separate method to call recursively.
     *
     * @param node the node being added to the output
     * @param output the list that stores the AVL's elements
     * @return list of the elements in the AVL
     */
    private List<T> levelorder(AVLNode<T> node, ArrayList<T> output) {
        Queue<AVLNode<T>> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            AVLNode<T> curr = queue.poll();
            output.add(curr.getData());
            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
            }
        }
        return output;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
