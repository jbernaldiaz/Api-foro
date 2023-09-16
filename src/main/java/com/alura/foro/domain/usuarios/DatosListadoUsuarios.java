package com.alura.foro.domain.usuarios;

public record DatosListadoUsuarios(Long id, String login) {

    public DatosListadoUsuarios(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getLogin()
        );
    }
}
