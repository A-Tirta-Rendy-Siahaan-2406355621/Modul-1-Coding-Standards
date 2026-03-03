package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface CRUD<T, ID> {
    T create(T entity);
    List<T> findAll();
    T findById(ID id);
    boolean delete(ID id);
    boolean update(T entity);
}