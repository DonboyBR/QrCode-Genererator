package com.tribus.qrcode.generator.service;

import com.tribus.qrcode.generator.dto.QrCodeGenerateResponse;
import com.tribus.qrcode.generator.ports.StoragePort;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

// Essa anotação diz pro Spring que essa classe é um "serviço".
// É tipo o "faz-tudo" que orquestra as operações de negócio.
@Service
public class QrCodeGeneratorService {
    // Aqui declara a dependência do 'StoragePort'.
    // É o "contrato" de armazenamento.
    private final StoragePort storage;

    // Construtor do serviço. O Spring injeta a implementação do 'StoragePort' aqui.
    // Assim, não precisa se preocupar em criar o S3StorageAdapter, por exemplo.
    public QrCodeGeneratorService(StoragePort storage) {
        this.storage = storage; // Atribui o serviço de armazenamento injetado.
    }

    // Método principal: gera o QR Code e já manda ele pro armazenamento.
    // Pode jogar umas exceções se der ruim na escrita do QR Code ou no I/O (entrada/saída).
    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        // Cria um escritor de QR Code. É a ferramenta que vai desenhar o código.
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // Transforma o texto em uma matriz de bits (os quadradinhos pretos e brancos do QR Code).
        // Define o formato (QR_CODE) e o tamanho (200x200 pixels).
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        // Cria um "stream" de saída na memória pra guardar a imagem do QR Code.
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        // Escreve a matriz de bits (o QR Code) nesse balde, no formato PNG.
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        // Pega os bytes da imagem do QR Code. Agora ele é um array de bytes pronto pra ser salvo.
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        // Manda o arquivo (os bytes do QR Code) pro serviço de armazenamento.
        // gera um nome de arquivo único (UUID) pra não ter problema de nomes repetidos.
        // E diz que é uma imagem PNG.
        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");

        // Retorna a resposta com o link do QR Code gerado.
        return new QrCodeGenerateResponse(url);
    }
}