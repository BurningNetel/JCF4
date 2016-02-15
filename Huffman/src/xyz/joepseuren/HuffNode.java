package xyz.joepseuren;

/**
 * Created by joep on 2/14/16.
 *
 */
public class HuffNode {

    private int count;
    private char character;
    private HuffNode left, right;
    private String code;

    public HuffNode(char character, int count, HuffNode left, HuffNode right){
        this.character = character;
        this.count = count;
        this.left = left;
        this.right = right;
    }

    public int getCount() {
        return count;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return String.valueOf(character) + ": " +String.valueOf(count);
    }

    public HuffNode getLeft() {
        return left;
    }

    public HuffNode getRight() {
        return right;
    }
}
