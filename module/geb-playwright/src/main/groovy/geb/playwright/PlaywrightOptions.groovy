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

@SuppressWarnings(['NoDouble', 'DuplicateNumberLiteral'])
class PlaywrightOptions {
    String browserType = 'chromium'
    boolean headless = true
    double slowMo = 0
    String channel
    boolean recordVideo = false
    String videoDir = 'build/playwright/video'
    boolean tracing = false
    String tracesDir = 'build/playwright/traces'
    boolean screenshotsOnTrace = true
    boolean snapshotsOnTrace = true
    boolean sourcesOnTrace = true
    String locale
    String timezoneId
    String userAgent
    List<String> permissions = []
    Map<String, Object> geolocation
    boolean offline = false
    String colorScheme
    String reducedMotion
    Map<String, String> extraHTTPHeaders = [:]
    String recordHarPath
    String recordHarMode
    boolean acceptDownloads = true
    boolean javaScriptEnabled = true
    boolean bypassCSP = false
    double deviceScaleFactor = 1
    boolean isMobile = false
    boolean hasTouch = false
    Map<String, String> httpCredentials
    Map<String, String> proxy
    boolean blockServiceWorkers = false
    String serviceWorkers
    int viewportWidth = 1280
    int viewportHeight = 720
    boolean ignoreHTTPSErrors = false
    String baseURL
    List<String> launchArgs = []
    double defaultTimeoutMs = 30000
    double navigationTimeoutMs = 30000

    PlaywrightOptions copy() {
        PlaywrightOptions copy = new PlaywrightOptions()
        properties.each { String name, Object value ->
            if (name in ['class', 'metaClass']) {
                return
            }
            copy[name] = value instanceof List ? new ArrayList(value) : value instanceof Map ? new LinkedHashMap(value) : value
        }
        copy
    }
}
