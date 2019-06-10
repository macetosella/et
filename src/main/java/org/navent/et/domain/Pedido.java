package org.navent.et.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="Pedido")
public @Data class Pedido implements Cloneable, Persistable<Integer>{

    @Id
    @Column(updatable = false,unique = true, nullable = false)
    private Integer idPedido;
    @Column
    private String nombre;
    @Column
    private BigDecimal monto;
    @Column
    private BigDecimal descuento;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Pedido pedido = (Pedido) super.clone();
        return pedido;
    }

    @Transient @Override
    public Integer getId(){
        return this.idPedido;
    }
}
