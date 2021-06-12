package com.george;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, LifecycleException {
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext(contextPath, docBase);
        tomcat.addServlet(contextPath, "PersonServlet", new PersonServlet());
        context.addServletMappingDecoded("/person", "PersonServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
