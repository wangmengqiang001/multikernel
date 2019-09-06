package org.crossbridge.loadedjar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLauncher {
	
	public ClassLauncher() {
		super();
		
	}

	/**
	 * @param classProvider the classProvider to set
	 */
	protected void setClassProvider(IClassProvider classProvider) {
		this.classProvider = classProvider;
	}

	private ClassLoader classLoader=null;
	public ClassLauncher(ClassLoader classLoader, IClassProvider classProvider) {
		super();
		this.classLoader = classLoader;
		this.classProvider = classProvider;
	}

	private IClassProvider classProvider=null;
	
	protected synchronized ClassLoader initiaClassLoader(ClassLoader cl) {
		if(classLoader == null) {
			if(cl == null) {
				//classLoader = new URLClassLoader(new URL[] {},this.getClass().getClassLoader());
				classLoader = Thread.currentThread().getContextClassLoader();
			}else {
				classLoader = cl;
			}
		}
		return classLoader;
	}
	
	public void loadJarByDefineClass(String file) throws Exception {

		JarFile jar = null;
		InputStream is = null;
		Method mdclr = null;
		try{
			jar = new JarFile(file);
			Enumeration<JarEntry> entries = jar.entries();
			// ��ǰ��classloader
//			ClassLoader cl = (ClassLoader) Thread.currentThread()
//					.getContextClassLoader();
			// ��ȡClassLoader��defineClass������������protected���͵ģ���Ҫ�ſ�����Ȩ��
			ClassLoader cl = this.initiaClassLoader(null);
		    mdclr = java.lang.ClassLoader.class
					.getDeclaredMethod("defineClass", String.class, byte[].class,
							int.class, int.class);
		    mdclr.setAccessible(true);

			String name;
			// ����JAR��ÿһ���඼����һ��
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().endsWith(".class")) {
					name = entry.getName();
					name = name.substring(0, name.length() - 6);
					name = name.replaceAll("/", ".");

					
					// jar��ĳ������ֽ���
					is = jar.getInputStream(entry);
					ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
					// ��ȡ����ֽڣ���defineClass����
					byte[] packData = new byte[2048];
					int readLen = 0;
					while (-1 != (readLen = is.read(packData))) {
						dataOut.write(packData, 0, readLen);
					}
					if (dataOut.size() <= 0) {
						throw new ClassNotFoundException(name);
					}

					byte[] classFile = dataOut.toByteArray();
					// ������
					mdclr.invoke(cl, name, classFile, 0, classFile.length);
				}
			}
		}finally{
			if(is != null){
				is.close();
			}
			if(jar != null){
				jar.close();
			}
			if(mdclr != null) {
				 mdclr.setAccessible(false);
			}
		}
		
	}
	
	public  void loadJarsByDefineClass(String[] files) throws Exception{
		for(String jarfile: files) {
			loadJarByDefineClass(jarfile);
		}
		
	}
	public void loadJars(String version) throws Exception {
		//get the jar file list via version
		String[] fileList = listFiles(version);
		this.loadJarsByDefineClass(fileList);
		
	}

	/* (non-Javadoc)
	 * @see org.crossbridge.loadedjar.IClassProvider#listFiles(java.lang.String)
	 */
	public String[] listFiles(String version) {
		if(classProvider != null) {
			return classProvider.listFiles(version);
		}
		
			
		return null;
	}

}
