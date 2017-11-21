package $package$.http

import cats.effect.IO
import org.http4s.{HttpService, Request, Status, Uri}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpecLike, Matchers}
import $package$.IOAssertion
import $package$.TestUsers._
import $package$.http.ResponseBodyUtils._
import $package$.model.UserName
import $package$.service.TestUserService

class UserHttpEndpointSpec extends UserHttpEndpointFixture with FlatSpecLike with Matchers {

  val httpService: HttpService[IO] = new UserHttpEndpoint[IO](TestUserService.service).service

  forAll(examples) { (username, expectedStatus, expectedBody) =>
    it should s"find the user with username: \$username" in IOAssertion {
      val request = Request[IO](uri = Uri(path = s"/users/\${username.value}"))
      httpService(request).value.map { task =>
        task should not be None
        task.fold(fail("Empty response")) { response =>
          response.status should be(expectedStatus)
          response.body.asString should be(expectedBody)
        }
      }
    }
  }

}

trait UserHttpEndpointFixture extends PropertyChecks {

  private val user1 = users.head
  private val user2 = users.tail.head
  private val user3 = users.last

  val examples = Table(
    ("username", "expectedStatus", "expectedBody"),
    (user1.username, Status.Ok, s"""{"username":"\${user1.username.value}","email":"\${user1.email.value}"}"""),
    (user2.username, Status.Ok, s"""{"username":"\${user2.username.value}","email":"\${user2.email.value}"}"""),
    (user3.username, Status.Ok, s"""{"username":"\${user3.username.value}","email":"\${user3.email.value}"}"""),
    (new UserName("xxx"), Status.NotFound, s""""xxx"""")
  )
}
