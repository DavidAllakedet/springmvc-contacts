package com.contact.app;

import com.contact.app.config.SpringWebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() { return new Class[0]; }
    @Override
    protected Class<?>[] getServletConfigClasses() { return new Class[]{SpringWebConfig.class}; }
    @Override
    protected String[] getServletMappings() { return new String[]{"/"}; }
}
