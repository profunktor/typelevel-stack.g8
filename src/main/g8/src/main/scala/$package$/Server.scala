package $package$

import cats.effect.{Effect, IO}
import fs2.Stream
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.{ExitCode, StreamApp}

// The only place where the Effect is defined. You could change it for `monix.eval.Task` for example.
object Server extends HttpServer[IO]

class HttpServer[F[_] : Effect] extends StreamApp[F] {

  override def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, ExitCode] =
    BlazeBuilder[F]
      .bindHttp() // Default address `localhost:8080`
      .mountService(UserHttpEndpoint[F].service) // You can mount as many services as you want
      .serve

}
