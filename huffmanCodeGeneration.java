

import java.io.IOException;
import java.util.List;
import java.util.Vector;



public class huffmanCodeGeneration {

	
	private PairingHeap pairingHeap;
	private fourWayHeap cache4Way;
	private binaryHeap bh;

	public binaryHeap buildTreeUsingBinaryHeap(binaryHeap binary, List<BinaryNode> nodes, int k){
		long startTime = System.currentTimeMillis();//
		long time = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			binary = new binaryHeap(nodes.size(), k);
			binary.insertIncHeap(nodes);//
			//binary.encodeTree();
		}
		binary.setTime(System.currentTimeMillis() - startTime);
		
		System.out.println(" time taken by binary heap for 10 runs :" + binary.getTime()+" milliSeconds");
		return binary;
		
	}
	public fourWayHeap buildTreeUsingFourWayCacheHeap(fourWayHeap cacheKWay, List<BinaryNode> nodes, int k,int startIndex) {
		long startTime = System.currentTimeMillis();//
		long time = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			cacheKWay = new fourWayHeap(nodes.size(), k,startIndex);
			cacheKWay.insertInHeap(nodes);//
			//cacheKWay.encodeTree();
			
		}
		cacheKWay.setTime(System.currentTimeMillis() - startTime);//
		
		
		System.out.println(" time taken by cache optimized 4 way heap for 10 runs :" + cacheKWay.getTime()+" milliSeconds");
		return cacheKWay;
	}
	Node root = null;

	public void buildTreeUsingPairingHeap(List<BinaryNode> nodes) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			pairingHeap = new PairingHeap();
			pairingHeap.insertIntoPairingHeap(nodes);
			
			//pairingHeap.encode();
			
		}
		long time = System.currentTimeMillis()-startTime;
		System.out.println(" time taken by pairing heap for 10 runs :" + time+" milliSeconds");
		
	}
	
	




	public void encode(List<BinaryNode> nodes, fileHandling fileParser) throws IOException {
		//long startTime = System.currentTimeMillis();
		bh = buildTreeUsingBinaryHeap(bh,nodes,2);
		
		cache4Way = buildTreeUsingFourWayCacheHeap(cache4Way, nodes, 4,3);
		
		buildTreeUsingPairingHeap(nodes);
		Vector<String> code = new Vector<String>();
		long cache4WayTime = cache4Way.getTime();
		long binaryHeapTime = bh.getTime();
		
		fourWayHeap heap = cache4Way;
		binaryHeap heap1 = bh;
		PairingHeap ph = pairingHeap;
		
		
		//heap.encodeTree();
		ph.encode();
		code.add("0");
		code.add("1");
		
		
		
	
		//heap.encode(heap.peek(), "", code);
		//fileParser.encodeFile(heap.getCodeTable());
		ph.encode(ph.peek(), "", code);
		fileParser.encodeFile(ph.getCodeTable());
		
	}
}

