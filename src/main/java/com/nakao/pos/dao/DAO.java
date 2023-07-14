package com.nakao.pos.dao;

import java.util.List;
import java.util.Optional;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */
public interface DAO<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T insert(T t);

    T update(ID id, T t);

    Boolean delete(ID id);

}
