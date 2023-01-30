package com.fnb.game.model;

import com.fnb.game.common.IPit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pit implements IPit {
    private final String id;
    private int stones;

    public Pit(String id){
        this.id = id;
        stones = 6;
    }

    @Override
    public void addStone() {
        stones++;
    }

    @Override
    public void add(int stones) {
        if (stones > 0) {
            this.stones += stones;
        }
    }

    @Override
    public String getId(){
        return this.id;
    }

    public boolean isPitEmpty(){
        return this.stones == 0;
    }

    public void emptyPit(){
        stones = 0;
    }
}
