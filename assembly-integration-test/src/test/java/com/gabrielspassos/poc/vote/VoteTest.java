package com.gabrielspassos.poc.vote;

import com.intuit.karate.junit5.Karate;

public class VoteTest {

    @Karate.Test
    Karate testVoteScenarios() {
        return Karate.run("vote").relativeTo(getClass());
    }

}
