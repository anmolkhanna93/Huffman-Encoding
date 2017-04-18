import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;

import java.util.Map;

public class decoder {
	
	
	private String stingBackup = "";
	

	private void decode(String[] args) throws IOException, FileNotFoundException {
		String encoded_file,code_Table_File;
		encoded_file = args[0];
		code_Table_File = args[1];
		boolean flag = true;
		fileHandling readCodeTable = new fileHandling(code_Table_File);
		Node decodeReader = readCodeTable.scanCodeTableFile();
		Map<String, String> codeTable = readCodeTable.scanCodeTable();
		Map<Byte, String> bitMap = new HashMap<Byte, String>();//
		File readEncodeFile = new File(encoded_file);//
		BufferedInputStream read = new BufferedInputStream(new FileInputStream(readEncodeFile));//check this line
		
		createDecodingBitMap(bitMap);
		 String filePath = "";
			if (readEncodeFile.getParentFile() != null) {
				filePath=readEncodeFile.getParentFile().getAbsolutePath()+"/";
			}
			 
		BufferedWriter write = new BufferedWriter(
				new FileWriter(filePath + "decoded.txt"));
		
		int input = 0, count;
		
		while (flag) {
			byte[] temp = new byte[32000];//
			count = 0;
			
			while (count < 32000 && (input = read.read()) != -1) {
				byte convert = (byte) input;
				temp[count++] = convert;
			}
			if (input == -1) {
				temp = Arrays.copyOf(temp, count);
				flag = false;
			}
			String stringDecoded = convertBitsToBinary(temp, bitMap);
			write.write(convertBinaryToString(decodeReader, stringDecoded));
		}
		write.close();
		read.close();
	}

	private String convertBinaryToString(Node root, String decodedString) {
		int index=0,count;
		Node n =root;
		StringBuffer outBuffer = new StringBuffer();
		decodedString = stingBackup+decodedString;
		
		for(count=0;count<decodedString.length();count++){
			char c= decodedString.charAt(count);
			if(c=='0'){
				n=n.getLeft();
			}else{
				n=n.getRight();
			}
			if(n.getLeft()==null && n.getRight()==null){
				outBuffer.append(n.getElement()).append("\n");
				n=root;
				index=count;
			}
			
		}
		
		stingBackup = decodedString.substring(index+1);
		return outBuffer.toString();
	}
	
	private void createDecodingBitMap(Map<Byte, String> bitMap) {
		for (int i = 0; i <= 255; i++) {
			int buffer = 128;
			StringBuffer current = new StringBuffer();//
			
			while (buffer >= 1) {
				if ((i & buffer) > 0) {
					current.append("1");
				} else {
					current.append("0");
				}
				buffer/=2;
				
			}
			bitMap.put((byte) (i), current.toString());
		}
	}

	private String convertBitsToBinary(byte[] byteData, Map<Byte, String> bitMap) {
		StringBuffer binary = new StringBuffer();
		for (Byte current : byteData) {
			binary.append(bitMap.get(current));
		}
		return binary.toString();
	}

	

	

	public static void main(String args[]) throws IOException {
		decoder decoder = new decoder();
		long startTime = System.currentTimeMillis();
		decoder.decode(args);
		long time = System.currentTimeMillis()-startTime;
		System.out.println("time for decoder is: "+time +" milliSeconds");
	}


}
