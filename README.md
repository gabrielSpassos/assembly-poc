# Assembly POC

* [Design da Solução](https://drive.google.com/file/d/1D89YICl0G12tjsUN1oOEWA4xbFs4ibZ7/view?usp=sharing) 
* [Kafdrop](http://localhost:19000/)
* [Assembly-Service](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)

## Como usar

<br>

- Executar testes unitários
- ``` cd assembly-service ```
- ``` ./gradlew clean test ```
- Reporte em ./build/reports/tests/test/index.html
<br>

- Executar testes de mutação
- ``` cd assembly-service ```
- ``` ./gradlew pitest ```
- Reporte em ./build/reports/pitest/index.html
<br>

- Buildar o projeto
- ``` cd assembly-service ```
- ``` ./gradlew clean build ```
<br>

- Executar projeto 
- ``` docker-compose up ```
<br>

- Executar projeto de testes de integração
- ``` cd assembly-integration-test ```
- ``` ./gradlew clean test ``` 
- Obs: alguns testes podem falar devido ao serviço 'user-service' ser randômico
<br>


## Tecnologias
* **SpringBoot Webflux**: framework reativo do SpringBoot que auxilia na performance de I/O da aplicação. 
* **MongoDB**: banco de dados `schemaless` facilitando no desenvolvimento, com driver reativo já adequado para o uso do **SpringBoot Webflux**
* **Shedlock**: dependência que realiza o lock a nível de instâncias para apenas uma instância realizar um determinado job. Nesse caso a tarefa expirar a entity `assembly`
* **Apache Kafka**: plaforma de streaming de eventos amplamente utilizada em soluções corporativas, com capacidade de suportar alto throughput
* **JUnit 5**: versão atualizada do framework, com novas features facilitando a criação de testes unitários
* **Karate**: abstração do framework Cucumber, faciliando a construção dos testes de integração. [Karate Docs](https://intuit.github.io/karate/) 
* **Wiremock**: depencia de testes capaz de criar um server de mock para executar os testes de clients. [Wiremock Docs](http://wiremock.org/)
* **Pitest**: capaz de executar testes de mutação no código para avaliar qualidade do mesmo
* **Lombok**: plugin para auxiliar na limpeza de código dos POJO's
* **Swagger**: auxilio na documentação dos endpoints
