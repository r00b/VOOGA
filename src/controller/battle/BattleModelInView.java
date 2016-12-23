package controller.battle;

/**
 * Interface to expose select methods from the Model to the View.
 *
 * @author Daniel Chai
 */
public interface BattleModelInView {
    double getPlayerHP();

    double getEnemyHP();

    void setPlayerHP(double playerHP);

    void setEnemyHP(double enemyHP);

    boolean checkPlayerWon();

    boolean checkPlayerLost();

    void addBattleWon();

    void addBattleLost();

    int battlesWon();

    int battlesLost();

    int getNumPokemon();

    void reduceNumPokemon();

    void resetPlayer();
}
