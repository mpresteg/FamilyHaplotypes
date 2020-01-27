package org.dash.family.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dash.family.Family;
import org.dash.family.Genotype;
import org.dash.family.Person;

public class GLStringUtilities {
	private static final String GENE_DELIMITER_REGEX = "[\\^]";
	private static final String GENE_DELIMITER = "^";
	public static final String GENOTYPE_AMBIGUITY_DELIMITER = "|";
	public static final String GENE_COPY_DELIMITER = "+";
	public static final String GENE_PHASE_DELIMITER = "~";
	public static final String ALLELE_AMBIGUITY_DELIMITER = "/";
	public static final String ESCAPED_ASTERISK = "\\*";

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
	
	public List<String> convertToAmbiguityStrings(List<String> geneCopies) {	
		List<String> convertedGeneCopies = new ArrayList<String>();
		
		for (String geneCopy : geneCopies) {
			convertedGeneCopies.add(GLStringUtilities.getInstance().convertToAmbiguityString(geneCopy));
		}
		
		return convertedGeneCopies;
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
		
		if (!gene.contains("+") && !gene.contains(Locus.HLA_DRB3.getFullName()) && !gene.contains(Locus.HLA_DRB4.getFullName()) && !gene.contains(Locus.HLA_DRB5.getFullName())) {
			normalizedGene = gene + "+" + gene;
		}
		else {
			normalizedGene = gene;
		}
		
		return normalizedGene;
	}
	
	public static List<String> parse(String value, String delimiter) {
		List<String> elements = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(value, delimiter);
		while (st.hasMoreTokens()) {
			elements.add(st.nextToken());
		}

		return elements;
	}
	
	public static HashMap<Locus, String> parseGLStringIntoLoci(String glString) {
		HashMap<Locus, String> locusMap = new HashMap<Locus, String>();
		
		List<String> genes = parse(glString,
				GENE_DELIMITER);
		
		for (String gene : genes) {
			String[] splitString = gene
					.split(ESCAPED_ASTERISK);
			
			locusMap.put(Locus.normalizeLocus(Locus.lookup(splitString[0])), gene);
		}
		
		return locusMap;
	}
	
	public static Family resolveGenotypicAmbiguity(Family family) {
		List<Person> children = family.getChildren();
		
		Person mother = family.getMother();
		Person father = family.getFather();
		
		List<Map<Locus, TreeSet<String>>> childrenSetList = new ArrayList<Map<Locus, TreeSet<String>>>();

		for (Person child : children) {
			childrenSetList.add(child.getGenotype().getInheritedTypingSet());
		}
		// go through parent's ambiguities		
		mother.setGenotype(new Genotype(evaluateParentsAgainstChildren(mother, childrenSetList)));
		father.setGenotype(new Genotype(evaluateParentsAgainstChildren(father, childrenSetList)));
		
		HashMap<Locus, String> singleLocusGLStrings;

		for (Person child : children) {
			singleLocusGLStrings = child.getGenotype().getLocusGLStrings();
			
			StringBuffer sb = new StringBuffer();
			int i=0;
			for (Locus locus : singleLocusGLStrings.keySet()) {
				String singleLocusGLString = singleLocusGLStrings.get(locus);
				List<String> genotypeAmbiguities = parse(singleLocusGLString, GENOTYPE_AMBIGUITY_DELIMITER);
				
				for (int j=0;j<genotypeAmbiguities.size();j++) {
					TreeSet<String> childSet = new TreeSet<String>();

					childSet.addAll(GLStringUtilities.getInstance().convertToAmbiguityStrings(parse(genotypeAmbiguities.get(j), GENE_COPY_DELIMITER)));
										
					if (!resolveChildAmbiguityByParents(mother, father, locus, childSet)) {
						genotypeAmbiguities.remove(j);
						continue;
					}
										
					sb.append(genotypeAmbiguities.get(j).toString());
					if (j < genotypeAmbiguities.size() - 1) {
						sb.append(GENOTYPE_AMBIGUITY_DELIMITER);
					}
				}
				
				if (sb.toString().endsWith(GENOTYPE_AMBIGUITY_DELIMITER)) sb = new StringBuffer(sb.substring(0, sb.length() - 1));
				
				if (i < singleLocusGLStrings.size() - 1) sb.append(GENE_DELIMITER);
				
				i++;
			}
			
			child.setGenotype(new Genotype(sb.toString()));
		}
		
		return family;
	}

	public static String evaluateParentsAgainstChildren(Person parent,
			List<Map<Locus, TreeSet<String>>> childrenSetList) {
		StringBuffer modifiedGLString = new StringBuffer();

		HashMap<Locus, String> singleLocusGLStrings = parent.getGenotype().getLocusGLStrings();
		int x = 0;
		for (Locus locus : singleLocusGLStrings.keySet()) {
			String singleLocusGLString = singleLocusGLStrings.get(locus);
			List<String> genotypeAmbiguities = parse(singleLocusGLString, GENOTYPE_AMBIGUITY_DELIMITER);
			
			for (int i=0; i < genotypeAmbiguities.size(); i++) {
				List<String> parentSet = GLStringUtilities.getInstance().convertToAmbiguityStrings(parse(genotypeAmbiguities.get(i), GENE_COPY_DELIMITER));	
				
				for (Map<Locus, TreeSet<String>> childSetList : childrenSetList) {
					TreeSet<String> childSet = childSetList.get(locus);
					
					// if parent set doesn't contain either of the child gene copies, we can eliminate the ambiguity entirely
					if (!verifyChildTypingInheritence(parentSet, childSet)) {
						genotypeAmbiguities.remove(i);
					}
				}
			}
						
			for (int j=0;j < genotypeAmbiguities.size();j++) {
				modifiedGLString.append(genotypeAmbiguities.get(j).toString());
				if (j < genotypeAmbiguities.size() - 1) {
					modifiedGLString.append(GENOTYPE_AMBIGUITY_DELIMITER);
				}
			}
			
			if (modifiedGLString.toString().endsWith(GENOTYPE_AMBIGUITY_DELIMITER)) modifiedGLString = new StringBuffer(modifiedGLString.substring(0, modifiedGLString.length() - 1));
			
			if (x < singleLocusGLStrings.size() - 1) modifiedGLString.append(GENE_DELIMITER);
			
			x++;
		}
		
		return modifiedGLString.toString();
	}

