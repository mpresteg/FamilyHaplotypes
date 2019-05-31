package org.dash.family;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;

import org.dash.family.util.FileUtilities;
import org.junit.Test;

public class FileUtilitiesTest {
	
	private static final String CHILD_GL_STRING = "HLA-A*24:02:01:01+HLA-A*01:01:01:01^HLA-B*51:09:01+HLA-B*37:01:01^HLA-C*01:02:01+HLA-C*06:02:01:01^HLA-DPA1*01:03:01:02+HLA-DPA1*01:03:01:04^HLA-DPB1*04:01:01:01+HLA-DPB1*02:01:02^HLA-DQA1*05:05:01:02+HLA-DQA1*01:02:01:03^HLA-DQB1*03:01:01:03+HLA-DQB1*06:02:01^HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:01|HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:02|HLA-DRB1*11:01:01:01+HLA-DRB1*15:01:01:03^HLA-DRB3*02:02:01:02^HLA-DRB5*01:01:01";
	private static final String CHILD_SAMPLE_ID = "189B";
	private static final String MOTHER_GL_STRING = "HLA-A*01:01:01:01+HLA-A*23:01:01^HLA-B*37:01:01+HLA-B*14:02:01:01^HLA-C*06:02:01:01+HLA-C*08:02:01:01^HLA-DPA1*01:03:01:04^HLA-DPB1*04:01:01:01^HLA-DQA1*01:02:01:03+HLA-DQA1*01:01:02^HLA-DQB1*06:02:01+HLA-DQB1*05:01:01:01^HLA-DRB1*15:01:01:01+HLA-DRB1*01:02:01|HLA-DRB1*15:01:01:02+HLA-DRB1*01:02:01|HLA-DRB1*15:01:01:03+HLA-DRB1*01:02:01^HLA-DRB5*01:01:01";
	private static final String MOTHER_SAMPLE_ID = "189D";
	private static final String FATHER_GL_STRING = "HLA-A*24:02:01:01+HLA-A*01:01:01:01^HLA-B*51:09:01+HLA-B*58:01:01:01^HLA-C*01:02:01+HLA-C*07:18^HLA-DPA1*02:01:01:01+HLA-DPA1*01:03:01:02^HLA-DPB1*17:01+HLA-DPB1*02:01:02^HLA-DQA1*05:05:01:02+HLA-DQA1*02:01:01:01^HLA-DQB1*03:01:01:03+HLA-DQB1*02:02:01:01^HLA-DRB1*11:01:01:01+HLA-DRB1*07:01:01:01^HLA-DRB3*02:02:01:02^HLA-DRB4*01:01:01:01";
	private static final String FATHER_SAMPLE_ID = "189C";
	private static final String ETHNICITY = "EUR";
	private static final String LAB_CODE = "labcode";
	private static final String FAMILY_ID = "FAM0222";

	@Test
	public void testParseFileInputs() {
		BufferedReader reader = FileUtilities.readFile("testInput.csv");
		
		Family family = FileUtilities.parseFileInputs(reader);
		
		Person mother = family.getMother();
		Person father = family.getFather();
		
		assertNotNull(family);
		assertTrue(family.getChildren().size() == 1);
		assertTrue(mother != null);
		assertTrue(father != null);
		
		Person child = family.getChildren().get(0);
		assertTrue(child != null);
		assertTrue(child.getGlstring().equals(CHILD_GL_STRING));
		assertTrue(child.getSampleId().equals(CHILD_SAMPLE_ID));
		assertTrue(child.getEthnicity().equals(ETHNICITY));
		assertTrue(child.getLabCode().equals(LAB_CODE));
		assertTrue(family.getFamilyId().equals(FAMILY_ID));
		assertTrue(mother.getGlstring().equals(MOTHER_GL_STRING));
		assertTrue(mother.getSampleId().equals(MOTHER_SAMPLE_ID));
		assertTrue(father.getGlstring().equals(FATHER_GL_STRING));
		assertTrue(father.getSampleId().equals(FATHER_SAMPLE_ID));
	}

}
