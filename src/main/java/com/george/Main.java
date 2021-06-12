package com.george;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, LifecycleException {
        String contextPath = "/";
        String appsBase = ".";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        Context context = tomcat.addContext(contextPath, appsBase);
        Tomcat.addServlet(context, "PersonServlet", new PersonServlet());

        context.addServletMappingDecoded("/*", "PersonServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
