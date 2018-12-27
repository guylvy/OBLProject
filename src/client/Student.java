package client;

import java.io.Serializable;

public class Student implements Serializable{
	private String id=null;
	private String name=null;
	private String StatusMembership=null;
	private String Operation=null;
	private boolean Freeze=false;
	public Student() {
		
	}
	public Student(String id, String name, String StatusMembership, String Operation, boolean Freeze) {
		this.id=id;
		this.name=name;
		this.StatusMembership=StatusMembership;
		this.Operation=Operation;
		this.Freeze=Freeze;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public synchronized String getName() {
		return name;
	}
	public synchronized void setName(String name) {
		this.name = name;
	}
	public String getStatusMembership() {
		return StatusMembership;
	}
	public void setStatusMembership(String statusMembership) {
		StatusMembership = statusMembership;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}
	public boolean isFreeze() {
		return Freeze;
	}
	public void setFreeze(boolean freeze) {
		Freeze = freeze;
	}
	
}
