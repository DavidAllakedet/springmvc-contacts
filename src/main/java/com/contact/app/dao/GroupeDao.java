package com.contact.app.dao;
import com.contact.app.entities.GroupeEntity;
public class GroupeDao extends RepositoryImpl<GroupeEntity> implements IGroupeDao {
    public GroupeDao() { super(GroupeEntity.class); }
}
