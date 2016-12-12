package controller;
import controller.Constants;
/**
 * Created by Павлюки on 28.11.2016.
 */
public class TestPlayer extends Player{
    Thread playerHandler;
    public TestPlayer(){
        super();
        playerHandler = new Thread(()->{
           while(true){
               try {
                
               if(this.rateAccess){
                   nextRate(gamesitustion);
                   gamesitustion = null;
                   Thread.currentThread().sleep(Constants.PLAYER_DELAY);
                   while(rateAccess)
                       rateAccess=false;
               }else{
                   Thread.currentThread().sleep(Constants.PLAYER_DELAY);
                   
               }
               }
           catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

        });
        playerHandler.start();
    }
}
