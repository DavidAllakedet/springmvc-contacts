package com.contact.app.service;

import com.contact.app.dao.IGroupeDao;
import com.contact.app.dao.GroupeDao;
import com.contact.app.dto.GroupeDto;
import com.contact.app.entities.GroupeEntity;
import com.contact.app.mapper.ContactMapper;
import java.util.List;

public class GroupeService {
    private final IGroupeDao dao = new GroupeDao();
    public GroupeDto save(GroupeDto dto) { GroupeEntity e = ContactMapper.toGroupeEntity(dto); dao.save(e); return ContactMapper.toGroupeDto(e); }
    public GroupeDto update(GroupeDto dto) { GroupeEntity e = ContactMapper.toGroupeEntity(dto); dao.update(e); return ContactMapper.toGroupeDto(e); }
    public void delete(Long id) { dao.delete(id); }
    public GroupeDto findById(Long id) { return ContactMapper.toGroupeDto(dao.findById(id)); }
    public List<GroupeDto> findAll() { return ContactMapper.toListGroupeDto(dao.findAll()); }
}
