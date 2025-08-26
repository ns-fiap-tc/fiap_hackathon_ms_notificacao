package br.com.fiap.hacka.notificacao.input.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validDto_NoViolations() {
        NotificacaoDto dto = new NotificacaoDto("Test message", "http://example.com");
        
        Set<ConstraintViolation<NotificacaoDto>> violations = validator.validate(dto);
        
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullMensagem_HasViolation() {
        NotificacaoDto dto = new NotificacaoDto(null, "http://example.com");
        
        Set<ConstraintViolation<NotificacaoDto>> violations = validator.validate(dto);
        
        assertEquals(1, violations.size());
    }

    @Test
    void nullUrl_HasViolation() {
        NotificacaoDto dto = new NotificacaoDto("Test message", null);
        
        Set<ConstraintViolation<NotificacaoDto>> violations = validator.validate(dto);
        
        assertEquals(1, violations.size());
    }

    @Test
    void bothFieldsNull_HasTwoViolations() {
        NotificacaoDto dto = new NotificacaoDto(null, null);
        
        Set<ConstraintViolation<NotificacaoDto>> violations = validator.validate(dto);
        
        assertEquals(2, violations.size());
    }
}