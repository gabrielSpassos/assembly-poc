Feature: Assemblies feature

  Background:
    * url assemblyServiceBaseUrl
    * def assembliesEndpoint = '/v1/assemblies'
    * header Accept = 'application/json'
    * header Content-Type = 'application/json'

  @CreateAssembly
  Scenario: Create assembly
    Given path assembliesEndpoint
    And request '{"name": "teste de integração", "description": "descrição de integração"}'
    When method POST
    Then status 200
    And match response ==
    """
    {
      "id": "#notnull",
      "name": "teste de integração",
      "description": "descrição de integração",
      "status": "CLOSED",
      "registerDateTime": #notnull,
      "updateDateTime": #null,
      "expirationDateTime": #null
    }
    """

  @GetAssemblyById
  Scenario: Get assembly by id
    * def createAssemblyScenario = callonce read('classpath:com/gabrielspassos/poc/assembly/assembly.feature@CreateAssembly')
    * def assemblyId = createAssemblyScenario.response.id
    Given path assembliesEndpoint, assemblyId
    When method GET
    Then status 200
    And assert assemblyId == response.id
    And match response ==
    """
    {
      "id": #notnull,
      "name": "teste de integração",
      "description": "descrição de integração",
      "status": "CLOSED",
      "registerDateTime": #notnull,
      "updateDateTime": #null,
      "expirationDateTime": #null
    }
    """

  @GetAssemblies
  Scenario: Get assemblies
    Given path assembliesEndpoint
    When method GET
    Then status 200
    And match response contains
    """
    {
      "id": #notnull,
      "name": "teste de integração",
      "description": "descrição de integração",
      "status": "CLOSED",
      "registerDateTime": #notnull,
      "updateDateTime": #null,
      "expirationDateTime": #null
    }
    """

  @UpdateAssemblyById
  Scenario: Update assemblyById
    * def createAssemblyScenario = callonce read('classpath:com/gabrielspassos/poc/assembly/assembly.feature@CreateAssembly')
    * def assemblyId = createAssemblyScenario.response.id
    Given path assembliesEndpoint, assemblyId
    And request '{"expirationDateTime":null,"newStatus":"OPEN"}'
    When method PATCH
    Then status 200
    And assert assemblyId == response.id
    And match response ==
    """
    {
      "id": #notnull,
      "name": "teste de integração",
      "description": "descrição de integração",
      "status": "OPEN",
      "registerDateTime": #notnull,
      "updateDateTime": #notnull,
      "expirationDateTime": #notnull
    }
    """
