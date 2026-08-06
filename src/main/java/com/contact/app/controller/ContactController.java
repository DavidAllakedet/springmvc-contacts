package com.contact.app.controller;

import com.contact.app.dto.ContactDto;
import com.contact.app.dto.GroupeDto;
import com.contact.app.service.ContactService;
import com.contact.app.service.GroupeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {

    private final ContactService contactService = new ContactService();
    private final GroupeService groupeService = new GroupeService();

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        model.addAttribute("totalContacts", contactService.findAll().size());
        model.addAttribute("totalGroupes", groupeService.findAll().size());
        return "index";
    }

    @GetMapping("/contacts")
    public String list(@RequestParam(value = "groupeId", required = false) Long groupeId, Model model) {
        if (groupeId != null) {
            model.addAttribute("contacts", contactService.findAll().stream()
                    .filter(c -> groupeId.equals(c.getGroupeId())).collect(java.util.stream.Collectors.toList()));
            model.addAttribute("filterGroupe", groupeId);
        } else {
            model.addAttribute("contacts", contactService.findAll());
        }
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
                       @RequestParam(value = "groupeId", required = false) Long groupeId) {
        ContactDto dto = new ContactDto();
        dto.setNom(nom); dto.setPrenom(prenom); dto.setEmail(email);
        dto.setTelephone(telephone); dto.setAdresse(adresse); dto.setGroupeId(groupeId);
        contactService.save(dto);
        return "redirect:/contacts";
    }

    @GetMapping("/contacts/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("contact", contactService.findById(id));
        model.addAttribute("groupes", groupeService.findAll());
        return "add-contact";
    }

    @PostMapping("/contacts/update")
    public String update(@RequestParam("id") Long id, @RequestParam("nom") String nom,
                         @RequestParam(value = "prenom", required = false) String prenom,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "telephone", required = false) String telephone,
                         @RequestParam(value = "adresse", required = false) String adresse,
                         @RequestParam(value = "groupeId", required = false) Long groupeId) {
        ContactDto dto = new ContactDto();
        dto.setId(id); dto.setNom(nom); dto.setPrenom(prenom); dto.setEmail(email);
        dto.setTelephone(telephone); dto.setAdresse(adresse); dto.setGroupeId(groupeId);
        contactService.update(dto);
        return "redirect:/contacts";
    }

    @GetMapping("/contacts/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        contactService.delete(id);
        return "redirect:/contacts";
    }
}
