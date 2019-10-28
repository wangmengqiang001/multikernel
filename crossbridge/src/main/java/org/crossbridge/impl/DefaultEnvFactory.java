package org.crossbridge.impl;

import org.crossbridge.EnvFactory;
import org.crossbridge.EnvTypeDetector;
import org.crossbridge.ExtensionsDeployer;
import org.crossbridge.InitiaPropertiesProvider;

public class DefaultEnvFactory extends EnvFactory {

	public InitiaPropertiesProvider createInitialPropertiesProvider() {
		InitiaPropertiesProvider provider = null;
		if(this.envTypeDetector == null) {
			provider = null; //throw exception
			
		}else if(this.envTypeDetector.serverType()) {
			//provider = new xxx 
		}else if(this.envTypeDetector.appRunning()) {
			//provider = new xxx
		}else if(this.envTypeDetector.appDeveloping()) {
			//provider = new xxx
		}
				
		// TODO Auto-generated method stub
		return provider;
	}

	public ExtensionsDeployer createExtensionsDeployer() {
		ExtensionsDeployer deployor = null;
		if(this.envTypeDetector == null) {
			deployor = null; //throw exception
			
		}else if(this.envTypeDetector.serverType()) {
			//provider = new xxx 
		}else if(this.envTypeDetector.appRunning()) {
			//provider = new xxx
		}else if(this.envTypeDetector.appDeveloping()) {
			//provider = new xxx
		}
				
		// TODO Auto-generated method stub
		return deployor;
	}

	public DefaultEnvFactory() {
		super();
		// TODO Auto-generated constructor stub
		EnvTypeDetector detector = new DefaultEnvTypeDetector();
		setEnvTypeDetector(detector);
	}

	
}
