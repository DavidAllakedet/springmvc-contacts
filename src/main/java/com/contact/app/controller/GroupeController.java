package com.contact.app.controller;

import com.contact.app.dto.GroupeDto;
import com.contact.app.service.GroupeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GroupeController {

    private final GroupeService groupeService = new GroupeService();

    @GetMapping("/groupes")
    public String list(Model model) {
        model.addAttribute("groupes", groupeService.findAll());
        return "groupes";
    }

    @GetMapping("/groupes/add")
    public String showAddForm(Model model) {
        model.addAttribute("groupe", new GroupeDto());
        return "add-groupe";
    }

    @PostMapping("/groupes/save")
    public String save(@RequestParam("nom") String nom,
                       @RequestParam(value = "description", required = false) String description) {
        GroupeDto dto = new GroupeDto();
        dto.setNom(nom); dto.setDescription(description);
        groupeService.save(dto);
        return "redirect:/groupes";
    }

    @GetMapping("/groupes/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("groupe", groupeService.findById(id));
        return "add-groupe";
    }

    @PostMapping("/groupes/update")
    public String update(@RequestParam("id") Long id, @RequestParam("nom") String nom,
                         @RequestParam(value = "description", required = false) String description) {
        GroupeDto dto = new GroupeDto();
        dto.setId(id); dto.setNom(nom); dto.setDescription(description);
        groupeService.update(dto);
        return "redirect:/groupes";
    }

    @GetMapping("/groupes/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        groupeService.delete(id);
        return "redirect:/groupes";
    }
}
