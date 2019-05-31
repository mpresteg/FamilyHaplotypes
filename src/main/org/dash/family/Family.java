package org.dash.family;

import java.util.ArrayList;
import java.util.List;

public class Family {
	private String familyId;
	
	private Person mother;
	private Person father;
	private List<Person> children = new ArrayList<Person>();
	
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	public Person getMother() {
		return mother;
	}
	public void setMother(Person mother) {
		this.mother = mother;
	}
	public Person getFather() {
		return father;
	}
	public void setFather(Person father) {
		this.father = father;
	}
	public List<Person> getChildren() {
		return children;
	}
	public void addChild(Person child) {
		this.children.add(child);
	}
}
