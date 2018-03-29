package part1;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;




public class DataCollect {
	
	
	private List<Double> tr0=new ArrayList<>();
	private List<Double> tr1=new ArrayList<>();
	private List<Double> tr2=new ArrayList<>();
	private List<Double> tr3=new ArrayList<>();
	private List<Double> te0=new ArrayList<>();
	private List<Double> te1=new ArrayList<>();
	private List<Double> te2=new ArrayList<>();
	private List<Double> te3=new ArrayList<>();
	private List<String> trainType=new ArrayList<>();
	private List<String> testType=new ArrayList<>();
	private List<Double> r = new ArrayList<Double>();
	private List<List<Double>>trCols= new ArrayList<>();
	private List<List<Double>>teCols= new ArrayList<>();
	
	
	public DataCollect() {
		loadFile();
		r.add(findRange(tr0));
		r.add(findRange(tr1));
		r.add(findRange(tr2));
		r.add(findRange(tr3));
		trCols.add(tr0);
		trCols.add(tr1);
		trCols.add(tr2);
		trCols.add(tr3);
		teCols.add(te0);
		teCols.add(te1);
		teCols.add(te2);
		teCols.add(te3);
	}
	public void loadFile() {
		File directory = new File("./");
		String currentPath = directory.getAbsolutePath();
		currentPath = currentPath.substring(0, currentPath.length() - 1);
		File testFile = new File(currentPath + "ass1-data/ass1-data/part1/iris-test.txt");
		File trainFile = new File(currentPath + "ass1-data/ass1-data/part1/iris-training.txt");
		analyse(readFile(testFile),false);
		analyse(readFile(trainFile),true);
		
		
	}
	public double findRange(List<Double> tlist) {
		return Collections.max(tlist)-Collections.min(tlist);
		
	}
//	public void test(List<Double> t) {
//		for (int i = 0; i < t.size(); i++) {
//			System.out.println(t.get(i));
//		}
//	}
	public void analyse(List<List<String>> testValues,boolean forTrain) {
		for (int row = 0;!testValues.get(row).isEmpty() ; row++) {
			for (int col = 0; col < testValues.get(0).size(); col++) {
				if(forTrain) {
					switch (col) {
					case 0:
						tr0.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 1:
						tr1.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 2:
						tr2.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 3:
						tr3.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 4:
						trainType.add(testValues.get(row).get(col));
						break;
					default:
						break;
					}
				}else {
					switch (col) {
					case 0:
						te0.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 1:
						te1.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 2:
						te2.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 3:
						te3.add(Double.parseDouble(testValues.get(row).get(col)));
						break;
					case 4:
						testType.add(testValues.get(row).get(col));
						break;
					default:
						break;
					}
				}
				
			}
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

					if (!string.isEmpty()) {
						table.get(row).add(string);
//						System.out.println("row: " + row + "string:" + string);
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
	public List<String> getTrainType() {
		return trainType;
	}
	public List<String> getTestType() {
		return testType;
	}
	public List<Double> getR() {
		return r;
	}
	public List<List<Double>> getTrCols() {
		return trCols;
	}
	public List<List<Double>> getTeCols() {
		return teCols;
	}
	
}
