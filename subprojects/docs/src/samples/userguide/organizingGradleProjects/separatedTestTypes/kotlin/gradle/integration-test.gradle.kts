// tag::custom-source-set[]
sourceSets {
    create("integTest") {
        java.srcDir(file("src/integTest/java"))
        resources.srcDir(file("src/integTest/resources"))
        compileClasspath = compileClasspath + sourceSets["main"].output + configurations.testRuntimeClasspath
        runtimeClasspath = runtimeClasspath + output + compileClasspath
    }
}
// end::custom-source-set[]

// tag::test-task[]
task<Test>("integTest") {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = sourceSets["integTest"].output.classesDirs
    classpath = sourceSets["integTest"].runtimeClasspath
    mustRunAfter(tasks["test"])
}

tasks["check"].dependsOn(tasks["integTest"])
// end::test-task[]
