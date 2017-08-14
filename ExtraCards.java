import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;


public class ExtraCards extends Player {
    public ExtraCards(Card[] cards){this.hand = new ArrayList<Card>(Arrays.asList(cards));}
    @Override
    public boolean play(DiscardPile discardPile,Stack<Card> drawPile,ArrayList<Player> player) {



        //gets the index of the next player
        int indexOfNextPlayer=player.indexOf(player)+1;
        if (indexOfNextPlayer > player.size()) {
            indexOfNextPlayer=0;}

        //checks if the next player card size is equal to 1
        if (player.get(indexOfNextPlayer).getSizeOfHand() == 1) {
            //if its equal to one it will lunch ExtraCardss method
            ExtraCardss(discardPile,drawPile, player);}
        discardPile.add(this.hand.remove(0));
        if (this.hand.size() == 0) {
            return true;
        }return false;
    }
    // will pick cards until it gets the power card
    public boolean ExtraCardss(DiscardPile discardPile,Stack<Card> drawPile,ArrayList<Player> player) {


        //iterates if the power cars are in hand
                for (int i = 0; i < hand.size(); i++) {
                    //checks if power card number two exist is in hands
                    if (hand.get(i).getRank() == 2) {
                        //cheeks the suit of the card in hand and the discarded card on the top
                        if(this.hand.get(i).getSuit()==discardPile.top().getSuit()){
                            discardPile.add(this.hand.remove(0));
                           //return card 2
                        return true;}
                    }
                    //checks if power card number Jack exist is in hands
                    else if (hand.get(i).getRank() == 4) {
                        //cheeks the suit of the card in hand and the discarded card on the top
                        if(hand.get(i).getSuit()==discardPile.top().getSuit()){
                            discardPile.add(this.hand.remove(0));

                        //return card 4
                        return true;}
                    }
                    //checks if power card number Jack exist is in hands
                    else if (hand.get(i).getRank() == 7) {
                        //cheeks the suit of the card in hand and the discarded card on the top
                        if(hand.get(i).getSuit()==discardPile.top().getSuit()){
                            discardPile.add(this.hand.remove(0));
                            //return card 7
                            if( this.hand.size() == 0 ){return true;}
                            return false;}
                    }
                    //checks if power card number eight exist is in hands
                    else if (hand.get(i).getRank() == 8) {
                        hand.add(0,hand.remove(i));
                        play(discardPile,drawPile,player);
                        //return card 8
                        return true;}
                    }

                // if there in no power card in hand
                //pick card from pile until it gets the power cards
                while (true) {
                    //

                    Card pickedcard = pickupCard(drawPile);
                    //check the drawPile is a power card
                    if (pickedcard.getRank() == 2) {
                        //it will check if the top discarded card suit is the same as the new draw card suit
                        if (discardPile.top().getSuit() == pickedcard.getSuit()) {
                            discardPile.add(this.hand.remove(hand.size()-1));
                            break;
                        }
                        continue;
                    } else if (pickedcard.getRank() == 4) {
                        //it will check if the top discarded card suit is the same as the new draw card suit
                        if (discardPile.top().getSuit() == pickedcard.getSuit()) {
                            discardPile.add(this.hand.remove(hand.size()-1));
                            return true;
                        }
                        continue;
                    } else if (pickedcard.getRank() == 7) {
                        //it will check if the top discarded card suit is the same as the new draw card suit
                        if (discardPile.top().getSuit() == pickedcard.getSuit()) {
                            discardPile.add(this.hand.remove(hand.size()-1));
                            return true;
                        }
                        continue;
                    } else if (pickedcard.getRank() == 8) {
                        discardPile.add(this.hand.remove(hand.size()-1));
                        return true;
                        }
                    // continue the loop until it gets the power cards
                    continue;
                }

        // return false if player doesn't left with one card
        return false;
    }
}
