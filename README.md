# Gerador de QR Code com Java 21

Esse projeto é um gerador de QR Code, feito com Java 21 e Spring Boot. A ideia é ser um serviço que gera QR Codes rapidão e de forma escalável, com integração Docker e AWS S3 para armazenamento.

---

### O que usei: 🛠

* **Java 21**: A linguagem principal do projeto.
* **Spring Boot**: Pra subir a API rapidinho e com tudo configurado.
* **Maven**: Pra gerenciar as dependências do projeto.
* **Docker**: Pra "empacotar" a aplicação e rodar em qualquer lugar sem dor de cabeça.
* **AWS S3**: Pra guardar os QR Codes gerados de forma segura e escalável (se a funcionalidade estiver implementada).

---


### Estrutura do Projeto: 📂

* `src/main/java`: Onde fica o código Java principal.
* `src/main/resources`: Arquivos de configuração do Spring Boot.
* `Dockerfile`: As instruções pra criar a imagem Docker da aplicação.
* `pom.xml`: O arquivo de configuração do Maven.

---
