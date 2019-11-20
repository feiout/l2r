package com.l2r.base;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Messi on 2019/11/18.
 */
public abstract class AbstractService<T> implements IService<T>  {
    @Override
    @Transactional
    public T insert(T t) {
        return getRepository().save(t);
    }

    @Override
    @Transactional
    public void delete(String id) {
        getRepository().deleteById(id);
    }

    @Override
    @Transactional
    public T update(T t) {
        return getRepository().save(t);
    }

    @Override
    public List<T> batchUpdate(List<T> list) {
        return getRepository().saveAll(list);
    }

    @Override
    public T get(String id) {
        return getRepository().findById(id).get();
    }

    @Override
    public List<T> getAll() {
        return getRepository().findAll();
    }
}
