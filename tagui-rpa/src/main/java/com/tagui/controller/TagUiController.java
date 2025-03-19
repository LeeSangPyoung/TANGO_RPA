package com.tagui.controller;

import com.tagui.service.TagUiService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tagui")  // 🌟 중요! API 경로 확인
public class TagUiController {
    private final TagUiService tagUiService;

    public TagUiController(TagUiService tagUiService) {
        this.tagUiService = tagUiService;
    }

    @GetMapping("/execute/{actionId}")  // 🌟 실행할 API 엔드포인트
    public String executeRpaByActionId(@PathVariable String actionId) { 
        System.out.println("✅ API 호출됨 - actionId: " + actionId);
        return tagUiService.executeRpaByActionId(actionId);
    }
}