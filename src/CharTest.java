import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CharTest {
    public static void main(String[] args) throws FileNotFoundException {
        HuffmanTree tree = new HuffmanTree(new Scanner(new File("hamlet.code")));
    }
}
