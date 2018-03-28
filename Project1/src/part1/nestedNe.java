package part1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.text.AbstractDocument.BranchElement;

public class nestedNe {

	public static void main(String[] args) {
		File directory = new File("./");
		String currentPath = directory.getAbsolutePath();
		currentPath = currentPath.substring(0,currentPath.length()-1);
		File testFile = new File(currentPath+"ass1-data/ass1-data/part1/iris-test.txt");
		File trainFile= new File(currentPath+"ass1-data/ass1-data/part1/iris-training.txt");
		BufferedReader bfreader = null;
		BufferedReader bfreaderTrain=null;
		FileReader freader=null;
		FileReader freaderTrain =null;
		try {
			freader = new FileReader(testFile);
			bfreader = new BufferedReader(freader);
			String sCurrentLine;
			while((sCurrentLine=bfreader.readLine())!=null) {
				System.out.println(sCurrentLine);
			}
			freaderTrain = new FileReader(trainFile);
			bfreaderTrain= new BufferedReader(freaderTrain);
			String tCurrentLine;
			while((tCurrentLine=bfreaderTrain.readLine())!=null) {
				System.out.println("training: "+tCurrentLine);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(bfreader!=null){
					bfreader.close();
				}
				if(freader!=null){
					freader.close();
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
