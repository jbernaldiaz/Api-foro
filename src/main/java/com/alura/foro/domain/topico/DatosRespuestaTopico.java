package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(Long id, String titulo, String mensaje, String autor, String curso, LocalDateTime fecha_creacion, Boolean status) {

}
