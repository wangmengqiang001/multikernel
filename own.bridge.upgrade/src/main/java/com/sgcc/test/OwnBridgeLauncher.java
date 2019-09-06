package com.sgcc.test;

import java.io.File;

import org.crossbridge.CrossBridgeLauncher;
import org.crossbridge.EnvFactory;

public class OwnBridgeLauncher extends CrossBridgeLauncher {
 
	@Override
	protected void initResourceBase() {
		// TODO Auto-generated method stub
		//super.initResourceBase();
		this.resourceBase = "/WEB-INF/repository/"; 
	}

	@Override
	protected void copyDeployedResources(File target) {
		// TODO Auto-generated method stub
		System.out.println("--copyDeployedRes from : "+ this.resourceBase);
		System.out.println("--copyDeployedRes to : "+ target.getAbsolutePath());
		

	}

	@Override
	protected void removeDeployedResources(File target) {
		// TODO Auto-generated method stub

	}

	@Override
	protected EnvFactory factoryFromSubClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
