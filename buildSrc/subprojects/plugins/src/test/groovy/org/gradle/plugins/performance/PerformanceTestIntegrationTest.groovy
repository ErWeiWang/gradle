package org.gradle.plugins.performance

import org.gradle.testing.PerformanceTest
import org.gradle.testing.performance.generator.tasks.NativeProjectGeneratorTask

class PerformanceTestIntegrationTest extends AbstractIntegrationTest {
    def setup() {
        buildFile << """
            plugins {
                id 'gradlebuild.int-test-image'
                id 'gradlebuild.performance-test'
            }
        """

        file("internalPerformanceTesting/build.gradle") << "apply plugin: 'java'"
        settingsFile << """
            include 'internalPerformanceTesting'
        """
    }
    def "honors branch name in channel"() {
        buildFile << """
            def distributedPerformanceTests = tasks.withType(org.gradle.testing.DistributedPerformanceTest)
            distributedPerformanceTests.all {
                // resolve these tasks
            }
            task assertChannel {
                doLast {
                    distributedPerformanceTests.each { distributedPerformanceTest ->
                        assert distributedPerformanceTest.channel.endsWith("-branch")
                    }
                }
            }
        """

        expect:
        build("assertChannel", "-Porg.gradle.performance.branchName=branch")
    }

    def "sample task adds its properties to performance test task"() {
        buildFile << """
            import ${NativeProjectGeneratorTask.canonicalName}
            import ${PerformanceTest.canonicalName}

            // sample task needs to be realized
            task sample(type: NativeProjectGeneratorTask) {
                inputs.property("example", "foobar")
            }
            task assertHasProperties {
                doLast {
                    tasks.withType(PerformanceTest) {
                        inputs.properties["example"] == "foobar"
                    }
                }
            }
        """
        expect:
        build("assertHasProperties")
    }
}
