package com.mattdamon.site.handlebars;

import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.jknack.handlebars.io.URLTemplateSource;
import org.apache.commons.lang3.Validate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * 
 * IndexFileTemplateLoader
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class IndexFileTemplateLoader extends FileTemplateLoader {

	public IndexFileTemplateLoader(String basedir) {
		super(basedir);
	}

	public TemplateSource sourceAt(String uri) throws IOException {
		Validate.notEmpty(uri, "The uri is required.", new Object[0]);

		String location = resolve(normalize(uri));
		URL resource = getResource(location);
		if (resource != null) {
			return new URLTemplateSource(location, resource);
		}

		location = resolve(normalize(uri + "/index"));
		resource = getResource(location);
		if (resource == null) {
			throw new FileNotFoundException(location);
		}

		return new URLTemplateSource(location, resource);
	}
}