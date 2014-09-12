/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.util.Observable;
import javafx.application.Platform;

/**
 *
 * @author Filipe
 */
public class NotifyHelper extends Observable {
    
    /**
    * 
    * @deprecated Passe o objeto como parametro!
    */
    @Deprecated
    public void notifyTheController(){
        setChanged();
        notifyObservers();
    }
    
    public void notifyTheController(Object arg){
        Platform.runLater(new Runnable() { //!importante para modificar a ui em javafx
            @Override
            public void run() {
                setChanged();
                notifyObservers(arg);
            }
        });
    }
}
