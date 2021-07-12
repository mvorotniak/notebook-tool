/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.mvorodeveloper.notebooktool.entities.Note;

/**
 *
 */
public interface NoteService {

    Optional<Note> getById(Integer id);

    void save(Note note);

    void update(Integer id, String message, boolean done);

    void delete(Integer id);

    Page<Note> findAllOrderByDate(Sort sort);

    Page<Note> findAllByStatusOrderByDate(boolean isDone, Sort sort);
}
