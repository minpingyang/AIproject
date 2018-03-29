package part1;



public class nestedNe {

	public static void main(String[] args) {
		DataCollect dataCollect=new DataCollect();
		Computing computing = new Computing(dataCollect.getTeCols(), dataCollect.getTrCols(), dataCollect.getR(),dataCollect.getTrainType());
		
	}

}
