// Author: Rajpal

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class HamperLeader extends Player{


	public HamperLeader(Card[] cards){ super(cards); }

	  /* play a card */
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players)
	{
		Player leader = players.get(0);
		for (Player player : players) {
			if (player.getSizeOfHand() > leader.getSizeOfHand()) {
				leader = player;
			}
		}
		Player nextPlayer = players.get(Crazy8Game.getNextPlayer());
		int s = this.getSizeOfHand(); /* used to check if the size of hands is still the same after checking if there are power cards in hand
										if the size of hand is still the same(ie. no card was played), then a valid card will be played */
		if(nextPlayer == leader){
			int i =0; 
			boolean b = false; // if a card is played b will equal true and program will get out of the loop
				// check each card in hand
			while(i<this.getSizeOfHand() && b==false){ 
				//checking to see each card in hand, if there is a power card in the player's hand, then they will play it
				if(this.hand.get(i).getRank()==8){
					discardPile.add(this.hand.remove(i)); //add the 8 card to the discard pile, if true
					b = true;
							
				}else if(this.hand.get(i).getRank()==4){
					if(this.hand.get(i).getSuit() == discardPile.top().getSuit()){
						discardPile.add(this.hand.remove(i));
						b=true;
						}
					
				}else if(this.hand.get(i).getRank()==2){
					if(this.hand.get(i).getSuit() == discardPile.top().getSuit()){
						discardPile.add(this.hand.remove(i));
						b=true;
					}
				
				}else if(this.hand.get(i).getRank()==7){
					if(this.hand.get(i).getSuit() == discardPile.top().getSuit()){
						discardPile.add(this.hand.remove(i));
						b=true;
						}
				}
				i=i+1;
							
			}
			
			if(this.getSizeOfHand()==s){
			
				for(int j =0; j<this.getSizeOfHand(); j=j+1){
					if (discardPile.isValidPlay(this.hand.get(j))==true){
						discardPile.add(this.hand.remove(j));
					}
				}
			}
			
		}else{
			for(int j =0; j<this.getSizeOfHand(); j=j+1){
				if (discardPile.isValidPlay(this.hand.get(j))==true){
					discardPile.add(this.hand.remove(j));
				}
			}
		}
		return getSizeOfHand()==0;
	}
}	
	
