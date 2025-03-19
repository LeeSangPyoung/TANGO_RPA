package com.tagui.controller;

import com.tagui.service.TagUiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tagui")
public class RpaController {
    private final TagUiService tagUiService;

    public RpaController(TagUiService tagUiService) {
        this.tagUiService = tagUiService;
    }

    // ✅ 특정 actionId로 실행 (GET 요청)
//    @GetMapping("/execute/{actionId}")
//    public String executeRpaByActionId(@PathVariable String actionId) {
//    	System.out.println("18181818");
//        return tagUiService.executeRpaByActionId(actionId);
//    }
}
