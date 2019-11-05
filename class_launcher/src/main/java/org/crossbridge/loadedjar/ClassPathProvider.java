package org.crossbridge.loadedjar;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class ClassPathProvider implements IClassProvider {
	
	private String classPath;
	
	

	public ClassPathProvider(String classPath) {
		super();
		this.classPath = classPath;
	}



	public String[] listFiles(String version) {
		// TODO Auto-generated method stub
		//list all jars in folder: classPath
		File file=new File(classPath);
		String szbase = file.getAbsolutePath();
		if(!szbase.endsWith(File.separator)) {
			szbase += File.separator;
			
		}
		System.out.println("szbase: "+szbase);
		//FilenameFilter filter;
		String[] files =file.list(new FilenameFilter(){

			public boolean accept(File dir, String name) {
				
				if(name.endsWith(".jar"))
					return true;
				else 
					return false;
			}
			
		});

		if(files!=null)		
			return parsePath(szbase,files);
		else
			return null;
	}
	
	private String[] parsePath(String base, String[] strings) {
		ArrayList<String> aryFiles=new ArrayList<String>();
		if(base !=null && !base.isEmpty()){
			for(String file:strings) {
				aryFiles.add(base+file);
			}
		}
		return aryFiles.toArray(new String[aryFiles.size()]);
		
	}

}
