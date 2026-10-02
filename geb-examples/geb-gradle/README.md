# Example Geb and Gradle Project

## Description

This is an example of incorporating Geb into a Gradle build. It shows the use of Spock and JUnit tests.

The build is set up to work with Firefox and Chrome. Have a look at the `build.gradle` and the `src/test/resources/GebConfig.groovy` files.

## Usage

The build uses Selenium Grid and Selenium's built-in Selenium Manager to download the appropriate version of the webdrivers.

The following commands will launch the tests with the individual browsers:

    ./gradlew chromeTest
    ./gradlew firefoxTest

To run with all, you can run:

    ./gradlew test

Replace `./gradlew` with `gradlew.bat` in the above examples if you're on Windows.

## Questions and issues

Please ask questions on [Groovy user mailing list][mailing_list] or in the [Groovy Community Slack][groovy_slack]'s #geb channel.

Raise issues in [Geb issue tracker][issue_tracker].

[build_status]: https://circleci.com/gh/geb/geb-example-gradle/tree/master.svg?style=shield&circle-token=38eb8de9af8f889922b91624a7943c474c0c3617 "Build Status"
[mailing_list]: https://lists.apache.org/list.html?users@groovy.apache.org
[issue_tracker]: https://github.com/apache/groovy-geb/issues/issues
[groovy_slack]: https://www.groovycommunity.com/
