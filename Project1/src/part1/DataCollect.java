package part1;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

public class DataCollect {
	private int a1 = 0, a2 = 0, a3 = 0, a4 = 0;
	private int b1 = 0, b2 = 0, b3 = 0, b4 = 0;
	private List<Integer> r = new ArrayList<Integer>();

	public void loadFile() {
		File directory = new File("./");
		String currentPath = directory.getAbsolutePath();
		currentPath = currentPath.substring(0, currentPath.length() - 1);
		File testFile = new File(currentPath + "ass1-data/ass1-data/part1/iris-test.txt");
		File trainFile = new File(currentPath + "ass1-data/ass1-data/part1/iris-training.txt");
		List<List<String>> testValues = new ArrayList<>();
		testValues = readFile(testFile);
		
		// for(int row=0;row<testValues.size();row++) {
		// for(int col=0;col<testValues.get(0).size();col++) {
		// System.out.println(testValues.get(row).get(col));
		// }
		// }
		for (int col = 0; col < testValues.get(0).size(); col++) {
			System.out.println(testValues.get(0).get(col));
		}
	}

	public List<List<String>> readFile(File fname) {
		BufferedReader bfreader = null;
		FileReader fReader = null;
		List<List<String>> table = new ArrayList<List<String>>();

		int row = 0;
		int col = 0;

		try {
			fReader = new FileReader(fname);
			bfreader = new BufferedReader(fReader);
			String sCurrentLine;
			while ((sCurrentLine = bfreader.readLine()) != null) {
				for (String string : sCurrentLine.split(" ")) {
					table.add(new ArrayList<>());
				
					if(!string.isEmpty()) {
						table.get(row).add(string);
						System.out.println("row: "+row+"string:"+string);
					}		
				}
				row++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bfreader != null) {
					bfreader.close();
				}
				if (fReader != null) {
					fReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return table;
	}
}
