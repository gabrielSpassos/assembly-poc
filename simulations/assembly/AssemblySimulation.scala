package assembly

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

class AssemblySimulation extends Simulation {

  val noOfUsers = 50
  val rampUpTimeSecs = 60 seconds
  val minWaitMs = 1000 milliseconds
  val maxWaitMs = 5000 milliseconds
  val baseURL = "http://localhost:8080"
  var assemblyId:String = "60453c47b702994f4c443a3f" //put the assemblyId here

  val httpConfiguration = http
    .baseUrl(baseURL)
    .acceptHeader("application/json")

  val scn = scenario("Create Votes and Get Results")
    .exec(http(s"Submit vote customer 1 to assembly ${assemblyId}")
      .post(s"/v1/assemblies/${assemblyId}/votes")
      .body(StringBody("""{"choice":"Sim","customer":{"id":"1","cpf":"54979576095"}}""".stripMargin)).asJson
      .check(status.in(200, 404))
    ).pause(minWaitMs, maxWaitMs)
    .exec(http(s"Submit vote customer 2 to assembly ${assemblyId}")
      .post(s"/v1/assemblies/${assemblyId}/votes")
      .body(StringBody("""{"choice":"Não","customer":{"id":"2","cpf":"75719102000"}}""".stripMargin)).asJson
      .check(status.in(200, 404))
    ).pause(minWaitMs, maxWaitMs)
    .exec(http(s"Get assembly ${assemblyId} results")
      .get(s"/v1/assemblies/${assemblyId}/results")
      .check(status.is(200))
    )

  setUp(
    scn.inject(rampUsers(noOfUsers) during (rampUpTimeSecs))
  ).protocols(httpConfiguration)


}