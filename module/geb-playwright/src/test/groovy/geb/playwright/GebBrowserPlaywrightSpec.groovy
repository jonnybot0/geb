/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package geb.playwright

import com.sun.net.httpserver.HttpServer
import geb.Browser
import geb.Configuration
import geb.playwright.report.PlaywrightTraceReporter
import geb.report.ReportState
import org.opentest4j.TestAbortedException

import java.nio.file.Files
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Specification

/**
 * Verifies that the standard Geb Browser / Page / content DSL works on top of the Playwright driver.
 */
@IgnoreIf({ System.getProperty('geb.playwright.skip') == 'true' || System.getenv('PLAYWRIGHT_SKIP') == 'true' })
class GebBrowserPlaywrightSpec extends Specification {

    @Shared
    HttpServer server

    @Shared
    String baseUrl

    Browser browser

    def setupSpec() {
        server = HttpServer.create(new InetSocketAddress(0), 0)
        server.createContext('/') { exchange ->
            byte[] body = '''<!doctype html>
<html>
<head><title>Geb Playwright</title></head>
<body>
  <h1 id="heading">Hello Playwright</h1>
  <input id="name" name="name" value="">
  <button id="go" type="button"
    onclick="document.getElementById('out').textContent=document.getElementById('name').value">Go</button>
  <div id="out"></div>
</body>
</html>'''.getBytes('UTF-8')
            exchange.responseHeaders.add('Content-Type', 'text/html; charset=UTF-8')
            exchange.sendResponseHeaders(200, body.length)
            exchange.responseBody.withCloseable { it.write(body) }
        }
        server.start()
        baseUrl = "http://localhost:${server.address.port}/"
    }

    def cleanupSpec() {
        server?.stop(0)
    }

    def cleanup() {
        browser?.quit()
    }

    def "Geb Browser content DSL runs on Playwright"() {
        given:
        try {
            def conf = new Configuration()
            conf.baseUrl = baseUrl
            conf.driverConf = PlaywrightDriver.config {
                browserType = 'chromium'
                headless = true
            }
            conf.cacheDriver = false
            browser = new Browser(conf)
        } catch (Exception exception) {
            throw new TestAbortedException("Playwright Chromium is unavailable: ${exception.message}", exception)
        }

        when:
        browser.go('/')
        browser.page(GebPlaywrightSmokePage)
        browser.page.nameField.value('Geb')
        browser.page.goButton.click()

        then:
        browser.at(GebPlaywrightSmokePage)
        browser.page.heading.text() == 'Hello Playwright'
        browser.page.output.text() == 'Geb'
        browser.driver instanceof PlaywrightWebDriver
        (browser.driver as PlaywrightWebDriver).page.url().startsWith('http://localhost')
    }

    def 'trace reporter archives the active trace'() {
        given:
        def reportDirectory = Files.createTempDirectory('geb-playwright-report-')
        try {
            browser = new Browser()
            browser.driver = PlaywrightDriver.create {
                browserType = 'chromium'
                headless = true
                tracing = true
            }
        } catch (Exception exception) {
            reportDirectory.toFile().deleteDir()
            throw new TestAbortedException("Playwright Chromium is unavailable: ${exception.message}", exception)
        }

        when:
        new PlaywrightTraceReporter().writeReport(new ReportState(browser, 'trace', reportDirectory.toFile()))

        then:
        Files.size(reportDirectory.resolve('trace.zip')) > 0
        (browser.driver as PlaywrightWebDriver).session.tracingStarted

        cleanup:
        reportDirectory?.toFile()?.deleteDir()
    }
}
