package xyz.joepseuren;

import java.util.*;

public class Main {

    private static String data = "bananen";
    private static Map<Character, String> charCodeMap = new HashMap<>();

    public static void main(String[] args) {
        // 1 count the characters
        List<HuffNode> charList = countCharacters(data);
        // 2 sort the list
        PriorityQueue<HuffNode> charQueue = sortCharNodeList(charList);
        // 3 create the tree
        HuffNode tree = createTree(charQueue);
        // 4 generate the codes
        walkTree(tree, "");
        // 5 encode
        String encoded = encodeMessage(charCodeMap);
        // 6 decodeMessage
        decodeMessage(encoded, tree);
    }

    private static void decodeMessage(String S, HuffNode root)
    {
        StringBuilder sb = new StringBuilder();
        HuffNode c = root;
        for (int i = 0; i < S.length(); i++) {
            c = S.charAt(i) == '1' ? c.getRight() : c.getLeft();
            if (c.getLeft() == null && c.getRight() == null) {
                sb.append(c.getCharacter());
                c = root;
            }
        }
        System.out.print(sb);
    }

    private static String encodeMessage(Map<Character, String> charCodeMap) {
        StringBuilder sb = new StringBuilder();
        for (Character c : data.toCharArray()) {
            sb.append(charCodeMap.get(c));
        }
        return sb.toString();
    }

    private static void walkTree(HuffNode tree, String code)
    {
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

    private static PriorityQueue<HuffNode> sortCharNodeList(List<HuffNode> charList) {
        PriorityQueue<HuffNode> queue = new PriorityQueue<>(charList.size(), Comparator.comparing(HuffNode::getCount));
        queue.addAll(charList);
        return queue;
    }

    private static List<HuffNode> countCharacters(String data) {
        Map<Character, Integer> map = new HashMap<>();

        // O(n) counts characters, put them in a hash map
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
        }

        // Create objects because we have to use Collections sort method.
        List<HuffNode> nodeList = new ArrayList<>();
        map.forEach((character, count) -> nodeList.add(new HuffNode(character, count, null, null)));

        return nodeList;
    }
}