package com.berenberg.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.berenberg.library.service.LibraryService;

@Controller

public class LibModelController {

    @Autowired
    LibraryService service;

    @GetMapping(value = { "/display" })
    public ModelAndView display(@RequestParam int itemId) {
        ModelAndView mav = new ModelAndView("Home.html");
        mav.addObject("display", service.getCurrentInventory());
       
        return mav;

    }
}