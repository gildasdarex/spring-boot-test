package com.pej;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pej.domains.Candidat;


@Controller
public class WebController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showForm(@ModelAttribute("personForm") Candidat personForm) {
        return "form";
    }

    @PostMapping("/")
    public String checkPersonInfo(@Valid @ModelAttribute("personForm")  Candidat personForm, BindingResult bindingResult) {
    	System.out.println("START CHECKPERSON INFO OK");
        if (bindingResult.hasErrors()) {
            return "form";
        }

        return "redirect:/results";
    }
}
