/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Class that corresponds to the 'notes' table of the 'notebook_mysql' MySQL database
 */
@Entity(name = "notes")
@Table(name = "notes", schema = "notebook_mysql")
public class Note {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "message")
    private String message;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "done")
    private boolean done;

    public Note() {

    }

    public Note(String message) {
        this.message = message;
        this.createDate = new Date();
        this.done = false;
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

    public Date getDate() {
        return createDate;
    }

    public void setDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
