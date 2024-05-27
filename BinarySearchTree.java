/* CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 *  On my honor, Aaron Johnson, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: awj483
 *  email address: awjohnson2023@gmail.com
 *  TA name: Eliza
 *  Number of slip days I am using: 0 
 */

 import java.util.ArrayList;
 import java.util.List;

 /**
  * Shell for a binary search tree class.
  * @author scottm
  * @param <E> The data type of the elements of this BinarySearchTree.
  * Must implement Comparable or inherit from a class that implements
  * Comparable.
  *
  */
 public class BinarySearchTree<E extends Comparable<? super E>> {
 
     private BSTNode<E> root;
     // CS314 students. Add any other instance variables you want here
     private int size;
     // CS314 students. Add a default constructor here if you feel it is necessary.
     public BinarySearchTree() {
        root = null;
        size = 0;
     }
 
     /**
      *  Add the specified item to this Binary Search Tree if it is not already present.
      *  <br>
      *  pre: <tt>value</tt> != null<br>
      *  post: Add value to this tree if not already present. Return true if this tree
      *  changed as a result of this method call, false otherwise.
      *  @param value the value to add to the tree
      *  @return false if an item equivalent to value is already present
      *  in the tree, return true if value is added to the tree and size() = old size() + 1
      */
     public boolean add(E value) {
        if(value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }
        int preSize = size;
        root = addHelp(value, root);
        return preSize < size;
     }

     /*
      * Helper method for add method
      */
     private BSTNode<E> addHelp(E val, BSTNode<E> n) {
        if(n == null) {
            size++;
            return new BSTNode<E>(val);
        } else {
            int dir = val.compareTo(n.data);
            //Continue through tree depending on direction
            if(dir < 0) {
                n.left = addHelp(val, n.left);
            } else if(dir > 0) {
                n.right = addHelp(val, n.right);
            }
            return n;
        }
     }
     
 
     /**
      *  Remove a specified item from this Binary Search Tree if it is present.
      *  <br>
      *  pre: <tt>value</tt> != null<br>
      *  post: Remove value from the tree if present, return true if this tree
      *  changed as a result of this method call, false otherwise.
      *  @param value the value to remove from the tree if present
      *  @return false if value was not present
      *  returns true if value was present and size() = old size() - 1
      */
     public boolean remove(E value) {
        if(value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }
        int oldSize = size;
        root = removeHelp(value, root);
        return oldSize > size;
     }

     /*
      * Helper methodfor remove
      */
     private BSTNode<E> removeHelp(E value, BSTNode<E> n){
        if(n == null) {
            return null;
        }
        int dir = value.compareTo(n.data);
        //Cases for different directions
        if(dir < 0) {
            n.left = removeHelp(value, n.left);
        } else if(dir > 0) {
            n.right = removeHelp(value, n.right);
        } else {
            if(n.isLeaf()) {
                size--;
                return null;
            } else if(n.right == null) {
                size--;
                return n.left;
            } else if(n.left == null) {
                size--;
                return n.right;
            } else {
                //Must replace the node with the next largest
                n.data = getMax(n.left);
                n.left = removeHelp(n.data, n.left);
            }
        }
        return n;
     }

 
     /**
      *  Check to see if the specified element is in this Binary Search Tree.
      *  <br>
      *  pre: <tt>value</tt> != null<br>
      *  post: return true if value is present in tree, false otherwise
      *  @param value the value to look for in the tree
      *  @return true if value is present in this tree, false otherwise
      */
     public boolean isPresent(E value) {
        if(value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }
        return root == null ? false : isPresentHelp(value, root);
     }

     /*
      * Helper method for isPresent
      */
     private boolean isPresentHelp(E value, BSTNode<E> n) {
        if(n == null) {
            return false;
        } else {
            int dir = value.compareTo(n.data);
            //Keep searching if we haven't made it yet
            if(dir < 0) {
                return isPresentHelp(value, n.left);
            } else if(dir > 0) {
                return isPresentHelp(value, n.right);
            }
            return true;
        }
     }
 
 
     /**
      *  Return how many elements are in this Binary Search Tree.
      *  <br>
      *  pre: none<br>
      *  post: return the number of items in this tree
      *  @return the number of items in this Binary Search Tree
      */
     public int size() {
        return size;
     }
 
     /**
      *  return the height of this Binary Search Tree.
      *  <br>
      *  pre: none<br>
      *  post: return the height of this tree.
      *  If the tree is empty return -1, otherwise return the
      *  height of the tree
      *  @return the height of this tree or -1 if the tree is empty
      */
     public int height() {
        return heightHelp(root);
     }

     /*
      * Helper method for height
      */
     private int heightHelp(BSTNode<E> n) {
        return n == null ? -1 : (1 + Math.max(heightHelp(n.left), heightHelp(n.right)));
     }
 
     /**
      *  Return a list of all the elements in this Binary Search Tree.
      *  <br>
      *  pre: none<br>
      *  post: return a List object with all data from the tree in ascending order.
      *  If the tree is empty return an empty List
      *  @return a List object with all data from the tree in sorted order
      *  if the tree is empty return an empty List
      */
     public List<E> getAll() {
        if(root == null){
            return new ArrayList<E>();
        }
        List<E> list = allHelper(root);
        return list;
     }

     /*
      * Helper method for getAll method
      */
     private List<E> allHelper(BSTNode<E> n){
        if(n == null) {
            return new ArrayList<E>();
        }
        List<E> list = new ArrayList<E>();
        //Finds all in the left side of the tree
        list.addAll(allHelper(n.left));
        list.add(n.data);
        //Finds all in right side of the tree
        list.addAll(allHelper(n.right));
        return list;
     }
 
 
 
     /**
      * return the maximum value in this binary search tree.
      * <br>
      * pre: <tt>size()</tt> > 0<br>
      * post: return the largest value in this Binary Search Tree
      * @return the maximum value in this tree
      */
     public E max() {
        return root == null ? root.data : getMax(root);
     }

     /*
      * Helper method for max method
      */
     private E getMax(BSTNode<E> n) {
        //Keep going right until we can't anymore
        while(n.right != null){
            n = n.right;
        }
        return n.data;
     }
 
     /**
      * return the minimum value in this binary search tree.
      * <br>
      * pre: <tt>size()</tt> > 0<br>
      * post: return the smallest value in this Binary Search Tree
      * @return the minimum value in this tree
      */
     public E min() {
        return getMin(root);
     }

     private E getMin(BSTNode<E> n) {
        //Keep going left until we can't anymore
        while(n.left != null){
            n = n.left;
        }
        return n.data;
     }
 
     /**
      * An add method that implements the add algorithm iteratively 
      * instead of recursively.
      * <br>pre: data != null
      * <br>post: if data is not present add it to the tree, 
      * otherwise do nothing.
      * @param data the item to be added to this tree
      * @return true if data was not present before this call to add, 
      * false otherwise.
      */
     public boolean iterativeAdd(E data) {
        if(data == null){
            throw new IllegalArgumentException("Data cannot be null");
        } else if (size == 0) {
            root = new BSTNode<E>(data);
        }
        int preSize = size;
        //Check if value is already in the tree
        if(!getAll().contains(data)){
            iterativeAddHelp(data, root);
        }
        return preSize < size;
     }

     /*
      * Helper method for the iterativeAdd method
      */
     private  void iterativeAddHelp(E data, BSTNode<E> n) {
        BSTNode<E> curr = n;
        boolean found = true;
        //Keep searching the tree until we've found the place to insert the value
        while(found) {
            int dir = data.compareTo(curr.data);
            if(dir > 0){
                if(curr.right == null) {
                    curr.right = new BSTNode<E>(data);
                    found = false;
                    size++;
                } else {
                    curr = curr.right;
                }
            } else if (dir < 0) {
                if(curr.left == null) {
                    curr.left = new BSTNode<E>(data);
                    found = false;
                    size++;
                } else {
                    curr = curr.left;
                }
            }
        }
     }
 
 
     /**
      * Return the "kth" element in this Binary Search Tree. If kth = 0 the
      * smallest value (minimum) is returned.
      * If kth = 1 the second smallest value is returned, and so forth.
      * <br>pre: 0 <= kth < size()
      * @param kth indicates the rank of the element to get
      * @return the kth value in this Binary Search Tree
      */
     public E get(int kth) {
        List<E> list = getAll();
        return list.get(kth);
     }
 
 
     /**
      * Return a List with all values in this Binary Search Tree 
      * that are less than the parameter <tt>value</tt>.
      * <tt>value</tt> != null<br>
      * @param value the cutoff value
      * @return a List with all values in this tree that are less than 
      * the parameter value. If there are no values in this tree less 
      * than value return an empty list. The elements of the list are 
      * in ascending order.
      */
     public List<E> getAllLessThan(E value) {
        List<E> list = getAll();
        List<E> allLess = new ArrayList<E>();
        //Iterate through each item to list and add depnding on value
        for(E item : list){
            if(item.compareTo(value) < 0){
                allLess.add(item);
            }
        }
         return allLess;
     }
 
 
     /**
      * Return a List with all values in this Binary Search Tree 
      * that are greater than the parameter <tt>value</tt>.
      * <tt>value</tt> != null<br>
      * @param value the cutoff value
      * @return a List with all values in this tree that are greater
      *  than the parameter value. If there are no values in this tree
      * greater than value return an empty list. 
      * The elements of the list are in ascending order.
      */
     public List<E> getAllGreaterThan(E value) {
        List<E> list = getAll();
        List<E> allGreater = new ArrayList<E>();
        //Iterate through each item to list and add depnding on value
        for(E item : list){
            if(item.compareTo(value) > 0){
                allGreater.add(item);
            }
        }
         return allGreater;
     }
 
 
 
     /**
      * Find the number of nodes in this tree at the specified depth.
      * <br>pre: none
      * @param d The target depth.
      * @return The number of nodes in this tree at a depth equal to
      * the parameter d.
      */
     public int numNodesAtDepth(int d) {
         return helpDepth(root, d, 0);
     }

     /*
      * Helper method for numNodesAtDepth method
      */
     private int helpDepth(BSTNode<E> n, int d, int curr){
        if(curr == d){
            return 1;
        } else if(n == null){
            return 0;
        } else {
            return helpDepth(n.left, d, curr + 1) + helpDepth(n.right, d, curr + 1);
        }
     }
 
     /**
      * Prints a vertical representation of this tree.
      * The tree has been rotated counter clockwise 90
      * degrees. The root is on the left. Each node is printed
      * out on its own row. A node's children will not necessarily
      * be at the rows directly above and below a row. They will
      * be indented three spaces from the parent. Nodes indented the
      * same amount are at the same depth.
      * <br>pre: none
      */
     public void printTree() {
         printTree(root, "");
     }
 
     private void printTree(BSTNode<E> n, String spaces) {
         if(n != null){
             printTree(n.getRight(), spaces + "  ");
             System.out.println(spaces + n.getData());
             printTree(n.getLeft(), spaces + "  ");
         }
     }
 
     private static class BSTNode<E extends Comparable<? super E>> {
         private E data;
         private BSTNode<E> left;
         private BSTNode<E> right;
 
         private BSTNode() {
             this(null);
         }
 
         private BSTNode(E initValue) {
             this(null, initValue, null);
         }
 
         private BSTNode(BSTNode<E> initLeft,
                 E initValue,
                 BSTNode<E> initRight) {
             data = initValue;
             left = initLeft;
             right = initRight;
         }

         private BSTNode<E> getRight() {
            return right;
         }

         private BSTNode<E> getLeft() {
            return left;
         }

        private E getData() {
            return data;
        }
 
         private boolean isLeaf() {
             return left == null && right == null;
         }
     }
 }