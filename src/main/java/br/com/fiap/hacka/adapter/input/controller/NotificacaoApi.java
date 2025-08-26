package br.com.fiap.hacka.adapter.input.controller;

import br.com.fiap.hacka.adapter.input.dto.NotificacaoDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface NotificacaoApi {
    ResponseEntity<Void> sendWebhookMessage(@Valid @RequestBody NotificacaoDto notificacaoDto);
}
