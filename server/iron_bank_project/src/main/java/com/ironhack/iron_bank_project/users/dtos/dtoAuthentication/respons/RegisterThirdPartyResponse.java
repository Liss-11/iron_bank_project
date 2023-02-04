package com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.respons;

import lombok.Data;

@Data
public class RegisterThirdPartyResponse {
    String HashedKey;

    public RegisterThirdPartyResponse(String hashedKey) {
        HashedKey = hashedKey;
    }
    @Override
    public String toString() {
        return "Save this KEY. You must USE it to make Transactions to this Bank:\n" + HashedKey;
    }
}
