package org.crossbridge.web.osgi;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.crossbridge.loadedjar.ClassLauncher;

public class UapModuleContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
		event.getServletContext().log("应用系统正在停止中...");

		ServletContext ctx=event.getServletContext();
	
	
		String srinfo = ctx.getServletContextName();
		
		String launcher = ctx.getInitParameter("launcher_version");
		
		event.getServletContext().log("\nservInfo:" + srinfo);
		event.getServletContext().log("\nlauncher:" + launcher);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		ServletContext ctx=event.getServletContext();
		
			
		ctx.log("应用系统开始启动...");
		String srinfo = ctx.getServletContextName();
		
		String launcher = ctx.getInitParameter("launcher_version");
		
		event.getServletContext().log("\nservInfo:" + srinfo);
		event.getServletContext().log("\nlauncher:" + launcher);
		
		String userPath = System.getProperty("user.dir");
		ctx.log("user.dir:" + userPath);
		String basePath = ctx.getRealPath("/WEB-INF/launchers")+File.separator;
		ctx.log("basePath:" + basePath);
		
		
		
		
	
		UapOsgiLaunchers clsProviders = new UapOsgiLaunchers(basePath);
		ClassLauncher launchCls = new ClassLauncher(null,clsProviders);
		
		try {
			
			launchCls.loadJars(launcher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
	}

}
