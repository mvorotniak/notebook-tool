/*
 *  Copyright 2021-Present the original author or authors.
 *  No unauthorized use of this software.
 */
package com.mvorodeveloper.notebooktool.controllers;

import com.mvorodeveloper.notebooktool.entities.Note;
import com.mvorodeveloper.notebooktool.service.NoteService;
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

import java.util.Optional;

/**
 * Controller for operations related to notes
 */
@Controller
public class NoteController {

    private final NoteService noteService;

    // default values for sorting and filtering notes
    private Sort.Direction sortDateDirection = Sort.Direction.ASC;
    private NoteFilterMethods noteFilterMethod = NoteFilterMethods.ALL;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public String list(Model model, Pageable pageable) {
        Page<Note> notebookPage = filterAndSort(pageable);
        PageWrapper<Note> page = new PageWrapper<>(notebookPage, "/");
        model.addAttribute("notes", page.getContent());
        model.addAttribute("sort", sortDateDirection);
        model.addAttribute("filter", noteFilterMethod);
        model.addAttribute("page", page);
        return "index";
    }

    @GetMapping("/filter/{filter}")
    public String filterChoose(@PathVariable String filter) {
        noteFilterMethod = NoteFilterMethods.valueOf(filter);
        return "redirect:/";
    }

    @GetMapping("/sort/{sortDate}")
    public String sortChoose(@PathVariable String sortDate) {
        sortDateDirection = Sort.Direction.valueOf(sortDate);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newNote() {
        return "operations/new";
    }

    @PostMapping("/save")
    public String updateNote(@RequestParam String message) {
        noteService.save(new Note(message));
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Note> noteOptional = noteService.getById(id);
        noteOptional.ifPresent(note -> model.addAttribute("note", note));
        return "operations/edit";
    }

    @PostMapping("/update")
    public String saveNote(@RequestParam Integer id, @RequestParam String message,
        @RequestParam(value = "done", required = false) boolean done) {
        noteService.update(id, message, done);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        noteService.delete(id);
        return "redirect:/";
    }

    /**
     * @return a {@link Page} of ordered by created date and filtered by state notes
     */
    private Page<Note> filterAndSort(Pageable pageable) {
        Page<Note> page;

        switch (noteFilterMethod) {
            case DONE:
                page = noteService.findAllByStatusOrderByDate(true, sortDateDirection);
                break;
            case NOT_DONE:
                page = noteService.findAllByStatusOrderByDate( false, sortDateDirection);
                break;
            default:
                page = noteService.findAllOrderByDate(sortDateDirection);
                break;
        }

        return page;
    }
}
