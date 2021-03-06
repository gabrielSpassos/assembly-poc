# Assembly POC

* [Design da Solução](https://drive.google.com/file/d/1D89YICl0G12tjsUN1oOEWA4xbFs4ibZ7/view?usp=sharing) 
* [Kafdrop](http://localhost:19000/)
* [Assembly-Service](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)

## Como usar

- Executar testes unitários
- ``` cd assembly-service ```
- ``` ./gradlew clean test ```
- Reporte em ./build/reports/tests/test/index.html


- Executar testes de mutação
- ``` cd assembly-service ```
- ``` ./gradlew pitest ```
- Reporte em ./build/reports/pitest/index.html


- Buildar o projeto
- ``` cd assembly-service ```
- ``` ./gradlew clean build ```

- Executar projeto 
- ``` docker-compose up ```

- Executar projeto de testes de integração
- ``` cd assembly-integration-test ```
- ``` ./gradlew clean test ``` 
- Obs: alguns testes podem falar devido ao serviço 'user-service' ser randômico