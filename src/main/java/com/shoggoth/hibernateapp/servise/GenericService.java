package com.shoggoth.hibernateapp.servise;

import java.util.List;
import java.util.Optional;

public interface GenericService<K, D> {
    Optional<K> add(D dto);

    Optional<D> getById(K id);

    List<D> getAll();
    boolean update(D dto);

    boolean delete(K id);

}

