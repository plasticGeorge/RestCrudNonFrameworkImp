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
        return store.put(newPerson.getId(), newPerson) == null;
    }

    public Person getPerson(int id){ //GET
        return store.get(id);
    }

    public boolean deletePerson(int id){ //DELETE
        return store.remove(id) != null;
    }
}
