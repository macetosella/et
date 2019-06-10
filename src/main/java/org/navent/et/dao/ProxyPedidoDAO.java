package org.navent.et.dao;

import org.navent.et.dao.implementadas.BumexMemcached;
import org.navent.et.dao.implementadas.PedidosDAO;
import org.navent.et.dao.util.KeyManager;
import org.navent.et.domain.Pedido;
import org.navent.et.domain.Persistable;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProxyPedidoDAO implements ProxyDAO<Integer,Pedido> {

    @Inject
    private KeyManager<Integer, Persistable<Integer>> keyManager;

    public void setKeyManager(KeyManager keyManager){
        this.keyManager = keyManager;
    }

    public void save(Pedido pedido){
        PedidosDAO.insertOrUpdate(pedido);
        //se supone que el id esta, podria manejar un control o esperar un Optional del DAO.
        BumexMemcached.getInstance().set(keyManager.getKey(pedido),pedido);
    }

    @Override
    public Pedido select(Integer id) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);
        BumexMemcached bumexMemcached = BumexMemcached.getInstance();
        String key = this.keyManager.getKey(pedido);
        pedido = (Pedido) bumexMemcached.get(key);
        if(pedido == null) {
            pedido = PedidosDAO.select(id);
            //TODO: ver en concurrencia
            bumexMemcached.set(key,pedido);
        }
        return pedido;
    }

    @Override
    public void delete(Integer id) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);
        //TODO: ver concurrencia
        BumexMemcached.getInstance().delete(this.keyManager.getKey(pedido));
        //si no existe falla?
        PedidosDAO.delete(pedido);
    }
}
