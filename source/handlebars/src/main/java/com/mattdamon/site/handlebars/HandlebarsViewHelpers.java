package com.mattdamon.site.handlebars;

import java.text.DecimalFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;

/**
 * 
 * HandlebarHelpers
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
@Component
public class HandlebarsViewHelpers {

	@Autowired
	private HandlebarEngine handlebarEngine;

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(
			"#.###");

	private static final Splitter splitter = Splitter.on(",");

	@PostConstruct
	public void init() {
		// this.handlebarEngine.registerHelper("json", new Helper() {
		// public CharSequence apply(Object context, Options options)
		// throws IOException {
		// // 改为使用alibaba fastJson
		// return JSON.toJSONString(context);
		// }
		// });
	}
}
