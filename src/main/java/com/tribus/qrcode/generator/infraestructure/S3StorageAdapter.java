package com.tribus.qrcode.generator.infrastructure;

import com.tribus.qrcode.generator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// Essa anotação diz pro Spring que essa classe é um componente.
@Component
// Essa classe é tipo a ponte entre nossa aplicação e o serviço de armazenamento (S3, nesse caso).
// Ela implementa a interface 'StoragePort', que define o que um serviço de armazenamento deve fazer.
public class S3StorageAdapter implements StoragePort {

    // Cliente do S3 da AWS. É quem faz a comunicação de fato com o serviço.
    private final S3Client s3Client;
    // Nome do bucket (onde guarda as coisas no S3). Pega esse valor das configurações.
    private final String bucketName;
    // Região da AWS (tipo 'us-east-1'). Pega esse valor das configurações também.
    private final String region;

    // Construtor da classe. O Spring injeta os valores do bucket e da região
    // que estão lá nas suas configurações (application.properties ou .yml).
    public S3StorageAdapter(@Value("${aws.s3.region}") String region,
                            @Value("${aws.s3.bucket-name}") String bucketName) {
        this.bucketName = bucketName; // Atribui o nome do bucket
        this.region = region;         // Atribui a região
        // Cria o cliente S3, configurando a região.
        this.s3Client = S3Client.builder()
                .region(Region.of(this.region))
                .build();
    }

    // Esse método é o que faz o upload do arquivo pro S3.
    // É uma implementação do que foi definido na interface 'StoragePort'.
    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        // Monta a requisição pra enviar o objeto (nosso QR Code) pro S3.
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName) // O bucket de destino
                .key(fileName)      // O nome que o arquivo vai ter lá no S3
                .contentType(contentType) // O tipo do arquivo (ex: image/png)
                .build();

        // Manda o arquivo pro S3. O 'RequestBody.fromBytes' é pra enviar os dados brutos do arquivo.
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        // Retorna o link público do arquivo que acabou de ser enviado pro S3.
        // Assim, a gente podemos acessar o QR Code depois.
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, fileName);
    }
}