package org.dash.family.file;

public class Pedigree {
	private String individualId;
	private String familyGenerationId;
	private String relation;

	public String getIndividualId() {
		return individualId;
	}
	public void setIndividualId(String individualId) {
		this.individualId = individualId;
	}
	public String getFamilyGenerationId() {
		return familyGenerationId;
	}
	public void setFamilyGenerationId(String familyGenerationId) {
		this.familyGenerationId = familyGenerationId;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
}
