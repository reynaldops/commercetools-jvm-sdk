package io.sphere.sdk.plugin;

import javassist.*;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@Mojo(name = "process-classes", threadSafe = true, defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class ChildCreationMojo extends AbstractMojo {

    private ClassPool pool = ClassPool.getDefault();


    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(property = "artifactsToProcess")
    private String[] artifactsToProcess;

    private String childClassPrefix = "___";

    public ChildCreationMojo() {
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {

            if (artifactsToProcess == null || artifactsToProcess.length == 0)
                return;

            List<String> set = Arrays.asList(artifactsToProcess);
            List<Artifact> artifacts = project.getDependencyArtifacts()
                    .stream().filter(artifact -> set.contains(artifact.getArtifactId()))
                    .collect(Collectors.toList());

            for (Artifact artifact : artifacts) {
                File jarLocation = artifact.getFile();

                loadJarToClassPath(jarLocation);

                JarFile jarFile = new JarFile(jarLocation);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry next = entries.nextElement();
                    if (!next.isDirectory()
                            && !next.getName().contains("$")
                            && next.getName().endsWith(".class")) {

                        String className = next.getName().replace(File.separator, ".").replaceAll(".class$", "");
                        generateChildClasses(className, project.getBuild().getOutputDirectory());
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new MojoExecutionException("Error occurred while processing dependencies", e);
        }

    }

    private void loadJarToClassPath(final File jarFile) throws Exception {
        pool.appendClassPath(jarFile.getAbsolutePath());
    }

    private void generateChildClasses(final String className, final String outDire) throws Exception {

            CtClass superClass = pool.get(className);

            if(!shouldCreateChild(superClass))
                return;

            StringBuilder stringBuilder = new StringBuilder(className);
            String newClassName = stringBuilder.insert(className.lastIndexOf(".")+1,childClassPrefix).toString();
            CtClass child = pool.makeClass(newClassName );
            child.setSuperclass(superClass);
            child.writeFile(outDire);

    }

    private boolean shouldCreateChild(final CtClass superClass) throws Exception{

        //only classes containing Tests shoul be processed
        boolean isTestClass = false;

        for(CtMethod method : superClass.getMethods()){
            if(method.getAnnotation(Test.class) != null){
                isTestClass = true;
                break;
            }
        }
        if(!isTestClass)
            return false;

        if (Modifier.isFinal(superClass.getModifiers()) || Modifier.isInterface(superClass.getModifiers()))
            throw new Exception(superClass.getName() + " should not be final in order to be able to extend it for test.");

        boolean allConstructorsPrivate = Arrays.stream(superClass.getConstructors())
                .map(CtConstructor::getModifiers)
                .map(Modifier::isPrivate)
                .reduce((a, b) -> a && b).orElse(true);

        if (allConstructorsPrivate)
            throw new Exception(superClass.getName() + " should contain at least one non private constructor");

        return true;

    }


}