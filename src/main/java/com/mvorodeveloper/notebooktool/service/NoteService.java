/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.service;

import com.mvorodeveloper.notebooktool.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 *
 */
public interface NoteService {

    Optional<Note> getById(Integer id);

    void save(Note note);

    void update(Integer id, String message, boolean done);

    void delete(Integer id);

    Page<Note> findAllOrderByDate(Sort.Direction sort);

    Page<Note> findAllByStatusOrderByDate(boolean isDone, Sort.Direction sort);
}
