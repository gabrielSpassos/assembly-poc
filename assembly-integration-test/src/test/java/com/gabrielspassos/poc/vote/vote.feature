Feature: Vote feature

  Background:
    * url assemblyServiceBaseUrl
    * def assembliesEndpoint = '/v1/assemblies'
    * def votesEndpoint = 'votes'
    * header Accept = 'application/json'
    * header Content-Type = 'application/json'

  # This scenario may fail because user-service is random
  @SubmitVote
  Scenario: Submit vote to assembly
    * def updateAssemblyScenario = callonce read('classpath:com/gabrielspassos/poc/assembly/assembly.feature@UpdateAssemblyById')
    * def assemblyId = updateAssemblyScenario.response.id
    Given path assembliesEndpoint, assemblyId, votesEndpoint
    And request '{"choice":"Sim","customer":{"id":"1","cpf":"80050098012"}}'
    When method POST
    Then status 200
    And assert assemblyId == response.assemblyId
    And match response ==
    """
    {
      "id": #null,
      "assemblyId": "#notnull",
      "voteChoice": "ACCEPTED",
      "customer": {
        "id": "1",
        "cpf": "80050098012"
      }
    }
    """

  Scenario: Get vote by id
    * def submitVoteScenario = callonce read('classpath:com/gabrielspassos/poc/vote/vote.feature@SubmitVote')
    * def assemblyId = submitVoteScenario.response.assemblyId
    Given path assembliesEndpoint, assemblyId, votesEndpoint
    When method GET
    Then status 200
    And match response contains
    """
    {
      "id": #notnull,
      "assemblyId": "#notnull",
      "voteChoice": "ACCEPTED",
      "customer": {
        "id": "1",
        "cpf": "80050098012"
      }
    }
    """