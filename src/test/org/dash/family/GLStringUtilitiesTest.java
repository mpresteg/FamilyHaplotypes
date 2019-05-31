package org.dash.family;

import static org.junit.Assert.assertTrue;

import org.dash.family.util.GLStringUtilities;
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
	
	//RemoveNotation variables
	private static final String DPB1_0402_105_CLEAN = "HLA-DPB1*04:02:01:02/HLA-DPB1*105:01/HLA-DPB1*105:01";
	private static final String DPB1_0402_105_BAD = DPB1_0402_105_CLEAN + "i1";
	
	private static final String DPB1_BAD = "HLA-DPB1*13:01:01/HLA-DPB1*13:01:01e1/HLA-DPB1*133:01/HLA-DPB1*04:02:01:02/HLA-DPB1*105:01/HLA-DPB1*105:01i1";
	private static final String DPB1_CLEAN = "HLA-DPB1*13:01:01/HLA-DPB1*13:01:01/HLA-DPB1*133:01/HLA-DPB1*04:02:01:02/HLA-DPB1*105:01/HLA-DPB1*105:01";

	@Test
	// TODO: Implement remaining test cases
	public void testAlleleAmbiguities() {
		GLStringUtilities ambiguityUtils = GLStringUtilities.getInstance();
		assertTrue(DRB1_1501_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DRB1_1501)));
		assertTrue(A_0101.equals(ambiguityUtils.convertToAmbiguityString(A_0101)));
		assertTrue(DQA1_0104_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DQA1_0104)));
		assertTrue(DQA1_0104_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DQA1_0104_PARTIAL_AMBIGUITY)));
		assertTrue(DRB1_0301_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DRB1_0301)));
		assertTrue(DPB1_0401_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DPB1_0401_AMBIGUITY)));
	}
	
	@Test
	// TODO: Implement remaining test cases
	public void testRemoveNotation() {
		assertTrue(DPB1_0402_105_CLEAN.equals(GLStringUtilities.getInstance().removeNotation(DPB1_0402_105_BAD)));
		assertTrue(DPB1_CLEAN.equals(GLStringUtilities.getInstance().removeNotation(DPB1_BAD)));
	}

}
