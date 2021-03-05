package com.gabrielspassos.poc.enumerator;

public enum AssemblyResultEnum {
    ACCEPTED, DECLINED, TIED;

    public static AssemblyResultEnum getResult(Long acceptedVotesCount, Long declinedVotesCount) {
        int compareTo = acceptedVotesCount.compareTo(declinedVotesCount);

        switch (compareTo) {
            case 1:
                return ACCEPTED;
            case -1:
                return DECLINED;
            default:
                return TIED;
        }
    }
}
