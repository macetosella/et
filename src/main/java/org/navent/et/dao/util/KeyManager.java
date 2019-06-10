package org.navent.et.dao.util;

import org.navent.et.domain.Persistable;

public interface KeyManager<TID,TM extends Persistable<TID>> {

    default public String getKey(TM m){
        //podria usar la tabla del domain model
        return m.getClass().getName()+"_"+m.getId();
    }
}
