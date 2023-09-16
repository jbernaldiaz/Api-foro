package com.alura.foro.domain.topico;

import java.time.LocalDateTime;

public record DatosActualizarTopico(Long id, String titulo, String mensaje, String autor, String curso){
}
