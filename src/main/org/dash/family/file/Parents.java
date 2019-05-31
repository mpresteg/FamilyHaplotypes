package org.dash.family.file;

public class Parents {
	private String paternalId;
	private String maternalId;
	private String familyId;
	
	public String getPaternalId() {
		return paternalId;
	}
	public void setPaternalId(String paternalId) {
		this.paternalId = paternalId;
	}
	public String getMaternalId() {
		return maternalId;
	}
	public void setMaternalId(String maternalId) {
		this.maternalId = maternalId;
	}
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	
	@Override
	public boolean equals(Object element1) {
		Parents parents = (Parents) element1;
		if (getMaternalId().equals(parents.getMaternalId()) &&
				getPaternalId().equals(parents.getPaternalId()) &&
				getFamilyId().equals(parents.getFamilyId())) {
			return true;
		}
		
		return false;
	}
}
