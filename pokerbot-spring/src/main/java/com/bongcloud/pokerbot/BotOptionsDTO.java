package com.bongcloud.pokerbot;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BotOptionsDTO {
    int playstyle;
    int numGames;
    List<String> opponents;
}
