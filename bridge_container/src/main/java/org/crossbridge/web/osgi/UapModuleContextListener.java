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
	
	private boolean deleteDirectory(File directory) {
		boolean bDel=false;
		//if(directory.exists()) {
			if (directory.isDirectory()) {
				File[] files = directory.listFiles();
				if(files.length <=0) {
					bDel = directory.delete();
					System.out.println("del file:" + bDel +"  "+directory.getPath());
				}else {
					for (int i = 0; i < files.length; i++) {
	
						bDel =deleteDirectory(files[i]);
	
	
						//boolean rt= files[i].delete();
						System.out.println("del file:" + bDel +"  "+files[i].getPath());
					}
					if(directory.list().length == 0) {
						bDel = directory.delete();
						System.out.println("del file:" + bDel +"  "+directory.getPath());
					}
				}
			}else {
				bDel = directory.delete();
				System.out.println("del file:" + bDel +"  "+directory.getPath());
			}
//		}else {
//			System.out.println("file cannot be accessed:" + "  "+directory.getPath());
//		}

		return bDel;
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
		String basePath = ctx.getRealPath("/");
		//String basePath = ctx.getRealPath("/");//
		ctx.log("basePath:" + basePath); 
		
		clearOsgiFolder(ctx);	
		
	
		UapOsgiLaunchers clsProviders = new UapOsgiLaunchers(basePath+"WEB-INF"+File.separator+"launchers"+File.separator);
		ClassLauncher launchCls = new ClassLauncher(null,clsProviders);
		
		try {
			
			launchCls.loadJars(launcher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		
		
	}

	private void clearOsgiFolder(ServletContext ctx) {
		File servletTemp = (File) ctx
				.getAttribute("javax.servlet.context.tempdir");
		
		ctx.log("servletTemp:" + servletTemp.getPath());
		
		this.deleteDirectory(new File(servletTemp.getPath()+File.separator+"eclipse"));
	}

}
