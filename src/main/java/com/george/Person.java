package com.george;

public class Person {
    private static int counter;

    private final int id;
    private final String name;
    private final Sex sex;
    private final JobTitle jobTitle;

    public Person(String name, Sex sex, JobTitle jobTitle) {
        this.name = name;
        this.sex = sex;
        this.jobTitle = jobTitle;
        this.id = counter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }
}
