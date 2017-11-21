package $package$

import $package$.model.{Email, User, UserName}

object TestUsers {

  val users = List(
    User(new UserName("gvolpe"), new Email("gvolpe@github.com")),
    User(new UserName("tpolecat"), new Email("tpolecat@github.com")),
    User(new UserName("msabin"), new Email("msabin@github.com"))
  )

}
