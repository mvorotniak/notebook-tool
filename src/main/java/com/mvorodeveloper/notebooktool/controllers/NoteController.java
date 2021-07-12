/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvorodeveloper.notebooktool.entities.Note;
import com.mvorodeveloper.notebooktool.service.NoteService;

/**
 * Controller for operations related to notes
 * The filter method defaults to {@link NoteFilterMethods#ALL}.
 */
@Controller
public class NoteController {

    private final NoteService noteService;

    private NoteFilterMethods noteFilterMethod = NoteFilterMethods.ALL;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Spring automatically adds the page, size, sort parameters suggestions with {@link Pageable}.
     * Format: "http://localhost:8080/{?page,size,sort}" where:
     * page - the index of page that we want to retrieve
     * size - the number of pages that we want to retrieve. By default, the page size is 20
     * sort - properties to sort results. By default is Sort#UNSORTED
     * Example: "http://localhost:8080/?page=1&size=10&sort=createDate,desc"
     *
     * @return all the notes in the notebook filtered and sorted
     */
    @GetMapping("/")
    public String list(Model model, Pageable pageable) {
        Page<Note> page = filterAndSort(pageable);
        PageWrapper<Note> pageWrapper = new PageWrapper<>(page, "/");

        model.addAttribute("notes", pageWrapper.getContent());
        model.addAttribute("sort", pageable.getSort());
        model.addAttribute("filter", noteFilterMethod);
        model.addAttribute("page", pageWrapper);

        return "index";
    }

    @GetMapping("/filter/{noteFilterMethod}")
    public String filterChoose(@PathVariable String noteFilterMethod) {
        this.noteFilterMethod = NoteFilterMethods.valueOf(noteFilterMethod);

        return "redirect:/";
    }

    @GetMapping("/new")
    public String newNote() {
        return "operations/new";
    }

    @PostMapping("/save")
    public String saveNote(@RequestParam String message) {
        noteService.save(new Note(message));

        return "redirect:/";
    }

    @GetMapping("/edit/{noteId}")
    public String edit(@PathVariable Integer noteId, Model model) {
        Optional<Note> noteOptional = noteService.getById(noteId);
        noteOptional.ifPresent(note -> model.addAttribute("note", note));

        return "operations/edit";
    }

    @PostMapping("/update")
    public String updateNote(@RequestParam Integer id, @RequestParam String message,
        @RequestParam(value = "done", required = false) boolean done) {
        noteService.update(id, message, done);

        return "redirect:/";
    }

    @GetMapping("/delete/{noteId}")
    public String delete(@PathVariable Integer noteId) {
        noteService.delete(noteId);

        return "redirect:/";
    }

    /**
     * @return a {@link Page} of ordered by created date and filtered by state notes
     */
    private Page<Note> filterAndSort(Pageable pageable) {
        Sort sort = pageable.getSort();
        Page<Note> page;

        if (noteFilterMethod == NoteFilterMethods.ALL) {
            page = noteService.findAllOrderByDate(sort);
        } else {
            boolean isDone = noteFilterMethod.equals(NoteFilterMethods.DONE);
            page = noteService.findAllByStatusOrderByDate(isDone, sort);
        }

        return page;
    }
}
