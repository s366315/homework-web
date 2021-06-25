package org.hillel.web;

import org.hillel.web.config.JspConfig;
import org.hillel.web.config.RootConfig;
import org.hillel.web.config.TlConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;

public class Application implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootConfig = new AnnotationConfigWebApplicationContext();
        rootConfig.register(RootConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootConfig));

        AnnotationConfigWebApplicationContext jspAppContext = new AnnotationConfigWebApplicationContext();
        jspAppContext.register(JspConfig.class);

        ServletRegistration.Dynamic jspServlet =
                servletContext.addServlet("jspServlet", new DispatcherServlet(jspAppContext));
        jspServlet.addMapping("/welcome");

        AnnotationConfigWebApplicationContext tlAppContext = new AnnotationConfigWebApplicationContext();
        tlAppContext.register(TlConfig.class);

        ServletRegistration.Dynamic tlServlet =
                servletContext.addServlet("tlServlet", new DispatcherServlet(tlAppContext));
        tlServlet.addMapping("/tl/*");

        FilterRegistration.Dynamic charsetFilter = servletContext.addFilter("charsetFilter", new CharacterEncodingFilter(StandardCharsets.UTF_8.displayName()));
        charsetFilter.addMappingForUrlPatterns(null, true, "/*");
    }
}
