package com.contact.app.dao;
import com.contact.app.entities.ContactEntity;
public class ContactDao extends RepositoryImpl<ContactEntity> implements IContactDao {
    public ContactDao() { super(ContactEntity.class); }
}
