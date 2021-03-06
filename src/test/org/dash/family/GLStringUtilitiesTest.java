package org.dash.family;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.dash.family.util.GLStringUtilities;
import org.dash.family.util.Locus;
import org.junit.Test;

public class GLStringUtilitiesTest {
	
	// Allele Ambiguity variables
	private static final String DRB1_1501_AMBIGUITY = "HLA-DRB1*15:01:01:01/HLA-DRB1*15:01:01:02/HLA-DRB1*15:01:01:03";
	private static final String DRB1_1501 = "HLA-DRB1*15:01:01:01";
	
	private static final String A_0101 = "HLA-A*01:01:01:01";
	
	private static final String DQA1_0104_AMBIGUITY = "HLA-DQA1*01:04:01:01/HLA-DQA1*01:04:01:02/HLA-DQA1*01:04:01:04";
	private static final String DQA1_0104_PARTIAL_AMBIGUITY = "HLA-DQA1*01:04:01:01/HLA-DQA1*01:04:01:02";
	private static final String DQA1_0104 = "HLA-DQA1*01:04:01:02";
	
	private static final String DRB1_0301_AMBIGUITY = "HLA-DRB1*03:01:01:01/HLA-DRB1*03:01:01:02";
	private static final String DRB1_0301 = "HLA-DRB1*03:01:01:01";
	
	private static final String DPB1_0401_AMBIGUITY = "HLA-DPB1*04:01:01:01/HLA-DPB1*04:01:01:02";
	private static final String DPB1_0401 = "HLA-DPB1*04:01:01";
	
	//RemoveNotation variables
	private static final String DPB1_0402_105_CLEAN = "HLA-DPB1*04:02:01:02/HLA-DPB1*105:01/HLA-DPB1*105:01";
	private static final String DPB1_0402_105_BAD = DPB1_0402_105_CLEAN + "i1";
	
	private static final String DPB1_BAD = "HLA-DPB1*13:01:01/HLA-DPB1*13:01:01e1/HLA-DPB1*133:01/HLA-DPB1*04:02:01:02/HLA-DPB1*105:01/HLA-DPB1*105:01i1";
	private static final String DPB1_CLEAN = "HLA-DPB1*13:01:01/HLA-DPB1*13:01:01/HLA-DPB1*133:01/HLA-DPB1*04:02:01:02/HLA-DPB1*105:01/HLA-DPB1*105:01";
	
	private static final String MULTI_LOCUS_GL_STRING = "HLA-A*24:02:01:01+HLA-A*01:01:01:01^HLA-B*51:09:01+HLA-B*37:01:01^HLA-C*01:02:01+HLA-C*06:02:01:01^HLA-DPA1*01:03:01:02+HLA-DPA1*01:03:01:04^HLA-DPB1*04:01:01:01+HLA-DPB1*02:01:02^HLA-DQA1*05:05:01:02+HLA-DQA1*01:02:01:03^HLA-DQB1*03:01:01:03+HLA-DQB1*06:02:01^HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:01|HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:02|HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:03^HLA-DRB3*02:02:01:02^HLA-DRB5*01:01:01";
	private static final String A_GL_STRING = "HLA-A*24:02:01:01+HLA-A*01:01:01:01";
	private static final String B_GL_STRING = "HLA-B*51:09:01+HLA-B*37:01:01";
	
