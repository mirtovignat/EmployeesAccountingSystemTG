package org.example;

import org.w3c.dom.Entity;

import java.sql.*;
import java.util.List;

public abstract class AbstractDAO <K, T extends Entity> {

    public abstract List<T> findAll() throws SQLException; //найти все

    public abstract T findEntityById(K id) throws SQLException;//найти сущность по айди

    public abstract boolean delete(K id);// удалить по айди

    public abstract boolean delete(T entity);// удалить по сущности

    public abstract T create(T entity) throws SQLException;// создать

    public abstract T update(T entity);// обновить
}
