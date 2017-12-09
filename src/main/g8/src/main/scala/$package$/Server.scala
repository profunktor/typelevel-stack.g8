package $package$

import cats.effect.{Effect, IO}
import $package$.http.UserHttpEndpoint
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import org.http4s.server.blaze.BlazeBuilder

// The only place where the Effect is defined. You could change it for `monix.eval.Task` for example.
object Server extends HttpServer[IO]

class HttpServer[F[_] : Effect] extends StreamApp[F] {

  override def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, ExitCode] =
    BlazeBuilder[F]
      .bindHttp() // Default address `localhost:8080`
      .mountService(UserHttpEndpoint[F].service, "/users") // You can mount as many services as you want
      .serve

}
