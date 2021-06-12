package com.george;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "PersonServlet", urlPatterns = "/person")
public class PersonServlet extends HttpServlet {
    private final Store store = Store.getInstance();
    private final ObjectMapper mapper = new ObjectMapper();

    public PersonServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader in = req.getReader();
        StringBuilder builder = new StringBuilder();

        while (in.ready()){
            builder.append(in.readLine());
        }

        int person_id = mapper.readValue(builder.toString(), Integer.class);
        Person person = store.getPerson(person_id);
        String responseString = mapper.writeValueAsString(person);

        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.println(responseString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader in = req.getReader();
        StringBuilder builder = new StringBuilder();

        while (in.ready()){
            builder.append(in.readLine());
        }

        Person person = mapper.readValue(builder.toString(), Person.class);
        store.addPerson(person);

        super.doPut(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader in = req.getReader();
        StringBuilder builder = new StringBuilder();

        while (in.ready()){
            builder.append(in.readLine());
        }

        Person person = mapper.readValue(builder.toString(), Person.class);
        store.addPerson(person);

        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader in = req.getReader();
        StringBuilder builder = new StringBuilder();

        while (in.ready()){
            builder.append(in.readLine());
        }

        int person_id = mapper.readValue(builder.toString(), Integer.class);
        store.deletePerson(person_id);

        super.doPut(req, resp);
    }
}
