package org.navent.et.service;

import org.navent.et.domain.Pedido;
import org.navent.et.service.exceptions.InmutabilidadNoPracticableException;

public interface PedidosService {

    /**
     * Crea un pedido.<br>
     * @param pedido contiene los datos del pedido a crear o modificar.
     * @return retorna el pedido creado con el id.
     * @throws IllegalArgumentException cuando el pedido tiene asignado un id.
     * @throws  InmutabilidadNoPracticableException si el objeto pedido no permite clonarse.
     */
    public Pedido create(final Pedido pedido) throws IllegalArgumentException, InmutabilidadNoPracticableException;
    /**
     * Modifica un pedido.<br>
     * @param pedido contiene los datos del pedido a modificar.
     * @return retorna el pedido modificado. Es una nueva instancia para mantener la inmutabilidad del argumento.
     * @throws IllegalArgumentException cuando el pedido tiene asignado un id que no existe en el repositorio.
     * @throws  InmutabilidadNoPracticableException si el objeto pedido no permite clonarse.
     */
    public Pedido update(final Pedido pedido) throws IllegalArgumentException, InmutabilidadNoPracticableException;

    /**
     * Busca un pedido por id.
     * @param id debe ser un id valido.
     * @return el pedido encontrado
     * @throws IllegalArgumentException en caso de que el id no sea valido.
     */
    public Pedido findById(final Integer id) throws IllegalArgumentException;

    /**
     * borra un pedido asignado al id
     * @param id
     *
     */
    public void delete(final Integer id) throws IllegalArgumentException;
}
