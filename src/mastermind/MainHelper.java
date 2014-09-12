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
    
    public NotifyHelper notH;
    private String message;
    
    public String getMessage(){
        return this.message;
    }
    
    public MainHelper() throws RemoteException{
        super();
        System.out.println("Servidor criado!");
        this.notH = new NotifyHelper();
    }

    @Override
    public void canStart() throws RemoteException {
        this.message = "CanStart";
        this.notH.notifyTheController(this);
    }

    @Override
    public void setResult(String res) throws RemoteException {
        this.message = "result:"+res;
        this.notH.notifyTheController(this);
    }

    @Override
    public void setPass(String pass) throws RemoteException {
        this.message = "pass:"+pass;
        this.notH.notifyTheController(this);
    }

    @Override
    public void disconnect() throws RemoteException {
        this.message = "PacketException";
        this.notH.notifyTheController(this);
    }
}
