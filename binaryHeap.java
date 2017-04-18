import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class binaryHeap {
	
	private Vector<BinaryNode> heap;
	private int count;
	
	private long time;
	private Map<Integer,String> map = new HashMap<Integer,String>();
	
	public binaryHeap(int space, int count){
		this.count = count;
		heap = new Vector<BinaryNode>(space+1);
	}
	
	private int getChildren(int position,int n){
		int temp = count*position + n;
		return temp;
	}
	
	private int getParent(int position){
		int temp = (position-1)/count;
		return temp;
	}
	
	public void insert(BinaryNode node){
		heap.add(node);
		downHeapify(heap.size()-1);
	}
	
	public BinaryNode delete(int position){
		if(isEmpty()){
			System.out.println("error");
		}
		BinaryNode getItem,temp = null;
		getItem = heap.get(position);
		temp = heap.get(heap.size()-1);
		heap.set(position, temp);
		heap.remove(heap.size()-1);
		if(heap.size()>1){
			heapify(position);
		}
		return getItem;
	}
	
	private void heapify(int position) {
		// TODO Auto-generated method stub
		BinaryNode temp=null;
		temp = heap.get(position);
		int children;
		while(getChildren(position, 1)<heap.size()){
			children = getMinChildren(position);
			if(heap.get(children).compareTo(temp)<0){
				heap.set(position, heap.get(children));
				position = children;
			}else{
				break;
			}
			
		}
		heap.set(position, temp);
		
	}

	private int getMinChildren(int position) {
		// TODO Auto-generated method stub
		int i = getChildren(position,1);
		int k=2;
		int pos = getChildren(position, k);
		while((k<=count) && (pos<heap.size())){
			if(heap.get(pos).compareTo(heap.get(i))<0){
				i =pos;
			}
			pos = getChildren(position, ++k);
		}
		return i;
	}

	private void downHeapify(int i) {
		// TODO Auto-generated method stub
		BinaryNode tmp = heap.get(i);
		while (i > 0 && tmp.compareTo(heap.get(getParent(i))) < 0) {
			heap.set(i, heap.get(getParent(i)));
			i = getParent(i);
		}
		heap.set(i, tmp);
		
		
	}
	
	public BinaryNode findMin(){
		if(isEmpty()){
			System.out.println("error");//check for exception
		}
		//check for exception
		return heap.get(0);
	}

	public void clear(){
		heap.clear();
	}
	
	public int heapSize(){
		return heap.size();
	}
	
	public boolean isEmpty(){
		return heap.size()==0;
	}
	
	public BinaryNode peek(){
		return heap.get(0);
	}
	public void encode(BinaryNode node, String code, final Vector<String> prefix) {
		if (node.isEmpty()) {
			map.put(node.getData(), code);
		} else {
			int count = 0;
			for (String i : prefix) {
				encode(node.getNode(count++), code + i, prefix);
			}
		}
	}

	public void encodeTree() {
		int frequencyCount = 0, length = 0;
		int k=2;
		
		List<BinaryNode> nodes = new ArrayList<BinaryNode>();
		BinaryNode result =null;
		
		while (heapSize() > 1) {
			frequencyCount = 0;
			for (int i = 0; i < k; i++) {
				BinaryNode node = delete(0);
				nodes.add(node);
				frequencyCount = frequencyCount + node.getFrequency();
				length = length + String.valueOf(node.getData()).length();
			}
			result = new BinaryNode(-1, frequencyCount, length);
			result.addNodes(nodes);
			nodes.clear();
			insert(result);
		}
	}

	public Map<Integer, String> getCodeTable() {
		return map;
	}

	
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void insertIncHeap(List<BinaryNode>nodes){
		for(BinaryNode node:nodes){
			insert(node);
		}
		
	}
	
}
