package org.navent.et.service;

import io.micronaut.spring.tx.annotation.Transactional;
import org.navent.et.dao.implementadas.BumexMemcached;
import org.navent.et.dao.implementadas.PedidosDAO;
import org.navent.et.dao.ProxyDAO;
import org.navent.et.domain.Pedido;
import org.navent.et.service.exceptions.InmutabilidadNoPracticableException;
import org.navent.et.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Propagation;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PedidosServiceImpl implements PedidosService{

    @Inject
    ProxyDAO<Integer, Pedido> proxyDAO;

    public void setProxyDAO(ProxyDAO<Integer, Pedido> proxyDAO){
        this.proxyDAO = proxyDAO;
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class,propagation = Propagation.REQUIRED)
    public Pedido create(final Pedido pedido) throws IllegalArgumentException, InmutabilidadNoPracticableException, ServiceException {
        if(pedido.getIdPedido() != null) throw new IllegalArgumentException("El pedido nuevo no puede tener id");
        try {
            Pedido pedidoToCreate = (Pedido) pedido.clone();
            this.proxyDAO.save(pedidoToCreate);
            return pedidoToCreate;
        }catch(CloneNotSupportedException ce){
            throw new InmutabilidadNoPracticableException("El pedido no se ha podido clonar y la operacion de crear asigna id al pedido, siendo argumento final.",ce);
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class,propagation = Propagation.REQUIRED)
    public Pedido update(final Pedido pedido) throws IllegalArgumentException, InmutabilidadNoPracticableException, ServiceException {
        if(pedido.getIdPedido() == null) throw new IllegalArgumentException("Pedido para actualizar sin id");

        try {
            Pedido pedidoActualizar = (Pedido) pedido.clone();
            this.proxyDAO.save(pedidoActualizar);
            return pedidoActualizar;
        }catch(CloneNotSupportedException ce){
            throw new InmutabilidadNoPracticableException("El pedido no se ha podido clonar y siendo argumento final para mantener inmutabilidad.",ce);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Pedido findById(final Integer id) throws IllegalArgumentException, ServiceException {
        if(id == null) throw new IllegalArgumentException("Id para buscar pedido invalido");
        try {
            return this.proxyDAO.select(id);
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class,propagation = Propagation.REQUIRED)
    public void delete(final Integer id) throws IllegalArgumentException, ServiceException {
        if(id == null) throw new IllegalArgumentException("Id para eliminar pedido invalido");
        try {
            this.proxyDAO.delete(id);
        }catch(Exception e){
            throw new ServiceException(e);
        }
    }
}
