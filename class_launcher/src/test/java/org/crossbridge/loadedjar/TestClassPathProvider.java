package org.crossbridge.loadedjar;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TestClassPathProvider {

	@Test
	public void testListFiles() {
		ClassPathProvider provider = new ClassPathProvider("target");
		String[] files = provider.listFiles("");
		
		assertTrue(files.length > 0);
		System.out.println("files:" + files[0]);
		
	}
	
	@Test
	public void testListFiles_2() {
		ClassPathProvider provider = new ClassPathProvider("target"+File.separator);
		String[] files = provider.listFiles("");
		
		assertTrue(files.length > 0);
		System.out.println("files:" + files[0]);
		
	}

	@Test
	public void testNoFolder() {
		ClassPathProvider provider = new ClassPathProvider("xxx");
		String[] files = provider.listFiles("");
		assertNull(files);
//		assertTrue(files.length <= 0);
//		System.out.println("files:" + files[0]);
//		
	}
	
	@Test
	public void testNoFile() {
		ClassPathProvider provider = new ClassPathProvider("src");
		String[] files = provider.listFiles("");
		assertNotNull(files);
		assertTrue(files.length <= 0);
//		System.out.println("files:" + files[0]);
//		
	}
}
