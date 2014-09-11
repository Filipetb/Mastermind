/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Filipe
 */
public class ChatHelper extends UnicastRemoteObject implements ItfChatHelper {
    
    public ChatHelper() throws RemoteException{
        super();
        System.out.println("Servidor Chat criado.");
    }
}
