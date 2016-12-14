package controller;

import javafx.collections.FXCollections;

import java.util.*;



public class GameController {
    private GameBoard gameBoard;
    private CardHolder cardHolder;
    
    public Set<Player> winnerSet = new TreeSet<Player>();
    private Iterator<Player> dealerIterator;
    private Player dealerPlayer=null;
    private List<Player> playersInGame;
    @Deprecated
    private List<Player> allPlayers;
    private boolean firstRate=true;
    //private List<Player> allPlayers;


    public GameController nextGame() {
        cardHolder = new CardHolder();
        return this;
    }

    public GameController newGame(List<Player> players, double bigBlind, double playersBalance) {
        //создание игрального стола
        this.gameBoard = new GameBoard();
        
        this.allPlayers = players;
        this.cardHolder = new CardHolder();
        gameBoard.players = players;
        //инициализация итератора большого блайнда и малого
        this.gameBoard.setBigBlibd(bigBlind);
        this.gameBoard.setSmallBlibd(bigBlind / 2);
        //this.allPlayers = new ArrayList<Player>(players);
        this.playersInGame = new ArrayList<Player>(players);

        // Установка баланса игрокам
       for (Player player:players) {
            player.setBalance(playersBalance);
        }


        // Установка диллера, большого и малого блайнда
        dealerIterator = allPlayers.iterator();
        return this;
    }

    public GameController newGame(List<Player> players) {
        return newGame(players, 30, 1000);
    }

    public void preFloop() {
        playersInGame = new ArrayList<Player>(allPlayers);
        List<Player> losePlayers = new ArrayList<Player>();
        for (Player player:playersInGame) {
            
            // если баланс =0 или меньше 0 - игрок выбрасывается из игры.
            if (player.isPositiveBalance())
                while (player.setCard(cardHolder.nextRandCard())) ;
            else {
                losePlayers.add(player);
                player.setLoose(true);
            }

        }
        if(losePlayers.size()>0){
              for (Player player:losePlayers) {
                  playersInGame.remove(player);
              }
        }
    }
    
    public void setDealer(){
        if(dealerPlayer==null){
            dealerPlayer = allPlayers.get(0);
        }else{
        dealerPlayer = getNextPlayer(allPlayers, dealerPlayer);
        }
            while(dealerPlayer.isAllIn()||dealerPlayer.isFold()||!dealerPlayer.isPositiveBalance())
            dealerPlayer = getNextPlayer(allPlayers, dealerPlayer);
        dealerPlayer.setDealer(true);
        gameBoard.setDealerPlayer(dealerPlayer);
        
//        if (dealerIterator.hasNext()) {
//        if (false) {
//            Player player = dealerIterator.next();
//            if (player.isLoose) {
//                setDealer();
//
//            } else {
//                gameBoard.setDealerPlayer(player);
//                player.setDealer(true);
//            }
//
//        } else {
//            dealerIterator = allPlayers.iterator();
//            if (dealerIterator.hasNext()) {
//
//                Player player = dealerIterator.next();
//                if (player.isLoose) {
//                    setDealer();
//                } else {
//                    gameBoard.setDealerPlayer(player);
//                    player.setDealer(true);
//                }
//            }
//
//        }
    }
    
    private Player getNextPlayer(List list, Player player){
        Player nextPlayer = null;
        //проверка на наличие в коллекции игрока
        if(list.contains(player)){
            int index = list.indexOf(player);
            int nextIndex = index+1;
            if(nextIndex==list.size())
                nextIndex=0;
            nextPlayer = (Player) list.get(nextIndex);
        }
        return nextPlayer;
    }
    
    public void setSmallBlind() {
        double rate = gameBoard.getBigBlibd() / 2;
        Player dealerPlayer = gameBoard.getDealerPlayer();
        Player smallBlindPlayer = getNextPlayer(playersInGame, dealerPlayer);
        smallBlindPlayer.setSmallBlind(true);
        gameBoard.setSmallBlindPlayer(smallBlindPlayer);
        smallBlindPlayer.setRate(rate);
    }

    public void setBigBlind() {
        double rate = gameBoard.getBigBlibd();
        Player smallBlindPlayer = gameBoard.getSmallBlindPlayer();
        Player bigBlindPlayer = getNextPlayer(playersInGame, smallBlindPlayer);
        bigBlindPlayer.setBigBlind(true);
        gameBoard.setBigBlindPlayer(bigBlindPlayer);
        bigBlindPlayer.setRate(rate);
    }

