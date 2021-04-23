package com.example.quanlyluong.DAO;

import java.util.List;

public interface DAO<T> {
    public boolean create(T t ) throws  Exception;
    public List<T> getAll() throws Exception;
    public T getById(int id ) throws  Exception;
    public boolean deleteAll() throws Exception;
    public boolean deleteById(int id ) throws Exception;
    public boolean update(T t) throws Exception;
}
