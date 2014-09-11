/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filipe_bvr
 */
public class Rmi {
    private boolean isServer;
    private int port;
    private final String ip;
    private ItfHelper helper;
    private MainHelper mets;
    private ItfChatHelper chatHelper;
    private ChatHelper chatMets;
    
    
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
    
    public void start(){
        try {
            this.helper = (ItfHelper)Naming.lookup("//"+this.ip+"/mastermind_rmi");
            this.chatHelper = (ItfChatHelper)Naming.lookup("//"+this.ip+"/mastermind_rmi_chat");
            System.out.println("Objetos Localizados!");
            
            this.mets = new MainHelper();
            this.chatMets = new ChatHelper();
            Naming.rebind("//"+this.ip+"/mastermind_rmi_2", this.mets);
            Naming.rebind("//"+this.ip+"/mastermind_rmi_chat2", this.chatMets);
            System.out.println("Nomes registrados - cliente");
            
            //this.isServer = false;
	} catch(Exception e){
            
            try {
                LocateRegistry.createRegistry(1099);
                
                this.mets = new MainHelper();
                this.chatMets = new ChatHelper();
                Naming.rebind("mastermind_rmi",this.mets);
                Naming.rebind("mastermind_rmi_chat",this.chatMets);
                System.out.println("Servidor Registrado!");
                
                this.isServer = true;
                
                //servidor precisa dar um lookup qnd o cliente registrar o nome
                
            } catch (RemoteException | MalformedURLException ex) {
                System.out.println("fuuuuuuuu");
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		
    }
    
}
