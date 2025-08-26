package br.com.fiap.hacka.application.service;

import br.com.fiap.hacka.domain.exception.ValidacaoException;
import br.com.fiap.hacka.domain.model.ValidacaoEnum;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class WebHookService {
    public void enviarNotificacao(String mensagem, String urlPost){
        System.out.println("Iniciando envio webhook");
        try {
            StringEntity requestEntity = new StringEntity(mensagem, "UTF-8");

            CloseableHttpClient httpClient = HttpClients.createDefault();

            requestEntity.setContentType("application/json");
            HttpPost post = new HttpPost(urlPost);
            post.setEntity(requestEntity);

            CloseableHttpResponse con = httpClient.execute(post);
            InputStream in = con.getEntity().getContent();
            String encoding = "UTF-8";
            String body = IOUtils.toString(in, encoding);

            System.out.println("Log de retorno do webhook: "+body);
        }catch (Exception e){
            System.out.println("Erro ao enviar notificação");
            throw new ValidacaoException(ValidacaoEnum.ERRO_ENVIO_WEBHOOK);
        }
    }
}
