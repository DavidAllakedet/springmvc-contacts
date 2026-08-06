package com.contact.app.mapper;

import com.contact.app.dto.ContactDto;
import com.contact.app.dto.GroupeDto;
import com.contact.app.entities.ContactEntity;
import com.contact.app.entities.GroupeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactMapper {

    public static GroupeDto toGroupeDto(GroupeEntity e) {
        if (e == null) return null;
        return new GroupeDto(e.getId(), e.getNom(), e.getDescription());
    }

    public static GroupeEntity toGroupeEntity(GroupeDto d) {
        if (d == null) return null;
        GroupeEntity e = new GroupeEntity();
        e.setId(d.getId()); e.setNom(d.getNom()); e.setDescription(d.getDescription());
        return e;
    }

    public static List<GroupeDto> toListGroupeDto(List<GroupeEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(ContactMapper::toGroupeDto).collect(Collectors.toList());
    }

    public static ContactDto toContactDto(ContactEntity e) {
        if (e == null) return null;
        return new ContactDto(e.getId(), e.getNom(), e.getPrenom(), e.getEmail(), e.getTelephone(), e.getAdresse(),
                e.getGroupe() != null ? e.getGroupe().getId() : null,
                e.getGroupe() != null ? e.getGroupe().getNom() : null);
    }

    public static ContactEntity toContactEntity(ContactDto d, GroupeEntity groupe) {
        if (d == null) return null;
        ContactEntity e = new ContactEntity();
        e.setId(d.getId()); e.setNom(d.getNom()); e.setPrenom(d.getPrenom());
        e.setEmail(d.getEmail()); e.setTelephone(d.getTelephone()); e.setAdresse(d.getAdresse()); e.setGroupe(groupe);
        return e;
    }

    public static List<ContactDto> toListContactDto(List<ContactEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(ContactMapper::toContactDto).collect(Collectors.toList());
    }
}
