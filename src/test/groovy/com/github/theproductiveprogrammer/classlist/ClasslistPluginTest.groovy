package com.github.theproductiveprogrammer.classlist

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import spock.lang.Specification

/**
 * A simple unit test for the 'com.github.theproductiveprogrammer.classlist' plugin.
 */
public class ClasslistPluginTest extends Specification {
    def "plugin registers task"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.plugins.apply("com.github.theproductiveprogrammer.classlist")

        then:
        project.tasks.findByName("classlist") != null
    }
}
