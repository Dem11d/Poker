package controller;


import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    List<Player> players;
    List<Card> boardCards = null;

    private double bigBlibd;
    private double smallBlibd;
    private Player BigBlindPlayer = null;
    private ArrayList<Bank> allInBanks = new ArrayList<>();
    private Bank mainBank= new Bank();
    private Player SmallBlindPlayer = null;
    private double biggestRate;
    @Deprecated
    private Player lastRater =null;
    private Player LastRaiser=null;
    private Player DealerPlayer = null;

    public Player getDealerPlayer() {
        return DealerPlayer;
    }

    public void setDealerPlayer(Player DealerPlayer) {
        this.DealerPlayer = DealerPlayer;
    }
    

    public Player getLastRaiser() {
        return LastRaiser;
    }

    public void setLastRaiser(Player LastRaiser) {
        this.LastRaiser = LastRaiser;
    }

    public Bank getMainBank() {
        return mainBank;
    }

    public void setMainBank(Bank mainBank) {
        this.mainBank = mainBank;
    }

    public Player getSmallBlindPlayer() {
        return SmallBlindPlayer;
    }

    public ArrayList<Bank> getAllInBanks() {
        return allInBanks;
    }

    public void setAllInBanks(ArrayList<Bank> allInBanks) {
        this.allInBanks = allInBanks;
    }

    public void setSmallBlindPlayer(Player smallBlindPlayer) {
        SmallBlindPlayer = smallBlindPlayer;
    }


    public void setLastRaterNull(){
        lastRater =null;
    }

    public Player getLastRater() {
        return lastRater;
    }

    public void setLastRater(Player lastRater) {
        this.lastRater = lastRater;
    }

    public boolean addPlayer(Player player) {
        if (players == null) {
            players = new ArrayList<>();
        }
        if (players.size() == 8) {
            return false;
        } else {
            players.add(player);
            return true;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getBoardCards() {
        return boardCards;
    }

    public Bank getBank() {
        return mainBank;
    }

    public void setBank(Bank bank) {
        this.mainBank = bank;
    }

    public double getBigBlibd() {

        return bigBlibd;
    }

    public void setBigBlibd(double bigBlibd) {
        this.bigBlibd = bigBlibd;
    }

    public double getSmallBlibd() {
        return smallBlibd;
    }

    public void setSmallBlibd(double smallBlibd) {
        this.smallBlibd = smallBlibd;
    }

    public Player getBigBlindPlayer() {
        return BigBlindPlayer;
    }

    public void setBigBlindPlayer(Player bigBlindPlayer) {
        BigBlindPlayer = bigBlindPlayer;
    }

    public double getBiggestRate() {
        return biggestRate;
    }

    public void setBiggestRate(double biggestRate) {
        this.biggestRate = biggestRate;
    }

    public void increaseBank(double i) {
        this.mainBank.increaseBank(i);
    }
    public void newAllinBanks(){
        allInBanks = new ArrayList<Bank>();
    }
    public void newBank(){
        mainBank = new Bank();
    }
}

