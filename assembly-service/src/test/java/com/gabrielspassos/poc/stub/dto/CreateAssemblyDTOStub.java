package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.CreateAssemblyDTO;

public class CreateAssemblyDTOStub {

    public static CreateAssemblyDTO create() {
        return new CreateAssemblyDTO("name", "desc");
    }
}
