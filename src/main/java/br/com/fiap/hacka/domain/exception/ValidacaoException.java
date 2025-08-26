package br.com.fiap.hacka.domain.exception;


import br.com.fiap.hacka.domain.model.ValidacaoEnum;

public class ValidacaoException extends ExceptionAbstractImpl {
    public ValidacaoException(ValidacaoEnum validation){
        super(validation);
    }

    public ValidacaoException(ValidacaoEnum validation, String... params){
        super(validation,params);
    }
}
