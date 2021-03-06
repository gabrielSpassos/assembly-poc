# Assembly POC

* [Design da Solução](https://drive.google.com/file/d/1D89YICl0G12tjsUN1oOEWA4xbFs4ibZ7/view?usp=sharing) 
* [Kafdrop](http://localhost:19000/)
* [Assembly-Service](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)

## Como usar

- Executar testes unitários

```
./gradlew clean test
```

Obs: Reports at ./assembly-service/build/reports/tests/test/index.html


- Executar testes de mutação

```
./gradlew pitest
```

Obs: Reports at ./assembly-service/build/reports/pitest/index.html


- Buildar o projeto

```
./gradlew clean build
```