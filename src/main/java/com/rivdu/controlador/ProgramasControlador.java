/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.entidades.Programas;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ProgramasServicio;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import com.rivdu.util.RivduUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Christhian
 */

@RestController
@RequestMapping("programas")
public class ProgramasControlador {
    
    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
     
    @Autowired
    private ProgramasServicio programasServicio; 
    
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody Programas entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if(entidad != null){
            try {
                Programas guardado = programasServicio.crear(entidad);
                if (guardado != null ) {
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(guardado);
                }else{
                    throw new GeneralException(Mensaje.ERROR_CRUD_GUARDAR, "Guardar retorno nulo", loggerControlador);
                }
            } catch (Exception e) {
                throw e;
            }
        }else{
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.ERROR.getValor());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    @GetMapping("listar")
    public ResponseEntity show() throws GeneralException {
        Respuesta resp = new Respuesta();
        List<Programas> lista;
        try {
            lista = programasServicio.listar();
            if (lista != null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(lista);
            } else {
                throw new GeneralException("Lista no disponible", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
        
    @RequestMapping(value="eliminar", method = RequestMethod.POST)
    public ResponseEntity eliminar(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException{
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Programas programa = programasServicio.obtener(id);
            if(programa.isEstado()){
            programa.setEstado(Boolean.FALSE);
            }
            else{
            programa.setEstado(Boolean.TRUE);
            }
            programa = programasServicio.actualizar(programa);
            if (programa.getId()!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(programa);
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }//fin del metodo eliminar
            @RequestMapping(value="obtener", method = RequestMethod.POST)
    public ResponseEntity obtener(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException{
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Programas programa = programasServicio.obtener(id);
            if (programa!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(programa);
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }//Fin del Metodo obtener:traerparaedicion
    
        @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity actualizar(HttpServletRequest request, @RequestBody Programas entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if(entidad != null){
            try {
                Programas guardado = programasServicio.actualizar(entidad);
                if (guardado != null ) {
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(guardado);
                }else{
                    throw new GeneralException(Mensaje.ERROR_CRUD_GUARDAR, "Guardar retorno nulo", loggerControlador);
                }
                
            } catch (Exception e) {
                throw e;
            }
        }else{
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.ERROR.getValor());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}