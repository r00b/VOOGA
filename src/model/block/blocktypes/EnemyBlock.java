package model.block.blocktypes;

import model.block.AbstractBlock;

/**
 * Class for putting enemies on the board
 *
 * @author Daniel Chai, Bill Xiong
 */
public class EnemyBlock extends AbstractBlock {
    public static final int DEFAULT_HEALTH = 100;

    private double health;

    public EnemyBlock(String name, int row, int col) {
        super(name, row, col);
        this.health = DEFAULT_HEALTH;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double val) {
        health = val;
    }
}
