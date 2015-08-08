/**
 * this class represents a huffman code to be used in compression of data. 
 * compress and decompress a given file. each compression and decompression may
 * be saved to a file.
 */
import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanCode {
	private HuffmanNode overallRoot;

	/**
	 *  initialize a new HuffmanCode object, accepting an array of integers
	 *  ordered from least frequent to most frequent characters.
	 */
	public HuffmanCode(int[] frequencies) {
		PriorityQueue<HuffmanNode> priority = new PriorityQueue<HuffmanNode>();
		for (int i = 0; i < frequencies.length; i++) {
			if(frequencies[i] > 0) {
				HuffmanNode node = new HuffmanNode(i, frequencies[i]);
				priority.add(node);
			}
		}
		while (priority.size() > 1) { // repeating until one node left in queue
			HuffmanNode leftNode = priority.remove(); // remove two smallest nodes from queue
			HuffmanNode rightNode = priority.remove();
			int leftPlusRight = leftNode.freq + rightNode.freq; // combine their frequency
			HuffmanNode newNode = new HuffmanNode(0, leftPlusRight, leftNode, rightNode);
			priority.add(newNode); // add the new node back to the queue
		}
		this.overallRoot = priority.remove();
	}

	/**
	 * initialize a new HuffmanCode object from a given code file.
	 */
	public HuffmanCode(Scanner input) {
		this.overallRoot = new HuffmanNode(0,0); // initialize tree
		while (input.hasNextLine()) {
			int binary = Integer.parseInt(input.nextLine());
			String code = input.nextLine();
			this.overallRoot = buildTree(this.overallRoot, binary, code);
		}
	}

	/**
	 * building and returning a tree out of input file code. 
	 * Taking in a HuffmanNode, int and String.
	 */
	private HuffmanNode buildTree(HuffmanNode root, int binary, String code) {
		if (code.length() == 1) {
			if (code.equals("0")) {
				root.left = new HuffmanNode(binary, 0);
			} else {
				root.right = new HuffmanNode(binary, 0);
			}
		} else {    
			if (code.charAt(0) == '0') {
				if (root.left == null) {
					root.left = buildTree(new HuffmanNode(0, 0), binary, code.substring(1, code.length()));
				} else { // traverse through left side of tree
					buildTree(root.left, binary, code.substring(1, code.length()));
				}
			} else {
				if (root.right == null) {
					root.right = buildTree(new HuffmanNode(0, 0), binary, code.substring(1, code.length()));
				} else { // traverse through right side of tree
					buildTree(root.right, binary, code.substring(1, code.length()));
				}
			}
		}
		return root;
	}

	/**
	 * stores the current huffman codes to the given output stream in standard format
	 */
	public void save(PrintStream output) {
		String code = "";
		saveHelper(output, this.overallRoot, code);
	}

	/**
	 * stores the current huffman codes to the given output stream in standard format
	 * from the given root and code.
	 */
	private void saveHelper(PrintStream output, HuffmanNode root, String code) {
		if (root != null) {
			if (root.left == null && root.right == null) {
				output.println(root.data);
				output.println(code);
			} else {
				saveHelper(output, root.left, code + "0");
				saveHelper(output, root.right, code + "1");
			}
		}
	}

	/**
	 * reads individual bits from the input stream and writes the 
	 * corresponding characters to the output.
	 */
	public void translate(BitInputStream input, PrintStream output) {
		while (input.hasNextBit()) {
			output.write(translateHelper(this.overallRoot, input));
		}
	}

	/**
	 * checking the huffman tree for the data from the input file.
	 */
	private int translateHelper(HuffmanNode root, BitInputStream input) {
		if (root.data != 0) {
			return root.data;
		} else { 
			if (input.nextBit() == 0) {
				return translateHelper(root.left, input);
			} else {
				return translateHelper(root.right, input);
			}
		}
	}
}