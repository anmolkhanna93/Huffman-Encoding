

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class PairingHeap  {
	private pairingHeapNode root;
	
	private Map<Integer, String> codeTable = new HashMap<>();
	private long totalTime;

	public PairingHeap() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void makeEmpty() {
		root = null;
	}

	public pairingHeapNode insert(BinaryNode x) {
		pairingHeapNode newNode = new pairingHeapNode(x);
		if (root == null)
			root = newNode;
		else
			root = meld(root, newNode);
		return newNode;
	}

	private pairingHeapNode meld(pairingHeapNode first, pairingHeapNode second) {
		if (second == null)
			return first;

		if (second.element.compareTo(first.element) < 0) {
			second.prev = first.prev;
			first.prev = second;
			first.nextSibling = second.leftChild;
			if (first.nextSibling != null)
				first.nextSibling.prev = first;
			second.leftChild = first;
			return second;
		} else {
			second.prev = first;
			first.nextSibling = second.nextSibling;
			if (first.nextSibling != null)
				first.nextSibling.prev = first;
			second.nextSibling = first.leftChild;
			if (second.nextSibling != null)
				second.nextSibling.prev = second;
			first.leftChild = second;
			return first;
		}
	}

	private pairingHeapNode combineSiblings(pairingHeapNode firstSibling) {
		if (firstSibling.nextSibling == null)
			return firstSibling;
		int numSiblings = 0;
		Vector<pairingHeapNode> treeArray = new Vector<pairingHeapNode>(5);
		for (; firstSibling != null; numSiblings++) {
			treeArray.add(numSiblings, firstSibling);
			firstSibling.prev.nextSibling = null;
			firstSibling = firstSibling.nextSibling;
		}
		treeArray.add(numSiblings, null);
		int i = 0;
		for (; i + 1 < numSiblings; i += 2)
			treeArray.set(i, meld(treeArray.get(i), treeArray.get(i + 1)));
		int j = i - 2;
		if (j == numSiblings - 3)
			treeArray.set(j, meld(treeArray.get(j), treeArray.get(j + 2)));
		for (; j >= 2; j -= 2)
			treeArray.set(j - 2, meld(treeArray.get(j - 2), treeArray.get(j)));
		return treeArray.get(0);
	}

	public BinaryNode deleteMin() {
		if (isEmpty())
			return null;
		BinaryNode x = root.element;
		if (root.leftChild == null)
			root = null;
		else
			root = combineSiblings(root.leftChild);
		return x;
	}

	public void insertIntoPairingHeap(List<BinaryNode> nodes) {
		for (BinaryNode x : nodes) {
			insert(x);
		}
	}

	public void encode() {
		
		int count=0;
		while (root.getLeftChild()!=null) {
			BinaryNode node1 = deleteMin();
			BinaryNode node2 = deleteMin();
			BinaryNode node3 = new BinaryNode(-1,node1.getFrequency() + node2.getFrequency(), count++);
			node3.addNode(node1);
			node3.addNode(node2);
			insert(node3);
		}

	}

	public void encode(BinaryNode node, String code, final Vector<String> prefix) {
		if (node.isEmpty()) {
			codeTable.put(node.getData(), code);
		} else {
			int count = 0;
			for (String i : prefix) {
				encode(node.getNode(count++), code + i, prefix);
			}
		}
	}

	public Map<Integer, String> getCodeTable() {
		return codeTable;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public BinaryNode peek() {
		return root.element;
	}

}
