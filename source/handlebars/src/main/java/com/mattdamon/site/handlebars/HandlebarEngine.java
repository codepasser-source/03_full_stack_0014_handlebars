package com.mattdamon.site.handlebars;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mattdamon.core.exception.TemplateCompileException;
import com.mattdamon.core.log.CoreLogger;
import com.mattdamon.core.log.CoreLoggerFactory;

/**
 * 
 * HandlebarEngine
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
@Component
public class HandlebarEngine {

	private Handlebars handlebars;
	private final Map<String, String> hrefProps = Maps.newHashMap();
	private final LoadingCache<String, Optional<Template>> caches;

	private static final CoreLogger logger = CoreLoggerFactory
			.getLogger(HandlebarEngine.class);

	@Autowired
	public HandlebarEngine(
			@Value("#{configProperties['system.envrionment']}") String mode,
			@Value("#{configProperties['web.site.assetsHome']}") String assetsHome,
			@Value("#{configProperties['web.base.domain']}") String base,
			@Value("#{configProperties['web.site.href']}") String href,
			ServletContext servletContext) {
		ServletAndFileTemplateLoader templateLoader = new ServletAndFileTemplateLoader(
				servletContext, "/views", ".hbs", assetsHome);
		this.handlebars = new Handlebars(templateLoader);
		this.caches = this.initCache(Objects.equal(mode, "prod"));
		this.hrefProps.put("base", base);
		this.hrefProps.put("href", href);

	}

	private LoadingCache<String, Optional<Template>> initCache(boolean cachable) {
		return cachable ? CacheBuilder.newBuilder()
				.expireAfterWrite(5L, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Optional<Template>>() {
					public Optional<Template> load(String path)
							throws Exception {
						Template t = null;

						try {
							t = HandlebarEngine.this.handlebars.compile(path);
						} catch (Exception e) {
							logger.systemLog("failed to compile template(path={"
									+ path
									+ "}), cause:{"
									+ e.getMessage()
									+ "}");
						}

						return Optional.fromNullable(t);
					}
				})
				: null;
	}

	public <T> void registerHelper(String name, Helper<T> helper) {
		this.handlebars.registerHelper(name, helper);
	}

	@SuppressWarnings("rawtypes")
	public String execPath(String path, Map<String, Object> templateData) {

		try {
			if (templateData == null) {
				templateData = Maps.newHashMap();
			}
			Template template;
			if (this.caches == null) {
				template = this.handlebars.compile(path);
			} else {
				template = (Template) ((Optional) this.caches
						.getUnchecked(path)).orNull();
			}
			return template.apply(templateData);
		} catch (Exception e) {
			logger.systemLog("failed to execute handlebars\' template(path={"
					+ path + "}),cause:{" + Throwables.getStackTraceAsString(e)
					+ "}");
			throw new TemplateCompileException(
					"failed to execute handlebars\' template(path={" + path
							+ "}),cause:{"
							+ Throwables.getStackTraceAsString(e) + "}",
					e.getCause());
		}

	}

}
