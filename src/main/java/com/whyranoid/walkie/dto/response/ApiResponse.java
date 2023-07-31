package com.whyranoid.walkie.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse {

    @Schema(example = "200")
    Integer status;
    @Schema(example = "성공!")
    String message;
}
