package org.dash.family;

import java.util.HashMap;

import org.dash.family.util.GLStringUtilities;
import org.dash.family.util.Locus;

public class Genotype {
	private String glString;
	
	private HashMap<Locus, String> locusGLStrings = new HashMap<Locus, String>();
	
	public Genotype (String glString) {
		this.glString = glString;
		
		this.locusGLStrings = GLStringUtilities.getInstance().parseGLStringIntoLoci(glString);
	}

	public String getGlString() {
		return glString;
	}

	public void setGlString(String glString) {
		this.glString = glString;
	}

	public HashMap<Locus, String> getLocusGLStrings() {
		return locusGLStrings;
	}

	public void setLocusGLStrings(HashMap<Locus, String> locusGLStrings) {
		this.locusGLStrings = locusGLStrings;
	}
}
