package controller;

import java.util.ArrayList;
import java.util.Iterator;


public class Combination {
    private ArrayList<Card> cardsCombination;

    /*   описание комбинаций:
        0 - нет комбинаций(старшая карта)
        1 - пары
        2 - две пары
        3 - сет
        4 - стрит
        5- флеш
        6 - фулл хаус
        7 - каре
        8 - стритфлеш
        9 - флеш рояль
        */
    private int valueCombination;
    public ArrayList<Card> getCardsCombination() {
        return cardsCombination;
    }
    private int sumOfCards=0;

    public int getSumOfCards() {
        return sumOfCards;
    }

    public Combination(ArrayList<Card> cardsCombination, int valueCombination) {
        this.cardsCombination = cardsCombination;
        this.valueCombination = valueCombination;
        Iterator<Card> iterator = cardsCombination.iterator();
        while (iterator.hasNext()){
            sumOfCards+=iterator.next().getValue();
        }


    }

    public int getValueCombination() {
        return valueCombination;
    }

    @Override
    public String toString() {
        String str=null;
        switch (valueCombination) {
            case 0:
                str = new String("нет комбинаций(старшая карта)");
                break;
            case 1:
                str = new String("пары");
                break;
            case 2:
                str = new String("две пары");
                break;
            case 3:
                str = new String("сет");
                break;
            case 4:
                str = new String("стрит");
                break;
            case 5:
                str = new String("флеш");
                break;
            case 6:
                str = new String("фулл хаус");
                break;
            case 7:
                str = new String("каре");
                break;
            case 8:
                str = new String("стритфлеш");
                break;
            case 9:
                str = new String("флеш рояль");
                break;

        }
        return "комбинация \"" +str+"\"";
    }
}
