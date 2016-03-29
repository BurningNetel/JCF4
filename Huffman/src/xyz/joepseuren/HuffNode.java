package xyz.joepseuren;

/**
 * Created by joep on 2/14/16.
 */
public class HuffNode implements Comparable<HuffNode>{

    private int count;
    private char character;
    private HuffNode left, right;

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

    public void decode(StringBuilder sb, String encodedMessage) {
        while(encodedMessage.length() != 0)
        {
            encodedMessage = leftOrRight(sb, encodedMessage);
        }
    }

    /**
     * Decodes the node and appends the chars to the stringbuilder
     * @param sb Stringbuilder that will contain the output after the call of this function
     * @param encodedMessage The bytestring for this tree
     * @return message
     */
    private String decodeNode(StringBuilder sb, String encodedMessage){
        // If it is a leaf. append char to stringbuilder
        if (getLeft() == null && getRight() == null) {
            sb.append(character);
        } else {
            encodedMessage = leftOrRight(sb, encodedMessage);
        }
        return encodedMessage;
    }

    private String leftOrRight(StringBuilder sb, String encodedMessage) {
        if (encodedMessage.charAt(0) == '1') {
            encodedMessage = encodedMessage.substring(1);
            encodedMessage = this.getRight().decodeNode(sb, encodedMessage);
        } else {
            encodedMessage = encodedMessage.substring(1);
            encodedMessage = this.getLeft().decodeNode(sb, encodedMessage);
        }
        return encodedMessage;
    }

    public int compareIfEquals(HuffNode other){
        if(getCharacter() == '\0'){
            return 1;
        } else {
           return Character.compare(getCharacter(), other.getCharacter());
        }
    }

    @Override
    public int compareTo(HuffNode other) {
        int comp = Integer.compare(count, other.getCount());

        if (comp == 0)
            return compareIfEquals(other);

        return comp;
    }
}
