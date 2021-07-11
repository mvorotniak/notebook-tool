/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.repository;

import com.mvorodeveloper.notebooktool.entities.Note;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface for organizing interactions with the database
 */
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query(value = "SELECT n FROM notes n WHERE done = :status")
    List<Note> findAllByStatusOrderByDate(@Param("status") boolean isDone, Sort sort);
}
