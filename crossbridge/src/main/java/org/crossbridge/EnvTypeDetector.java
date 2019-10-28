package org.crossbridge;

public interface EnvTypeDetector {

	void detectEnv();

	boolean serverType();

	boolean appRunning();

	boolean appDeveloping();

}
