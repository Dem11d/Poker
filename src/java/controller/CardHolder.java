package controller;

import java.util.Stack;

public class CardHolder {
    private Stack<Card> cardStack;


    public CardHolder() {
        this.cardStack = CardStack();
    }

    public void newCardStack() {
        this.cardStack = CardStack();
    }

    Stack<Card> CardStack() {
        Stack<Card> cardStack = new Stack<>();
        for (int i = 0; i < 4; i++) {
            Mask cardMask = Mask.CLUB;
            switch (i) {
                case 0:
                    cardMask = Mask.SPADE;
                    break;
                case 1:
                    cardMask = Mask.HEART;
                    break;
                case 2:
                    cardMask = Mask.DIAMOND;
                    break;
                case 3:
                    cardMask = Mask.CLUB;
                    break;
            }
            for (int j = 0; j < 13; j++) {
                cardStack.add(new Card(cardMask, j+2));
            }
        }
        return cardStack;
    }


    public Card nextRandCard() {
        int index = (int) (Math.random() * (cardStack.size() - 1));
        Card card = cardStack.get(index);
        cardStack.removeElement(card);
        return card;
    }


}
