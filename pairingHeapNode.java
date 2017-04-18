

public class pairingHeapNode {
	BinaryNode element;
	pairingHeapNode leftChild;
	pairingHeapNode nextSibling;
	pairingHeapNode prev;

	public pairingHeapNode(BinaryNode x) {
		element = x;
		leftChild = null;
		nextSibling = null;
		prev = null;
	}

	public pairingHeapNode getLeftChild() {
		return leftChild;
	}

	public pairingHeapNode getNextSibling() {
		return nextSibling;
	}

	public pairingHeapNode getPrev() {
		return prev;
	}
	
}
