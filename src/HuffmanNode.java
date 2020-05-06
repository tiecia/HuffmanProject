/**
 * @author Tyler Ciapala-Hazlerig
 * CSE 143 Data Structures
 * Huffman Coding Project: HuffmanNode
 * 5/5/2020
 */

public class HuffmanNode implements Comparable<HuffmanNode> {
    /**
     * Stores the character this node represents.
     * Is 0 when node does not store a character.
     */
    private char character;

    /**
     * Stores the frequency of the given character in the file the overall {@link HuffmanTree} represents.
     * Stores -1 when frequency is not given, such a constructing from a .code file.
     */
    private int frequency;

    /**
     * Stores the left child node
     */
    private HuffmanNode left;

    /**
     * Stores the right child node
     */
    private HuffmanNode right;

    /**
     * Creates a HuffmanNode with specific character and frequency values.
     * Used when creating a {@link HuffmanTree} from a decompressed file.
     *
     * @param character The character to assign the new node.
     * @param frequency The frequency to assign the character in this node.
     */
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    /**
     * Creates a HuffmanNode with a specific frequency, left node, and right node.
     * Used when creating a {@link HuffmanTree} from a decompressed file.
     *
     * @param frequency The frequency to assign this node.
     * @param left The HuffmanNode to assign as the left child.
     * @param right The HuffmanNode to assign as the right child.
     */
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this((char) 0, frequency);
        this.left = left;
        this.right = right;
    }

    /**
     * Creates a blank "intermediary" HuffmanNode.
     * Used as intermediary nodes when creating a tree from a .code file.
     * Frequency is automatically set to -1
     */
    public HuffmanNode(){
        this.frequency = -1;
    }

    /**
     * Tests to see if this node is a leaf node.
     * A leaf node is defined and both children equalling null.
     *
     * @return true is node is leaf; false if node is not a leaf.
     */
    public boolean isLeaf(){
        return this.right == null && this.left == null;
    }

    /**
     * Used to retrieve the character this node stores.
     * Note: Characters are automatically set to 0 when not assigned. All "intermediary" nodes will have a
     * character of zero. To properly test if a node stores a character use getCharacter() and getFrequency() combined.
     *
     * @return The character this node stores.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Used to retrieve the frequency the character in the node shows up in the file.
     *
     * @return the frequency of the stored character; -1 if the node is an "intermediary" node within the tree.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * @return the node attached to the left side of this node.
     */
    public HuffmanNode getLeft() {
        return left;
    }

    /**
     * @return the node attached to the right of this node.
     */
    public HuffmanNode getRight() {
        return right;
    }

    /**
     * @param left the node to assign as the new left child node.
     */
    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    /**
     * @param right the node to assign as the new right child node.
     */
    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    /**
     * This method compares this HuffmanNode to another HuffmanNode. Used in the priority queue for sorting.
     * @param o The node to compare this node to.
     * @return this node's frequency minus the other node's frequency
     */
    @Override
    public int compareTo(HuffmanNode o) {
        return this.getFrequency() - o.getFrequency();
    }
}