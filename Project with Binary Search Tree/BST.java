package project6;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This class represent a BST(binary search tree) object which implements Java’s Collection<E> interface.
 * This means that the class needs to provide all of the methods from the interface and override accordingly
 * It also means that the class needs to implement the Iterable<E> interface
 * (since it is a superinterface of the Collection<E> interface).
 *
 * @param <E> means that the LinkedList class can take in any type of element.
 *
 * @author Jiahui Han
 *
 */
public class BST<E extends Comparable<E>> implements Collection<E> {


    private BSTNode<E> root;

    //the variable needed for removal
    Boolean success = false;

    //initial size is zero
    private int size = 0;

    /**
     * Get the reference to the element stored in the tree
     * with a value equal to the value passed as the parameter
     * or null if no equal element exist in this tree
     *
     * @param value an element to search for in this tree
     * @return the reference to the element equal to the parameter value
     *         or null if not such element exists
     */
    public E get(E value) {
        if (value == null) {return null;}
        if (root == null) { return null; }
        return get(root, value);
    }

    /**
     * Helper get method with two parameters
     *
     * Get the reference to the element stored in the tree
     * with a value equal to the value passed as the parameter
     * or null if no equal element exist in this tree
     *
     * @param n
     * @param value
     * @return the reference to the element equal to the parameter value
     *         or null if not such element exists
     */
    private E get(BSTNode<E> n, E value) {
        if (n == null) {return null;}
        if (n.data.equals(value)) { return n.data; }
        if (n.data.compareTo(value) < 0 ){
            return get(n.right, value);
        }
        if (n.data.compareTo(value) > 0 ){
            return get(n.left, value);
        }
        return null;
    }

    /**
     * Construct a string representation consists of a list of the collection’s elements
     * in the order they are returned by its iterator (in this project is inorder)
     *
     * @return a string representation of this collection
     */
    public String toString() {
        String result = "[";
        for (E e: this) {
            String cur = String.valueOf(e);
            result = result + cur + ", ";
        }
        int length = result.length();
        result = result.substring(0, length - 2) + "]";
        return result;
    }

    /**
     * Produces tree like string representation of this BST.
     * @return string containing tree-like representation of this BST. */
    public String toStringTreeFormat() {
        StringBuilder s = new StringBuilder();
        preOrderPrint(root, 0, s);
        return s.toString();
    }

