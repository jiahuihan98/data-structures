package project3;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This class represents a LinkedList object which implements Collection<E></E> interface.
 * It implements all methods from the interface and override or add multiple methods.
 * @param <E> means that the LinkedList class can take in any type of element.
 *
 * @author Jiahui Han
 */
public class LinkedList<E> implements Collection<E> {
    private Node<E> head;
    private int size;

    /**
     * no parameter constructor
     * initialize the head to null and size to zero
     */
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * method that returns the index of the first occurrence of the specified element in this list
     * return -1 if this list does not contain the element.
     * @param o the object that we want to find it's index
     * @return index of the first occurrence or -1 if not found
     */
    //needed
    public int indexOf(Object o) {
        //if the list is empty then return -1
        if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size; i++) {
            if (o == null) {
                if (this.get(i) == null) {
                    return i;
                }
            } else if (o.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the corresponding element of the index
     * @throws IndexOutOfBoundsException if index less then zero or larger than size of the list.
     */
    //needed
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (head == null) {
            return null;
        }
        if (index == 0) {
            return head.data;
        }
        //initial a count so that we can iterate index number of times
        int count = 0;
        Node<E> temp = head;
        while (temp != null) {
            if (count == index) {
                return temp.data;
            }
            count += 1;
            temp = temp.next;
        }
        return null;
    }

    /**
     * Override the toString method to return a string representation of the list
     * consist of elements returned by the iterator, enclosed by [], separated by ", "
     * @return a string representation of this collection
     */
    @Override
    //needed
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        String result = "[";
        for (E e: this) {
            if (String.valueOf(e) != null && e != null){
                result += String.valueOf(e) + ", ";
            }
        }
        result += "]";
        return result;
    }

    /**
     * compatible with the specification provided by Collections class sort method
     * sorting the array and then converting it back to a list
     * @author Joanna
     */
    //needed
    public void sort( ) {
        Object [] array = this.toArray();
        Arrays.sort(array);
        this.clear();
        for (Object o : array){
            this.add((E) o);
        }

    }

    /**
     * Compares the specified object with this collection for equality.
     * @param o the object we are comparing with
     * @return true if they are equal
     */
    public boolean equals(Object o) {
        if (o == null ) {
            if (head == null) { return true; }
            return false;
        }
        if (!(o instanceof LinkedList)) {
            return false;
        }
        LinkedList l = (LinkedList)o;
        if (l.size != this.size) { return false; }

        for (int i = 0; i < this.size; i++) {
            if (!(this.get(i).equals(l.get(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of elements in this collection.
     * @return integer of the number of elements
     */
    @Override
    //needed
    public int size() {
        return this.size;
    }

    /**
     * Check if the collection is empty
     * @return Returns true if this collection contains no elements
     */
    @Override
    //needed
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * check if specific object is in the collection
     * @param o the object that we want to check if in the collection
     * @return Returns true if this collection contains the specified element.
     */
    @Override
    //needed
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public void forEach(Consumer<? super E> action) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * get every elements from the list and create a corresponding object array
     * @return Returns an array containing all of the elements in this collection.
     */
    @Override
    //needed
    public Object[] toArray() {
        Object[] resultArray = new Object[size];
        for (int i = 0; i < size; i++){
            resultArray[i] = this.get(i);
        }
        return resultArray;
    }

    /**
     * get every elements from the list and create a corresponding array
     * @param a input a list of any type of elements
     * @param <T> runtime type of the returned array is that of the specified array
     * @return Returns an array containing all of the elements in this collection
     */
    @Override
    //needed
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
     * Ensures that this collection contains the specified element
     * add to the end of the list
     * @param e the element we want to add to the collection
     * @return true if the element is successfully added
     */
    @Override
    //required and needed
    public boolean add(E e) {
        // if the list was empty, just add in the element as head
        if (size == 0) {
            Node tmp = new Node<>(e);
            head = tmp;
            size ++ ;
            return true;
        }
        else{
            Node<E> tmp = head;
            //iterate through the end of the list
            while(tmp.next != null) {
                tmp = tmp.next;
            }
            Node newNode = new Node<>(e);
            tmp.next = newNode;
            size++;
            return true;
        }
    }

    /**
     * Removes a single instance of the specified element from this collection, if it is present
     * @param o the object we like to remove
     * @return true if it is successfully removed
     */
    @Override
    //required
    public boolean remove(Object o) {
        //nothing to be removed
        if (head == null) {
            return false;
        }
        //set the list to null if the list only have one object
        if (this.size == 1){
            if (o.equals(head.data)) {
                head = null;
                size--;
                return true;
            }
            else{
                return false;
            }
        }

        Node tmp = head;
        if (o.equals(head.data)){
            head = head.next;
            size--;
            return true;
        }
        while(tmp.next != null){
            if (o.equals(tmp.next.data)){
                tmp.next = tmp.next.next;
                size--;
                return true;
            }else{
                tmp = tmp.next;
            }
        }
        return false;

    }

    /**
     * method that return the hashcode
     * @return integer that represent the hashcode
     */
    public int hashCode(){
        return super.hashCode();
    }

    /**
     * check if every element un the specified collection is in the collection
     * @param c the collection with all elements we want to check
     * @return true if this collection contains all of the elements in the specified collection
     */
    @Override
    //needed
    public boolean containsAll(Collection<?> c) {
        Iterator<?> e = c.iterator();
        while (e.hasNext()) {
            if (!(contains(e.next()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes all of the elements from this collection
     * reset head to null and size to 0
     */
    @Override
    //required and needed
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public Spliterator<E> spliterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<E> stream() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<E> parallelStream() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * create an iterator for the collection
     * @return an instance of the LinkedListIterator
     */
    //needed and required
    public Iterator<E> iterator(){
        return new LinkedListIterator();
    }


    /**
     * internal private class of object Node
     * has two data field "data" and reference to the next Node
     * @param <E> any type of element can be stored in a Node object
     *
     * @author Jiahui Han
     */
    private class Node<E> {
        public E data;
        public Node<E> next;

        /**
         * one parameter constructor
         * @param e the element we want to store in the Node
         * default the next Node reference to null
         */
        public Node(E e) {
            this.data = e;
            this.next = null;
        }

        /**
         * two parameters constructor
         * @param e the element we want to store in the Node
         * @param n the next Node reference
         */
        public Node(E e, Node<E> n) {
            this.data = e;
            this.next = n;
        }

        /**
         * return true if the Node has next Node instead of null reference
         * @return true if next Node reference is not null
         */
        public boolean hasNext() {
            if (next != null) {
                return true;
            }
            return false;
        }

    }

    /**
     * Internal private class that implements Iterator<E>
     * allow us to create an iterator for the LinkedList Class
     *
     * @author Jiahui Han
     */
    private class LinkedListIterator implements Iterator<E> {
        private int j = 0;

        /**
         * default constructor
         */
        public LinkedListIterator(){ }

        /**
         * check if there is next Node to iterate through
         * @return true if there is
         */
        public boolean hasNext(){return j< size;}

        /**
         * return the next Node's data
         * @return the next Node's data
         * @throws NoSuchElementException if we cannot find the next element
         */
        public E next() throws NoSuchElementException{
            if (j == size){
                throw new NoSuchElementException("No such element");
            }
            return get(j++);
        }
        //do not implement remove
    }
}
