package com.gabrielspassos.poc.assembly;

import com.intuit.karate.junit5.Karate;

public class AssemblyTest {

    @Karate.Test
    Karate testGetAssemblyByIdScenario() {
        return Karate.run("assembly").tags("@GetAssemblyById").relativeTo(getClass());
    }

    @Karate.Test
    Karate testGetAssembliesScenario() {
        return Karate.run("assembly").tags("@GetAssemblies").relativeTo(getClass());
    }

    @Karate.Test
    Karate testUpdateAssemblyByIdScenario() {
        return Karate.run("assembly").tags("@UpdateAssemblyById").relativeTo(getClass());
    }

}
