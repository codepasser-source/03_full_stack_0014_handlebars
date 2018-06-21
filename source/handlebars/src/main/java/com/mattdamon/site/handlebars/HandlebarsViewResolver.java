package com.mattdamon.site.handlebars;

import java.io.IOException;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.github.jknack.handlebars.io.ServletContextTemplateLoader;
import com.google.common.base.Preconditions;

/**
 * 
 * HandlebarsViewResolver
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class HandlebarsViewResolver extends AbstractTemplateViewResolver {

	public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";

	private HandlebarEngine handlebarEngine;

	@Deprecated
	public HandlebarsViewResolver() {
		throw new UnsupportedOperationException("operation not supported");
	}

	/**
	 * 构造器
	 * 
	 * @param handlebarEngine
	 */
	public HandlebarsViewResolver(HandlebarEngine handlebarEngine) {
		Preconditions.checkNotNull(handlebarEngine,
				"The handlebarEngine object is required.");
		this.handlebarEngine = handlebarEngine;
		this.setViewClass(HandlebarsView.class);
		this.setContentType(DEFAULT_CONTENT_TYPE);
	}

	@Override
	@Deprecated
	public void setPrefix(String prefix) {
		throw new UnsupportedOperationException("Use "
				+ ServletContextTemplateLoader.class.getName() + "#setPrefix");
	}

	@Override
	@Deprecated
	public void setSuffix(String suffix) {
		throw new UnsupportedOperationException("Use "
				+ ServletContextTemplateLoader.class.getName() + "#setSuffix");
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		return this.configure((HandlebarsView) super.buildView(viewName));
	}

	protected AbstractUrlBasedView configure(HandlebarsView view)
			throws IOException {
		String url = view.getUrl();
		if (!url.startsWith("/")) {
			url = "/" + url;
		}
		view.init(this.handlebarEngine, url);
		return view;
	}

	@Override
	protected Class<?> requiredViewClass() {
		return HandlebarsView.class;
	}

}
