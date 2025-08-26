package br.com.fiap.hacka.notificacao.input.controller;

import br.com.fiap.hacka.notificacao.input.dto.NotificacaoDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface NotificacaoApi {
    ResponseEntity<Void> sendWebhookMessage(@Valid @RequestBody NotificacaoDto notificacaoDto);
}
