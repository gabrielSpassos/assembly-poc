# Assembly POC

* [Design da Solução](https://drive.google.com/file/d/1D89YICl0G12tjsUN1oOEWA4xbFs4ibZ7/view?usp=sharing) 
* [Kafdrop](http://localhost:19000/)
* [Assembly-Service](http://localhost:8080/swagger-ui.html)

## Como usar

#### Forma mais simples:

 - ``` chmod +x start.sh ```
 - ``` sh start.sh ```

#### Forma detalhada:

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
- ``` docker-compose build ```
- ``` docker-compose up ```
<br>

- Executar projeto de testes de integração
- ``` cd assembly-integration-test ```
- ``` ./gradlew clean test ``` 
- Reporte em ./build/reports/tests/test/index.html
- Obs: alguns testes podem falhar devido ao serviço 'user-service' ser randômico
<br>

- Executar os testes de stress
- Faça o Download do [Gatling](https://gatling.io/open-source/start-testing/)
- Faça o unzip dos arquivos do Gatling
- Coloque toda a pasta [assembly](https://github.com/gabrielSpassos/assembly-poc/tree/main/simulations/assembly) no caminho `${your_gatling_folder}/user_files/simulations/`
- Ajustar a variavel `assemblyId` para o id de um assembleia aberta
- ``` ./bin/gatling.sh ```
- Selecionar no console o número da opção `assembly.AssemblySimulation`
- Reporte em `${your_gatling_folder}/results/assemblysimulation-*/index.html`
- Obs: nesses testes o cenário onde o usuário não está apto para votar (HttpStatus 404) está sendo considerado como sucesso.
<br>

- Acessar swagger da aplicação: [Swagger-UI](http://localhost:8080/swagger-ui.html)
- Acessar kafdrop para visualização do tópico: [Kafdrop](http://localhost:19000/)


## Tecnologias
* **Java 11**: Linguagem OO, com estrutura e ecosistema robusto, uma das versões mais atualizadas. 
* **SpringBoot Webflux**: framework reativo do SpringBoot que auxilia na performance de I/O da aplicação. 
* **MongoDB**: banco de dados `schemaless` facilitando no desenvolvimento, com driver reativo já adequado para o uso do **SpringBoot Webflux**
* **Shedlock**: dependência que realiza o lock a nível de instâncias para apenas uma instância realizar um determinado job. Nesse caso a tarefa expirar a entity `assembly`
* **Apache Kafka**: plaforma de streaming de eventos amplamente utilizada em soluções corporativas, com capacidade de suportar alto throughput
* **JUnit 5**: versão atualizada do framework, com novas features facilitando a criação de testes unitários
* **Karate**: abstração do framework Cucumber, faciliando a construção dos testes de integração. [Karate Docs](https://intuit.github.io/karate/) 
* **Wiremock**: depencia de testes capaz de criar um server de mock para executar os testes de clients. [Wiremock Docs](http://wiremock.org/)
* **Pitest**: capaz de executar testes de mutação no código para avaliar qualidade do mesmo
* **Gatling**: ferramenta capaz de realizar os testes de stress na aplicação com apenas um arquivo de código para configurar as requisições HTTP, além de disponibilizar reporte gráfico. [Gatling Docs](https://gatling.io/)
* **Lombok**: plugin para auxiliar na limpeza de código dos POJO's
* **Swagger**: auxilio na documentação dos endpoints
* **Docker**: ferramenta para criar o ambiente com os containers necessários para solução proposta
* **Docker Compose**: orquestração dos containers
* **Pull Request Size**: create tag of pull request size [Docs](https://github.com/noqcks/pull-request-size)
