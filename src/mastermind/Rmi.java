/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.rmi.*;
import java.net.MalformedURLException;

/**
 *
 * @author filipe_bvr
 */
public class Rmi {
    private boolean isServer;
    private int port;
    private String ip;
    //private String message;
    private ItfHelper helper;
    private MainHelper mets;
    
    
    public Rmi(){
        //this.port = 1270;
        this.ip = "localhost";
        this.isServer = false;
    }
    
    public Rmi(String ip){
        //this.port = 1270;
        this.ip = ip;
        this.isServer = false;
    }
    
    public Rmi(String ip, int porta){
        //this.port = porta;
        this.ip = ip;
        this.isServer = false;
    }
    
    public boolean isIsServer() {
        return isServer;
    }
    
    public void start(String nome){
        try {
            this.helper = (ItfHelper)Naming.lookup("//"+this.ip+"/"+nome);
            System.out.println("Objeto Localizado!");
            this.isServer = false;
	} catch(Exception e){
            
            try {
                
                this.mets = new MainHelper();
                Naming.rebind(nome,this.mets);
                System.out.println("Servidor Registrado!");
                this.isServer = true;
                
            } catch (RemoteException | MalformedURLException ex) {
                System.out.println("fuuuuuuuu");
            }
        }
		
    }
    
}
