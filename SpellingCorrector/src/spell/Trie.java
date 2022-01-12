package spell;

import java.util.Locale;

public class Trie implements ITrie {

    private int wordCount;
    private int nodeCount;
    private Node root;

    public Trie() {
        wordCount = 0;
        nodeCount = 1;
        root = new Node();
    }

    @Override
    public void add(String word) {
        String myWord = word.toLowerCase();
        INode currentNode = root;

        // for each char in word
        for (int i = 0; i < myWord.length(); i++) {
            char currentChar = myWord.charAt(i);
            int indexToCheck = currentChar - 'a';
            // if it is not in the list of children nodes
            if (currentNode.getChildren()[indexToCheck] == null) {
                // create a new node
                currentNode.getChildren()[indexToCheck] = new Node();
                nodeCount++;
            }
            currentNode = currentNode.getChildren()[indexToCheck];
        }
        // only increment wordCount if this is the first time seeing this word
        if (currentNode.getValue() == 0) {
            wordCount++;
        }
        // increment count for the specific node path
        currentNode.incrementValue();
    }

    @Override
    public INode find(String word) {
        String myWord = word.toLowerCase();
        INode currentNode = root;

        // for each char in word
        for (int i = 0; i < myWord.length(); i++) {
            char currentChar = myWord.charAt(i);
            int indexToCheck = currentChar - 'a';
            // if it is not in the list of children nodes
            if (currentNode.getChildren()[indexToCheck] == null) {
                // the word isn't in the trie
                return null;
            }
            else {
                // if we are on the last char of the word AND this is an actual word
                if (i == (myWord.length() - 1) && currentNode.getChildren()[indexToCheck].getValue() > 0) {
                    // we found the word
                    currentNode = currentNode.getChildren()[indexToCheck];
                    return currentNode;
                }
                // otherwise, keep checking
                currentNode = currentNode.getChildren()[indexToCheck];
            }
        }

        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder currentWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(root, currentWord, output);

        return output.toString();
    }

    private void toStringHelper(INode n, StringBuilder currentWord, StringBuilder output) {
        // pre-order traversal of the Trie

        // append the node's word to the output if count > 0
        if (n.getValue() > 0) {
            output.append(currentWord.toString());
            output.append("\n");
        }

        // use toStringHelper recursion to check each child
        for (int i = 0; i < n.getChildren().length; i++) {
            INode child = n.getChildren()[i];
            if (child != null) {
                // every time you visit a new non-null child, append the child's letter to currentWord
                char childLetter = (char)('a' + i);
                currentWord.append(childLetter);
                toStringHelper(child, currentWord, output);

                //remove the child's letter from the currentWord immediately after
                currentWord.deleteCharAt(currentWord.length()- 1);
            }
        }

    }

    @Override
    public int hashCode() {
        /* unique hashcode: multiplies together nodeCount, wordCount, and the indices of each of the root node's
        non-null children */
        int result = this.nodeCount * this.wordCount;
        for (int i = 0; i < root.getChildren().length; i++) {
            if (root.getChildren()[i] != null) {
                result *= i;
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        // is o == null?
        if (o == null) {
            return false;
        }
        // is o == this?
        if (o == this) {
            return true;
        }
        // does this and o have the same class?
        if (o.getClass() != this.getClass()) {
            return false;
        }

        // cast o to be of type Trie
        Trie toCompare = (Trie)o;

        // do this and toCompare have the same wordCount and nodeCount?
        if (this.getWordCount() != toCompare.getWordCount() || this.getNodeCount() != toCompare.getNodeCount()) {
            return false;
        }

        // if all above checks pass, the only option is to traverse both Tries
        return equalsHelper(this.root, toCompare.root);
    }

    private boolean equalsHelper(INode first, INode second) {
        // compare first and second to see if they're the same:
        // do first and second have the same count?
        if (first.getValue() != second.getValue()) {
            return false;
        }
        // do first and second have non-null children at the same indices in their children arrays?
        for (int i = 0; i < first.getChildren().length; i++) {
            if (first.getChildren()[i] == null && second.getChildren()[i] != null) {
                return false;
            }
            if (first.getChildren()[i] != null && second.getChildren()[i] == null) {
                return false;
            }
        }

        // if the two nodes are the same, recurse on the children and compare the child subtrees
        for (int i = 0; i < first.getChildren().length; i++) {
            // you can do the below line of code because you already know first and second have the same non-null children
            if (first.getChildren()[i] != null) {
                /* the reason you have to store the result in a boolean is because if it returns true BEFORE the loop
                finishes running, it won't check the rest of the children of first, one of which might return false */
                boolean result = equalsHelper(first.getChildren()[i], second.getChildren()[i]);
                if (!result) {
                    return false;
                }
            }
        }

        return true;
    }
}
