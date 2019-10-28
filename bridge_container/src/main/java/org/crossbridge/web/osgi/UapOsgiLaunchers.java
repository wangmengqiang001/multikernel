package org.crossbridge.web.osgi;

import java.util.ArrayList;

import org.crossbridge.loadedjar.IClassProvider;

public class UapOsgiLaunchers implements IClassProvider {
	
	public UapOsgiLaunchers(String basePath) {
		super();
		this.basePath = basePath;
	}
	String[][] launcherCls= {{"servletbridge.jar","crossbridge-1.0.0.0-SNAPSHOT.jar","own.bridge-0.0.1-SNAPSHOT.jar"},
			{"org.eclipse.equinox.servletbridge-1.3.200.jar","crossbridge-1.0.0.0-SNAPSHOT.jar","own.bridge.upgrade-0.0.1-SNAPSHOT.jar"}};

	String basePath;
	@Override
	public String[] listFiles(String version) {
		if("1.0.0".equalsIgnoreCase(version)) {
			return parsePath(basePath,launcherCls[0]);
		}else if("2.0.0".equalsIgnoreCase(version)){
			return parsePath(basePath,launcherCls[1]);
			
		}
		// TODO Auto-generated method stub
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
