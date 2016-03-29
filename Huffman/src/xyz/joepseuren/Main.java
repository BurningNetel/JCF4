package xyz.joepseuren;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class Main {

    private static String data = "";

    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter data: ");
        data = scanner.nextLine();

        // encode
        Huffman huffman = new Huffman(data);
        String encodedByteString = huffman.encodeMessage();
        HuffNode root = huffman.getRoot();

        // decode
        StringBuilder sb = new StringBuilder();
        root.decode(sb, encodedByteString);
        System.out.println(sb.toString());

        // statistics
        print_statistics(encodedByteString);
    }

    private static void print_statistics(String encoded) {
        int initialSize = data.length() * 8;
        int compressedSize = encoded.length();
        float ratio = (float) initialSize / compressedSize * 100;
        System.out.println("Initial size: " + initialSize + " Bits");
        System.out.println("Encoded message: " + encoded);
        System.out.println("Compressed size: " + compressedSize + " Bits");
        System.out.println("Compression rate: " + ratio + "%");
    }
}