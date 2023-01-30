package com.fnb.game.service;

import com.fnb.game.common.IPit;
import com.fnb.game.model.LargerPit;
import com.fnb.game.model.Pit;
import com.fnb.game.model.player.GameResult;
import com.fnb.game.model.player.Move;
import com.fnb.game.model.player.Move.PlayerTurn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fnb.game.constants.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Map.entry;

@Service
public class GamePlayLogicService implements IGamePlayLogicService{
    private final List<IPit> board;
    private int lastPitIndex;

    public static final String PLAYER_1_PATTERN = "^PLAYER_1_PIT_[1-6]$";
    public static final String PLAYER_2_PATTERN = "^PLAYER_2_PIT_[1-6]$";

    private final Map<String, String> opponentPits = new HashMap<>(Map.ofEntries(
            entry("PLAYER_1_PIT_1", "PLAYER_2_PIT_6"),
            entry("PLAYER_1_PIT_2", "PLAYER_2_PIT_5"),
            entry("PLAYER_1_PIT_3", "PLAYER_2_PIT_4"),
            entry("PLAYER_1_PIT_4", "PLAYER_2_PIT_3"),
            entry("PLAYER_1_PIT_5", "PLAYER_2_PIT_2"),
            entry("PLAYER_1_PIT_6", "PLAYER_2_PIT_1"),
            entry("PLAYER_2_PIT_1", "PLAYER_1_PIT_6"),
            entry("PLAYER_2_PIT_2", "PLAYER_1_PIT_5"),
            entry("PLAYER_2_PIT_3", "PLAYER_1_PIT_4"),
            entry("PLAYER_2_PIT_4", "PLAYER_1_PIT_3"),
            entry("PLAYER_2_PIT_5", "PLAYER_1_PIT_2"),
            entry("PLAYER_2_PIT_6", "PLAYER_1_PIT_1")
    ));

    @Autowired
    public GamePlayLogicService(List<IPit> board) {
        this.board = board;
    }

    @Override
    public void sowStone(String pitId) {
        int pitIndex = getDigIndexById(pitId);

        if (pitIndex == -1) {
            throw new RuntimeException("Invalid pit.");
        }
        Pit pit = (Pit) board.get(pitIndex);

        if (pit.isPitEmpty()) {
            throw new RuntimeException("This pit does not contain any stones to sow.");
        }
        int seedsToSow = pit.getStones();
        pit.emptyPit();

        while (seedsToSow > 0) {
            pitIndex = getNextDigIndex(pitIndex);
            board.get(pitIndex).addStone();
            seedsToSow--;
        }
        this.lastPitIndex = pitIndex;
    }

    @Override
    public int getLastIndex() {
        return lastPitIndex;
    }

    private int getDigIndexById(String id) {
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private int getNextDigIndex(int pitIndex) {
        if (pitIndex == 12) {
            return 0;
        }
        return pitIndex + 1;
    }

    @Override
    public void checkCapture(int lastIndex) {
        int seedsInLastPit = board.get(lastIndex).getStones();
        String lastPitId = board.get(lastIndex).getId();

        if (isLargerPit(lastPitId) || doesNotOwnThisPit(lastPitId)) {
            return;
        }

        if (seedsInLastPit == 1) {
            captureOpponentStones(lastPitId);
        }
    }

    @Override
    public boolean canMove(int lastIndex) {
        String lastPitId = board.get(lastIndex).getId();

        return lastPitId.equals(Constant.PLAYER_1_LARGER_PIT) ||
                lastPitId.equals(Constant.PLAYER_2_LARGER_PIT);
    }

    @Override
    public boolean doesNotOwnThisPit(String chosenPit) {
        if (PlayerTurn.PLAYER_1 == Move.getTurn()) {
            Pattern pattern = Pattern.compile(PLAYER_1_PATTERN);
            Matcher correctPattern = pattern.matcher(chosenPit);
            return !correctPattern.matches();
        }
        Pattern pattern = Pattern.compile(PLAYER_2_PATTERN);
        Matcher correctPattern = pattern.matcher(chosenPit);
        return !correctPattern.matches();
    }

    @Override
    public boolean hasStones() {
        int count = 0;
        int i = 0;

        while (count == 0 && i < 6) {
            count = board.get(i).getStones();
            i++;
        }
        return count != 0;
    }

    @Override
    public void AddStonesToLargerPit() {
        int stones = 0;
        Pit pit;
        for (int i = 0; i < 6; i++) {
            stones += board.get(i).getStones();
            pit = (Pit) board.get(i);
            pit.emptyPit();
        }
        getLargerPit().add(stones);
    }

    @Override
    public boolean isLargerPit(String pitId) {
        return pitId.equals("FARMER_1_STORE") || pitId.equals("FARMER_2_STORE");
    }

    @Override
    public LargerPit getLargerPit() {
        return (LargerPit) board.get(6);
    }

    @Override
    public GameResult getWinner(int player1Stones, int player2Stones) {
        if (player1Stones == player2Stones) {
            return GameResult.DRAW;
        }
        return player1Stones > player2Stones ? GameResult.PLAYER_1 : GameResult.PLAYER_2;
    }

    private void captureOpponentStones(String lastPitId) {
        Pit lastPitSown = getPitById(lastPitId);
        int lastPitSeed = lastPitSown.getStones();

        Pit opponentPit = getPitById(opponentPits.get(lastPitId));

        if (!opponentPit.isPitEmpty()) {
            int opponentStones = opponentPit.getStones();
            opponentPit.emptyPit();
            lastPitSown.emptyPit();

            LargerPit largerPit = getLargerPit();
            largerPit.add(opponentStones + lastPitSeed);
        }
    }

    private Pit getPitById(String id) {
        int pitIndex = getDigIndexById(id);
        if (pitIndex != -1) {
            return (Pit)board.get(pitIndex);
        }
        throw new RuntimeException("Pit not found.");
    }
}
