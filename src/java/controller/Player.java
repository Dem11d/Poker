package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


public class Player implements Comparable<Player> {
    protected String name;
    ArrayList<Card> cards = new ArrayList<>();
    protected Combination combination;
    protected double balance = 0;
    protected boolean isBigBlind;
    protected boolean isSmallBlind;
    volatile protected double rate = 0;
    protected boolean isLoose;
    protected boolean isAllIn;
    protected GameBoard gamesitustion;
    protected boolean fold=false;
    volatile protected boolean rateAccess;
    protected boolean isDealer;

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer(boolean isDealer) {
        this.isDealer = isDealer;
    }
    

    public boolean isFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.clearCards();
        this.fold = fold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGamesitustion(GameBoard gamesitustion) {
        this.gamesitustion = gamesitustion;
    }

    public void setRateAccess(boolean rateAccess) {
        this.rateAccess = rateAccess;
    }

    public boolean isRateAccess() {
        return rateAccess;
    }

    public boolean isAllIn() {
        return isAllIn;
    }

    public void setAllIn(boolean allIn) {
        isAllIn = allIn;
    }

    public boolean isBigBlind() {
        return isBigBlind;
    }

    public void setBigBlind(boolean bigBlind) {
        isBigBlind = bigBlind;
    }

    public boolean isSmallBlind() {
        return isSmallBlind;
    }

    public void setSmallBlind(boolean smallBlind) {
        isSmallBlind = smallBlind;
    }

    public boolean isLoose() {
        return isLoose;
    }

    public void setLoose(boolean loose) {
        isLoose = loose;
    }


    public Player(String name) {
        this.name = name;
        this.isLoose = false;

    }

    public Player() {
        String[] names = {"Jacob", "Emily", "Michael", "Emma", "Joshua", "Madison", "Matthew", "Olivia", "Ethan",
                "Hannah", "Andrew", "Abigail", "Daniel", "Isabella", "William", "Ashley", "Joseph", "Samantha",
                "Christopher", "Elizabeth", "Anthony", "Alexis"};
        int randIndex = (int) (Math.random() * (names.length - 1));
        this.name = names[randIndex];
        this.isLoose = false;
    }

    public boolean isPositiveBalance() {
        if (balance > 0)
            return true;
        else return false;
    }

    public double getBalance() {
        double bal = (Math.round(balance*100))/100;
        return bal;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    boolean setCard(Card card) {

        if (cards.size() == 2)
            return false;
        else cards.add(card);
        return true;
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
    }


    @Override
    public int compareTo(Player o) {
        int cnt=0;
        try{
        if(o==null)
            return -1;
        if (this.combination==null&& o.combination!=null)
               return 1;
           if (this.combination==null&& o.combination==null)
               return 0;
        
        if (this.combination.getValueCombination() > o.combination.getValueCombination())
            cnt = -1;
        else if ((this.combination.getValueCombination() < o.combination.getValueCombination()))
            cnt = 1;
        else {
            int cnt1 = 0;
            Iterator iterator1 = this.combination.getCardsCombination().iterator();
            while (iterator1.hasNext()) {
                Card card = (Card) iterator1.next();
                cnt1 += card.getValue();
            }
            int cnt2 = 0;
            Iterator iterator2 = o.combination.getCardsCombination().iterator();
            while (iterator2.hasNext()) {
                Card card = (Card) iterator2.next();
                cnt2 += card.getValue();
            }
            if (cnt1 > cnt2) return -1;
            else if (cnt1 < cnt2) return 1;

        }
        }catch(NullPointerException ex){
            System.out.println("this= "+this.toString()+"o= "+o.toString());
            ex.printStackTrace();
        }
        return cnt;
    }
    

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        //проверка на оллин
        if (rate >= balance&& rate!=0) {
            setAllIn(true);
            rate = balance;
        }
        if(!Objects.isNull(gamesitustion)){
        if (gamesitustion.getBiggestRate()<rate){
            gamesitustion.setBiggestRate(rate);
            gamesitustion.setLastRaiser(this);
        }
        if (gamesitustion.getBiggestRate()-rate>0.001 &&
                this.isAllIn!=true){
            System.out.println("gamesitustion.getBiggestRate() "+gamesitustion.getBiggestRate()+"rate"+rate);
//            rate=0;
            this.setFold(true);
        }
        }
        gamesitustion=null;
        this.rate = rate;
    }

    public void incrementRate(double rate) {
        
            setRate(this.rate + rate);
    }

    public double nextRate(GameBoard gameBoard) {
        if(rate==-1){
            setRate(0);
            return -1;
        }
        if(Math.random()<0.1){
            setRate(0);
            return -1;
        }
        if(Math.random()<0.1){
            if(gameBoard.getBigBlibd()>rate){
                incrementRate(gameBoard.getBigBlibd());
                return rate;
            }
        }
        if (gameBoard.getBiggestRate() < this.rate)
            gameBoard.setBiggestRate(this.rate);
        setRate(gameBoard.getBiggestRate());
        return rate;
    }

    public void decrementBalans(double i) {
        this.balance -= i;
    }

    public void incrementBalans(double i) {
        this.balance += i;
    }

    public void clearCards() {
        this.cards.clear();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", combination=" + combination +
                ", balance=" + balance +
                '}';
    }
}

