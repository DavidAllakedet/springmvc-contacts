package com.contact.app.controller;

import com.contact.app.dto.ContactDto;
import com.contact.app.service.ContactService;
import com.contact.app.service.GroupeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ContactController {

    private final ContactService contactService = new ContactService();
    private final GroupeService groupeService = new GroupeService();

    @GetMapping("/")
    public String dashboard(Model model) {
        List<ContactDto> allContacts = contactService.findAll();
        model.addAttribute("contacts", allContacts);
        model.addAttribute("totalContacts", allContacts.size());
        model.addAttribute("totalGroupes", groupeService.findAll().size());
        return "index";
    }

    @GetMapping("/contacts")
    public String list(@RequestParam(value = "groupeId", required = false) Long groupeId,
                       @RequestParam(value = "q", required = false) String query,
                       Model model) {
        List<ContactDto> contacts;
        if (query != null && !query.trim().isEmpty()) {
            String q = query.trim().toLowerCase();
            contacts = contactService.findAll().stream()
                    .filter(c -> (c.getNom() != null && c.getNom().toLowerCase().contains(q))
                            || (c.getPrenom() != null && c.getPrenom().toLowerCase().contains(q))
                            || (c.getEmail() != null && c.getEmail().toLowerCase().contains(q)))
                    .collect(Collectors.toList());
            model.addAttribute("searchQuery", query.trim());
        } else if (groupeId != null) {
            contacts = contactService.findAll().stream()
                    .filter(c -> groupeId.equals(c.getGroupeId()))
                    .collect(Collectors.toList());
            model.addAttribute("filterGroupe", groupeId);
        } else {
            contacts = contactService.findAll();
        }
        model.addAttribute("contacts", contacts);
        model.addAttribute("groupes", groupeService.findAll());
        return "contacts";
    }

    @GetMapping("/contacts/add")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new ContactDto());
        model.addAttribute("groupes", groupeService.findAll());
        return "add-contact";
    }

    @PostMapping("/contacts/save")
    public String save(@RequestParam("nom") String nom,
                       @RequestParam(value = "prenom", required = false) String prenom,
                       @RequestParam(value = "email", required = false) String email,
                       @RequestParam(value = "telephone", required = false) String telephone,
                       @RequestParam(value = "adresse", required = false) String adresse,
                       @RequestParam(value = "groupeId", required = false) Long groupeId,
                       RedirectAttributes redirectAttributes) {
        if (nom == null || nom.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("nomError", "Le nom est obligatoire.");
            return "redirect:/contacts/add";
        }
        ContactDto dto = new ContactDto();
        dto.setNom(nom.trim()); dto.setPrenom(prenom); dto.setEmail(email);
        dto.setTelephone(telephone); dto.setAdresse(adresse); dto.setGroupeId(groupeId);
        contactService.save(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Contact ajoute avec succes !");
        return "redirect:/contacts";
    }

    @GetMapping("/contacts/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        ContactDto contact = contactService.findById(id);
        if (contact == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Contact introuvable.");
            return "redirect:/contacts";
        }
        model.addAttribute("contact", contact);
        model.addAttribute("groupes", groupeService.findAll());
        return "add-contact";
    }

    @PostMapping("/contacts/update")
    public String update(@RequestParam("id") Long id, @RequestParam("nom") String nom,
                         @RequestParam(value = "prenom", required = false) String prenom,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "telephone", required = false) String telephone,
                         @RequestParam(value = "adresse", required = false) String adresse,
                         @RequestParam(value = "groupeId", required = false) Long groupeId,
                         RedirectAttributes redirectAttributes) {
        if (nom == null || nom.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("nomError", "Le nom est obligatoire.");
            return "redirect:/contacts/edit/" + id;
        }
        ContactDto dto = new ContactDto();
        dto.setId(id); dto.setNom(nom.trim()); dto.setPrenom(prenom); dto.setEmail(email);
        dto.setTelephone(telephone); dto.setAdresse(adresse); dto.setGroupeId(groupeId);
        contactService.update(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Contact mis a jour avec succes !");
        return "redirect:/contacts";
    }

    @PostMapping("/contacts/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        ContactDto contact = contactService.findById(id);
        if (contact == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Contact introuvable.");
            return "redirect:/contacts";
        }
        contactService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Contact supprime avec succes !");
        return "redirect:/contacts";
    }
}
