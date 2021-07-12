/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mvorodeveloper.notebooktool.entities.Note;
import com.mvorodeveloper.notebooktool.repository.NoteRepository;

/**
 * Class that performs GET and CRUD operations for the 'notes' table of the 'notebook_mysql' MySQL database
 */
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Optional<Note> getById(Integer id) {
        return noteRepository.findById(id);
    }

    @Override
    public void save(Note note) {
        noteRepository.save(note);
    }

    @Override
    public void update(Integer id, String message, boolean done) {
        Optional<Note> updatedOptional = getById(id);
        updatedOptional.ifPresent(updated -> {
            updated.setMessage(message);
            updated.setDone(done);
            noteRepository.save(updated);
        });
    }

    @Override
    public void delete(Integer id) {
        Optional<Note> toDeleteNoteOptional = getById(id);
        toDeleteNoteOptional.ifPresent(noteRepository::delete);
    }

    @Override
    public Page<Note> findAllOrderByDate(Sort sort) {
        List<Note> notes = noteRepository.findAll(sort);

        return convertToPage(notes);
    }

    @Override
    public Page<Note> findAllByStatusOrderByDate(boolean isDone, Sort sort) {
        List<Note> notes = noteRepository.findAllByStatusOrderByDate(isDone, sort);

        return convertToPage(notes);
    }

    /**
     * Converts a {@link List<Note>} into a {@link Page<Note>}
     */
    private Page<Note> convertToPage(List<Note> notes) {
        return new PageImpl<>(notes);
    }
}