	public static boolean verifyChildTypingInheritence(List<String> parentSet, TreeSet<String> childSet) {
		for (String childTyping : childSet) {
			if (parentSet.contains(childTyping)) {

				return true;
			}
		}
		
		return false;
	}

	public static boolean resolveChildAmbiguityByParents(Person mother, Person father, Locus locus,
			Set<String> childSet) {
		
		//check parents
		
		List<String> motherGenotypeAmbiguities = parse(mother.getGenotype().getLocusGLStrings().get(locus), GENOTYPE_AMBIGUITY_DELIMITER);
		List<String> fatherGenotypeAmbiguities = parse(father.getGenotype().getLocusGLStrings().get(locus), GENOTYPE_AMBIGUITY_DELIMITER);
		
		for (String motherGenotypeAmbiguity : motherGenotypeAmbiguities) {
			for (String fatherGenotypeAmbiguity : fatherGenotypeAmbiguities) {							
				for (String motherGenotypeCopy : parse(motherGenotypeAmbiguity, GENE_COPY_DELIMITER)) {
					for (String fatherGenotypeCopy : parse(fatherGenotypeAmbiguity, GENE_COPY_DELIMITER)) {
						Set<String> parentSet = new TreeSet<String>();

						parentSet.add(GLStringUtilities.getInstance().convertToAmbiguityString(motherGenotypeCopy));
						parentSet.add(GLStringUtilities.getInstance().convertToAmbiguityString(fatherGenotypeCopy));
																								
						if (childSet.equals(parentSet)) return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static String compressGenotypicAmbiguity(String singleLocusGLString) {
		List<String> genotypeAmbiguities = parse(singleLocusGLString, GENOTYPE_AMBIGUITY_DELIMITER);
		
		List<List<String>> ambiguities = new ArrayList<List<String>>();
		
		for (String genotypeAmbiguity : genotypeAmbiguities) {
			List<String> geneCopies = parse(genotypeAmbiguity, GENE_COPY_DELIMITER);
			
			if (ambiguities.isEmpty()) {
				ambiguities.add(geneCopies);
				continue;
			}
			
			boolean redundant = false;
			
			for (List<String> ambiguityGeneCopies : ambiguities) {
				
				// current gene copies being evaluated for redundancy
				String firstGeneCopy = geneCopies.get(0);
				String secondGeneCopy = geneCopies.get(1);
				
				// gene copies that have not already been seen
				String firstAmbiguityGeneCopy = ambiguityGeneCopies.get(0);
				String secondAmbiguityGeneCopy = ambiguityGeneCopies.get(1);
				
				if (firstGeneCopy.contains(firstAmbiguityGeneCopy) || firstAmbiguityGeneCopy.contains(firstGeneCopy)) {
					redundant = true;
					if (secondGeneCopy.contains(secondAmbiguityGeneCopy) || secondAmbiguityGeneCopy.contains(secondGeneCopy)) {
						break;
					}
					else {
						String replaceGeneCopy = secondAmbiguityGeneCopy + ALLELE_AMBIGUITY_DELIMITER + secondGeneCopy;
						ambiguityGeneCopies.set(1, replaceGeneCopy);
					}
				}
				else if (secondGeneCopy.contains(secondAmbiguityGeneCopy) || secondAmbiguityGeneCopy.contains(secondGeneCopy)) {
					redundant = true;
					String replaceGeneCopy = firstAmbiguityGeneCopy + ALLELE_AMBIGUITY_DELIMITER + firstGeneCopy;
					ambiguityGeneCopies.set(0, replaceGeneCopy);
				}
				else if ((firstGeneCopy.contains(secondGeneCopy) || secondGeneCopy.contains(firstGeneCopy)) &&
						(firstAmbiguityGeneCopy.contains(secondAmbiguityGeneCopy) || secondAmbiguityGeneCopy.contains(firstAmbiguityGeneCopy))) {
					redundant = true;
					String replaceGeneCopy = firstAmbiguityGeneCopy + ALLELE_AMBIGUITY_DELIMITER + firstGeneCopy;
					ambiguityGeneCopies.set(0,  replaceGeneCopy);
					replaceGeneCopy = secondAmbiguityGeneCopy  + ALLELE_AMBIGUITY_DELIMITER + secondGeneCopy;
					ambiguityGeneCopies.set(1,  replaceGeneCopy);
				}
			}
			
			// if the gene copies being evaluated are not redundant...then incorporate them
			if (!redundant) {
				ambiguities.add(geneCopies);
			}
		}
		
		StringBuffer modifiedGLString = new StringBuffer();
		for (int i=0;i<ambiguities.size();i++) {
			List<String> ambiguityGeneCopies = ambiguities.get(i);
			modifiedGLString.append(ambiguityGeneCopies.get(0) + "+" + ambiguityGeneCopies.get(1));
			if (i < ambiguities.size() - 1) {
				modifiedGLString.append(GENOTYPE_AMBIGUITY_DELIMITER);
			}
		}
		
		return modifiedGLString.toString();
	}
	
}
