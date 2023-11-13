package com.berenberg.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.berenberg.library.service.LibraryService;

@Controller
@RequestMapping(value = { "/display" })
public class LibModelController {

    @Autowired
    LibraryService service;



    @GetMapping(value = { "/home" })
    public ModelAndView  display(Model m) {
         ModelAndView modelView = new ModelAndView();
            System.out.println("gettiing  herrrreeeeeeeeeeeeeeeee++++++++++++++++++");
             
             modelView.setViewName("home");
             modelView.addObject("msg", "welcome");
        return modelView;

    }
}