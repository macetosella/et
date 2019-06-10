package org.navent.et.dao;

import org.navent.et.domain.Persistable;

public interface ProxyDAO<T,P extends Persistable<T>> {

    void save(P persistable);

    P select(T id);

    void delete(T id);
}
