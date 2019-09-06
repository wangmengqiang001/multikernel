package org.crossbridge.loadedjar;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLoadTarget2 {
	String filePath = "C:\\wkspace\\test_loader\\target\\test_loader-0.0.1-SNAPSHOT.jar";
	String classLoaded = "org.crossbridge.test_loader.TargetClass";
	@Test
	public void test_2()
	{
		try {
			Class<?> a = null;
			try {
				//a = ClassLoader.getSystemClassLoader().loadClass(classLoaded);
				a = Thread.currentThread().getContextClassLoader().loadClass(classLoaded);
			}catch(ClassNotFoundException ex) {
				//Thread.currentThread().getContextClassLoader()
				assertTrue(true);
				System.out.println("sure, cannot load class "+classLoaded);
			}finally {
				
				assertNull(a);
			}

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
