import java.util.List;
import java.util.Vector;

public class BinaryNode implements Comparable<BinaryNode> {
	private Vector<BinaryNode> nodes = new Vector<BinaryNode>();
	
	private int data;
	private int frequency;
	private int temp;
	
	

	public BinaryNode(int data,int frequnecy,int temp) {
		super();
		this.data=data;
		this.frequency=frequnecy;
		this.temp = temp;

		// TODO Auto-generated constructor stub
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	
	public void addNode(BinaryNode node){
		nodes.add(node);
	}
	public void addNodes(List<BinaryNode> node){
		nodes.addAll(node);
	}
	@Override
	public int compareTo(BinaryNode otherTree) {
		// TODO Auto-generated method stub
		
		if(this.frequency==otherTree.getFrequency()){
			
			if(otherTree.getData() == this.data){
				return this.temp - otherTree.getTemp();
			}
			
			if(otherTree.data==-1){
				return -1;
			}
			if(this.data==-1){
				return 1;
			}
			return otherTree.getData()-this.data;
		}
		
		return this.frequency-otherTree.getFrequency();
	}
	
	public BinaryNode getNode(int i){
		return nodes.get(i);
	}
	public boolean isEmpty(){
		return nodes.size()==0;
	}
	
	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	public int getFrequency() {
		return frequency;
	}
	
	public String toString(){
		return "Node [frequency="+ frequency +",data ="+data+"]";
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	

	


}
