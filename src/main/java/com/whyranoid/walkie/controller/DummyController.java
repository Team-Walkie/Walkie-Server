package com.whyranoid.walkie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller와 @ResponseBody가 합쳐진 어노테이션으로 주 용도는 Json 형태로 객체 데이터를 반환한다.
@RestController
@RequiredArgsConstructor
public class DummyController {

    @GetMapping("/")
    public String dummyController() {
        return "전현수 주용한 바보";
    }
}