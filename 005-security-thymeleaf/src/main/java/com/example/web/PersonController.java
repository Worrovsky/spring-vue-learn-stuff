package com.example.web;

import com.example.web.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PersonController {

    private static Logger log = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/person")
    public ModelAndView getPerson(Person person, BindingResult result) {

        person.setName("Alice");
        person.setEmail("alice@rt.com");
        person.setAge(27);

        ModelAndView modelAndView = new ModelAndView("person", "person", person);
        ObjectError error = new ObjectError("age", "test error for age");
        result.addError(error);

        return modelAndView;
    }

    @PostMapping("/person")
    public ModelAndView postPerson(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Have error...");
        }

        return new ModelAndView("person", "person", person);
    }

}

