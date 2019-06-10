package org.navent.et.dao.implementadas;

public class BumexMemcached {

    public static BumexMemcached instance;

    private BumexMemcached(){
    }

    public static BumexMemcached getInstance(){return instance == null?(instance=new BumexMemcached()):instance;}

    public void set(String key, Object value){}

    public Object get(String key){return null;}

    public void delete(String key){}
}
