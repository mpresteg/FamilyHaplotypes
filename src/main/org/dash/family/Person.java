package org.dash.family;

import java.util.List;

public class Person {
	private Genotype genotype;
	
	private String ethnicity;
	
	private String sampleId;
	
	private String labCode;
	
	private Haplotype haplotype;
	
	public Genotype getGenotype() {
		return genotype;
	}

	public void setGenotype(Genotype genotype) {
		this.genotype = genotype;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getLabCode() {
		return labCode;
	}

	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}

	public String getDerivedHaplotype() {
		return derivedHaplotype;
	}

	public void setDerivedHaplotype(String derivedHaplotype) {
		this.derivedHaplotype = derivedHaplotype;
	}

	public List<String> getHaplotypes() {
		return haplotypes;
	}

	public void setHaplotypes(List<String> haplotypes) {
		this.haplotypes = haplotypes;
	}

	private String derivedHaplotype;
	
	private List<String> haplotypes;
}
