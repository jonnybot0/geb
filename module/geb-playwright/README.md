<!--
  Licensed to the Apache Software Foundation (ASF) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The ASF licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
-->

# Geb Playwright (experimental)

`geb-playwright` is an experimental Playwright-backed `WebDriver` for Geb. Selenium stays the default. `geb-core` does not depend on Playwright until a project adds this module and selects `PlaywrightDriver`.

The artifact, package, and configuration may change or be removed. This module is not covered by Geb's compatibility guarantees.

The adapter exists so current Geb code (`Browser`, pages, the content DSL, modules, `waitFor`, reporting) can run on Playwright. It implements the WebDriver calls Geb already makes. Playwright features that have no WebDriver equivalent are used through `page` and `context` on the driver, not through a second wrapper API.

## Dependency

```groovy
dependencies {
    testImplementation 'org.apache.groovy.geb:geb-playwright:<version>'
}
```

Install a browser before running tests:

```bash
npx playwright install chromium
```

## GebConfig

```groovy
import geb.playwright.PlaywrightDriver

driver = PlaywrightDriver.config {
    browserType = 'chromium' // firefox or webkit
    headless = true
}

// Playwright Java is not thread-safe.
cacheDriverPerThread = true
```

`PlaywrightDriver.config` returns a closure so Geb can create and close the driver. Use `PlaywrightDriver.create { ... }` when the test manages `quit()` itself.

`tracing` and `recordVideo` are context options. Add `geb.playwright.report.PlaywrightTraceReporter` to a `CompositeReporter` when Geb reports should include a trace archive.

## Playwright APIs

```groovy
import geb.playwright.PlaywrightWebDriver

def playwrightDriver = browser.driver as PlaywrightWebDriver
playwrightDriver.page.getByTestId('submit').click()
playwrightDriver.context.route('**/api/**') { route -> route.abort() }
```

## Limits

* `driver.manage().logs()` is unsupported.
* Playwright cannot move or minimize native browser chrome. Window position is only remembered for WebDriver callers.
* Alert handling is best-effort.
* Selenium Grid, `RemoteWebDriver`, profiles, extensions, and BiDi are not provided.

Module tests can be skipped with `-Dgeb.playwright.skip=true` or `PLAYWRIGHT_SKIP=true`.
