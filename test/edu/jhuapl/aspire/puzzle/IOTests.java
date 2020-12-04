package edu.jhuapl.aspire.puzzle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IOTests<MyAssertTrueTest> {

	@Test
	void test() throws IOException {
		InputStream in = new FileInputStream("README.md");
		Assertions.assertTrue(true);
		String s = IOUtils.toString(in, "UTF-8");
		Assertions.assertFalse(s.isEmpty());
	}
	
	@Test
	void fileRead() throws IOException{
		FileInputStream stream = new FileInputStream("OutFile.txt");
		Scanner scanner = new java.util.Scanner(stream);
		System.out.println(scanner.nextLine());
	}
	
	@Test
	void fileWrite() throws IOException {
		FileOutputStream stream = new FileOutputStream("OutFile.txt");
		PrintWriter writer = new PrintWriter(stream);
		writer.println("Hannah lives in a big red house.");
		writer.flush();
		writer.close();
	}
	
}
