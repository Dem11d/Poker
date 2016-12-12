/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Card;
import java.util.List;

/**
 *
 * @author Павлюки
 */
public class PokerVisualUtils {
     public static String getClosedCards(List<Card> cards){
    String result = new String();
        if(cards.size()>1){
            StringBuilder sb = new StringBuilder();
            for(Card card:cards){
                sb.append("<div ");
                sb.append("class='");
                sb.append("card-shirt");
                sb.append("'></div>");
            }
            result = sb.toString();
        }
        
        return result;
}
       public static String getClassCards(List<Card> cards){
    String result = new String();
        if(cards.size()>1){
            StringBuilder sb = new StringBuilder();
            for(Card card:cards){
                sb.append("<div ");
                sb.append("class='");
                sb.append(card.toStyle());
                sb.append("'></div>");
            }
            result = sb.toString();
        }
        
        return result;
}
}
