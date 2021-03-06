package ru.pevnenko.springmvc.controlers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pevnenko.springmvc.dao.PersonDAO;
import ru.pevnenko.springmvc.models.Person;

import java.sql.SQLException;

/**
 * @author: Alex Pevnenko <Alex.pevnenko@gmail.com>
 * @version: v 1.0
 * @date: 14.05.2022
 */
@Controller
@RequestMapping("/people")
public class PeopleController{

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO){
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String index(Model model) throws SQLException{
        //Получим всех людей из Dao и передадим отображение в представление
        model.addAttribute("people",personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        model.addAttribute("person",personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) throws SQLException{
        if(bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person",personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person")@Valid Person person,BindingResult bindingResult,@PathVariable("id")int id){
        if(bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
