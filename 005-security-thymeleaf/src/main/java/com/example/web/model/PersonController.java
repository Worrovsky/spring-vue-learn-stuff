package com.example.web.model;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController {

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

}

