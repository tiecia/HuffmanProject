/**
 * @author Tyler Ciapala-Hazlerig
 * CSE 143 Data Structures
 * Huffman Coding Project: HuffmanTree
 * 5/5/2020
 */

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class HuffmanTree {

    /**
     * The root of the huffman tree. Will be populated with all nodes once the HuffmanTree(int[] count)
     * constructor is called
     */
    HuffmanNode root;

    /**
     * Creates a Huffman tree based on th frequency's given in the passed in array.
     * If a number in the array is greater than 0 then a node is created with the character as the ascii value of the
     * index and the frequency the data of the index.
     *
     * @param count array with integers that correspond to the frequency of the index's ascii character
     */
    public HuffmanTree(int[] count){
        Queue<HuffmanNode> treeBuilder = new PriorityQueue<>();
        //Populate priority queue with all valid nodes
        for (int i = 0; i < count.length; i++) {
            if(count[i] > 0){
                treeBuilder.add(new HuffmanNode((char) i, count[i]));
            }
        }
        //Add Pseudo EOF character
        treeBuilder.add((new HuffmanNode((char) count.length, 1)));

        //Build the Huffman tree
        while(treeBuilder.size() > 1){
            HuffmanNode left = treeBuilder.remove();
            HuffmanNode right = treeBuilder.remove();
            int frequency = left.getFrequency() + right.getFrequency();
            treeBuilder.add(new HuffmanNode(frequency, left, right));
        }
        this.root = treeBuilder.remove();
    }

    /**
     * Builds the Huffman tree from a .code file in standard (Preorder) form. Frequency is set to -1 since frequency is
     * not transmitted over a .code file.
     *
     * @param input A Scanner linked to a file or stream of data that represents the code to build the tree from.
     *              Stream must not be empty.
     */
    public HuffmanTree(Scanner input){
        this.root = new HuffmanNode();
        while(input.hasNextLine()){
            char character = (char) Integer.parseInt(input.nextLine());
            String address = input.nextLine();
            add(new HuffmanNode(character, -1), address, this.root);
        }
    }

    /**
     * Helper that adds a node to the huffman tree given the binary address. Used when creating the tree from .code file.
     *
     * @param node The node to add to the tree
     * @param address The binary address the node needs to be added to. 0 = left, 1 = right
     * @param current The node the recursive function is currently in.
     * @return {@link HuffmanNode} Needed to use x = change(x) algorithm
     */
    private HuffmanNode add(HuffmanNode node, String address, HuffmanNode current){
        String bit = address.substring(0,1);
        if(address.length() > 1){
            if(bit.equals("0")){
                if(current.getLeft() == null){
                    current.setLeft(new HuffmanNode());
                }
                current.setLeft(add(node, address.substring(1), current.getLeft()));
                return current;
            } else if(bit.equals("1")) {
                if(current.getRight() == null){
                    current.setRight(new HuffmanNode());
                }
                current.setRight(add(node, address.substring(1), current.getRight()));
                return current;
            }
        } else {
            if(bit.equals("0")){
                current.setLeft(node);
                return current;
            } else if(bit.equals("1")) {
                current.setRight(node);
                return current;
            }
        }
        return null;
    }

    /**
     * This method is used to write the Huffman Tree to a .code file in standard order (preorder).
     * For each item stored in the tree takes up two lines of the file. The first one will be the ascii value
     * of the character. The second one will be the binary "address" of that character in the tree. A 0 in the address
     * represents a left. A 1 in the address represents a right. On re-creation of a {@link HuffmanTree} this address
     * will be used for placement of the node within the tree.
     *
     * @param output The stream to output the Huffman tree to.
     */
    public void write(PrintStream output) {
        this.write(output, this.root, "");
    }

    /**
     * A helper method that does the work of writing the tree to the .code file.
     *
     * @param output A stream to the output destination
     * @param current The node the recursive write function is currently "in". Pass in a node and this method will
     *                print everything below it to the stream.
     * @param bitSequence The sequence of bits that represents the address within the tree. See above for
     *                    meaning of address.
     */
    private void write(PrintStream output, HuffmanNode current, String bitSequence) {
        if(current.isLeaf()){
            output.println((int) current.getCharacter());
            output.println(bitSequence);
        } else {
            write(output, current.getLeft(), bitSequence + "0");
            write(output, current.getRight(), bitSequence + "1");
        }
    }

    /**
     * Decodes a .short file based on the stored Huffman tree.
     *
     * @param input An {@link BitInputStream} that will give the bits for the given .short file.
     * @param output The stream to the .new file that will contain the decompressed file.
     * @param charMax The pseudo-EOF character. This character represents the end of the compressed file.
     *                It is used to know when to stop reading bits from the {@link BitInputStream}.
     */
    public void decode(BitInputStream input, PrintStream output, int charMax) {
        HuffmanNode current = this.root;
        boolean reading = true;
        while(reading){
            if(current.isLeaf()){
                if(current.getCharacter() == (char) charMax){
                    reading = false;
                } else {
                    output.write(current.getCharacter());
                    current = this.root;
                }
            } else {
                int bit = input.readBit();
                if(bit == 0){
                    current = current.getLeft();
                } else if(bit == 1){
                    current = current.getRight();
                }
            }
        }
    }
}