    /**
     * Uses pre-order traversal to produce a tree-like representation of this BST.
     * @param tree the root of the current subtree
     * @param level level (depth) of the current recursive call in the tree to
     * determine the indentation of each item
     * @param output the string that accumulated the string representation of this * BST
     */
    private void preOrderPrint(BSTNode<E> tree, int level, StringBuilder output) {
        if (tree != null) {
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append(tree.data);
            preOrderPrint(tree.left, level + 1, output);
            preOrderPrint(tree.right, level + 1, output);
        } else { // print the null children
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++) spaces += " ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append("null");
        }
    }

    /**
     * Returns an iterator over the elements in this collection.
     * The elements should be returned in the order determined by the preorder traversal of the tree.
     *
     * @return a BSTPreorderIterator, and preorder traversal iterator
     */
    public Iterator<E> preorderIterator() {
        return new BSTPreorderIterator();
    }

    /**
     * Returns an iterator over the elements in this collection.
     * The elements should be returned in the order determined by the postorder traversal of the tree.
     *
     * @return a BSTPostorderIterator, and postorder traversal iterator
     */
    public Iterator<E> postorderIterator() {
        return new BSTPostorderIterator();
    }

    /**
     * Ensures that this collection contains the specified element.
     * Add the element if it was not in the BST
     *
     * @param e the element need to be added
     * @return true if element is successfully added, false if not
     */
    public boolean add(E e) {
        if (e == null) {return false;}
        if (get(e) != null) {return false;}
        root = add(root, e);
        size++;
        return true;
    }

    /**
     * Helper add method to ensure that this collection contains the specified element
     * Add the element if it was not in the BST
     *
     * @param n a BSTNode we wanna operate on
     * @param e the element need to be added
     * @return a BSTNode with reference to elements after the add method
     */
    private BSTNode<E> add(BSTNode<E> n, E e) {
        if (n == null) {
            return new BSTNode(e);
        }
        if (n.data.compareTo(e) < 0) {
            n.right = add(n.right, e);
        }
        if (n.data.compareTo(e) > 0) {
            n.left = add(n.left, e);
        }
        return n;
    }

    /**
     * Removes all of the elements from this collection.
     * Reset size to zero.
     */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Check if the Object is in the Binary Search Tree
     *
     * @param o the object that we wanna check
     * @return true if this collection contains the specified element.
     */
    @Override
    public boolean contains(Object o){
        if (o == null){return false;}
        //From Tutoring, Jeff
        if (!(Comparable.class.isAssignableFrom(o.getClass()))){
            return false;
        }if(this.root == null)
            return false;
        //Check if we can do a casting
        if (o.getClass() == root.data.getClass()){
            E m = (E) o;
            if (this.get(m) != null){
                return true;
            }
        }
        /*
        @SuppressWarnings("unchecked")
            E m = (E) o;
        if (this.get(m) != null){
            return true;
        }
        */
        return false;
    }

    /**
     * Check if the Binary Search Tree contains all elements in the specified collection.
     *
     * @param c the specified collection
     * @return true if this collection contains all of the elements in the specified collection.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o: c){
            if (!(contains(o))){
                return false;
            }
        }
        return true;
    }

    /**
     * Compares the specified object with this collection for equality.
     *
     * @param o the Object we wanna check if equals to the BST.
     * @return true if they are equal or false if not.
     */
    public boolean equals (Object o) {
        if (o == null){
            if (this == null){return true;}
            else{return false;}
        }

        //Check if the Object is a BST
        if (!(o instanceof BST)){
            return false;
        }
        BST b = (BST) o;
        if (((BST) o).size() != this.size()){return false;}
        for (Object e : b){
            if (!(contains(e))){
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the BST is empty.
     *
     * @return Returns true if this collection contains no elements.
     */
    @Override
    public boolean isEmpty() {
        if (root == null) {return true;}
        return false;
    }

    /**
     * Returns an iterator over the elements in this collection.
     * The elements should be returned in the order determined by the inorder traversal of the tree.
     *
     * @return an iterator over the elements in this collection.
     */
    @Override
    public Iterator iterator() {
        return new BSTInorderIterator(this.root);
    }

    /**
     * Removes a single instance of the specified element from this collection
     * Only happen if it is present (optional operation).
     * Call helper method getRemove
     *
     * @param o the Object we want to remove from the BST
     * @return true if successfully remove the Object, false if not.
     */
    @Override
    public boolean remove(Object o){
        if (root == null) {return false;}
        if (o == null) {return false;}
        if (o.getClass() == root.data.getClass()){
            E m = (E) o;
            root = getRemove(root, m);
        }
        /*
        @SuppressWarnings("unchecked")
        E m = (E) o;
        return getRemove(root, m);
        */
        return success;
    }

    /**
     * Helper method to determine if the element is in the BST.
     * If it is than further call trueRemove to remove it from the BST.
     *
     * @param n the BSTNode we are checking if it contains the element.
     * @param e the element we want to remove
     * @return a BSTNode with correct descendants after removal.
     */
    private BSTNode<E> getRemove(BSTNode<E> n, E e) {
        if (n == null) {success = false;}
        else if (n.data.compareTo(e) < 0 ){
            n.right = getRemove(n.right, e);
        }
        else if (n.data.compareTo(e) > 0 ){
            n.left = getRemove(n.left, e);
        }
        else {
            n = trueRemove(n);
            size--;
            success = true;
        }

        return n;
    }

    /**
     * Helper method to truely remove the node after we ensure it is in the BST.
     * Call getPredecessor when necessary
     *
     * @param n the BSTNode need to be removed
     * @return a BSTNode with correct descendants after removal.
     */
    private BSTNode<E> trueRemove(BSTNode<E> n) {

        //Case with only one child
        if (n.left == null) {
            return n.right;
        }
        if (n.right == null){
            return n.left;
        }

        //If two children, find predecessor
        n.data = getPredecessor(n.left).data;
        n.left = getRemove(n.left, n.data);
        return n;
    }

    /**
     * Helper method to find the predecessor of the BSTNode
     *
     * @param n the BSTNode that we want to find predecessor for
     * @return the BSTNode that is the corresponding predecessor
     */
    private BSTNode<E> getPredecessor(BSTNode<E> n) {
        BSTNode<E> temp = n;
        while (temp.right != null)
            temp = temp.right ;
        return temp;
    }

    /**
     * Find the number of elements in this collection.
     * Call helper size function with root as parameter.
     *
     * @return the number of elements in this collection.
     */
    @Override
    public int size() {
        return size(root);
    }


    /**
     * Find the number of elements in this collection recursively.
     *
     * @param n the BSTNode that we want to operate on
     * @return the number of elements in this collection.
     */
    private int size(BSTNode<E> n) {
        if (n == null) {return 0;}
        return 1 + size(n.left) + size(n.right);
    }

    /**
     * Construct an array containing all of the elements in this collection.
     *
     * @return an array containing all of the elements in this collection.
     */
    @Override
    public Object[] toArray() {
        //Create an ArrayList to store elements
        ArrayList<Object> resultArrayList = new ArrayList<>();
        for (E e: this) {
            resultArrayList.add(e);
        }
        Object[] result = new Object[resultArrayList.size()];
        for (int i = 0; i < resultArrayList.size(); i++) {
            result[i] = resultArrayList.get(i);
        }
        return result;
    }


    /**
     * Construct an array containing all of the elements in this collection.
     * The runtime type of the returned array is that of the specified array.
     * @param a the collection containing elements.
     * @param <T> the specified type.
     * @return an array containing all of the elements in this collection.
     */
    @Override
    public <T> T[] toArray(T[] a) {
        //from source code
        int size = this.size();
        if (a.length < size){
            //from source code of jdk
            a = (T[]) Array.newInstance(a.getClass().getComponentType(),size);
        }else if (a.length > size){
            a[size] = null;
        }

        int i = 0;
        for (E e: this){
            a[i] = (T) e;
            i++;
        }
        return a;
    }


    /**
     * Internal BSTNode class representing a Binary Search Node Object.
     * With its own data and left and right reference to two other BSTNodes
     *
     * @param <E> any type of element can be stored in the BSTNode, the specified type for the element.
     */
    private static class BSTNode<E> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        /**
         * one parameter constructor
         * @param e the element we want to store in the Node
         * default the next Node reference to null
         */
        private BSTNode(E e) {
            this.data = e;
            this.left = null;
            this.right = null;
        }

        /**
         *
         * @param e
         * @param left the BSTNode we want to put in left reference
         * @param right the BSTNode we want to put in right reference
         */
        private BSTNode(E e, BSTNode<E> left, BSTNode<E> right) {
            this.data = e;
            this.left = left;
            this.right = right;
        }

    }

    /**
     * Internal BSTInorderIterator class which implement Iterator<E>.
     * Construct a iterator determined by inorder traversal.
     */
    private class BSTInorderIterator implements Iterator<E> {

        //create a Stack to store element in the BST in the order we want
        private Stack<BSTNode<E>> inorderStack;

        /**
         * One parameter constructor of the class
         *
         * @param root the root of the BST that we want to create iterator for.
         */
        private BSTInorderIterator(BSTNode<E> root){
            inorderStack = new Stack<>();
            while (root != null) {
                inorderStack.push(root);
                root = root.left;
            }
        }

        /**
         * Check if the BST has next element
         *
         * @return true if there is another element in the BST or false if not
         */
        @Override
        public boolean hasNext() {
            return !inorderStack.isEmpty();
        }

        /**
         * return the current element pointed by the iterator.
         * Iterate to the next element in the BST.
         *
         * @return the current element pointed by the iterator.
         */
        @Override
        public E next() {
            BSTNode<E> curBSTNode = inorderStack.pop();
            //Store the value of the BSTNode we just pop
            E result = curBSTNode.data;
            if (curBSTNode.right != null) {
                curBSTNode = curBSTNode.right;
                while (curBSTNode != null) {
                    inorderStack.push(curBSTNode);
                    curBSTNode = curBSTNode.left;
                }
            }
            return result;
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }


    /**
     * Internal BSTPreorderIterator class which implement Iterator<E>.
     * Construct a iterator determined by Preorder traversal.
     */
    private class BSTPreorderIterator implements Iterator<E> {

        //create a Stack to store element in the BST in the order we want
        private Stack<BSTNode<E>> preorderStack;

        /**
         * No parameter constructor of the class
         */
        private BSTPreorderIterator() {
            preorderStack = new Stack<>();
            if (root != null) {preorderStack.push(root);}
        }

        /**
         * Check if the BST has next element
         *
         * @return true if there is another element in the BST or false if not
         */
        @Override
        public boolean hasNext() {
            return !preorderStack.isEmpty();
        }

        /**
         * return the current element pointed by the iterator.
         * Iterate to the next element in the BST.
         *
         * @return the current element pointed by the iterator.
         */
        @Override
        public E next() {
            BSTNode<E> cur = preorderStack.peek();
            if(cur.left != null) {
                preorderStack.push(cur.left);
            }
            else {
                BSTNode<E> tmp = preorderStack.pop();
                while( tmp.right == null )
                {
                    if(preorderStack.isEmpty()) return cur.data;
                    tmp = preorderStack.pop();
                }
                preorderStack.push(tmp.right);
            }

            return cur.data;
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }


    /*
    private class BSTPostorderIterator implements Iterator<E> {
        private Stack<BSTNode<E>> postorderStack;

        private BSTPostorderIterator() {
            postorderStack = new Stack<>();
            postorderStack = new Stack<>();
            while (root != null) {
                postorderStack.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !postorderStack.isEmpty();
        }

        @Override
        public E next() {
            return null;
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
    */

    /**
     * Internal BSTPostorderIterator class which implement Iterator<E>.
     * Construct a iterator determined by Postorder traversal.
     */
    private class BSTPostorderIterator implements Iterator<E> {

        //create a ArrayList to store element in the BST in the order we want
        private ArrayList<E> list = new ArrayList();
        int cursor;
        E cur;

        /**
         * Add data to the ArrayList according to the Postorder traversal recursively
         * @param Node
         */
        private void postOrder(BSTNode<E> Node) {
            if(Node != null) {
                postOrder(Node.left);
                postOrder(Node.right);
                list.add(Node.data);
            }
        }

        /**
         * No parameter constructor of the class
         */
        public BSTPostorderIterator() {
            postOrder(root);
            cursor = 0;
            cur = list.get(cursor);
        }

        /**
         * Check if the BST has next element
         *
         * @return true if there is another element in the BST or false if not
         */
        public boolean hasNext() {
            return cursor < (list.size());
        }

        /**
         * return the current element pointed by the iterator.
         * Iterate to the next element in the BST.
         *
         * @return the current element pointed by the iterator.
         */
        public E next() {
            cur = list.get(cursor);
            cursor++;
            return cur;
        }
    }


    /**
     * Find the least element in this set greater than or equal to the given element.
     *
     * @param e the element we need to find the least element greater than or equal for
     * @return the element found or null if there is no such element.
     */
    public E ceiling(E e) {
        if (e == null) {return null;}
        return ceiling(root, null, e);
    }

    /**
     * Helper method to find the least element in this set greater than or equal to the given element.
     *
     * @param n the BSTNode we want to operate on
     * @param temp the element that is temporarily greater than the element
     * @param e the element we need to find the least element greater than or equal for
     * @return the element found or null if there is no such element.
     */
    public E ceiling(BSTNode<E> n,E temp, E e) {
        if (n == null) {
            return temp;
        }
        if (n.data.equals(e)) {return n.data;}

        if (n.data.compareTo(e) < 0) {
            return ceiling(n.right,temp,e);
        }
        if (n.data.compareTo(e) > 0) {
            temp = n.data;
            return ceiling(n.left,temp,e);
        }
        return null;
    }

    /**
     * Find the greatest element in this set less than or equal to the given element.
     *
     * @param e the element we need to find the greatest element less than or equal for.
     * @return the element found or null if there is no such element.
     */
    public E floor (E e) {
        if (e == null) {return null;}
        return floor(root, null, e);
    }

    /**
     * Helper function to find the greatest element in this set less than or equal to the given element.
     *
     * @param n the BSTNode we want to operate on.
     * @param temp the element that is temporarily less than the element.
     * @param e the element we need to find the greatest element less than or equal for.
     * @return the element found or null if there is no such element.
     */
    public E floor(BSTNode<E> n,E temp, E e) {
        if (n == null) {
            return temp;
        }
        if (n.data.equals(e)) {return n.data;}
        if (n.data.compareTo(e) < 0) {
            temp = n.data;
            return floor(n.right,temp,e);
        }
        if (n.data.compareTo(e) > 0) {
            return floor(n.left,temp,e);
        }
        return null;
    }


    /*
    public Object clone() {
        if (root == null) {return null;}
        BST<E> newTree = new BST<>();
        newTree.root = clone(this.root);
        return newTree;
    }

    private BSTNode<E> clone(BSTNode<E> n) {
        if (n == null) {
            return null;
        }
        else {
            return new BSTNode(n.data, clone(n.left), clone(n.right));
        }
    }
    */

    /**
     * Construct a shallow copy of this BST instance.
     *
     * @return a shallow copy of this BST instance.
     */
    public Object clone() {
        if (root == null) {return null;}
        BST<E> newTree = new BST<>();
        newTree = this;
        return newTree;
    }


    /**
     * Find the first (lowest) element currently in this set.
     *
     * @return the first (lowest) element currently in this set.
     */
    public E first() {
        //Go all the way left
        BSTNode<E> cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.data;
    }

    /**
     * Find the last (highest) element currently in this set.
     *
     * @return the last (highest) element currently in this set.
     */
    public  E last() {
        //Go all the way right
        BSTNode<E> cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.data;
    }

    /**
     * Find the least element in this set strictly greater than the given element.
     *
     * @param e the element we need to find the least element strictly greater than for
     * @return the element found or null if there is no such element.
     */
    public E higher (E e) {
        if (e == null) {return null;}
        return higher(root, null, e);
    }

    /**
     * Helper method to find the least element in this set strictly greater than the given element.
     *
     * @param n the BSTNode we want to operate on
     * @param temp the element that is temporarily greater than the element
     * @param e the element we need to find the least element strictly greater than for
     * @return the element found or null if there is no such element.
     */
    public E higher(BSTNode<E> n,E temp, E e) {
        if (n == null) {
            return temp;
        }
        if (n.data.equals(e)) {
            if  (n.right == null) {return temp;}
            return n.right.data;
        }
        if (n.data.compareTo(e) < 0) {
            return higher(n.right,temp,e);
        }
        if (n.data.compareTo(e) > 0) {
            temp = n.data;
            return higher(n.left,temp,e);
        }
        return null;
    }

    /**
     * Find the greatest element in this set strictly less than the given element.
     *
     * @param e the element we need to find the greatest element strictly less than for.
     * @return the element found or null if there is no such element.
     */
    public E lower (E e) {
        if (e == null) {return null;}
        return lower(root, null, e);
    }

    /**
     * Helper function to find the greatest element in this set strictly less than the given element.
     *
     * @param n the BSTNode we want to operate on.
     * @param temp the element that is temporarily less than the element.
     * @param e the element we need to find the greatest element strictly less than for.
     * @return the element found or null if there is no such element.
     */
    public E lower(BSTNode<E> n,E temp, E e) {
        if (n == null) {
            return temp;
        }
        if (n.data.equals(e)) {
            if  (n.left == null) {return temp;}
            return n.left.data;
        }
        if (n.data.compareTo(e) < 0) {
            temp = n.data;
            return lower(n.right,temp,e);
        }
        if (n.data.compareTo(e) > 0) {
            return lower(n.left,temp,e);
        }
        return null;
    }

    @Override
    public boolean addAll(Collection c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator spliterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream stream() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream parallelStream() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate filter) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
