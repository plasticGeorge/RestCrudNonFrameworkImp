package com.george;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private static Store instance = null;
    private static Map<Integer, Person> store;

    private Store() {
        store = new HashMap<>();
    }

    public static Store getInstance(){
        if(store == null)
            instance = new Store();
        return instance;
    }

    public boolean addPerson(Person newPerson){ //POST
        if(store.containsValue(newPerson))
            return false;
        store.put(newPerson.getId(), newPerson);
        return true;
    }

    public Person getPersonById(int id){ //GET
        return store.get(id);
    }

    public boolean updatePerson(int id, String name, Sex sex, JobTitle jobTitle){
        Person personToChange = store.get(id);
        if (personToChange != null) {
            if (name != null)
                personToChange.setName(name);
            if (sex != null)
                personToChange.setSex(sex);
            if (jobTitle != null)
                personToChange.setJobTitle(jobTitle);
            return true;
        }
        else
            return false;
    }

    public boolean deletePerson(int id){ //DELETE
        return store.remove(id) != null;
    }
}
