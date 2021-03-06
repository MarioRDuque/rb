/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.entidades.Rol;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.RolServicio;
import com.rivdu.util.Respuesta;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PROPIETARIO
 */
@RestController
@RequestMapping("tiposroles")
public class RolControlador {
    
     private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
   @Autowired
   private RolServicio rolservicio;

    @GetMapping ("listar")
    public ResponseEntity show() throws GeneralException{
        Respuesta resp = new Respuesta();
        List<Rol> lista;
        try {
          lista = rolservicio.listar();
            if (lista!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(lista);
            }else{
                throw new GeneralException("Lista no disponible", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
    
}