	private static final String UNCOMPRESSED_DPB1_GENOTYPIC_AMBIGUITY = "HLA-DPB1*04:01:01:01+HLA-DPB1*04:02:01:02|HLA-DPB1*04:01:01:02+HLA-DPB1*04:02:01:02|HLA-DPB1*105:01+HLA-DPB1*126:01";
	private static final String COMPRESSED_DPB1_GENOTYPIC_AMBIGUITY = "HLA-DPB1*04:01:01:01/HLA-DPB1*04:01:01:02+HLA-DPB1*04:02:01:02|HLA-DPB1*105:01+HLA-DPB1*126:01";
	private static final String UNCOMPRESSED_DRB1_GENOTYPIC_AMBIGUITY = "HLA-DRB1*15:01:01:01+HLA-DRB1*15:01:01:01|HLA-DRB1*15:01:01:02+HLA-DRB1*15:01:01:02|HLA-DRB1*15:01:01:03+HLA-DRB1*15:01:01:03|HLA-DRB1*15:01:01:01+HLA-DRB1*15:01:01:02|HLA-DRB1*15:01:01:01+HLA-DRB1*15:01:01:03|HLA-DRB1*15:01:01:02+HLA-DRB1*15:01:01:03";
	private static final String COMPRESSED_DRB1_GENOTYPIC_AMBIGUITY = "HLA-DRB1*15:01:01:01/HLA-DRB1*15:01:01:02/HLA-DRB1*15:01:01:03+HLA-DRB1*15:01:01:01/HLA-DRB1*15:01:01:02/HLA-DRB1*15:01:01:03";
	private static final String UNCOMPRESSED_DPB1_GENOTYPIC_AMBIGUITY_2 = "HLA-DPB1*02:01:02+HLA-DPB1*13:01:01|HLA-DPB1*02:01:02+HLA-DPB1*107:01|HLA-DPB1*02:01:19+HLA-DPB1*13:01:01|HLA-DPB1*02:01:19+HLA-DPB1*107:01";
	private static final String COMPRESSED_DPB1_GENOTYPIC_AMBIGUITY_2 = "HLA-DPB1*02:01:02/HLA-DPB1*02:01:19+HLA-DPB1*13:01:01/HLA-DPB1*107:01";
	
	private static final String AMBIG_FAMILY_2A_CHILD_1 = "DPB1*03:01:01+DPB1*05:01:01|DPB1*135:01+DPB1*104:01";
	private static final String AMBIG_FAMILY_2A_CHILD_2 = "DPB1*03:01:01+DPB1*04:01:01|DPB1*124:01+DPB1*350:01";
	private static final String AMBIG_FAMILY_2A_FATHER = "DPB1*03:01:01+DPB1*13:01:01|DPB1*03:01:01+DPB1*107:01";
	
	private static final String FAMILY_2A_CHILD_1 = "DPB1*03:01:01+DPB1*05:01:01";
	private static final String FAMILY_2A_CHILD_2 = "DPB1*03:01:01+DPB1*04:01:01";
	private static final String FAMILY_2A_FATHER = "DPB1*03:01:01+DPB1*13:01:01/DPB1*107:01";
	private static final String FAMILY_2A_MOTHER = "DPB1*04:01:01:01+DPB1*05:01:01";
	
	private static final String FAMILY_2B_CHILD_1 = "DPB1*05:01:01+DPB1*05:01:01";
	private static final String FAMILY_2B_CHILD_2 = "DPB1*05:01:01+DPB1*135:01";
	private static final String AMBIG_FAMILY_2B_CHILD_3_MOTHER = "DPB1*05:01:01+DPB1*13:01:01/DPB1*107:01|DPB1*135:01+DPB1*519:01";
	private static final String FAMILY_2B_CHILD_3_MOTHER = "DPB1*05:01:01+DPB1*13:01:01/DPB1*107:01";
	private static final String FAMILY_2B_FATHER = "DPB1*05:01:01+DPB1*135:01";

