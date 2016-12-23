package model.battle;

import java.util.Observable;

import api.Player;
import controller.battle.BattleModelInView;
import model.block.blocktypes.EnemyBlock;

/**
 * @author Daniel Chai
 */
public class BattleModel extends Observable implements BattleModelInView {
    private Player player;
    private EnemyBlock enemy;

    private boolean playerWon = false;
    private boolean playerLost = false;

    public BattleModel(Player player, EnemyBlock enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public double getPlayerHP() {
        return player.getHealth();
    }

    @Override
    public double getEnemyHP() {
        return enemy.getHealth();
    }

    @Override
    public void setPlayerHP(double playerHP) {
        if (playerHP <= 0) {
            playerLost = true;
            player.setHealth(0);
        } else {
            player.setHealth(playerHP);
        }

        setChanged();
        notifyObservers();
    }

    @Override
    public void setEnemyHP(double enemyHP) {
        if (enemyHP <= 0) {
            playerWon = true;
            enemy.setWalkableStatus(true);
            enemy.setHealth(0);
        } else {
            enemy.setHealth(enemyHP);
        }

        setChanged();
        notifyObservers();
    }

    @Override
    public boolean checkPlayerWon() {
        return playerWon;
    }

    @Override
    public boolean checkPlayerLost() {
        return playerLost;
    }

    @Override
    public void addBattleWon() {
        player.incrementBattlesWon();
    }

    @Override
    public void addBattleLost() {
        player.incrementBattlesLost();
    }

    @Override
    public int battlesWon() {
        return player.getBattlesWon();
    }

    @Override
    public int battlesLost() {
        return player.getBattlesLost();
    }

    @Override
    public int getNumPokemon() {
        return player.getNumPokemon();
    }

    @Override
    public void reduceNumPokemon() {
        player.decrementNumPokemon();
    }

    @Override
    public void resetPlayer() {
        playerWon = false;
        playerLost = false;
        player.setHealth(Player.DEFAULT_HEALTH);
        setChanged();
        notifyObservers();
    }
}
