package duyen.model;

import java.util.ArrayList;

public class Group {
	private String Name;
	private String Tien;
	private ArrayList<Child> Items;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public ArrayList<Child> getItems() {
		return Items;
	}
	public void setItems(ArrayList<Child> Items) {
		this.Items = Items;
	}
	public String getTien() {
		return Tien;
	}
	public void setTien(String tien) {
		Tien = tien;
	}
	
}
