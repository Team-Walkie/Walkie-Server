//package com.whyranoid.walkie.controller;
//
//import com.whyranoid.walkie.dto.response.HistoryResponse;
//import com.whyranoid.walkie.service.HistoryService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "history", description = "운동기록 API")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/history")
//public class HistoryController {
//    HttpHeaders httpHeaders = new HttpHeaders();
//    private final HistoryService historyService;
//
//    @Operation(summary = "운동기록 가져오기", description = "해당 유저의 운동기록을 가져옵니다.")
//    @ApiResponse(responseCode = "200", description = "호출 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HistoryResponse.class))))
//    @GetMapping("/my-history")
//    public ResponseEntity getHistory(
//            @RequestParam Long walkieId
//    ) {
//        return new ResponseEntity<>(historyService.getHistory(walkieId), httpHeaders, HttpStatus.OK);
//    }
//}
