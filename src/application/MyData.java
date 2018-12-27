package application;

import java.io.Serializable;
import java.util.HashMap;

public class MyData implements Serializable{
	private String action;
	private HashMap<String,Object> data;
	public MyData(String datatype) {
		this.action=datatype;
	}
	private HashMap<String,Object> getData() {
		if (this.data==null)
			this.data=new HashMap<>();
		return data;
	}
	public void add(String datatype, Object data) {
		getData().put(datatype, data);
	}
	public Object getData(String datatype) {
		return getData().get(datatype);
	}
	public String getAction() {
		return action;
	}
}
