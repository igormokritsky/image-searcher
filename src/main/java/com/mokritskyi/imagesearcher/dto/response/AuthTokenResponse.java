package com.mokritskyi.imagesearcher.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthTokenResponse {

    private boolean auth;
    private String token;
}
