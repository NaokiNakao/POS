package com.nakao.pos.dao;

import java.util.List;
import java.util.Optional;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */
public interface DAO<T, S> {

    List<T> findAll();

    Optional<T> findById(S id);

    T insert(T t);

    T update(S id, T t);

    Boolean delete(S id);

}
