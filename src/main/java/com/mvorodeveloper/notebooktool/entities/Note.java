/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class that corresponds to the 'notes' table of the 'notebook_mysql' MySQL database
 */
@Entity(name = "notes")
@Table(name = "notes", schema = "notebook_mysql")
public class Note {

    @Id
    @GeneratedValue
    private int id;

    private String message;

    private boolean done;

    // Spring uses lower snake case by default, which means it uses only lower case letters and separates words
    // with underscores. Therefore, the `createDate` variable corresponds to the `create_date` column in the database
    private Date createDate;

    public Note() {
    }

    public Note(String message) {
        this.message = message;
        this.done = false;
        this.createDate = new Date();
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDate() {
        return createDate;
    }

    public void setDate(Date createDate) {
        this.createDate = createDate;
    }
}
