package br.com.fiap.hacka.notificacao.input.controller;

import br.com.fiap.hacka.notificacao.domain.exception.handler.ValidacaoMetodoHandler;
import br.com.fiap.hacka.notificacao.infrastructure.utils.MensagensUtils;
import br.com.fiap.hacka.notificacao.input.dto.NotificacaoDto;
import br.com.fiap.hacka.notificacao.service.WebHookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificacaoController.class)
@Import({ValidacaoMetodoHandler.class, MensagensUtils.class})
@TestPropertySource(properties = "spring.messages.basename=msgs/mensagens")
class NotificacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebHookService webHookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sendWebhookMessage_Success() throws Exception {
        NotificacaoDto dto = new NotificacaoDto("Test message", "http://example.com/webhook");

        mockMvc.perform(post("/notificacao-service/v1/send/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(webHookService).enviarNotificacao("Test message", "http://example.com/webhook");
    }

    @Test
    void sendWebhookMessage_InvalidRequest_MissingMensagem() throws Exception {
        NotificacaoDto dto = new NotificacaoDto(null, "http://example.com/webhook");

        mockMvc.perform(post("/notificacao-service/v1/send/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(webHookService);
    }

    @Test
    void sendWebhookMessage_InvalidRequest_MissingUrl() throws Exception {
        NotificacaoDto dto = new NotificacaoDto("Test message", null);

        mockMvc.perform(post("/notificacao-service/v1/send/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(webHookService);
    }
}