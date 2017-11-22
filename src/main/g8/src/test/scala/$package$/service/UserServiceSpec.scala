package $package$.service

import org.scalatest.{FlatSpecLike, Matchers}
import $package$.IOAssertion
import $package$.TestUsers._
import $package$.model.{UserName, UserNotFound}

class UserServiceSpec extends FlatSpecLike with Matchers {

  it should "retrieve an user" in IOAssertion {
    TestUserService.service.findUser(users.head.username).map { user =>
      user should be (users.head)
    }
  }

  it should "fail retrieving an user" in IOAssertion {
    TestUserService.service.findUser(new UserName("xxx")).attempt.map { result =>
      result should be (Left(UserNotFound("xxx")))
    }
  }

}
