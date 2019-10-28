package org.crossbridge;

import java.io.File;
import java.util.Map;

public interface InitiaPropertiesProvider {

	Map buildPropertiesMap();
	File getPlatformDirectory();
	String[] buildCommandLineArguments();

}
