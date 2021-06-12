package com.george;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Person {
    private static int counter;

    private final int id;
    private String name;
    private Sex sex;
    private JobTitle jobTitle;

    @JsonCreator
    public Person(@JsonProperty("name") String name,
                  @JsonProperty("sex") Sex sex,
                  @JsonProperty("jobTitle") JobTitle jobTitle) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && sex == person.sex && jobTitle == person.jobTitle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sex, jobTitle);
    }
}
