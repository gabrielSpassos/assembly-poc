Feature: Result feature

  Background:
    * url assemblyServiceBaseUrl
    * def assembliesEndpoint = '/v1/assemblies'
    * def resultsEndpoint = 'results'
    * header Accept = 'application/json'
    * header Content-Type = 'application/json'

  Scenario: Get results by assembly id
    * def submitVoteScenario = callonce read('classpath:com/gabrielspassos/poc/vote/vote.feature@SubmitVote')
    * def assemblyId = submitVoteScenario.response.assemblyId
    Given path assembliesEndpoint, assemblyId, resultsEndpoint
    When method GET
    Then status 200
    And match response ==
    """
    {
      "assemblyResult": "ACCEPTED",
      "acceptedVotesCount": 1,
      "declinedVotesCount": 0,
      "assembly": {
        "id": #notnull,
        "name": "teste de integração",
        "description": "descrição de integração",
        "status": "OPEN",
        "registerDateTime": #notnull,
        "updateDateTime": #notnull,
        "expirationDateTime": #notnull
      },
      "votes": [
        {
          "id": #notnull,
          "assemblyId": #notnull,
          "voteChoice": "ACCEPTED",
          "customer": {
            "id": "1",
            "cpf": "80050098012"
          }
        }
      ]
    }
    """