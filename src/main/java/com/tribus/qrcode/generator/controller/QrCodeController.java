package com.tribus.qrcode.generator.controller;

import com.tribus.qrcode.generator.dto.QrCodeGenerateRequest;
import com.tribus.qrcode.generator.dto.QrCodeGenerateResponse;
import com.tribus.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Essa anotação diz pro Spring que essa classe vai cuidar das requisições HTTP e já devolve JSON/XML.
@RestController
// Define o caminho base pra todos os endpoints aqui.
@RequestMapping("/qrcode")
public class QrCodeController {

    // Aqui a gente declara o 'QrCodeGeneratorService'. Ele não é criado aqui, só avisamos que vamos precisar dele.
    // O 'final' é pra garantir que, uma vez que tenha ele, não vai mudar.
    private final QrCodeGeneratorService qrCodeGeneratorService;

    // Construtor do nosso Controller. O Spring Boot é esperto e já injeta a dependência do 'QrCodeGeneratorService' aqui.
    public QrCodeController(QrCodeGeneratorService qrCodeService) {
        this.qrCodeGeneratorService = qrCodeService; // Atribui o serviço injetado pra gente usar.
    }

    // Método que vai ser ativado quando alguém fizer uma requisição POST para "/qrcode".
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request){
        // Bloco try-catch pra garantir que, se algo der errado na geração do QR Code, o programa não quebre.
        try {
            // Chama o serviço pra gerar e subir o QR Code. Passamos o texto que veio na requisição.
            QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            // Se deu tudo certo, a gente devolve uma resposta HTTP 200 (OK) com os dados do QR Code gerado.
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Se der ruim (acontecer alguma exceção), a gente loga o erro (pra saber o que rolou)
            System.out.println(e);
            // E devolve uma resposta HTTP 500 (Erro Interno do Servidor), avisando que algo não deu certo.
            return ResponseEntity.internalServerError().build();
        }
    }
}

