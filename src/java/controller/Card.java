package controller;

public class Card implements Comparable {
    Mask mask;
    int value;
    String Stringvalue;
    

    public Card(Mask mask, int value) {

            String val = new String();
            switch (value) {
                case 11:
                    val = "Valet";
                    break;
                case 12:
                    val = "Dame";
                    break;
                case 13:
                    val = "Roi";
                    break;
                case 14:
                    val = "As";
                    break;
            }
            this.Stringvalue = val.isEmpty()?
                    String.valueOf(value) :
                    val;
        this.mask = mask;
        this.value = value;
    }

    public Mask getMask() {
        return mask;
    }

    public int getValue() {
        return value;
    }

    public String getStringValue() {
        return Stringvalue;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof Card) {
            Card card = (Card) o;

            if (this.value > card.value)
                return 1;
            else if (this.value == card.value)
                return 0;
            else if (this.value < card.value)
                return -1;
        }
        return -1;
    }

    @Override
    public String toString() {
        
        return "Card{" + mask + " " + Stringvalue +
                '}';
    }

    public boolean sameValue(Card card) {
        if (card.value == this.value)
            return true;
        else return false;
    }
    public String toStyle(){
        String strCard=null;
       
        StringBuilder sb = new StringBuilder();
        sb.append("card-");
        sb.append(getStringValue()+" ");
        sb.append(getMask());
        strCard = sb.toString();
       
        return strCard;
    }
}
