package xyz.joepseuren;

import java.util.*;

/**
 * Creates a huffman tree from the given data
 * Created by joep on 3/29/16.
 */

public class Huffman {

    private String data;
    private HuffNode root;

    public Huffman(String _data){
        data = _data;
        root = null;
    }
    /**
     * Encodes the data that is given in the huffman constructor.
     * @return the compressed message in a bytestring.
     */
    public String encodeMessage() {

        Set<HuffNode> characterFrequencyHuffNodeSet = countCharactersToHuffNodeSet();

        PriorityQueue<HuffNode> huffNodePriorityQueue = setToQueue(characterFrequencyHuffNodeSet);

        this.root = createTree(huffNodePriorityQueue);

        Map<Character, String> charCodeMap = new HashMap<>();
        createCharCodes(root, "", charCodeMap);

        // Encode the data using the charcodemap. This will output the binary string.
        // O(n)
        StringBuilder sb = new StringBuilder();
        for (Character c : data.toCharArray()) {
            sb.append(charCodeMap.get(c));
        }
        return sb.toString();
    }

    /**
     * Counts the frequency of the characters of the data.
     * Fills a set with huffnodes. Each huffnode in the set is a huffnode of a character.
     * @return A set of huffnodes
     */
    public Set<HuffNode> countCharactersToHuffNodeSet() {
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

    /**
     * Fills a priorityqueue from a set of huffnodes.
     * @param huffNodeSet set of huffnodes
     * @return A priorityqueue that is sorted low to high
     */
    public PriorityQueue<HuffNode> setToQueue(Set<HuffNode> huffNodeSet) {
        // O(n log n)
        PriorityQueue<HuffNode> queue = new PriorityQueue<>(huffNodeSet.size());

        queue.addAll(huffNodeSet);
        return queue;
    }

    /**
     * Creates the binary code for each character in the tree.
     * These codes are used when building the final bytestring.
     * @param tree The root node
     * @param code Initial call must be with an empty string
     * @param charCodeMap A map for the char/code combination.
     */
    public void createCharCodes(HuffNode tree, String code, Map<Character, String> charCodeMap)
    {
        // O(n)
        if (tree != null)
        {
            createCharCodes(tree.getLeft(), code + '0', charCodeMap);
            createCharCodes(tree.getRight(), code + '1', charCodeMap);
            if (tree.getRight() == null && tree.getLeft() == null)
            {
                charCodeMap.put(tree.getCharacter(), code);
            }
        }
    }

    /**
     * Creates the huffman tree
     * @param charQueue A priorityqueue of huffnodes
     * @return The root node
     */
    public HuffNode createTree(PriorityQueue<HuffNode> charQueue) {
        // O(n log(n) *2)
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

    public HuffNode getRoot() {
        return root;
    }
}
