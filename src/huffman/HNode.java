package huffman;

public class HNode {
    private HNode next;
    private HNode left;
    private HNode right;
    private char ch;
    private int count;

    public HNode(HNode next, HNode left, HNode right, char ch, int count) {
        this.next = next;
        this.left = left;
        this.right = right;
        this.ch = ch;
        this.count = count;
    }

    public HNode() {
        this.next = null;
        this.left = null;
        this.right = null;
        this.ch = 0;
        this.count = 0;
    }

    public HNode getNext() {
        return (next != null) ? next : null;
    }

    public HNode getLeft() {
        return (left != null) ? left : null;
    }

    public HNode getRight() {
        return (right != null) ? right : null;
    }

    public char getCh() {
        return ch;
    }

    public int getCount() {
        return count;
    }

    public void setNext(HNode next) {
        this.next = next;
    }

    public void setLeft(HNode left) {
        this.left = left;
    }

    public void setRight(HNode right) {
        this.right = right;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HNode connect(HNode v1, HNode v2)
    {
        this.left = v1;
        this.right = v2;
        this.count = v1.getCount() + v2.getCount();
        return this;
    }

    public boolean isLeaf()
    {
        return left == null && right == null;
    }
}
