public class HuffmanNode implements Comparable<HuffmanNode> {
    private char character;
    private int frequency;

    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this((char) 0, frequency);
        this.left = left;
        this.right = right;
    }

    public HuffmanNode(){
        this.frequency = -1;
    }

    public boolean isLeaf(){
        return this.right == null && this.left == null;
    }

    public char getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.getFrequency() - o.getFrequency();
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "character=" + character +
                ", frequency=" + frequency +
                '}';
    }
}
