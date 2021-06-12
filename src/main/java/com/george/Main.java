package com.george;

import org.apache.catalina.LifecycleException;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat.init("/", Paths.get(".").toAbsolutePath(), new PersonServlet());
    }
}
