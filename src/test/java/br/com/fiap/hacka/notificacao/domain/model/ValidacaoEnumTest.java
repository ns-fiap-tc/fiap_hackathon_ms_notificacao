package br.com.fiap.hacka.notificacao.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoEnumTest {

    @Test
    void valueOf_ValidCodes() {
        assertEquals(ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA, ValidacaoEnum.valueOf(-1));
        assertEquals(ValidacaoEnum.ERRO_ENVIO_WEBHOOK, ValidacaoEnum.valueOf(-2));
        assertEquals(ValidacaoEnum.NAO_IDENTIFICADO, ValidacaoEnum.valueOf(-999));
    }

    @Test
    void valueOf_InvalidCode_ReturnsNull() {
        assertNull(ValidacaoEnum.valueOf(999));
        assertNull(ValidacaoEnum.valueOf(0));
    }

    @Test
    void getCodigo_ReturnsCorrectValues() {
        assertEquals(-1, ValidacaoEnum.ENTRADA_DE_DADOS_INVALIDA.getCodigo());
        assertEquals(-2, ValidacaoEnum.ERRO_ENVIO_WEBHOOK.getCodigo());
        assertEquals(-999, ValidacaoEnum.NAO_IDENTIFICADO.getCodigo());
    }
}