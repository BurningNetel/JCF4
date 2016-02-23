package xyz.joepseuren;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class Main {

    private static String data = "banenen";
    private static Map<Character, String> charCodeMap = new HashMap<>();

    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter data: ");
        data = scanner.nextLine();

        // 1 count the characters
        Set<HuffNode> charList = countCharacters(data);
        // 2 sort the list
        PriorityQueue<HuffNode> charQueue = sortCharNodeList(charList);
        // 3 create the tree
        HuffNode tree = createTree(charQueue);
        // 4 generate the codes
        walkTree(tree, "");
        // 5 encode
        String encoded = encodeMessage(charCodeMap);
        // 6 decode
        decodeMessage(encoded, tree);
        // statistics
        print_statistics(encoded);
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

    private static void decodeMessage(String S, HuffNode root)
    {
        // O(n)
        StringBuilder sb = new StringBuilder();
        HuffNode c = root;
        for (int i = 0; i < S.length(); i++) {
            c = S.charAt(i) == '1' ? c.getRight() : c.getLeft();
            if (c.getLeft() == null && c.getRight() == null) {
                sb.append(c.getCharacter());
                c = root;
            }
        }
        System.out.println("Result: " + sb);
    }

    private static String encodeMessage(Map<Character, String> charCodeMap) {
        // O(n)
        StringBuilder sb = new StringBuilder();
        for (Character c : data.toCharArray()) {
            sb.append(charCodeMap.get(c));
        }
        return sb.toString();
    }

    private static void walkTree(HuffNode tree, String code)
    {
        // O(n)
        if (tree != null)
        {
            walkTree(tree.getLeft(), code + '0');
            walkTree(tree.getRight(), code + '1');
            if (tree.getRight() == null && tree.getLeft() == null)
            {
                charCodeMap.put(tree.getCharacter(), code);
            }
        }
    }

    private static HuffNode createTree(PriorityQueue<HuffNode> charQueue) {
        // O(n)
        while(charQueue.size() > 1)
        {
            HuffNode left = charQueue.poll();
            HuffNode right = charQueue.poll();
            int nodeSum = left.getCount() + right.getCount();
            HuffNode node = new HuffNode('\0', nodeSum, left, right);
            charQueue.add(node);
        }
        return charQueue.poll();
    }

    private static PriorityQueue<HuffNode> sortCharNodeList(Set<HuffNode> charList) {
        // O(n log n)
        PriorityQueue<HuffNode> queue = new PriorityQueue<>(charList.size(), Comparator.comparing(HuffNode::getCount));
        queue.addAll(charList);
        return queue;
    }

    private static Set<HuffNode> countCharacters(String data) {
        System.out.println("Input: " + data);
        Map<Character, Integer> map = new HashMap<>();

        // O(n) counts characters, put them in a hash map
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
        }

        // Create objects because we have to use Collections sort method.
        Set<HuffNode> nodeList = new HashSet<>();
        map.forEach((character, count) -> nodeList.add(new HuffNode(character, count, null, null)));

        return nodeList;
    }
}