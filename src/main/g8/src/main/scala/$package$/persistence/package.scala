package $package$

import $package$.model._

package object persistence {

  type UserDTO = (Int, String, String)

  implicit class UserConversions(dto: UserDTO) {
    def toUser: User = User(
      username = new UserName(dto._2),
      email = new Email(dto._3)
    )
  }
  
}
