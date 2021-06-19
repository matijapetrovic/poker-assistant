package com.bongcloud.pokerbot;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Getter
public class BotOptionsDTO {
    int playstyle;
    int numGames;
    String opponent;
}
