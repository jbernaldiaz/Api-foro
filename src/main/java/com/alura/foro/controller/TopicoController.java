package com.alura.foro.controller;

import com.alura.foro.domain.topico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

     @PostMapping
     @Transactional
    public ResponseEntity<DatosRespuestaTopico> createTopico(@RequestBody DatosRegistroTopico dtoRegistroTopico, UriComponentsBuilder uriComponentsBuilder){


         Topico topico = topicoRepository.save(new Topico(dtoRegistroTopico));

         DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                 topico.getId(),
                 topico.getTitulo(),
                 topico.getMensaje(),
                 topico.getAutor(),
                 topico.getCurso(),
                 topico.getFecha_creacion(),
                 topico.getStatus()
         );
         URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);


     }

    @GetMapping
    public ResponseEntity<Page<Topico>> listadoTopicos(Pageable paginacion){

        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion));

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> listarTopico(@PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){

        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso(),
                topico.getFecha_creacion(),
                topico.getStatus()
        );

        return ResponseEntity.ok(datosTopico);

    }

    @PutMapping
    @Transactional
    public ResponseEntity updateTopico(@RequestBody DatosActualizarTopico datosActualizarTopico ){
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);

        return ResponseEntity.ok(new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso(),
                topico.getFecha_creacion(),
                topico.getStatus()
        ));

    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrarTopico(@PathVariable Long id){

        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();

        return ResponseEntity.noContent().build();
    }

}
