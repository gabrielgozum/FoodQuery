package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Filename: BPTree.java
 * Project: p5 (final project)
 * 
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * Contributor: Mitchell Schaller
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 * 
 * No known bugs.
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor, which checks for a correct
     * branching factor and sets the BF and root
     * appropriately.
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        this.root = new LeafNode();   
    }
    
    
    /**
     * The BPTree insert method, which is a call that goes to the 
     * root and eventually to the leaves if necessary.
     * @param  key and value, both generic types
     */
    @Override
    public void insert(K key, V value) {
    	root.insert(key, value);
    }
    
    
    /**
     * Checks for valid comparator then calls the 
     * internal node's rangeSearch method.
     * @param key, comparator
     * @return list of generic V
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        return root.rangeSearch(key, comparator);
    }
    
    
    /**
     * Provided toString method shows the format of 
     * the tree for debugging purposes.
     * @return string
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
        	this.keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /**
         * Returns list of FoodItem values based on key and comparator.
         * @param key
         * @param comparator
         * @return list
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        /**
         * Calls toString on keys to represent 
         * the various key values relevant to call.
         */
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor which allocates children array
         * and also initializes key through super call.
         */
        InternalNode() {
            super();
            children = new ArrayList<Node>();
        }
        
        /**
         * Returns first leafKey through the children list's 
         * zeroth index
         * @return Leaf type k
         */
        K getFirstLeafKey() {
            
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * Checks that branching factor isn't greater than
         * the amount of children in a node.
         * @return boolean
         */
        boolean isOverflow() {
            boolean returnVal = false;
            returnVal = branchingFactor < children.size();
            return returnVal;
        }
        
        /**
         * Adds a node to the tree and appropriately 
         * splits the corresponding insert node and subsequent
         * root.
         * @param key, value
         */
        void insert(K key, V value) {
        	// find the correct location, with adjustments given binarySearch
        	// return value
        	int index = Collections.binarySearch(keys, key);
        	int index2;
        	if (index >= 0){
        		index2 = index + 1;
        	}else{
        		index2 = (-1 * index) - 1;
        	}
            Node below = children.get(index2);
            below.insert(key, value);
            if (below.isOverflow()){
            	// first case of needing to split
            	Node neighbor = below.split();
            	K newKey = neighbor.getFirstLeafKey();
            	int index3 = Collections.binarySearch(keys, newKey);
            	int index4;
            	if (index3 >= 0){
            		index4 = index3 + 1;
            	}else{
            		index4 = (-1 * index3) - 1;
            	}
            	keys.add(index4, newKey);
            	children.add(index4 + 1, neighbor);
            }
            if (root.isOverflow()){
            	// second opportunity for needing to split
            	Node neighbor = split();
            	InternalNode replacement = new InternalNode();
            	replacement.keys.add(neighbor.getFirstLeafKey());
            	replacement.children.add(this);
            	replacement.children.add(neighbor);
            	root = replacement;
            }
        }
        
        /**
         * Splits a node that is too big based on branching factor
         * @return node
         */
        Node split() {
        	
        	// find beginning and end of list
        	int start = (keys.size() / 2) + 1;
        	int end = keys.size();
        	// Appropriately add the keys of altered structures
        	InternalNode split = new InternalNode();
        	split.keys.addAll(keys.subList(start, end));
        	split.children.addAll(children.subList(start, end + 1));
            // ensure lists are properly fixed
        	children.subList(start, end + 1).clear();
        	keys.subList(start - 1, end).clear();
            return split;
        }
        
        /**
         * Gets the index to start and calls the rangeSearch for leaf node
         * @param key, comparator
         * @return list
         */
        List<V> rangeSearch(K key, String comparator) {
        	int index = Collections.binarySearch(keys, key);
        	int index2; 
        	if (index >= 0){
        		index2 = index + 1;
        	}else{
        		index2 = (-1 * index) - 1;
        	}
            return children.get(index2).rangeSearch(key, comparator);
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        LeafNode prev;
        
        /**
         * Package constructor, calls for keys allocation
         * and gets values allocated as well.
         * 
         */
        LeafNode() {
            super();
            values = new ArrayList<V>();
        }
        
        
        /**
         * Returns first key at zeroth index of list
         * @return LeafNode k
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * For leaf, check that size of values list 
         * isn't greater than adjusted branching factor
         * @return boolean
         */
        boolean isOverflow() {
        	boolean returnVal = false;
            returnVal = (branchingFactor - 1) < values.size();
            return returnVal;
        }
        
        /**
         * Inserts at a leaf, getting correct location
         * and checking for root overflow
         * @params key, value
         */
        void insert(K key, V value) {
        	// get index with adjustment
        	int index;
        	int place = Collections.binarySearch(keys, key);
        	if (place >= 0){
        		index = place;
        	}else{
        		index = (-1 * place) - 1;
        	}
         	values.add(index, value);
       		keys.add(index, key);
       		// check for root overflow and therefore split
        	if (root.isOverflow()){
        		InternalNode replacement = new InternalNode();
        		Node below = split();
        		replacement.keys.add(below.getFirstLeafKey());
        		replacement.children.add(this);
        		replacement.children.add(below);
        		root = replacement;
        	}
        }
        
        /**
         * Splits node and properly updates next and prev references
         * @return LeafNode 
         */
        Node split() {
        	int start = (keys.size() + 1) / 2;
        	int end = keys.size();
        	
        	LeafNode split = new LeafNode();
        	split.keys.addAll(keys.subList(start, end));
        	split.values.addAll(values.subList(start, end));
        	values.subList(start, end).clear();
        	keys.subList(start, end).clear();
        	split.next = next;
        	split.prev = this;
        	if (next != null){
        		next.prev = split;
        	}
        	next = split;
        	
            return split;
        }
        
        /**
         * Actual meat of rangeSearch done at the leafs. 
         * Given the starting key, ensure that you don't have 
         * many duplicates, then either traverse forwards or 
         * backwards to find values to add to list
         * @return List
         */
        List<V> rangeSearch(K key, String comparator) {
            List<V> list = new ArrayList<V>();
            if (key == null || !(comparator.equals("==") || 
            		comparator.equals(">=") || comparator.equals("<="))){
            	return list;
            }else{
            	// ensure duplicates not lost from binary search
            	LeafNode curr = this;
            	while (curr.prev != null){
            		if (curr.prev.keys.contains(curr.getFirstLeafKey())){
            			curr = curr.prev;
            			continue;
            		}else{
            			break;
            		}
            	}
            	// Go backwards
            	if (comparator.equals("<=")){
            		while (curr != null){
	            		Iterator<K> keysIt = curr.keys.iterator();
	            		Iterator<V> valuesIt = curr.values.iterator();
	            		while (keysIt.hasNext()){
	            			K currKey = keysIt.next();
	            			V currValue = valuesIt.next();
	            			switch(comparator){
	            				case ">=": 
	            					if (currKey.compareTo(key) >= 0) list.add(currValue);
	            					break;
	            				case "==":
	            					if (currKey.compareTo(key) == 0) list.add(currValue);
	            					break;
	            				case "<=":
	            					if (currKey.compareTo(key) <= 0) list.add(currValue);
	            					break;
	            			}	
	            		}
	            		if(curr.prev != null){
	            			curr = curr.prev;
	            		}else{
	            			break;
	            		}
            		}
            	}else {
            		// go forwards 
            		while (curr != null){
	            		Iterator<K> keysIt = curr.keys.iterator();
	            		Iterator<V> valuesIt = curr.values.iterator();
	            		while (keysIt.hasNext()){
	            			K currKey = keysIt.next();
	            			V currValue = valuesIt.next();
	            			switch(comparator){
	            				case ">=": 
	            					if (currKey.compareTo(key) >= 0) list.add(currValue);
	            					break;
	            				case "==":
	            					if (currKey.compareTo(key) == 0) list.add(currValue);
	            					break;
	            				case "<=":
	            					if (currKey.compareTo(key) <= 0) list.add(currValue);
	            					break;
	            			}	
	            		}
	            		curr = curr.next;
            		}
            	}
            }
            	return list;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            int j = rnd1.nextInt(10);
            list.add((double)j);
            bpTree.insert((double)j, (double)j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
