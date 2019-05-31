package org.dash.family;

import static org.junit.Assert.assertTrue;

import org.dash.family.util.AmbiguityUtilities;
import org.junit.Test;

public class AmbiguityUtilitiesTest {
	
	private static final String DR15_AMBIGUITY = "HLA-DRB1*15:01:01:01/HLA-DRB1*15:01:01:02/HLA-DRB1*15:01:01:03";
	
	private static final String DRB1_1501 = "HLA-DRB1*15:01:01:01";
	
	private static final String DRB3_0202 = "HLA-DRB3*02:02:01:02v1";
	private static final String A_0101 = "HLA-A*01:01:01:01";

	@Test
	public void testAlleleAmbiguities() {
		AmbiguityUtilities ambiguityUtils = AmbiguityUtilities.getInstance();
		assertTrue(DR15_AMBIGUITY.equals(ambiguityUtils.convertToAmbiguityString(DRB1_1501)));
		
		assertTrue(DRB3_0202.equals(ambiguityUtils.convertToAmbiguityString(DRB3_0202)));
		assertTrue(A_0101.equals(ambiguityUtils.convertToAmbiguityString(A_0101)));
	}

}
