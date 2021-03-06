package com.gabrielspassos.poc.result;

import com.intuit.karate.junit5.Karate;

public class ResultTest {

    @Karate.Test
    Karate testResultScenarios() {
        return Karate.run("result").relativeTo(getClass());
    }

}
