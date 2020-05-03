import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class HuffmanTree {

    HuffmanNode root;

    public HuffmanTree(int[] count){
        Queue<HuffmanNode> treeBuilder = new PriorityQueue<>();
        for (int i = 0; i < count.length; i++) {
            if(count[i] > 0){
                treeBuilder.add(new HuffmanNode((char) i, count[i]));
            }
        }
        //Add Pseudo EOF character
        treeBuilder.add((new HuffmanNode((char) count.length, 1)));

        while(treeBuilder.size() > 1){
            HuffmanNode left = treeBuilder.remove();
            HuffmanNode right = treeBuilder.remove();
            int frequency = left.getFrequency() + right.getFrequency();
            treeBuilder.add(new HuffmanNode(frequency, left, right));
        }
        this.root = treeBuilder.remove();
    }

    /**
     *
     * @param input A Scanner linked to a file or stream of data that represents the code to build the tree from.
     *              Stream must not be empty.
     */
    public HuffmanTree(Scanner input){
        this.root = new HuffmanNode();
        while(input.hasNextLine()){
            char character = (char) Integer.parseInt(input.nextLine());;
            String address = input.nextLine();
            add(new HuffmanNode(character, -1), address, this.root);
        }
    }

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

    public void printPreorder(){
        printPreorder(this.root);
    }

    private void printPreorder(HuffmanNode current){
        if(current != null){
            System.out.println(current);
            printPreorder(current.getLeft());
            printPreorder(current.getRight());
        }
    }

    public void printPreorderFile(String file) throws FileNotFoundException {
        printPreorderFile(this.root, new PrintStream(new File(file)));
    }

    private void printPreorderFile(HuffmanNode current, PrintStream file){
        if(current != null){
            file.println(current);
            printPreorderFile(current.getLeft(), file);
            printPreorderFile(current.getRight(), file);
        }
    }

    public void printInorder(){
        printInorder(this.root);
    }

    private void printInorder(HuffmanNode current){
        if(current != null){
            printPreorder(current.getLeft());
            System.out.println(current);
            printPreorder(current.getRight());
        }
    }

    public void write(PrintStream output) {
        this.write(output, this.root, "");
    }

    private void write(PrintStream output, HuffmanNode current, String bitSequence) {
        if(current.isLeaf()){
            output.println((int) current.getCharacter());
            output.println(bitSequence);
        } else {
            write(output, current.getLeft(), bitSequence + "0");
            write(output, current.getRight(), bitSequence + "1");
        }
    }

    public void decode(BitInputStream input, PrintStream output, int charMax) {
        HuffmanNode current = this.root;
        boolean reading = true;
        while(reading){
            if(current.isLeaf()){
                if(current.getCharacter() == (char) charMax){
                    reading = false;
                } else {
                    output.print(current.getCharacter());
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
