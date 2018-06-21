package com.mattdamon.site.handlebars;

import com.google.common.base.Preconditions;
import org.springframework.web.servlet.view.AbstractTemplateView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 * HandlebarsView
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class HandlebarsView extends AbstractTemplateView {

	private HandlebarEngine handlebarEngine;

	private String path;

	void init(HandlebarEngine handlebarEngine, String path) {
		Preconditions.checkNotNull(handlebarEngine,
				"A handlebarEngine is required.");
		Preconditions.checkNotNull(path, "A path is required.");
		this.handlebarEngine = handlebarEngine;
		this.path = path;
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.getWriter().write(
				this.handlebarEngine.execPath(this.path, model));
	}

}
