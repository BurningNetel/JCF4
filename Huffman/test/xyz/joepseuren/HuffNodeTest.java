package xyz.joepseuren;

import org.junit.Test;
import org.w3c.dom.CharacterData;

import static org.junit.Assert.*;

/**
 * Created by joep on 3/29/16.
 */
public class HuffNodeTest {

    private String data = "bananen";

    @Test
    public void testRootIsNullBeforeEncoding(){
        Huffman huffman = new Huffman(data);
        HuffNode root = huffman.getRoot();
        assertNull(root);
    }

    @Test
    public void testRootisRootAfterEncoding(){
        Huffman huffman = new Huffman(data);
        huffman.encodeMessage();
        HuffNode root = huffman.getRoot();
        assertNotNull(root);
    }

    /**
     * http://www.algorasim.com/ bananen klopt
     */
    @Test
    public void testEncodingResultsInCorrectByteString(){
        Huffman huffman = new Huffman(data);
        String result = huffman.encodeMessage();
        String expResult = "1101001001110";
        assertEquals(expResult, result);
    }

    @Test
    public void testHuffmanTree(){
        Huffman huffman = new Huffman(data);
        huffman.encodeMessage();
        HuffNode root = huffman.getRoot();
        assertEquals('n', root.getLeft().getCharacter());
        assertEquals('a', root.getRight().getLeft().getCharacter());
        assertEquals('b', root.getRight().getRight().getLeft().getCharacter());
        assertEquals('e', root.getRight().getRight().getRight().getCharacter());
    }

    @Test
    public void testDecodingWithLongString(){
        String data = "The Java platform includes a collections framework. A collection is an object that represents a group of objects (such as the classic Vector (Links to an external site.) class). A collections framework is a unified architecture for representing and manipulating collections, enabling collections to be manipulated independently of implementation details." +
                "The primary advantages of a collections framework are that it:" +
                "Reduces programming effort by providing data structures and algorithms so you don't have to write them yourself." +
                "Increases performance by providing high-performance implementations of data structures and algorithms. Because the various implementations of each interface are interchangeable, programs can be tuned by switching implementations." +
                "Provides interoperability between unrelated APIs by establishing a common language to pass collections back and forth." +
                "Reduces the effort required to learn APIs by requiring you to learn multiple ad hoc collection APIs." +
                "Reduces the effort required to design and implement APIs by not requiring you to produce ad hoc collections APIs." +
                "Fosters software reuse by providing a standard interface for collections and algorithms with which to manipulate them.";
        Huffman huffman = new Huffman(data);
        String message = huffman.encodeMessage();
        HuffNode root = huffman.getRoot();

        StringBuilder sb = new StringBuilder();
        root.decode(sb, message);

        String result = sb.toString();
        assertEquals(data, result);

    }
}