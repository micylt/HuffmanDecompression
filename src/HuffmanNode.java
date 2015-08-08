/** 
 * generates HuffmanNode object to create a tree. implements the Comparable class.
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    public int data;
    public int freq;
    public HuffmanNode left;
    public HuffmanNode right;
 
    /**
     * creates tree with initial data.
     */
    public HuffmanNode(int data, int freq) {
        this(data, freq, null, null);
    }
 
    /**
     * creates tree with given left and right nodes
     */
    public HuffmanNode(int data, int freq, HuffmanNode left, HuffmanNode right) {
        this.data = data;
        this.freq = freq;	
        this.left = left; // left node
        this.right = right; // right node
    }
     
    /**
     * compares the frequency between this frequency and the other HuffmanNode
     * frequency. returning an int representing which if one is greater, equal or less
     * than the other. overrides the compare to method from Comparable.
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return this.freq - other.freq; // checks which freq is lower 
    }
     
    /**
     * returns a String representation of the HuffmanNode.
     */
    public String toString() {
        return "[ data: " + (char) this.data + " , freq: " + this.freq + "]";
    }   
}