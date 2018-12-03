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
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
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
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
    	root.insert(key, value);
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        return root.rangeSearch(key, comparator);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
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
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
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
         * Package constructor
         */
        InternalNode() {
            super();
            children = new ArrayList<Node>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            boolean returnVal = false;
            returnVal = branchingFactor < children.size();
            return returnVal;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
        	int index = Collections.binarySearch(keys, key);
        	if (index >= 0){
        		index++;
        	}else{
        		index = (-1 * index) - 1;
        	}
            Node below = children.get(index);
            below.insert(key, value);
            if (below.isOverflow()){
            	Node neighbor = below.split();
            	K newKey = neighbor.getFirstLeafKey();
            	int index2 = Collections.binarySearch(keys, key);
            	if (index2 >= 0){
            		index2++;
            	}else{
            		index2 = (-1 * index2) - 1;
            	}
            	if (index2 >= 0){
            		children.set(index2, neighbor);
            	}else{
            		keys.add(index2, key);
            		children.add(index2 + 1, neighbor);
            	}
            }
            if (root.isOverflow()){
            	Node neighbor = split();
            	InternalNode replacement = new InternalNode();
            	replacement.keys.add(neighbor.getFirstLeafKey());
            	replacement.children.add(this);
            	replacement.children.add(neighbor);
            	root = replacement;
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	
        	int start = (keys.size() / 2) + 1;
        	int end = keys.size();
        	
        	InternalNode split = new InternalNode();
        	split.keys.addAll(keys.subList(start, end));
        	split.children.addAll(children.subList(start, end + 1));
        	children.subList(start, end + 1).clear();
        	keys.subList(start - 1, end).clear();
            return split;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	int index = Collections.binarySearch(keys, key);
        	if (index >= 0){
        		index++;
        	}else{
        		index = (-1 * index) - 1;
        	}
            return children.get(index).rangeSearch(key, comparator);
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
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            values = new ArrayList<V>();
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	boolean returnVal = false;
            returnVal = (branchingFactor - 1) < values.size();
            return returnVal;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
        	int place = Collections.binarySearch(keys, key);
        	int index;
        	//System.out.println(index);
        	if (place >= 0){
        		index = place;
        	}else{
        		index = (-1 * place) - 1;
        	}
        	if (place < 0){
        		values.add(index, value);
        		keys.add(index, key);
        	}else{
        		values.set(index, value);
        	}
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
         * (non-Javadoc)
         * @see BPTree.Node#split()
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
        	next = split;
        	
            return split;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            List<V> list = new ArrayList<V>();
            if (key == null || !(comparator.equals("==") || 
            		comparator.equals(">=") || comparator.equals("<="))){
            	return list;
            }else{
            	LeafNode curr = this;
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
            	return list;
            }
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
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
