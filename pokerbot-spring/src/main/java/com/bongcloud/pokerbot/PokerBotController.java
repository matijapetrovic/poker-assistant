package com.bongcloud.pokerbot;

import lombok.RequiredArgsConstructor;
import org.drools.core.io.impl.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/game", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PokerBotController {
    private final PokerBotService pokerBotService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> playGame(@RequestBody BotOptionsDTO botOptions) throws IOException {
        return ResponseEntity.ok(pokerBotService.playGame(botOptions));
    }

}
