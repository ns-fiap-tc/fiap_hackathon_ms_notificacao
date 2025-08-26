package br.com.fiap.hacka.adapter.input.controller;

import br.com.fiap.hacka.application.service.WebHookService;
import br.com.fiap.hacka.adapter.input.dto.NotificacaoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CommonsLog
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/notificacao-service/v1")
@Tag(name = "notificacao-service")
public class NotificacaoController implements NotificacaoApi {

    private final WebHookService service;

    @Override
    @Operation(summary = "Envia uma mensagem via webhook para a URL informada.", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Objeto invalido.")
    })
    @PostMapping("/send/webhook")
    public ResponseEntity<Void> sendWebhookMessage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "objeto a ser criado")
            @Valid @RequestBody NotificacaoDto notificacaoDto) {
        service.enviarNotificacao(notificacaoDto.getMensagem(),notificacaoDto.getUrl());
        return ResponseEntity.ok().build();
    }
}
