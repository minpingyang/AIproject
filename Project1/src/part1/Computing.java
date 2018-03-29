package part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Computing {
	private List<Double> distance = new ArrayList<>();
	private int k=1;

	public Computing(List<List<Double>> teCols, List<List<Double>>trCols,List<Double> r,List<String> trainType) {
		findDistance(teCols, trCols, r,trainType);
	}
	public void findDistance(List<List<Double>> teCols, List<List<Double>>trCols,List<Double> r,List<String> trainType) {
		for(int row=0;row<trCols.get(0).size();row++) {	
			double sumEle=0;
			for(int col=0;col<4;col++) {
			
				double value1=teCols.get(col).get(0)-trCols.get(col).get(row);//0 need to change to other rows later
				value1 = value1*value1;
				double ri=r.get(col);
				ri=ri*ri;
				double ele=value1/ri;
				sumEle+=ele;
			}
			distance.add(Math.sqrt(sumEle));
			
		}
		Map<Double, String> disToType= new HashMap<>();
		for(int i=0;i<distance.size();i++) {
			disToType.put(distance.get(i),trainType.get(i));
		}
		findNearestK(disToType);
	}
	/**
	 * Find k minimums in distance, get the index/row of the minimums, then find their type proportion,
	 * return the most proportion type in the k minimums value 
	 * **/
	public void findNearestK(Map<Double, String> disToType) {
		Collections.sort(distance);
		List<String> typeNames = new ArrayList<>();
		assert k>0;
		for(int i=0;i<k;i++) {
			typeNames.add(disToType.get(distance.get(i)));
		}
		
		System.out.println(mostNearest(typeNames));
		
		
	}
	public String mostNearest(List<String> typeNames) {
		Map<String,Integer> countTypes= new HashMap<>();
		countTypes.put("Iris-setosa", 0);
		countTypes.put("Iris-versicolor", 0);
		countTypes.put("Iris-virginica", 0);
		for(String type:typeNames) {
			countTypes.put(type,countTypes.get(type)+1);//maybe in progress error
		}
		//find the key name with highest value
		//if the highest value have more than one, then return the one with smallest distance.
		List<String> maxTypes= new ArrayList<>();
		for(String key:countTypes.keySet()) {
//			System.out.println(i+": "+countTypes.get(i));
			int value = countTypes.get(key);
			if(value==Collections.max(countTypes.values())){
				maxTypes.add(key);
			}
		}
		if(maxTypes.size()>1) {
			for(String type:typeNames) {
				if(maxTypes.contains(type)) {
					return type;
				}
			}
		}
		
		return maxTypes.get(0);
	}
}
