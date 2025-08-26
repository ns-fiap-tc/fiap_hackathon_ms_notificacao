package br.com.fiap.hacka.notificacao.service;

import br.com.fiap.hacka.notificacao.domain.exception.ValidacaoException;
import br.com.fiap.hacka.notificacao.domain.model.IEnumLabel;
import br.com.fiap.hacka.notificacao.domain.model.ValidacaoEnum;
import br.com.fiap.hacka.notificacao.infrastructure.utils.MensagensUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class WebHookServiceTest {

    @InjectMocks
    private WebHookService webHookService;

    @Test
    void enviarNotificacao_InvalidUrl_ThrowsException() {
        try (MockedStatic<MensagensUtils> mockedEnumUtils = mockStatic(MensagensUtils.class)) {
            mockedEnumUtils.when(() -> MensagensUtils.getEnumLabel(any()))
                    .thenReturn("Label Mockado");

            String resultado = MensagensUtils.getEnumLabel(ValidacaoEnum.ERRO_ENVIO_WEBHOOK);

            String mensagem = "Test message";
            String urlInvalida = "invalid-url";

            ValidacaoException exception = assertThrows(ValidacaoException.class,
                    () -> webHookService.enviarNotificacao(mensagem, urlInvalida));

            assertEquals(ValidacaoEnum.ERRO_ENVIO_WEBHOOK.getDescricao(), exception.getMensagem());
        }
    }

    @Test
    void enviarNotificacao_NullUrl_ThrowsException() {
        try (MockedStatic<MensagensUtils> mockedEnumUtils = mockStatic(MensagensUtils.class)) {
            mockedEnumUtils.when(() -> MensagensUtils.getEnumLabel(any()))
                    .thenReturn("Label Mockado");

            String resultado = MensagensUtils.getEnumLabel(ValidacaoEnum.ERRO_ENVIO_WEBHOOK);

            String mensagem = "Test message";
            String urlNula = null;

            ValidacaoException exception = assertThrows(ValidacaoException.class,
                    () -> webHookService.enviarNotificacao(mensagem, urlNula));

            assertEquals(ValidacaoEnum.ERRO_ENVIO_WEBHOOK.getCodigo(), exception.getCodigo());
        }
    }

    @Test
    void enviarNotificacao() {
        String mensagemVazia = "Teste";
        String url = "http://example.com/webhook";

        webHookService.enviarNotificacao(mensagemVazia, url);
    }
}