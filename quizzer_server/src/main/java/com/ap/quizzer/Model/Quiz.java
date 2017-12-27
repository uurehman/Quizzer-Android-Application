package com.ap.quizzer.Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String title;
    private String Description;
    @OneToMany
    private Collection<Problem> Problems;


    public Quiz(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Problem> get_problems() {
        return Problems;
    }

    public void setProblems(LinkedList<Problem> problems) {
        Problems = problems;
    }

    public void addQuestion(Problem p) {
        Problems.add(p);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
