import java.util.Arrays;
import java.util.ArrayList;
import java.util.Stack;

public class APlayer extends Player{
	
	DiscardPile discardPile = new DiscardPile();
    Player[] players = new Player[3];
    ArrayList<Player> player = new ArrayList<>();
	Player leader;

	public APlayer(Card[] cards){ super(cards); }
 
  /* play a card */ 
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players)
	{
		discardPile.add(this.hand.remove(0));
		if(this.hand.size() == 0){
			return true;
			}
		return false;
	}
	
	
		
	// checking to see what player is the leader(has the fewest amount of cards)
	public Player Leader(){
		if (players[0].getSizeOfHand() < players[1].getSizeOfHand() && players[0].getSizeOfHand() < players[2].getSizeOfHand() &&
		players[0].getSizeOfHand() < players[3].getSizeOfHand()){
			leader = players[0];
		}
		if (players[1].getSizeOfHand() < players[0].getSizeOfHand() && players[1].getSizeOfHand() < players[2].getSizeOfHand() &&
		players[1].getSizeOfHand() < players[3].getSizeOfHand()){
			leader = players[1];
		}
		if (players[2].getSizeOfHand() < players[0].getSizeOfHand() && players[2].getSizeOfHand() < players[1].getSizeOfHand() &&
		players[2].getSizeOfHand() < players[3].getSizeOfHand()){
			leader = players[2];
		}
		if (players[3].getSizeOfHand() < players[0].getSizeOfHand() && players[3].getSizeOfHand() < players[1].getSizeOfHand() &&
		players[3].getSizeOfHand() < players[2].getSizeOfHand()){
			leader = players[3];
		}
		
		return leader;
		
	}
	
		
	public void HamperLeader(){
		
		int indexOfNextPlayer=player.indexOf(players)+1;
		if(indexOfNextPlayer > 3){
			indexOfNextPlayer = 0;
		}

		if(players[indexOfNextPlayer]==Leader()){
					
			int i =0; 
			boolean b = false;
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
		}
	}
		
	
	
	
	
	
	
}