package com.contact.app.dao;

import com.contact.app.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RepositoryImpl<T> implements Repository<T> {
    private final Class<T> entityClass;
    public RepositoryImpl(Class<T> entityClass) { this.entityClass = entityClass; }

    @Override
    public T save(T entity) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction(); s.save(entity); tx.commit(); return entity;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public T update(T entity) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction(); s.merge(entity); tx.commit(); return entity;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction(); T entity = s.get(entityClass, id);
            if (entity != null) s.delete(entity); tx.commit();
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public T findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) { return s.get(entityClass, id); }
    }

    @Override
    public List<T> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        }
    }
}
