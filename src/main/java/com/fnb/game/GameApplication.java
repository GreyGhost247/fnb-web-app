package com.fnb.game;

import com.fnb.game.controller.GameController;
import com.fnb.game.model.LargerPit;
import com.fnb.game.model.Pit;
import com.fnb.game.service.BoardLogicService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {GameController.class, BoardLogicService.class})
public class GameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}

}
