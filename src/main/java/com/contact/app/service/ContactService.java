package com.contact.app.service;

import com.contact.app.dao.IContactDao;
import com.contact.app.dao.IGroupeDao;
import com.contact.app.dao.ContactDao;
import com.contact.app.dao.GroupeDao;
import com.contact.app.dto.ContactDto;
import com.contact.app.entities.ContactEntity;
import com.contact.app.entities.GroupeEntity;
import com.contact.app.mapper.ContactMapper;
import java.util.List;

public class ContactService {
    private final IContactDao contactDao = new ContactDao();
    private final IGroupeDao groupeDao = new GroupeDao();

    public ContactDto save(ContactDto dto) {
        GroupeEntity groupe = dto.getGroupeId() != null ? groupeDao.findById(dto.getGroupeId()) : null;
        ContactEntity e = ContactMapper.toContactEntity(dto, groupe);
        contactDao.save(e);
        return ContactMapper.toContactDto(e);
    }

    public ContactDto update(ContactDto dto) {
        GroupeEntity groupe = dto.getGroupeId() != null ? groupeDao.findById(dto.getGroupeId()) : null;
        ContactEntity e = ContactMapper.toContactEntity(dto, groupe);
        contactDao.update(e);
        return ContactMapper.toContactDto(e);
    }

    public void delete(Long id) { contactDao.delete(id); }
    public ContactDto findById(Long id) { return ContactMapper.toContactDto(contactDao.findById(id)); }
    public List<ContactDto> findAll() { return ContactMapper.toListContactDto(contactDao.findAll()); }
}
