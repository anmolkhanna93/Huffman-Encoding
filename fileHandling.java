import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class fileHandling {
	private String fName;
	
	
	public fileHandling(String name) {
		super();
		this.fName = name;
		// TODO Auto-generated constructor stub
	}
	
	public List<BinaryNode> scanFile() throws IOException{
		int order = 0;
		String readLine;
		
		File file = new File(fName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<BinaryNode> result = new ArrayList<>();
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();
		Map<Integer, Integer> ordering = new HashMap<Integer, Integer>();
		while ((readLine = reader.readLine()) != null) {
			if (readLine.trim().equals("")) {
				continue;
			}
			int element = Integer.parseInt(readLine);
			Integer eCount = count.get(element);
			if (eCount == null) {
				count.put(element, 1);
				ordering.put(element, order++);
			} else {
				count.put(element, eCount + 1);
			}
		}
		reader.close();
		for (Integer element : count.keySet()) {
			result.add(new BinaryNode(element,count.get(element), 0));
		}
		return result;
	
	}
	


	public Map<String,String> scanCodeTable() throws IOException{
		String readLine;
		File file = new File(fName);
		BufferedReader current = new BufferedReader(new FileReader(file));
		Map<String,String> map = new HashMap<String,String>();
		while((readLine = current.readLine())!=null){
			if(readLine.trim().equals("")){
				continue;
			}
			String[] codes = readLine.split(" ");//check here once again
			map.put(codes[1].trim(), codes[0].trim());
		}
		current.close();
		return map;
	}
	
	private Hashtable<String, Byte> encodingMap() {
		// TODO Auto-generated method stub
		Hashtable<String, Byte> map = new Hashtable<String,Byte>();
		for(int i=0;i<=255;i++){
			StringBuffer  current = new StringBuffer();//
			int value = 128;
			while(value>=1){
				if((i&value)>0){
					current.append("1");
				}else{
					current.append("0");
				}
				value/=2;//
			}
			map.put(current.toString(), (byte)i);
			
		}
		return map;
	}
	
	private Object insertTree(Node node, String key, String value, int index, int length) {
		if (index == length) {
			if (key.charAt(index) == '0') {
				node.setLeft(new Node(Integer.parseInt(value)));
			} else {
				node.setRight(new Node(Integer.parseInt(value)));
			}
			return 0;
		} else {
			if (key.charAt(index) == '0') {
				if (node.getLeft() == null) {
					node.setLeft(new Node(0));
				}
				return insertTree(node.getLeft(), key, value, index + 1, length);
			} else {
				if (node.getRight() == null) {
					node.setRight(new Node(0));
				}
				return insertTree(node.getRight(), key, value, index + 1, length);
			}

		}
	}	
	public void encodeFile(Map<Integer, String> codeTable) throws IOException {
		
		File file = new File(fName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		
	  String filePath = "";
		if (file.getParentFile() != null) {
			filePath=file.getParentFile().getAbsolutePath()+"/";
		}
		 
		File codeFile = new File(filePath + "code_table.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(codeFile));
		for (Integer temp : codeTable.keySet()) {
			writer.write(temp + " " + codeTable.get(temp));
			writer.newLine();
		}
		writer.close();
		String line;
		BufferedOutputStream encodeFile = new BufferedOutputStream(new FileOutputStream(new File(filePath + "encoded.bin")));
		Hashtable<String, Byte> encoding = encodingMap();
		StringBuffer current = new StringBuffer();
		int count, remainder;
		while ((line = reader.readLine()) != null) {
			if (line.trim().equals("")) {
				continue;
			}
			current.append(codeTable.get(Integer.parseInt(line)));
			if (current.length() > 32000) {
				remainder = current.length() % 8;
				for (count = 0; count < current.length() - remainder; count += 8) {
					String strBits = current.substring(count, count + 8);
					byte realBits = encoding.get(strBits);
					encodeFile.write(realBits);
				}
				if (remainder == 0) {
					current = new StringBuffer();
				} else {
					current = new StringBuffer(current.substring(count));
				}
			}
		}
		remainder = current.length() % 8;
		if (remainder > 0) {
			for (count = 0; count < (8 - remainder); count++) {
				current.append("0");
			}
		}
		for (count = 0; count < current.length(); count += 8) {
			String strBits = current.substring(count, count + 8);
			byte realBits = encoding.get(strBits);
			encodeFile.write(realBits);
		}

		encodeFile.close();
		reader.close();
	}

	public Node scanCodeTableFile() throws IOException {
		// TODO Auto-generated method stub
		String line;
		File file = new File(fName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Map<String, String> codeTable = new HashMap<String, String>();
		while ((line = reader.readLine()) != null) {
			if (line.trim().equals("")) {
				continue;
			}
			String[] code = line.split(" ");
			codeTable.put(code[1].trim(), code[0].trim());
		}
		reader.close();
		return decodeTree(codeTable);
	}

	private Node decodeTree(Map<String, String> codeTable) {
		Node root=new Node(0);
		for(String key:codeTable.keySet()){
			insertTree(root,key,codeTable.get(key),0,key.length()-1);
		}
		return root;
	}
	
	
	

}
