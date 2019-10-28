package org.crossbridge;

public abstract class EnvFactory {
	
	protected EnvTypeDetector envTypeDetector;
	

	public abstract InitiaPropertiesProvider createInitialPropertiesProvider();

	public abstract ExtensionsDeployer createExtensionsDeployer();

	//detect the environment type by detector
	public void init(){
		if(envTypeDetector!= null) {
			envTypeDetector.detectEnv();
			
		}
		
	}

	/**
	 * @return the envTypeDetector
	 */
	protected EnvTypeDetector getEnvTypeDetector() {
		return envTypeDetector;
	}

	/**
	 * @param envTypeDetector the envTypeDetector to set
	 */
	protected void setEnvTypeDetector(EnvTypeDetector envTypeDetector) {
		this.envTypeDetector = envTypeDetector;
	};

}
