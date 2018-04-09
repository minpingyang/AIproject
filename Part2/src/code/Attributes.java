package code;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minpingyang
 *
 */
public class Attributes {
	
	private List<String> status_List;
	private String name;
	private List<Boolean> booList = new ArrayList<>();
	public Attributes(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public List<Boolean> getBooList() {
		return booList;
	}
	public void addBool_to_List(boolean b){
		booList.add(b);
	}
	public void setStatus_List(List<String> status_List) {
		this.status_List = status_List;
	}
	public List<String> getStatus_List() {
		return status_List;
	}
	
	
}
