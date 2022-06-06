1. Open https://gitpod.io/#https://github.com/armanbilge/sandbox/tree/cfr-bug
2. `sdk install scalacli`
3. `scala-cli setup-ide bug.scala`
4. Import build to Metals.
5. Metals: Show decompiled with CFR.

Output:
```
Dependency(Module(org.benf, cfr), 0.151)
org.benf.cfr.reader.Main
/workspace/sandbox/.scala-build/project_183d125c5c-45290ff20c/classes/main
List(--extraclasspath, /workspace/sandbox/.scala-build/project_183d125c5c-45290ff20c/classes/main:/workspace/sandbox/.scala-build/project_183d125c5c-45290ff20c/classes/main:/home/gitpod/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.1.2/scala3-library_3-3.1.2.jar:/home/gitpod/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.8/scala-library-2.13.8.jar:/home/gitpod/.cache/scalacli/local-repo/v0.1.7/org.virtuslab.scala-cli/stubs/0.1.7/jars/stubs.jar:/home/gitpod/.cache/coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.7.4/semanticdb-javac-0.7.4.jar, --elidescala, true, --analyseas, CLASS, bug$package.class)
Picked up JAVA_TOOL_OPTIONS:  -Xmx3435m
```