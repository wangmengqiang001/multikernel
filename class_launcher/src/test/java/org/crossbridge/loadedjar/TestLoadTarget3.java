/**
 * 
 */
package org.crossbridge.loadedjar;

import static org.junit.Assert.*;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

/**
 * @author wmqiang
 *
 */
public class TestLoadTarget3 {



	//String filePath = "C:\\wkspace\\test_loader\\target\\test_loader-0.0.1-SNAPSHOT.jar";
	String classLoaded = "org.crossbridge.test_loader.TargetClass";
	ClassLoader localLoader = new URLClassLoader(new URL[] {},Thread.currentThread().getContextClassLoader());
	String version ="1.0.0";
	

	@Test	
	public void test_1()  {
		//fail("Not yet implemented");

		try {//class cannot be loaded before the jar is loaded
			Class<?> a = null;
			try {
				//a = ClassLoader.getSystemClassLoader().loadClass(classLoaded);
				a = Thread.currentThread().getContextClassLoader().loadClass(classLoaded);
			}catch(ClassNotFoundException ex) {
				assertTrue(true);
				System.out.println("sure, cannot load class "+classLoaded);
			}finally {

				assertNull(a);
			}


			ClassLauncher cl= new ClassLauncher();
			cl.setClassProvider(new LocalClassProvider());
			cl.initiaClassLoader(localLoader);
			
			cl.loadJars(version);
			//loadJarByDefineClass(filePath);
			
			//ClassLoader.getSystemClassLoader().loadClass(classLoaded);

			try {//after the jar is loaded, class can be loaded and create new instance now.
				a = localLoader.loadClass(classLoaded);
				//a= cl.initiaClassLoader().loadClass(classLoaded);
				//a = cl.getClass().getClassLoader().loadClass(classLoaded);
				//a = Thread.currentThread().getContextClassLoader().loadClass(classLoaded);
				Object x = a.newInstance();
				assertNotNull(a);

			}catch(ClassNotFoundException ex) {
				assertTrue(false);
			}finally {
				assertTrue(a != null );
				System.out.println("OK, success load class "+classLoaded);
				
				//System.gc();

			}



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_2()
	{
		try {
			Class<?> a = null;
			try {//
				//a = ClassLoader.getSystemClassLoader().loadClass(classLoaded);
				a = localLoader.loadClass(classLoaded);
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
