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
 * @author filipe_bvr
 */
public class MainHelper extends UnicastRemoteObject implements ItfHelper {
    
    public MainHelper() throws RemoteException{
        super();
        System.out.println("Servidor criado!");
    }
}
