package com.fnb.game.controller;

import com.fnb.game.common.IPit;
import com.fnb.game.model.LargerPit;
import com.fnb.game.model.player.GameResult;
import com.fnb.game.model.player.Move;
import com.fnb.game.service.IBoardLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@CrossOrigin
public class GameController {

    private IBoardLogicService board;
    @Autowired
    public GameController(IBoardLogicService board){
        this.board = board;
    }

    @PostMapping("/{pitId}")
    public List<IPit> sow(@PathVariable("pitId") String pitId) {
        board.play(pitId);
        return board.getBoard();
    }

    @PostMapping("/reset")
    public void resetGame() {
        board.resetBoard();
    }

    @GetMapping("/board")
    public List<IPit> getBoard() {
        return board.getBoard();
    }

    @GetMapping("/turn")
    public Move.PlayerTurn getTurn() {
        return Move.getTurn();
    }

    @GetMapping("/winner")
    public GameResult getWinner() {
        return board.getWinner();
    }

    @GetMapping("/larger-pits")
    public List<LargerPit> getLargerPits() {
        return board.getLargerPits();
    }
}
