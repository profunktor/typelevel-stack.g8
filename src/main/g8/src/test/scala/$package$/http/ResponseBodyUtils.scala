package $package$.http

import cats.effect.IO
import org.http4s.EntityBody

object ResponseBodyUtils {

  implicit class ByteVector2String(body: EntityBody[IO]) {
    def asString: String = {
      val array = body.compile.toVector.unsafeRunSync().toArray
      new String(array.map(_.toChar))
    }
  }

}
