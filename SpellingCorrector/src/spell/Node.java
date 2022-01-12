package spell;

public class Node implements INode {

    private final int SIZE = 26;
    private int count;
    private Node[] children;

    public Node() {
        count = 0;
        children = new Node[SIZE];
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }
}
