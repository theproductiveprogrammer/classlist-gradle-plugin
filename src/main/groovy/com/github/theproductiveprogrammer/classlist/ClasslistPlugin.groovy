package com.github.theproductiveprogrammer.classlist

import org.gradle.api.Project
import org.gradle.api.Plugin

import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry
import java.nio.file.Files;
import java.nio.file.FileSystems;

/**
 * Lists all the class files available in the current project
 * Developed for vim autocompletion
 * Lists classes from:
 *    1. The JRE
 *    2. The Project Source Set
 *    3. Project Dependencies
 */
public class ClasslistPlugin implements Plugin<Project> {
  public void apply(Project project) {
    project.tasks.register("classlist") {
      doLast {

        def fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        def top = fs.getPath("/");
        Files.walk(top).each{ f ->
          String n = f
          if(!n.endsWith(".class") || !n.contains("/java.base/")) {
              return
          }
          n = n.substring(0, n.size() - ".class".size())
          n = n.replaceFirst(~/.*java.base\//,'')
          n = n.replace('/', '.')
          println n
        }

        project.sourceSets.main.java.findAll{ f ->
          def n = f.path
          n = n.substring(0, n.size() - ".java".size())
          n = n.replaceFirst(~/.*src\//,'')
          n = n.replace('/', '.')
          println n
        }

        project.configurations.compileClasspath.findAll{ f ->
          def zip = new ZipInputStream(new FileInputStream(f.path))
          def entry = zip.nextEntry
          while(entry) {
            def n = entry.getName()
            if(n.endsWith(".class")) {
              n = n.substring(0, n.size() - ".class".size())
              n = n.replace('/', '.')
              println n
            }
            entry = zip.nextEntry
          }
        }
      }
    }
  }
}
