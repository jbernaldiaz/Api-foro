package com.alura.foro.controller;


import com.alura.foro.domain.infra.security.SecurityConfiguration;
import com.alura.foro.domain.usuarios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> createUsuario(@RequestBody DatosRegistroUsuario dtoRegistroUsuario, UriComponentsBuilder uriComponentsBuilder){

        Usuario user = new Usuario();

        user.setLogin(dtoRegistroUsuario.login());
        user.setClave(passwordEncoder.encode(dtoRegistroUsuario.clave()));

        Usuario usuario = usuarioRepository.save(user);

        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getLogin()
        );
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaUsuario);


    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuarios>> listarUsuarios(Pageable paginacion){
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosListadoUsuarios::new));

    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoUsuarios> listarUsuario(@PathVariable Long id, UriComponentsBuilder uriComponentsBuilder){

        Usuario usuario = usuarioRepository.getReferenceById(id);
        var datosTopico = new DatosListadoUsuarios(
                usuario.getId(),
                usuario.getLogin()
        );

        return ResponseEntity.ok(datosTopico);

    }

    @PutMapping
    @Transactional
    public void updateUsuario(@RequestBody DatosActualizarUsuario datosActualizarUsuario){

        Usuario user = usuarioRepository.getReferenceById(datosActualizarUsuario.id());

        String log = user.setLogin(datosActualizarUsuario.login());
        String cla = user.setClave(passwordEncoder.encode(datosActualizarUsuario.clave()));

        user.actualizarDatos(log, cla);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUsuario(@PathVariable Long id){

        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivarUsuario();

        return ResponseEntity.noContent().build();
    }


    }