	@Test
	// TODO: Implement remaining test cases
	public void testAlleleAmbiguities() {
		GLStringUtilities ambiguityUtils = GLStringUtilities.getInstance();
		assertTrue(DRB1_1501_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DRB1_1501)));
		assertTrue(A_0101.equals(ambiguityUtils.convertToAmbiguityString(A_0101)));
		assertTrue(DQA1_0104_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DQA1_0104)));
		assertTrue(DQA1_0104_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DQA1_0104_PARTIAL_AMBIGUITY)));
		assertTrue(DRB1_0301_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DRB1_0301)));
		assertTrue(DPB1_0401_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DPB1_0401)));
	}
	
	@Test
	// TODO: Implement remaining test cases
	public void testRemoveNotation() {
		assertTrue(DPB1_0402_105_CLEAN.equals(GLStringUtilities.getInstance().removeNotation(DPB1_0402_105_BAD)));
		assertTrue(DPB1_CLEAN.equals(GLStringUtilities.getInstance().removeNotation(DPB1_BAD)));
	}
	
	@Test
	public void testParseGLStringIntoLoci() {
		HashMap<Locus, String> locusMap = GLStringUtilities.parseGLStringIntoLoci(MULTI_LOCUS_GL_STRING);
		assertTrue(locusMap.get(Locus.HLA_A).equals(A_GL_STRING));
		assertTrue(locusMap.get(Locus.HLA_B).equals(B_GL_STRING));
		
	}
	
	@Test
	public void testCompressGenotypicAmbiguity() {
		assertEquals(COMPRESSED_DPB1_GENOTYPIC_AMBIGUITY, GLStringUtilities.compressGenotypicAmbiguity(UNCOMPRESSED_DPB1_GENOTYPIC_AMBIGUITY));
		assertEquals(COMPRESSED_DRB1_GENOTYPIC_AMBIGUITY, GLStringUtilities.compressGenotypicAmbiguity(UNCOMPRESSED_DRB1_GENOTYPIC_AMBIGUITY));
		assertEquals(COMPRESSED_DPB1_GENOTYPIC_AMBIGUITY_2, GLStringUtilities.compressGenotypicAmbiguity(UNCOMPRESSED_DPB1_GENOTYPIC_AMBIGUITY_2));

	}
	
	@Test
	public void testResolveGenotypicAmbiguity_2A() {
		Family family = new Family();
		Person mother = new Person();
		Person father = new Person();
		Person child1 = new Person();
		Person child2 = new Person();
		
		mother.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(FAMILY_2A_MOTHER)));
		father.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(AMBIG_FAMILY_2A_FATHER)));
		child1.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(AMBIG_FAMILY_2A_CHILD_1)));
		child2.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(AMBIG_FAMILY_2A_CHILD_2)));
		
		family.setFather(father);
		family.setMother(mother);
		
		family.addChild(child1);
		family.addChild(child2);
		
		GLStringUtilities.resolveGenotypicAmbiguity(family);
	
		assertEquals(mother.getGenotype().getGlString(), FAMILY_2A_MOTHER);
		assertEquals(father.getGenotype().getGlString(), FAMILY_2A_FATHER);
		assertEquals(child1.getGenotype().getGlString(), FAMILY_2A_CHILD_1);
		assertEquals(child2.getGenotype().getGlString(), FAMILY_2A_CHILD_2);
	}
	
	@Test
	public void testResolveGenotypicAmbiguity_2B() {
		Family family = new Family();
		Person mother = new Person();
		Person father = new Person();
		Person child1 = new Person();
		Person child2 = new Person();
		Person child3 = new Person();
		
		mother.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(AMBIG_FAMILY_2B_CHILD_3_MOTHER)));
		father.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(FAMILY_2B_FATHER)));
		child1.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(FAMILY_2B_CHILD_1)));
		child2.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(FAMILY_2B_CHILD_2)));
		child3.setGenotype(new Genotype(GLStringUtilities.compressGenotypicAmbiguity(AMBIG_FAMILY_2B_CHILD_3_MOTHER)));
		
		family.setFather(father);
		family.setMother(mother);
		
		family.addChild(child1);
		family.addChild(child2);
		family.addChild(child3);
		
		GLStringUtilities.resolveGenotypicAmbiguity(family);
	
		assertEquals(mother.getGenotype().getGlString(), FAMILY_2B_CHILD_3_MOTHER);
		assertEquals(father.getGenotype().getGlString(), FAMILY_2B_FATHER);
		assertEquals(child1.getGenotype().getGlString(), FAMILY_2B_CHILD_1);
		assertEquals(child2.getGenotype().getGlString(), FAMILY_2B_CHILD_2);
		assertEquals(child3.getGenotype().getGlString(), FAMILY_2B_CHILD_3_MOTHER);
	}
}
