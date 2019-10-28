package org.crossbridge;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.equinox.servletbridge.FrameworkLauncher;


public abstract  class CrossBridgeLauncher extends FrameworkLauncher {
	

	private final String environmentFactory = "environmentFactory";
	protected InitiaPropertiesProvider initialPropertiesProvider;
	private boolean deployExtensions;
	private ExtensionsDeployer extensionsDeployer;

	
	//change the private memeber platformDirectory by reflection 
	//In fact, it will be useless if both deploy and undeploy are overrided
	protected void setPlatformDirectory(File platformDirectory) {
		final String fileldName="platformDirectory";
		
		//this.platformDirectory = platformDirectory;
		// 得到私有字段
			try {
				Field privateField = FrameworkLauncher.class
						.getDeclaredField(fileldName);
 
				// 通過反射設置私有對象可以訪問
				privateField.setAccessible(true);				
				 
				// 將私有對象設置新的值
				privateField.set(this, platformDirectory);				
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	

	@Override
	public synchronized void undeploy() {
		
		//super.undeploy();
		File target = this.getPlatformDirectory();
		removeDeployedResources(target);
	}




	/* (non-Javadoc)
	 * @see org.eclipse.equinox.servletbridge.FrameworkLauncher#init()
	 * 
	 * 
	 * 
	 */
	@Override
	public void init() {
		// rertrive envFactory via configuration or override function of sub-class
		EnvFactory envFactory;
		
		envFactory = buildEnvFactory();
		if(envFactory != null) {
			this.initialPropertiesProvider = envFactory.createInitialPropertiesProvider();
			this.extensionsDeployer = envFactory.createExtensionsDeployer();
		}
		super.init();
	}



	private EnvFactory buildEnvFactory() {
		EnvFactory envFactory;
		// call the abstract method to new instance of factory class to assembly the components 
		//judge the environment type(server mode, application running, application developing)
		envFactory = factoryInjectByConfig();
		if( envFactory == null) {	
			envFactory = factoryFromSubClass();
		}
		return envFactory;
	}
	
	



	@SuppressWarnings("rawtypes")
	private EnvFactory factoryInjectByConfig() {
		//inject class by configuration
		EnvFactory envFactory = null;
		String factoryClassName = this.config.getInitParameter(environmentFactory);
		if(factoryClassName != null) {
			try {
				Class envFactoryClass = this.getClass().getClassLoader().loadClass(factoryClassName);
				envFactory = (EnvFactory) envFactoryClass.newInstance();
				envFactory.init(); //environment info
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return envFactory;
	}



	//@Override
	public synchronized void deploy() {
		
		System.out.println("--call deploy() from child bridge");
		if(initialPropertiesProvider != null) {
			File target =initialPropertiesProvider.getPlatformDirectory();
			
			if( deployExtensions && 
					extensionsDeployer != null) {//needDeployExtensions
					extensionsDeployer.deployExt(target);				
			}
		

			copyDeployedResources(target);
			//finally, to store it to field of super class
			this.setPlatformDirectory(target);
			
		}else {	
		
			//show error message
			super.deploy();
		}
		
	}

	//protected abstract void deployExt(File target);
	protected abstract void copyDeployedResources(File target);
	protected abstract  void removeDeployedResources(File target);	
	protected abstract EnvFactory factoryFromSubClass();


	//@Override
	protected Map buildInitialPropertyMap() {
		// TODO Auto-generated method stub
		System.out.println("--call buildInitiaPropertyMap from child bridge");
		
		if( initialPropertiesProvider != null ) {
			return initialPropertiesProvider.buildPropertiesMap();
		} else 
			return super.buildInitialPropertyMap();
	}

	@Override
	protected String[] buildCommandLineArguments() {
		// TODO Auto-generated method stub
		System.out.println("--call buildCommandLineArguments from child bridge");
		if( initialPropertiesProvider != null ) {
			return initialPropertiesProvider.buildCommandLineArguments();
		} else 
			return super.buildCommandLineArguments();
	}

	protected boolean isDeployExtensions() {
		return deployExtensions;
	}



	protected void setDeployExtensions(boolean deployExtensions) {
		this.deployExtensions = deployExtensions;
	}



	public void setInitialPropertiesProvider(InitiaPropertiesProvider initialPropertiesProvider) {
		this.initialPropertiesProvider = initialPropertiesProvider;
	}



	public void setExtensionsDeployer(ExtensionsDeployer extensionsDeployer) {
		this.extensionsDeployer = extensionsDeployer;
	}

	
	

}
