package com.whyranoid.walkie.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {

    Integer status;
    String message;
}
