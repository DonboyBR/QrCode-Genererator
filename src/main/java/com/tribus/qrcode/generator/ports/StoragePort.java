package com.tribus.qrcode.generator.ports;

// Essa é uma interface. Pensa nela como um "contrato" ou uma lista de "tarefas"
// que qualquer classe que queira armazenar algo (tipo no S3, ou em outro lugar)
// precisa ser capaz de fazer. É pra garantir que todo "armazenador" vai ter esse método.
public interface StoragePort {
    // Esse método define que qualquer classe que implementar essa interface
    // TEM QUE ter uma forma de "uploadFile" (enviar arquivo).
    // Ela espera receber os dados do arquivo (em bytes), o nome que o arquivo vai ter
    // e o tipo de conteúdo (ex: "image/png").
    // E, depois de tudo, ela deve devolver o link onde o arquivo foi salvo.
    String uploadFile(byte[] fileData, String fileName, String contentType);
}