package com.bongcloud.pokerbot;

import bots.BotRepository;
import bots.bongcloudbot.PlayerDesc;
import game.GameIDGenerator;
import game.GameRunner;
import game.HandHistoryWriter;
import game.TableSeater;
import game.cash.CashGameDescription;
import game.cash.CashGameTableSeater;
import game.deck.DeckFactory;
import game.deck.SerializedDeck;
import game.stats.BankrollGraphUI;
import lombok.RequiredArgsConstructor;
import org.drools.core.io.impl.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PokerBotService {

    public byte[] playGame(BotOptionsDTO botOptions) throws IOException {

        // number of games
        int numGames = botOptions.getNumGames();
        // if to permute seats to reduce variance
        boolean permuteSeats = true;
        // four Bots fight against each other
        // valid BotNames can be obtained from the botRepository
        String[] botNames = new String[]{"BongcloudBot/BongcloudBot", "DemoBot/AlwaysCallBot"};

        BotRepository botRepository = new BotRepository();
        TableSeater tableSeater = new CashGameTableSeater(botRepository, permuteSeats);
        GameIDGenerator gameIDGenerator = new GameIDGenerator(System.nanoTime());
        HandHistoryWriter handHistoryWriter = new HandHistoryWriter();
        String simulationFileName = new SimpleDateFormat("yyMMdd-hhmm").format(new Date());
        handHistoryWriter.setWriter(new FileWriter("./data/" + simulationFileName + "-history.txt"));

        // in the future created via GUI, and persisted via XML to the ./data/games dir
        CashGameDescription cashGameDescription = new CashGameDescription();
        cashGameDescription.setSmallBlind(0.01);
        cashGameDescription.setBigBlind(0.02);
        cashGameDescription.setInitialBankRoll(2);
        cashGameDescription.setNumGames(numGames);

        cashGameDescription.setBotNames(botNames);
        //cashGameDescription.setInGameNames(new String[] { "Simply #1", "Simply #2", "Cally #3", "Cally #4" });

        // start the game
        GameRunner runner = cashGameDescription.createGameRunner();
        BankrollGraphUI bankrollgraphUI = new BankrollGraphUI();
        runner.addBankrollObserver(bankrollgraphUI);
        DeckFactory deckFactory = SerializedDeck.createFactory("./data/decks/deck-100000.deck");
        runner.runGame(deckFactory, tableSeater, gameIDGenerator, Arrays.asList(handHistoryWriter));

        bankrollgraphUI.createGraph(simulationFileName);

        return getImage("./data/" + simulationFileName + "-chart.png");
    }

    private byte[] getImage(String imageName) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(imageName));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        return bos.toByteArray();
    }
}
