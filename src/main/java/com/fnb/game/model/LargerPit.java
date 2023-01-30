package com.fnb.game.model;

import com.fnb.game.common.IPit;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LargerPit implements IPit {
    private int stones;
    private final String id;

    public LargerPit(String id){
        this.id = id;
        this.stones = 0;
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
}