    public void rates(Counter steps) {
/*добавление в банк ставок большого и малого блайнда!
  проход одного круга ставок!
* добавление ставок в банк
* проверка одинаковы ли ставки
* проход ещё одного круга ставок с последнего поставившего*/
//добавление в банк ставок большого и малого блайнда
if(playersInGame.size()>0){
        if (this.gameBoard.getLastRater() == null) {
           //gameBoard.getSmallBlindPlayer().setSmallBlind(false);
            gameBoard.setBiggestRate(gameBoard.getBigBlindPlayer().getRate());
            //gameBoard.getBigBlindPlayer().setBigBlind(false);
            int index = playersInGame.indexOf(gameBoard.getBigBlindPlayer());
            index++;
            boolean marker = true;
            while (marker) {
                if(index==playersInGame.size())
                    index = 0;
                if (playersInGame.get(index).isLoose())
                    index++;
                else {
                    gameBoard.setLastRater(playersInGame.get(index));
                    marker = false;
                }
            }
            rates(steps);
        } else {
            loopRate(playersInGame, steps);

            while (!checkRates(playersInGame)) {
                rates(steps);
            }

            fillBank(allPlayers);
            //gameBoard.setLastRaterNull();
        }
            //return allPlayers;
    }
    }

    private void loopRate(List<Player> players, Counter step) {
        Player nextRater=null;
        //int index = playersInGame.indexOf(gameBoard.getLastRater());
        if(firstRate){
        nextRater = getNextPlayer(allPlayers, gameBoard.getBigBlindPlayer());
        while(nextRater.isAllIn()||nextRater.isFold()||!nextRater.isPositiveBalance())
            nextRater = getNextPlayer(allPlayers, nextRater);
        //firstRate=false;
        }else{
        nextRater = getNextPlayer(allPlayers, gameBoard.getDealerPlayer());
         while(nextRater.isAllIn()||nextRater.isFold()||!nextRater.isPositiveBalance())
            nextRater = getNextPlayer(allPlayers, nextRater);
        }
        // цикл пройдет по всем игрокам
        List<Player> foldPlayers = new ArrayList<>();// сохранение игроков, которые выбыли
        for (int j = players.size(); j > 0; j--) {

            Player player = nextRater;
            nextRater=getNextPlayer(allPlayers, player);
            while(nextRater.isAllIn()||nextRater.isFold()||!nextRater.isPositiveBalance())
                nextRater = getNextPlayer(allPlayers, nextRater);
            if(gameBoard.getLastRaiser()==player){
                gameBoard.setLastRaiser(null);
                break;
            }
            if(players.contains(player)) {
                gameBoard.setLastRater(player);
                   //установка возможности игроку делать ставку
                player.setGamesitustion(gameBoard);
                step.increment();
                player.setRateAccess(true);
                //вход в ожидание ставки
                while (true){
                    try {
                        Thread.currentThread().sleep(200);
                        if(!(player.rateAccess))
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //проверка на fold игроков
                boolean isFold = player.isFold();
                if (isFold) {
                    foldPlayers.add(player);
                }
            }
        }
        //удаление fold игроков из игры
        if (!foldPlayers.isEmpty()) {
            for (Player player : foldPlayers) {
                //player.setFold(false);
                players.remove(player);
                //если есть оллин банки - то удаление из них тоже
                 if(gameBoard.getAllInBanks().size()>0){
                     for(Bank bank:gameBoard.getAllInBanks()){
                         bank.getMembers().remove(player);
                     }
                 }
            }
            foldPlayers = null;
        }
    }

    private void fillBank(List<Player> players) {
         while((findAllin(allPlayers)!=null)){
                Player player = findAllin(allPlayers);
                gameBoard.getAllInBanks().add(allInBank(allPlayers));
                player.setAllIn(false);
            }
        for (Player player : players) {
            gameBoard.increaseBank(player.getRate());
            player.decrementBalans(player.getRate());
            player.setRate(0);
        }
          
       
        gameBoard.setBiggestRate(0);
    }

    //в случае отсуствия оллина возвращает NULL
    //ищет игрока с найменьшей ставкой и маркером аллин
    private Player findAllin(List<Player> players) {
        Player allinPlayer = null;
        double minrate = Double.MAX_VALUE;

        for (Player player : players) {
            if (player.isAllIn() && player.getRate() < minrate) {
                allinPlayer = player;
                minrate = player.getRate();
            }
        }
        return allinPlayer;
    }

    //формирует новый банк по оллину
    private Bank allInBank(List<Player> players) {
        Player allinPlayer = findAllin(players);
        Bank allInBank = null;
        if (!(allinPlayer == null)) {
            allInBank = new Bank();
            double rate = allinPlayer.getRate();
            for (Player player : allPlayers) {// проход по всем игрокам т.к. нужно принять ставки игроков, которые fold
                if (player.getRate() < rate) {
                    allInBank.increaseBank(player.rate);
                    player.setRate(0);
                }
                if (player.getRate() == rate) {
                    if (player.isAllIn())
                        player.setAllIn(false);
                    allInBank.addMember(player, player.getRate());
                    player.setRate(0);
                    if (player.isAllIn()) {
                        this.playersInGame.remove(player);
                    }
                }
                if (player.getRate() > rate) {
                    allInBank.addMember(player, rate);
                    player.setRate(player.getRate() - rate);
                }
            }
        }
        return allInBank;
    }

    private boolean sameRates(List<Player> players) {
        if(players.size()==0)
            return true;
        int cnt = 0;
        double rate = gameBoard.getBiggestRate();
        for (Player player : players) {
            if (player.getRate() == rate||player.isAllIn||player.fold)
                cnt++;
        }
         if (cnt == players.size()) {
           
            return true;
        }
        else return false;
    }

    /* проверяет на наличие оллина
    * в случае нескольких оллинов создаёт несколько банков*/
    public boolean checkRates(List<Player> players) {
        /*1. проверка на наличие оллина
            -поиск наименьшего оллина
        * 2. проверка одинаковы ли у игроков ставки
        */
        while (findAllin(players) != null) {
            players.remove(findAllin(players));
                //gameBoard.getAllInBanks().add(allInBank(players));
        }
        return sameRates(players);
    }



    public void floop(List<Card> list) {
        
        gameBoard.boardCards = list;

        gameBoard.boardCards.add(cardHolder.nextRandCard());
        gameBoard.boardCards.add(cardHolder.nextRandCard());
        gameBoard.boardCards.add(cardHolder.nextRandCard());
    }
   
    public void floop() {
        this.firstRate=false;
        gameBoard.boardCards = new ArrayList<>();

        gameBoard.boardCards.add(cardHolder.nextRandCard());
        gameBoard.boardCards.add(cardHolder.nextRandCard());
        gameBoard.boardCards.add(cardHolder.nextRandCard());
    }

    public void tern() {
        gameBoard.boardCards.add(cardHolder.nextRandCard());
    }

    public void river() {
        gameBoard.boardCards.add(cardHolder.nextRandCard());

    }

    private ArrayList<Player> determWinner(List<Player> players){
        ArrayList<Player> winnersTable = new ArrayList<>(players);
        Collections.sort(winnersTable);
        ArrayList<Player> winnerList = new ArrayList<>();
        Combination winnerCombination = winnersTable.get(0).getCombination();
        for(Player player:winnersTable){
            if (player.getCombination().getValueCombination() == winnerCombination.getValueCombination() &&
                    player.getCombination().getSumOfCards() == winnerCombination.getSumOfCards())
                winnerList.add(player);
            else break;
        }
        return winnerList;
    }

    /*определяет победителя из членов банка, присваивает победителям выигрыш*/
    private void realizeBank(Bank bank) {
        //присвоение каждому игроку комбинации(розыгрыш основного банка)
        setCombinations(bank.getMembers(), gameBoard.getBoardCards());
        //определение выигравших игроков
        ArrayList<Player> winnerList = determWinner(bank.getMembers());

        //присвоение выиграша игрокам
        double prise = bank.getBalance() / winnerList.size();
        for (Player player : winnerList) {
            player.incrementBalans(prise);


            this.winnerSet.add(player);
        }
    }

    public Set<Player> cvalification() {

       //добавление игроков в основной банк
       if(playersInGame.size()>0){
        for(Player player:playersInGame)
        gameBoard.getMainBank().addMember(player);
       
        //квалификация банков
        
        realizeBank(gameBoard.getMainBank());
       }
        if(gameBoard.getAllInBanks().size()>0){
            for(Bank bank:gameBoard.getAllInBanks())
                realizeBank(bank);
            //удаление банков
            gameBoard.newAllinBanks();
        }
        return this.winnerSet;

        }

    private void setCombinations(List<Player> players, List<Card> boardCards) {
        for(Player player: players){
            player.setCombination(Analizer.analis(boardCards, player.getCards()));
        }
    }

    public void clear() {
            this.firstRate=true;
        for(Player player:allPlayers) {
            player.clearCards();
            player.combination = null;
            player.setBigBlind(false);
            player.setSmallBlind(false);
            player.setDealer(false);
            player.setFold(false);
        }
        gameBoard.getBoardCards().clear();
        winnerSet = new TreeSet<Player>();
        gameBoard.setMainBank(new Bank());
        gameBoard.setLastRater(null);
        this.firstRate=true;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public List<Player> getPlayers() {
        return allPlayers;
    }
  
    public List<Player> getPlayersInGame() {
        return playersInGame;
    }
public int getPositiveBalancePlayers(){
    int cnt = 0;
    for(Player player:allPlayers){
        if(player.isPositiveBalance())
            cnt++;
    }
    return cnt;
}    
}

