package org.crossbridge.loadedjar;

public class LocalClassProvider implements IClassProvider {

	public String[] listFiles(String version) {
		
		if("1.0.0".equals(version)) {
			String[] files= {"C:\\wkspace\\test.loader.interface\\target\\test.loader.interface-0.0.1-SNAPSHOT.jar",
			"C:\\wkspace\\test.loader.targetclass1\\target\\test.loader.targetclass1-0.0.1-SNAPSHOT.jar"};
			return files;
		}else if("2.0.0".equals(version)) {
			String[] files= {"C:\\wkspace\\test.loader.interface\\target\\test.loader.interface-0.0.1-SNAPSHOT.jar",
			"C:\\wkspace\\test.loader.targetclass2\\target\\test.loader.targetclass2-0.0.1-SNAPSHOT.jar"};
			return files;
		}

		return null;
	}

}
