package code;

import java.util.ArrayList;
import java.util.List;

public class Case {
	
	private String status;

	private List<Boolean> booltuple = new ArrayList<>();
	
	public List<Boolean> getBooltuple() {
		return booltuple;
	}

	public void setStatus(String sta) {
		status=sta;
	}
	
	public String getStatus() {
		return status;
	}



}
