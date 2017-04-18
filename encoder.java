import java.io.IOException;
import java.util.List;

public class encoder {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = args[0];
		fileHandling handle = new fileHandling(fileName);
		
		try{
			List<BinaryNode> node = handle.scanFile();
			huffmanCodeGeneration huffman = new huffmanCodeGeneration();
			huffman.encode(node, handle);
			
			
		}catch(IOException exception){
			System.out.println(exception.getMessage());
			}

	}

}
