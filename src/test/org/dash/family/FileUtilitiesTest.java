package org.dash.family;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;

import org.dash.family.util.FileUtilities;
import org.junit.Test;

public class FileUtilitiesTest {
	
	public static final String CHILD_GL_STRING = "HLA-A*24:02:01:01+HLA-A*01:01:01:01^HLA-B*51:09:01+HLA-B*37:01:01^HLA-C*01:02:01+HLA-C*06:02:01:01^HLA-DPA1*01:03:01:02+HLA-DPA1*01:03:01:04^HLA-DPB1*04:01:01:01+HLA-DPB1*02:01:02^HLA-DQA1*05:05:01:02+HLA-DQA1*01:02:01:03^HLA-DQB1*03:01:01:03+HLA-DQB1*06:02:01^HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:01|HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:02|HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:03^HLA-DRB3*02:02:01:02^HLA-DRB5*01:01:01";
	public static final String ETHNICITY = "EUR";

	@Test
	public void testParseFileInputs() {
		BufferedReader reader = FileUtilities.readFile("testInput.csv");
		
		Family family = FileUtilities.parseFileInputs(reader);
		
		assertNotNull(family);
		assertTrue(family.getChildren().size() == 1);
		assertTrue(family.getMother() != null);
		assertTrue(family.getFather() != null);
		
		Person child = family.getChildren().get(0);
		assertTrue(child != null);
		assertTrue(child.getGlstring().equals(CHILD_GL_STRING));
		assertTrue(child.getEthnicity().equals(ETHNICITY));
	}

}
