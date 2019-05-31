package org.dash.family.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AmbiguityUtilities {
	private static AmbiguityUtilities instance = null;
	private List<String> alleleAmbiguities;
	
	private AmbiguityUtilities() {
		this.alleleAmbiguities = loadAlleleAmbiguities();
	}
	
	public static AmbiguityUtilities getInstance() {
		if (instance == null) instance = new AmbiguityUtilities();
		
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
}
