/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.util.Observable;

/**
 *
 * @author Filipe
 */
public class NotifyHelper extends Observable {
    
    public void notifyTheController(){
        setChanged();
        notifyObservers();
    }
}
