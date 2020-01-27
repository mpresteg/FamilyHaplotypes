package org.dash.family;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.dash.family.util.GLStringUtilities;
import org.dash.family.util.Locus;

public class Genotype {
	private String glString;
	
	private HashMap<Locus, String> locusGLStrings = new HashMap<Locus, String>();
	private HashMap<Locus, TreeSet<String>> inheritedTypingSet = new HashMap<Locus, TreeSet<String>>();
	
	public Genotype (String glString) {
		this.glString = glString;
		
		this.locusGLStrings = GLStringUtilities.parseGLStringIntoLoci(glString);
		
		for (Locus locus : this.locusGLStrings.keySet()) {			
			setInheritedTypingSet(locus, this.locusGLStrings.get(locus));
		}

	}

	public String getGlString() {
		return glString;
	}

	public HashMap<Locus, String> getLocusGLStrings() {
		return locusGLStrings;
	}
	
	public HashMap<Locus, TreeSet<String>> getInheritedTypingSet() {
		return inheritedTypingSet;
	}
	
	public void setLocusGLString(Locus locus, String singleLocusGLString) {
		setInheritedTypingSet(locus, singleLocusGLString);
		
	}

	private void setInheritedTypingSet(Locus locus, String singleLocusGLString) {
		List<String> genotypeAmbiguities = GLStringUtilities.parse(singleLocusGLString, GLStringUtilities.GENOTYPE_AMBIGUITY_DELIMITER);
		TreeSet<String> set = new TreeSet<String>();
		for (String genotypeAmbiguity : genotypeAmbiguities) {
			set.addAll(GLStringUtilities.getInstance().convertToAmbiguityStrings(GLStringUtilities.parse(genotypeAmbiguity, GLStringUtilities.GENE_COPY_DELIMITER)));
		}
		this.inheritedTypingSet.put(locus, set);
	}
}
