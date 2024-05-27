/*
 * Student information for assignment: 
 * On my honor, Aaron Johnson, this programming assignment is my own work 
 * and I have not provided this code to any other student. 
 * UTEID:awj483
 * email address: awj483@utexas.edu
 * Number of slip days I am using: 0 
 */

 import java.util.Iterator;

 public class LinkedList<E> implements IList<E> {
     // CS314 students. Add you instance variables here.
     // You decide what instance variables to use.
     // Must adhere to assignment requirements. 
     // No ArrayLists or Java LinkedLists.
     private DoubleListNode<E> first;
     private DoubleListNode<E> last;
     private int size;
 
     // CS314 students, add constructors here:
     public LinkedList(){
        size = 0;
     }
     
     /*
      * toString method
      pre: none
      post: string representing the LinkedList
      */
     public String toString(){
        StringBuilder res = new StringBuilder();
        if(size == 0){
            return "[]";
        }
        res.append("[");
        res.append(first.getData());
        DoubleListNode<E> temp = first.getNext();
        //iterate through the linked list nodes and append to
        //the StringBuilder
        while(temp != null){
            res.append(", ");
            res.append(temp.getData());
            temp = temp.getNext();
        }
        res.append("]");
        return res.toString();
     }
 
     /*
      * return the linked list size
      pre: none
      post: linked list size
      */
     public int size(){
        return size;
     }
     /**
      * add item to the front of the list. <br>
      * pre: item != null <br>
      * post: size() = old size() + 1, get(0) = item
      * 
      * @param item the data to add to the front of this list
      */
     public void addFirst(E item) {
        if(item == null){
            throw new IllegalArgumentException("Null item");
        }

        if(size == 0){
            add(item);
        } else {
            DoubleListNode<E> newNode = new DoubleListNode<>(null, item, first);
            first.setPrev(newNode);
            first = newNode;
            size++;
        }
     }
 
     /**
      * add item to the end of the list. <br>
      * pre: item != null <br>
      * post: size() = old size() + 1, get(size() -1) = item
      * 
      * @param item the data to add to the end of this list
      */
     public void addLast(E item) {
        if(item == null){
            throw new IllegalArgumentException("Null item");
        }

        if(size == 0){
            add(item);
        } else {
            DoubleListNode<E> newNode = new DoubleListNode<>(last, item, null);
            last.setNext(newNode);
            last = newNode;
            size++;
        }
     }
 
     /**
      * remove and return the first element of this list. <br>
      * pre: size() > 0 <br>
      * post: size() = old size() - 1
      * 
      * @return the old first element of this list
      */
     public E removeFirst() {
        if(size() <= 0){
            throw new IllegalArgumentException("Can't Remove item");
        }
         E oldFirst = first.getData();
         first = first.getNext();
         size--;
         if(size == 0){
            last = null;
         }
        return oldFirst;
     }
 
     /**
      * remove and return the last element of this list. <br>
      * pre: size() > 0 <br>
      * post: size() = old size() - 1
      * 
      * @return the old last element of this list
      */
     public E removeLast() {
        if(size() <= 0){
            throw new IllegalArgumentException("Can't Remove item");
        }
        E oldLastData = last.data;
        if(size == 1){
            last = null;
            first = first.getNext();
        } else {
            DoubleListNode<E> temp = last.prev;
            temp.setNext(null);
            last = temp;
        }
        size--;
        return oldLastData;
     }

     /*
      * Add a linked list value
      pre: value != null
      post: size() = old size() + 1, get(size() - 1) = item
      */
     public void add(E val){
        if(val == null){
            throw new IllegalArgumentException("Null value");
        }
        DoubleListNode<E> newNode = new DoubleListNode<>(last, val, null);
        if(size == 0){
            first = newNode;
        } else {
            last.setNext(newNode);
        }
        last = newNode;
        size++;
     }

     /*
      * Set a linked list item to something different
        pre: 0 <= pos < size(), item != null
        post: get(pos) = item, return the old get(pos)
      */
     public E set(int pos, E val){
        if(val == null || pos < 0 || pos >= size()){
            throw new IllegalArgumentException("Null value");
        }
        DoubleListNode<E> temp = getNode(pos);
        E oldVal = temp.getData();
        temp.setData(val);
        return oldVal;
     }

     /*
      * remove a range of list values 
      * pre: 0 <= start <= size(), start <= stop <= size()
        post: size() = old size() - (stop - start)
      */
     public void removeRange(int val1, int val2){
        int range = val2 - val1;
        //Remove the value position range times
        for(int i = 0; i < range; i++){
            remove(val1);
        }

     }


        public Iterator<E> iterator() {
            return new doubleIterator();
        }
        
        private class doubleIterator implements Iterator<E> {
            
            private DoubleListNode<E> nodeWithNext;
            int removePos;
            
            public doubleIterator() {
                nodeWithNext = first;
                removePos = -1;
            }
            
            public boolean hasNext() {
                return nodeWithNext != null;
            }
            
            /*
             * pre: next has another value
             */
            public E next() {
                E res = nodeWithNext.data;
                // Make nodeWithNext refer to the next node in the structure.
                nodeWithNext = nodeWithNext.next;
                removePos++;
                return res;
            }
            
            public void remove() {
                LinkedList.this.remove(removePos);
                removePos--;
            }
        }
        
    /*
     * Find the position of an element in the list.
    pre: item != null
    post: return the index of the first element equal to item or -1 if item is not present
     */
     public int indexOf(E item){
        if(item == null){
            throw new IllegalArgumentException("Null value");
        }
        //Iterate through list and stop when the value is found
        for(int i = 0; i < size; i++){
            if(get(i) == item){
                return i;
            }
        }
        //return -1 if not present 
        return -1;
     }

     
     /*
      * find the position of an element in the list starting at a specified position.
        pre: 0 <= pos < size(), item != null
        post: return the index of the first element equal to item starting at pos or -1 if item is not present from position pos onward
      */
     public int indexOf(E item, int pos){
        if(item == null || pos < 0 || pos >= size()){
            throw new IllegalArgumentException("Null value");
        }
        //Iterate through list and stop when value is found
        for(int i = pos; i < size; i++){
            if(get(i) == item){
                return i;
            }
        }
        //if not found return -1
        return -1;
     }
     

     /*
      * Insert an item at a specified position in the list.
        pre: 0 <= pos <= size(), item != null
        post: size() = old size() + 1, get(pos) = item, all elements in the list with a positon >= pos have a position = old position + 1
      */
     public void insert(int pos, E val){
        if(val == null || pos < 0 || pos > size()){
            throw new IllegalArgumentException("Null value");
        }

        if(pos == 0){
            addFirst(val);
        } 
        else if(pos == size) {
            add(val);
        } else {
            //Insert value
            DoubleListNode<E> newNode = new DoubleListNode<E>();
            DoubleListNode<E> prev = getNode(pos - 1);
            newNode.setNext(prev.getNext());
            prev.setNext(newNode);
            newNode.setPrev(prev);
            newNode.setData(val);
            size++;
        }
     }

     /*
      * Get an element from the list.
        pre: 0 <= pos < size()
        post: return the item at pos
      */
     public E get(int pos){
        if(pos < 0 || pos >= size()){
            throw new IllegalArgumentException("Null value");
        }
        
        if(size - 1 == pos){
            return last.getData();
        }
        DoubleListNode<E> temp = first;
        for(int i = 0; i < pos; i++){
            temp = temp.getNext();
        }
        return temp.getData();
     }

     /*
      * Remove an element in the list based on position.
        pre: 0 <= pos < size()
        post: size() = old size() - 1, all elements of list with a position > pos have a position = old position - 1
      */
     public E remove(int pos){
        if(pos < 0 || pos >= size()){
            throw new IllegalArgumentException("Null value");
        }

        if(pos == 0){
            return removeFirst();
        } 
        else if(pos == size - 1){
            return removeLast();
        } else { 
            if(pos > size / 2){
                return removeBackwards(pos);
            } else {
                return removeForwards(pos);
            }
        }
     }

     /*
      * Iterates through the list backwards
      * and returns the old value
      * pre: 0 <= pos < size()
      */
     public E removeBackwards(int pos){
        if(pos < 0 || pos >= size()){
            throw new IllegalArgumentException("Null value");
        }
        DoubleListNode<E> temp = last;
        int i = size - 1;
        //keep iterating backwards until the position
        while(i > pos){
            temp = temp.getPrev();
            i--;
        }
        E res = temp.data;
        temp.prev.setNext(temp.next);
        temp.next.setPrev(temp.prev);
        size--;
        return res;
     }

     /*Iterates through the list forwards
     * and returns the old value
      */
     public E removeForwards(int pos){
        if(pos < 0 || pos >= size()){
            throw new IllegalArgumentException("Null value");
        }
        DoubleListNode<E> temp = first;
        int i = 0;
        //keep iterating until to the position
        while(i < pos){
            temp = temp.getNext();
            i++;
        }
        E res = temp.data;
        temp.prev.setNext(temp.next);
        temp.next.setPrev(temp.prev);
        size--;
        return res;
     }

     /*Remove the first occurrence of obj in this list. Return true if this list changed as a result of this call, false otherwise.
        pre: obj != null
        post: if obj is in this list the first occurrence has been removed and size() = old size() - 1. If obj is not present the list is not altered in any way.
      */
     public boolean remove(E val){
        boolean found = false;
        int i = 0;
        //Keep looking until the value is found then remove it
        while(!found && i < size){
            if(get(i) == val){
                found = true;
                remove(i);
            }
            i++;
        }
        return found;
     }

     /*
      * return the list to an empty state.
        pre: none
        post: size() = 0
      */
     public void makeEmpty(){
        if(size > 0){
            int oldsize = size;
            //Keep removing values until all gone
            for(int i = 0; i < oldsize; i++){
                removeFirst();
            }
        }
    }

    /*
     * Get a specific node
     * pre: 0 <= pos < size()
     */
    public DoubleListNode<E> getNode(int pos){
        if(pos < 0 || pos >= size()){
            throw new IllegalArgumentException("Null value");
        }
        if(size - 1 == pos){
            return last;
        }
        DoubleListNode<E> temp = first;
        //Iterate through nodes until position is found
        for(int i = 0; i < pos; i++){
            temp = temp.getNext();
        }
        return temp;
    }

    /*
     * Return a sublist of elements in this list from start inclusive to stop exclusive.
     *  This list is not changed as a result of this call.
     * pre: 0 <= start <= size(), start <= stop <= size()
     * post: return a list whose size is stop - start and contains 
     * the elements at positions start through stop - 1 in this list.
     */
     public LinkedList<E> getSubList(int start, int stop){
        if(start < 0 || start > size() || stop < start || stop > size()){
            throw new IllegalArgumentException("Null value");
        }
        LinkedList<E> LL = new LinkedList<>();
        for(int i = start; i < stop; i++){
            LL.add(get(i));
        }
        return LL;
     }

     /*
      * return the list to an empty state.
        pre: none
        post: size() = 0
      */
     public boolean equals(LinkedList<E> other){
        if(toString().equals(other.toString())){
            return true;
        }
        return false;
     }
 
 
     /**
      * A class that represents a node to be used in a linked list.
      * These nodes are doubly linked. All methods are O(1).
      *
      * @author Mike Scott
      * @version 9/25/2023
      */
 
     private static class DoubleListNode<E> {
 
         // instance variables
 
         // The data stored in this node.
         private E data;
 
         // The link to the next node (presumably in a list).
         private DoubleListNode<E> next;
 
         // The link to the previous node (presumably in a list).
         private DoubleListNode<E> prev;
 
         /**
          * default constructor.
          * <br>pre: none
          * <br>post: getData() = null, getNext() = null, getPrev() = null
          */
         public DoubleListNode() {
             this(null, null, null);
         }
 
         /**
          * create a DoubleListNode that holds the specified data
          * and refers to the specified next and previous elements.
          * <br>pre: none
          * <br>post: getData() = data, getNext() = next, getPrev() = prev
          * @param prev the previous node
          * @param data the  data this DoubleListNode should hold
          * @param next the next node
          */
         public DoubleListNode(DoubleListNode<E> prev, E data,
                 DoubleListNode<E> next) {
             this.prev = prev;
             this.data = data;
             this.next = next;
         }
 
         /* Note, the following methods are not necessary.
          * They are provided as a convenience. As this class is a private
          * nested class the only client is the LinkedList class itself and
          * the implementation of Iterator. We leave it up to the student
          * whether to access and change the private instance variables of a
          * node directly or via these methods.
          */
         /**
          * return the data in this node.
          * <br>pre: none
          * @return the data this DoubleListNode holds
          */
         public E getData() {
             return data;
         }
 
         /**
          * return the DoubleListNode this ListNode refers to.
          * <br>pre: none
          * @return the DoubleListNode this DoubleListNode refers to
          * (normally the next one in a list)
          */
         public DoubleListNode<E> getNext() {
             return next;
         }
 
         /**
          * return the DoubleListNode this DoubleListNode refers to.
          * <br>pre: none
          * @return the DoubleListNode this DoubleListNode refers to
          * (normally the previous one in a list)
          */
         public DoubleListNode<E> getPrev() {
             return prev;
         }
 
         /**
          * set the data in this node.
          * The old data is over written.
          * <br>pre: none
          * <br>post: getData() == data
          * @param data the new data for this DoubleListNode to hold
          */
         public void setData(E data) {
             this.data = data;
         }
 
         /**
          * set the next node this DoubleListNode refers to.
          * <br>pre: none
          * <br>post: getNext() = next
          * @param next the next node this DoubleListNode should refer to
          */
         public void setNext(DoubleListNode<E> next) {
             this.next = next;
         }
 
         /**
          * set the previous node this DoubleListNode refers to.
          * <br>pre: none
          * <br>post: getPrev() = next
          * @param prev the previous node this DoubleListNode should refer to
          */
         public void setPrev(DoubleListNode<E> prev) {
             this.prev = prev;
         }
     }
 }