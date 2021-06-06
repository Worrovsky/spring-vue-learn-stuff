package com.example.personExample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PersonController {

    private static Logger log = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/person")
    public ModelAndView getPerson(Person person, BindingResult result) {

        person.setName("Alice");
        person.setEmail("alice@rt.com");
        person.setAge(27);

        return new ModelAndView("person", "person", person);
    }

    @PostMapping("/person")
    public ModelAndView postPerson(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError e : errorList) {
                log.warn(e.toString());
            }
        }

        ObjectError error = new ObjectError("person"
                , "programmatically added error for 'Person' ");
        result.addError(error);

        FieldError fieldError = new FieldError("person", "age"
                , "programmatically added error for 'Age' of 'Person'");
        result.addError(fieldError);

        return new ModelAndView("person", "person", person);
    }

}

