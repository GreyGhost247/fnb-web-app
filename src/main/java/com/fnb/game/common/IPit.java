package com.fnb.game.common;

public interface IPit {
    String getId();
    void addStone();
    void add(int stones);

    int getStones();
}
