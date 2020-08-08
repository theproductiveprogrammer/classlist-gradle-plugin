package com.github.theproductiveprogrammer.classlist

import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner

/**
 * A simple functional test for the 'com.github.theproductiveprogrammer.classlist' plugin.
 */
public class ClasslistPluginFunctionalTest extends Specification {
    def "can run task"() {
        given:
        def projectDir = new File("build/functionalTest")
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle") << ""
        new File(projectDir, "build.gradle") << """
            plugins {
                id('com.github.theproductiveprogrammer.classlist.classlist')
            }
        """

        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("classlist")
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains("List of classes from plugin 'com.github.theproductiveprogrammer.classlist.classlist'")
    }
}
