package org.navent.et.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.navent.et.domain.Pedido;

import java.util.Random;

@Controller("/pedido")
public class PedidoController {


    @Post(value = "/guardar",produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Pedido> guardar(@Body Pedido pedido){
        //dummy return para tener una devolucion en el html
        Random random = new Random();
        if(random.nextBoolean())
            return HttpResponse.accepted();
        else {
            HttpResponse response = HttpResponse.badRequest().header("X-Error","random false!");
            return response;
        }
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String test(){
        return "Funcionando...";
    }
}
