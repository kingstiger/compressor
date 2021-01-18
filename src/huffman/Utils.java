package huffman;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

public class Utils {
    public static String encodedString = "";
    public static String encodedTree = "";

    public static BitSet encodedBits;


    public static HNode makeList(HNode root, String data) {
        int i, x;
        char cx;
        HNode p;
        boolean t;

        root = null;
        for (i = 0; i < data.length(); i++) {
            p = root;
            while (p != null && (p.getCh() != data.charAt(i)))
                p = p.getNext();
            if (p == null) {
                p = new HNode(root, null, null, data.charAt(i), 0);
                root = p;
            }
            p.setCount(p.getCount() + 1);
        }
        do {
            t = true;
            p = root;
            while (p.getNext() != null) {
                if (p.getCount() > p.getNext().getCount()) {
                    cx = p.getCh();
                    p.setCh(p.getNext().getCh());
                    p.getNext().setCh(cx);
                    x = p.getCount();
                    p.setCount(p.getNext().getCount());
                    p.getNext().setCount(p.getCount());
                    t = false;
                }
                p = p.getNext();
            }
        } while (!t);
        return root;
    }

    public static HNode makeTree(HNode root) {
        HNode p, r, v1, v2;

        while (true) {
            v1 = root;
            v2 = v1.getNext();

            if (v2 == null) break;

            root = v2.getNext();
            p = new HNode();
            p.setLeft(v1);
            p.setRight(v2);
            p.setCount(v1.getCount() + v2.getCount());

            if (root == null || (p.getCount() <= root.getCount())) {
                p.setNext(root);
                root = p;
                continue;
            }

            r = root;

            while (r.getNext() != null && (p.getCount() > r.getNext().getCount()))
                r = r.getNext();

            p.setNext(r.getNext());
            r.setNext(p);
        }
        return root;
    }

    public static void printTree(HNode p, String code) {
        if (p.getLeft() == null) System.out.println(p.getCh() + " " + code);
        else {
            printTree(p.getLeft(), code + "0");
            printTree(p.getRight(), code + "1");
        }
    }

    private static boolean codeSign(char c, HNode p, String b) {
        if (p.getLeft() == null) {
            if (c != p.getCh()) return false;
            else {
                encodedString += b;
                return true;
            }
        } else return codeSign(c, p.getLeft(), b + "0") || codeSign(c, p.getRight(), b + "1");
    }

    public static void codeHuffman(HNode root, String data) {
        int i;
        for (i = 0; i < data.length(); i++) {
            codeSign(data.charAt(i), root, "");
        }
        encodeTree(root);
        encodedBits = toBitsAndPieces();
    }

    private static BitSet toBitsAndPieces() {
        BitSet bitSet = new BitSet(32 + encodedString.length() + encodedTree.length());
        int bitCounter = 0;

        BitSet treeSize = convert(encodedTree.length());
        for(; bitCounter < treeSize.length(); bitCounter++)
        {
            bitSet.set(bitCounter, treeSize.get(bitCounter));
        }

        for (Character c : encodedTree.toCharArray()) {
            if (c.equals('1')) {
                bitSet.set(bitCounter);
            }
        }

        for (Character c : encodedString.toCharArray()) {
            if (c.equals('1')) {
                bitSet.set(bitCounter);
            }
            bitCounter++;
        }
        return bitSet;
    }

    private static void encodeTree(HNode p) {
        if (p.isLeaf()) {
            encodedTree += "1";
            encodedTree += String.valueOf((int) p.getCh());
        } else {
            encodedTree += "0";
            encodeTree(p.getLeft());
            encodeTree(p.getRight());
        }
    }

    public static String unhuffHuffman(byte[] data) {
        int sizeOfTree = 0;
        byte[] bytesOfSize = new byte[4];
        for (int i = 0; i < 4; i ++) {
            bytesOfSize[i] = data[i];
        }
        sizeOfTree = fromByteArray(bytesOfSize);
        HNode root = readTree(Arrays.copyOfRange(data, 4, sizeOfTree/8));
        printTree(root, "");

        return readText(root, Arrays.copyOfRange(data, 4 + sizeOfTree/8, data.length));
    }

    private static String readText(HNode root, byte[] bytes) {
        return null;
    }

    static int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static HNode readTree(byte[] data) {
        BitSet bitSet = BitSet.valueOf(data);
        BitSet[] bitSetArr = new BitSet[1];
        bitSetArr[0] = bitSet;

        return readNodes(bitSetArr);
    }

    private static HNode readNodes(BitSet[] data) {
        if(readBit(data) == 1) {
            return new HNode(null, null, null, readByte(data), 0);
        } else {
            HNode left = readNodes(data);
            HNode right = readNodes(data);
            return new HNode(null, left, right, (char) 0, 0);
        }
    }

    private static char readByte(BitSet[] data) {
        byte b = data[0].get(0, 8).toByteArray()[0];
        data[0] = data[0].get(9, data[0].length());
        return (char) b;
    }

    private static int readBit(BitSet[] data) {
        int bit = (data[0].get(0)) ? 1 : 0;
        data[0] = data[0].get(1, data[0].length());
        return bit;
    }

    public static BitSet convert(int value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0) {
            if (value % 2 != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }
//722360004 Maciejewska Monika koordynator MOPR
    public static int convert(BitSet bits) {
        int value = 0;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1 << i) : 0;
        }
        return value;
    }
}
