package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.Entry.Attribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.StyledEditorKit.ForegroundAction;

public class DataCollection {
//	private Set<Case> table = new HashSet<>();
	private List<Case> table = new ArrayList<>();
	public static String []kindsOfStatus=new String[2]; 
	private List<String> status_List;
	private List<Attributes>attributes_List;
	public DataCollection() {
		loadFile();
	}
	
	/**
	 * load data from test.txt and training.txt
	 * */
	public void loadFile() {
		File directory = new File("./");
		String currentPath = directory.getAbsolutePath();
		currentPath = currentPath.substring(0, currentPath.length() - 1);
		File testFile = new File(currentPath + "ass1-data/ass1-data/part2/golf-test.dat");
		File trainFile = new File(currentPath + "ass1-data/ass1-data/part2/golf-training.dat");
//		File trainFile = new File(currentPath + "ass1-data/ass1-data/part2/hepatitis-training-run01.dat");
		readFile(trainFile);
//		testTable();
		
		
	}
	public void testTable() {
		for(Case case1:table) {
			System.out.print(case1.getStatus()+" ");
			for(boolean b:case1.getBooltuple()) {
				System.out.print(b+" ");
			}
			System.out.println();
			
		}
	}
	/**
	 * Read data from text files
	 * **/
	public void readFile(File fname) {
		BufferedReader bfreader = null;
		FileReader fReader = null;
		String[] tuple;
		status_List=new ArrayList<>();
		attributes_List = new ArrayList<>(); 
		
		try {
			fReader = new FileReader(fname);
			bfreader = new BufferedReader(fReader);
			String sCurrentLine;
			kindsOfStatus=bfreader.readLine().split("\\s+"); 
			String[] attributesNames = bfreader.readLine().split("\\s+");

			for(String name:attributesNames) {
				attributes_List.add(new Attributes(name));
			}
			
			while ((sCurrentLine = bfreader.readLine()) != null) {
//				System.out.println(sCurrentLine);
				
				tuple=sCurrentLine.split("\\s+");
				status_List.add(tuple[0]);
				
//				System.out.println(attributes_List.get(0).getName());
				Case case1 =new Case();
				case1.setStatus(tuple[0]);
				for(int i=0; i<attributesNames.length;i++) {
					attributes_List.get(i).addBool_to_List(Boolean.parseBoolean(tuple[i+1]));
					
					case1.getBooltuple().add(Boolean.parseBoolean(tuple[i+1]));
					
				}
				table.add(case1);
//				table.add(new Case(tuple[0], tuple[1],tuple[2], 
//						tuple[3], tuple[4],tuple[5], tuple[6]
//						
//						));
			}
			//set status list
			for(Attributes attributes:attributes_List) {
				attributes.setStatus_List(status_List);
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
		
	}
//	public Set<Case> getTable() {
//		return table;
//	}

	
	public List<Attributes> getAttributes_List() {
		return attributes_List;
	}
}
