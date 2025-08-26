package br.com.fiap.hacka.notificacao.domain.model;


import br.com.fiap.hacka.notificacao.infrastructure.utils.MensagensUtils;

public interface IEnumLabel<E extends Enum<E>>{
    default String getDescricao(){
        return MensagensUtils.getEnumLabel(this);
    }

    default String getDescricao(String[] mensagem){
        return MensagensUtils.getEnumLabel(this,mensagem);
    }
}
