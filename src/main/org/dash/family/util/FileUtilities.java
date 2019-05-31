package org.dash.family.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.dash.family.Family;
import org.dash.family.Person;

public class FileUtilities {
	private static final String FILE_DELIMITER_REGEX = "[\t,]";
	private static final int LAB_CODE_POS = 0;
	private static final int FAMILY_ID_POS = 1;
	private static final int SAMPLE_ID_POS = 2;
	private static final int RELATION_POS = 3;
	private static final int GL_STRING_POS = 4;
	private static final int ETHNICITY_POS = 5;
	
	private static final String MOTHER = "mother";
	private static final String FATHER = "father";

	public static BufferedReader readFile(String filename) {
		InputStream stream = FileUtilities.class.getClassLoader()
				.getResourceAsStream(filename);
		if (stream == null) {
			try {
				stream = new FileInputStream(filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		return reader;
	}
	
	public static Family parseFileInputs(BufferedReader reader) {
		Family family = new Family();
		String line;
		String[] parts;
		int lineCounter = 0;
		
		try {
			while ((line = reader.readLine()) != null) {
				lineCounter++;
				if (lineCounter == 1) continue;
				
				parts = line.split(FILE_DELIMITER_REGEX);
				
				family.setFamilyId(parts[FAMILY_ID_POS]);
				
				Person person = new Person();
				person.setLabCode(parts[LAB_CODE_POS]);
				person.setSampleId(parts[SAMPLE_ID_POS]);
				person.setGlstring(parts[GL_STRING_POS]);
				person.setEthnicity(parts[ETHNICITY_POS]);
				
				switch (parts[RELATION_POS]) {
					case MOTHER:
						family.setMother(person);
						break;
					case FATHER:
						family.setFather(person);
						break;
					default:
						family.addChild(person);
						break;
						
				}		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return family;
	}
}
