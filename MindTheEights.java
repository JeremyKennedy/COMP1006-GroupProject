// Author: Carson

import java.util.Arrays;

public class MindTheEights extends Player{
  
  int numberofturns = discardPile.length();
  int count;
  
  public int turnCounter(int numberofturns){
    for(int i = 0; i < numberofturns; i++){
      count++;}
    return count;
  }
  
  Player MindTheEights = new Player();
  String hand [];
  String eights [];
  int numberOfCards;
  
  public abstract hand(String [] args){
    while(numberOfCards < 5){
      Card card = new Card();
      hand.add(card);
      card.getSuit();
      card.getRank();
      if(rank == 8){
        eights.add(card);
        hand.remove(card);
      }
    }
  }
  
  public boolean playingEights(int x){
    int i = eights.length();
    
    if(discardPile.length() == 0){
      for(int i = 0; i <= eights.length(); i++){
        eights.play();
        eights.remove();
    }
  }
    
  
  public boolean play(DiscardPile       discardPile, 
                     Stack<Card>       drawPile, 
           ArrayList<Player> players){
    while(discardPile.Stack.length() == 0){
      eights.play();}
    
  discardPile.add(this.hand.remove(0));
  if( this.hand.size() == 0 ){return true;}
  return false;
 }
  
}