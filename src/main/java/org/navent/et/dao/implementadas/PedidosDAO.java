package org.navent.et.dao.implementadas;

import org.navent.et.domain.Pedido;

public class PedidosDAO {

    /**
     * inserta un nuevo pedido en la base de datos o modifica un pedido existente
     * (en cado de crear uno nuevo, el pedido pasado como parametro se completa con el nuevo id)
     * @param pedido
     */
    public static void insertOrUpdate(Pedido pedido){}

    /**
     * elimina el pedido que corresponde al id recibido.
     * @param pedido
     */
    public static void delete (Pedido pedido){}

    /**
     * busca un pedido por id.
     * @param idPedido
     * @return
     */
    public static Pedido select(Integer idPedido){return null;}
}
