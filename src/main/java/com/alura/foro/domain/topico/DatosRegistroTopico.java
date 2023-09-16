package com.alura.foro.domain.topico;


import java.time.LocalDateTime;

public record DatosRegistroTopico(String titulo, String mensaje, String autor, String curso, LocalDateTime fecha_creacion, Boolean status) {

}

