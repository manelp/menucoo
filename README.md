# menucoo #

TODO

## License ##

This code is licensed under the Mozilla Public License Version 2.0, see the
[LICENSE](LICENSE) file for details.

## System requirements ##

- Java 11
- PostgreSQL 11 (or higher)

## Developer guide ##

For development and testing you need to install [sbt](http://www.scala-sbt.org/).
Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details how to to contribute
to the project.

During development you can start (and restart) the application via the `reStart`
sbt task provided by the sbt-revolver plugin.

### Tests ###

Tests are included in the project. You can run them via the appropriate sbt tasks
`test` and `IntegrationTest/test`. The latter will execute the integration tests.
Be aware that the integration tests might need a working database.

## Deployment guide ##

