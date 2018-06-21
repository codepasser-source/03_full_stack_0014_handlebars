package com.mattdamon.site.handlebars;

import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import org.apache.commons.lang3.Validate;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * 
 * ServletAndFileTemplateLoader
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class ServletAndFileTemplateLoader extends ServletContextTemplateLoader {

	public static final String RESOURCE = "resource:";
	private FileTemplateLoader fileTemplateLoader;

	public ServletAndFileTemplateLoader(ServletContext servletContext,
			String prefix, String suffix, String baseDir) {
		super(servletContext, prefix, suffix);
		this.fileTemplateLoader = new IndexFileTemplateLoader(baseDir);
	}

	public TemplateSource sourceAt(String uri) throws IOException {
		Validate.notEmpty(uri, "The uri is required.", new Object[0]);
		System.out.println(uri);
		uri = this.normalize(uri);
		return uri.startsWith(RESOURCE) ? super.sourceAt(uri.substring(RESOURCE
				.length())) : this.fileTemplateLoader.sourceAt(uri);
	}

}
