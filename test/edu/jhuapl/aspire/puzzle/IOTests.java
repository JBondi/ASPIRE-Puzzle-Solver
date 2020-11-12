package edu.jhuapl.aspire.puzzle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IOTests<MyAssertTrueTest> {

	@Test
	void test() throws IOException {
		InputStream in = new FileInputStream("README.md");
		Assertions.assertTrue(true);
		String s = IOUtils.toString(in, "UTF-8");
	}
}
