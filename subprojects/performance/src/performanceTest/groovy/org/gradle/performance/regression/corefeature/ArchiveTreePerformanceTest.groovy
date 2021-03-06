/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.performance.regression.corefeature

import org.gradle.performance.AbstractCrossVersionPerformanceTest

class ArchiveTreePerformanceTest extends AbstractCrossVersionPerformanceTest {
    def setup() {
        runner.targetVersions = ["5.0-20180920090625+0000"]
    }

    def "visiting zip trees"() {
        given:
        runner.testProject = "archivePerformanceProject"
        runner.tasksToRun = ['visitZip']

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()
    }
    def "visiting tar trees"() {
        given:
        runner.testProject = "archivePerformanceProject"
        runner.tasksToRun = ['visitTar']

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()
    }

    def "visiting gzip tar trees"() {
        given:
        runner.testProject = "archivePerformanceProject"
        runner.tasksToRun = ['visitTarGz']

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()
    }
}
