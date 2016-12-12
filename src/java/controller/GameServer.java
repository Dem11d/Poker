package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import controller.Constants;

public class GameServer{
    
    List<Player> players;
    GameController gameController;
    Thread gameThread;
    volatile GameStatus gameStatus = GameStatus.WAITING_FOR_PLAYERS;
    boolean isNewGame = true;
    Thread mainThread;
    private GameStatus previousStatus= GameStatus.WAITING_FOR_PLAYERS;
    private Counter step= new Counter();
    Set<Player> winnerSet = new TreeSet<Player>();
   
    public GameController getGameController() {
        return gameController;
    }
    
    public GameServer() {
        this.step.setCount(0);
        mainThread= Thread.currentThread();
        gameThread = new Thread(() -> {
            while (true) {
                switch (gameStatus) {
                    case WAITING_FOR_PLAYERS:
                        if(Objects.isNull(players)||players.size()<8){}
                        else gameStatus = GameStatus.GAME_PREFLOP;
                        break;
                    case GAME_PREFLOP:
                        if (isNewGame) {
                            gameController = new GameController();
                            gameController.newGame(players);
                            gameController.preFloop();
                            isNewGame = false;
                        } else {
                            gameController.nextGame();
                            gameController.preFloop();
                        }
                        gameStatus = GameStatus.GAME_DEALER;
                        break;
                    case GAME_DEALER:
                        gameController.setDealer();
                        gameStatus = GameStatus.GAME_SB;
                        break;
                    case GAME_SB:
                        gameController.setSmallBlind();
                        gameStatus = GameStatus.GAME_BB;
                        break;
                    case GAME_BB:
                        gameController.setBigBlind();
                        gameStatus = GameStatus.GAME_PREFLOOP_RATES;
                        break;
                    case GAME_PREFLOOP_RATES:
                        gameController.rates(step);
                        gameStatus = GameStatus.GAME_FLOOP;
                        break;
                    case GAME_FLOOP:
                        gameController.floop();
                        gameStatus = GameStatus.GAME_FLOOP_RATES;
                        break;
                    case GAME_FLOOP_RATES:
                        gameController.rates(step);
                        gameStatus = GameStatus.GAME_TERN;
                        break;
                    case GAME_TERN:
                        gameController.tern();
                        gameStatus = GameStatus.GAME_TERN_RATES;
                        break;
                    case GAME_TERN_RATES:
                        gameController.rates(step);
                        gameStatus = GameStatus.GAME_RIVER;
                        break;
                    case GAME_RIVER:
                        gameController.river();
                        gameStatus = GameStatus.GAME_RIVER_RATES;
                        break;
                    case GAME_RIVER_RATES:
                        gameController.rates(step);
                        gameStatus = GameStatus.GAME_CVALIFICATION;
                        break;
                    case GAME_CVALIFICATION:
                        gameController.cvalification();
                        winnerSet = gameController.winnerSet;
                        gameStatus = GameStatus.GAME_AFTER_GAME;
                        step.increment();
                        sleep(Constants.SHOW_CARD_DELAY);
                        break;
                    case GAME_AFTER_GAME:
                        gameController.clear();
                        if(gameController.getPositiveBalancePlayers()>1){
                        gameStatus = GameStatus.GAME_PREFLOP;
                        winnerSet = new TreeSet<Player>();
                        }
                        else
                        gameStatus = GameStatus.GAME_END;
                        break;
                    case GAME_END:
                        players.clear();
                        isNewGame = true;
                        gameStatus = GameStatus.WAITING_FOR_PLAYERS;
                     
                        break;
                }
                if(!(gameStatus.toString().equals(previousStatus.toString()))){
                    previousStatus=gameStatus;
                    step.increment();
                }
                sleep(Constants.SERVER_DELAY);
            }
        });
        gameThread.start();
    }

    public int getStep() {
        return step.getCount();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String gameStatus() {

        return gameStatus.toString();
    }
  
    public int countOfPlayers(){
        int count = 0;
        if (Objects.isNull(players))
        return count;
        return players.size();
    }

    public boolean addPlayer(Player player) {
        if (Objects.isNull(player)) {
            return false;
        }
        if (Objects.isNull(players)) {
            players = new ArrayList<>(8);
        }
        if (players.size() == 8) {
            return false;
        }
        players.add(player);
        step.increment();
        return true;
    }

    private enum GameStatus {
        WAITING_FOR_PLAYERS,
        GAME_PREFLOP,
        GAME_DEALER,
        GAME_SB,
        GAME_BB,
        GAME_PREFLOOP_RATES,
        GAME_FLOOP,
        GAME_FLOOP_RATES,
        GAME_TERN,
        GAME_TERN_RATES,
        GAME_RIVER,
        GAME_RIVER_RATES,
        GAME_CVALIFICATION,
        GAME_AFTER_GAME,
        GAME_END,
    }

    public Set<Player> getWinnerSet() {
        return winnerSet;
    }
    private void sleep(int delay){
        try {
                    gameThread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }
    public void newGame(){
        this.step.setCount(0);
        this.players.clear();
        this.isNewGame=true;
        this.gameStatus = GameStatus.WAITING_FOR_PLAYERS;
        this.winnerSet = new TreeSet<Player>();
        gameThread.run();
    }
    
}
