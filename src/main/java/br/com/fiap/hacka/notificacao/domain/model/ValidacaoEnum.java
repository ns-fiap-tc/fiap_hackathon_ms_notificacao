package br.com.fiap.hacka.notificacao.domain.model;

import lombok.Getter;

@Getter
public enum ValidacaoEnum implements IEnumLabel<ValidacaoEnum> {
    ENTRADA_DE_DADOS_INVALIDA(-1),
    ERRO_ENVIO_WEBHOOK(-2),
    NAO_IDENTIFICADO(-999);

    private Integer codigo;

    ValidacaoEnum(Integer codigo){
        this.codigo = codigo;
    }

    public static ValidacaoEnum valueOf(Integer codigo){
        for(ValidacaoEnum val: ValidacaoEnum.values()){
            if(val.codigo.equals(codigo)) return val;
        }
        return null;
    }
}
