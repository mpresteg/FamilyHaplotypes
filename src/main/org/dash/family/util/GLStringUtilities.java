package org.dash.family.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GLStringUtilities {
	private static final String GENE_DELIMITER_REGEX = "[\\^]";
	private static final String GENE_DELIMITER = "^";
	private static GLStringUtilities instance = null;
	private List<String> alleleAmbiguities;
	
	private GLStringUtilities() {
		this.alleleAmbiguities = loadAlleleAmbiguities();
	}
	
	public static GLStringUtilities getInstance() {
		if (instance == null) instance = new GLStringUtilities();
		
		return instance;
	}
	
	private List<String> loadAlleleAmbiguities() {
		BufferedReader reader = FileUtilities.readFile("alleleAmbiguities.txt");
		List<String> alleleAmbiguities = new ArrayList<String>();
		
		String line;
		
		try {
			while ((line = reader.readLine()) != null) {
				alleleAmbiguities.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return alleleAmbiguities;
	}
	
	public String convertToAmbiguityString(String allele) {
		for (String ambiguityString : alleleAmbiguities) {
			if (ambiguityString.contains(allele)) return ambiguityString;
		}
		
		// TODO:  Revisit whether any of this logic is needed - it was lifted over explicitly from AmbiguityList.java in HaploObserv
		
//		if (converted.contains("HLA-DRB3*01:01:02:01")) {
//			converted = "HLA-DRB3*01:01:02:01";
//		}
//		
//		// fixed here
//		// type: "HLA-DQA1*01:02:01:01/HLA-DQA1*01:02:01:03"
//		boolean fieldmatch = false;
//		if (converted.length() > 0) {
//			for (List<String> tmpList : alleleList) {
//				for (String str : tmpList) {
//					String [] list = type.split("/");	// if type.contains("/)					
//					for (String allele : list) {
//						if (allele.equals(str)) {
//							fieldmatch = true;
//						}
//					}					
//				}
//			}
//			if (!fieldmatch) {
//				converted = type;
//			}
//		}	
		
		return allele;
	}
	
	public String normalizeGlString(String glString) {
		
		// separate the glString into each gene
		StringTokenizer st = new StringTokenizer(glString,
				GENE_DELIMITER_REGEX);
		
		String gene;
		StringBuffer normalizedGlString = new StringBuffer();
		
		while (st.hasMoreTokens()) {
			gene = st.nextToken();
			
			gene = removeNotation(gene);
			
			gene = fixHomozygous(gene);
			
			normalizedGlString.append(gene + GENE_DELIMITER);
		}
		
		return normalizedGlString.substring(0, normalizedGlString.length() - 1);
	}
	
	public String removeNotation(String gene) {
		String regx = "[a-z][0-9]*@*"; // remove vendor specific bonus characters
		boolean test = true;
		
		while (test) {
			Pattern pattern = Pattern.compile(regx);	// handle e, i and x
			Matcher matcher = pattern.matcher(gene);
			
			if (matcher.find()) {
				gene = gene.replace(matcher.group(0), "");	
			}
			else {
				test = false;
			}
		}
		
		return gene;
	}
	
	public String fixHomozygous(String gene) {
		String normalizedGene;
		
		if (!gene.contains("+") && !gene.contains("DRB3") && !gene.contains("DRB4") && !gene.contains("DRB5")) {
			normalizedGene = gene + "+" + gene;
		}
		else {
			normalizedGene = gene;
		}
		
		return normalizedGene;
	}
}
