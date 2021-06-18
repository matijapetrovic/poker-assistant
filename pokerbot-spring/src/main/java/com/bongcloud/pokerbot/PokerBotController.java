package com.bongcloud.pokerbot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/game", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PokerBotController {
    private final PokerBotService pokerBotService;

    @PostMapping("")
    public ResponseEntity<String> playGame(BotOptionsDTO botOptions) {
        return ResponseEntity.ok(pokerBotService.playGame(botOptions));
    }
}
