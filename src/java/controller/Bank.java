package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Павлюки on 09.11.2016.
 */
public class Bank {

    private List<Player> members=new ArrayList<>();
    private double balance=0;


    public Bank() {
    }
    public void addMember(Player player, double rate){
        members.add(player);
        increaseBank(rate);
        player.decrementBalans(rate);
    }
    public void addMember(Player player){
        members.add(player);
    }

    public Bank(List<Player> members, double balance) {
        this.members = members;
        this.balance = balance;
    }
    public void increaseBank(double rate){

        balance=balance+rate;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

