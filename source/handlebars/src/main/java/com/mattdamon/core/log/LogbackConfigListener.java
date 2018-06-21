package com.mattdamon.core.log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * LogbackConfigListener
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 17, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class LogbackConfigListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		LogbackWebConfigurer.initLogging(event.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent event) {
		LogbackWebConfigurer.shutdownLogging(event.getServletContext());
	}
}
