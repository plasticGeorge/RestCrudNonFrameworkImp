package com.george;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "PersonServlet", urlPatterns = "/person")
public class PersonServlet extends HttpServlet {
    private final Store store;
    private final ObjectMapper mapper;

    public PersonServlet(){
        super();
        store = Store.getInstance();
        mapper = new ObjectMapper();
    }

    public PersonServlet(Store customStore, ObjectMapper customMapper){
        super();
        store = customStore;
        mapper = customMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in = req.getInputStream();
        ObjectNode objectNode = mapper.readValue(in, ObjectNode.class);
        String output;
        if(objectNode.has("id")){
            JsonNode idNode = objectNode.get("id");
            Person person = store.getPersonById(idNode.isInt() ? idNode.asInt() : -1);
            output = mapper.writeValueAsString(person);
        }
        else if(objectNode.has("ids")){
            JsonNode idsNode = objectNode.get("ids");
            List<Person> personList = new LinkedList<>();
            for(JsonNode idNode : idsNode){
                personList.add(store.getPersonById(idNode.asInt()));
            }
            output = mapper.writeValueAsString(personList);
        }
        else
            throw new IllegalArgumentException("Incorrect parameters for GET method: " + objectNode.toString());

        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.println(output);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in = req.getInputStream();
        Person person;
        try {
            person = mapper.readValue(in, Person.class);
        }
        catch (Exception e){
            e.initCause(new IllegalArgumentException("Incorrect input data cannot be converted to Person object..."));
            throw e;
        }
        boolean succ = store.addPerson(person);

        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.println(mapper.writeValueAsString("{successful: " + succ + "}"));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in = req.getInputStream();
        JsonParser parser = mapper.createParser(in);

        int idToChange = -1;
        String newName = null;
        Sex newSex = null;
        JobTitle newJobTitle = null;

        JsonToken jsonToken;
        while (!parser.isClosed()){
            jsonToken = parser.nextToken();
            if(JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();
                switch (fieldName){
                    case "id":
                        jsonToken = parser.nextToken();
                        idToChange = parser.getValueAsInt();
                        break;
                    case "name":
                        jsonToken = parser.nextToken();
                        newName = parser.getValueAsString();
                        break;
                    case "sex":
                        jsonToken = parser.nextToken();
                        newSex = parser.readValueAs(Sex.class);
                        break;
                    case "jobTitle":
                        jsonToken = parser.nextToken();
                        newJobTitle = parser.readValueAs(JobTitle.class);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown field in PUT method arguments: " + fieldName);
                }
            }
        }

        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.println(mapper.writeValueAsString("{successful: " + store.updatePerson(idToChange, newName, newSex, newJobTitle) + "}"));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream in = req.getInputStream();
        ObjectNode objectNode = mapper.readValue(in, ObjectNode.class);
        StringBuilder output = new StringBuilder();
        if(objectNode.has("id")){
            JsonNode idNode = objectNode.get("id");
            int personId = idNode.isInt() ? idNode.asInt() : -1;
            output.append("{successful: {").append(personId).append(":").append(store.deletePerson(personId)).append("}}");
        }
        else if(objectNode.has("ids")){
            JsonNode idsNode = objectNode.get("ids");
            List<Person> personList = new LinkedList<>();
            output.append("{successful: {");
            for(JsonNode idNode : idsNode){
                int personId = idNode.isInt() ? idNode.asInt() : -1;
                output.append(personId).append(":").append(store.deletePerson(personId)).append(",");
            }
            output.append("}}");
        }
        else
            throw new IllegalArgumentException("Incorrect parameters for GET method: " + objectNode.toString());

        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.println(mapper.writeValueAsString(output.toString()));
    }
}
