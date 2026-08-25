package com.contact.app.controller;

import com.contact.app.dto.GroupeDto;
import com.contact.app.service.GroupeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                       @RequestParam(value = "description", required = false) String description,
                       RedirectAttributes redirectAttributes) {
        if (nom == null || nom.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("nomError", "Le nom du groupe est obligatoire.");
            return "redirect:/groupes/add";
        }
        GroupeDto dto = new GroupeDto();
        dto.setNom(nom.trim()); dto.setDescription(description);
        groupeService.save(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Groupe ajoute avec succes !");
        return "redirect:/groupes";
    }

    @GetMapping("/groupes/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        GroupeDto groupe = groupeService.findById(id);
        if (groupe == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Groupe introuvable.");
            return "redirect:/groupes";
        }
        model.addAttribute("groupe", groupe);
        return "add-groupe";
    }

    @PostMapping("/groupes/update")
    public String update(@RequestParam("id") Long id, @RequestParam("nom") String nom,
                         @RequestParam(value = "description", required = false) String description,
                         RedirectAttributes redirectAttributes) {
        if (nom == null || nom.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("nomError", "Le nom du groupe est obligatoire.");
            return "redirect:/groupes/edit/" + id;
        }
        GroupeDto dto = new GroupeDto();
        dto.setId(id); dto.setNom(nom.trim()); dto.setDescription(description);
        groupeService.update(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Groupe mis a jour avec succes !");
        return "redirect:/groupes";
    }

    @PostMapping("/groupes/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        GroupeDto groupe = groupeService.findById(id);
        if (groupe == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Groupe introuvable.");
            return "redirect:/groupes";
        }
        groupeService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Groupe supprime avec succes !");
        return "redirect:/groupes";
    }
}
