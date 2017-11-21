Unofficial [Giter8][g8] template for the [Typelevel][typelevel] Stack ([Http4s][http4s] / [Doobie][doobie] / [Circe][circe] / [Cats Effect][cats-effect]) based on [Cats][cats] v1.0.0-RC1
==========================================================================================================================================================================================

Typelevel Stack QuickStart
--------------------------

1. Install [sbt][sbt]
2. `sbt new gvolpe/typelevel-stack.g8`
3. `cd $name;format="norm,word"$`
4. Install [PostgreSQL][postgresql] and configure access for user `postgres` and password `postgres` (or change it on `UserDao`)
5. Create database `users` and table `api_user` (see `src/main/resources/users.sql`).
6. `sbt test` (optional)
7. `sbt run`
8. `curl http://localhost:8080/users/USERNAME`

About Template
--------------

It contains the minimal code to get you started:

- `UserDao`: Defines a method to find a user.
  - `PostgresUserDao`: Implementation of the UserDao interface using Doobie and PostgreSQL.
- `UserService`: Business logic on top of the UserDao.
- `UserHttpEndpoint`: Defines the http endpoints of the REST API making use of the UserServce.
- `http` package: Includes custom Circe Json Encoders for value classes.
- `Server`: The main application that wires all the components and starts the web server.

Template License
----------------
Written in <2017> by [@gvolpe][gvolpe]

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
[typelevel]: https://typelevel.org
[http4s]: http://http4s.org/
[doobie]: http://tpolecat.github.io/doobie/
[circe]: https://circe.github.io/circe/
[cats-effect]: https://github.com/typelevel/cats-effect
[cats]: https://typelevel.org/cats/

[sbt]: http://www.scala-sbt.org/1.x/docs/Setup.html
[postgresql]: https://www.postgresql.org/download/
[gvolpe]: https://github.com/gvolpe

