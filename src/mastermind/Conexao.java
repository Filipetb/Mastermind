
package mastermind;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import javafx.application.Platform;


/**
 *
 * @author filipe_bvr
 */
public class Conexao extends Observable /* implements Runnable */ {
    
    private boolean isServer;
    private int port;
    private String ip;
    private Socket conn;
    private ServerSocket srvsoc;
    private String message;
    
    public Conexao(){
        this.port = 1270;
        this.ip = "127.0.0.1";
        this.isServer = false;
    }
    
    public Conexao(String ip){
        this.port = 1270;
        this.ip = ip;
        this.isServer = false;
    }
    
    public Conexao(String ip, int porta){
        this.port = porta;
        this.ip = ip;
        this.isServer = false;
    }
    
    private Conexao(Socket soc){
        this.conn = soc;
    }

    public boolean isIsServer() {
        return isServer;
    }

    public String getMessage() {
        return this.message;
    }

    public void flagMessage(String mensagem) {
        this.message = mensagem;
        setChanged();
        notifyObservers();
    }
    
    public void closeConn() {
        try{
            this.conn.close();
        }catch(IOException e){
            System.out.println("Excessao foi gerada ao tentar FECHAR CONEXAO");
        }
    }
    
    public String startConn(String playerName){
        
        DataOutputStream out = null;
	DataInputStream in = null; 
        String pname = null;
        
        try{
            this.conn = new Socket(this.ip,this.port);
            
            out = new DataOutputStream(this.conn.getOutputStream());
            out.writeUTF(playerName); 
            
            in = new DataInputStream(this.conn.getInputStream());
            pname = in.readUTF();
            
            new Thread(new Recv(this.conn)).start();
            
            
        }catch(ConnectException e){
            
            try{
                this.isServer = true;

                this.srvsoc = new ServerSocket(this.port);

                this.conn = this.srvsoc.accept();
                
                in = new DataInputStream(this.conn.getInputStream());
		pname = in.readUTF();		
                
                out = new DataOutputStream(this.conn.getOutputStream());
                out.writeUTF(playerName);
                
                new Thread(new Recv(this.conn)).start();
                
            
            }catch(IOException ex){}
            
        }catch(IOException ex){}
        
        
        return pname;
    }
    
    public void send(String msg) {
        DataOutputStream out;
        try{
        out = new DataOutputStream(this.conn.getOutputStream());
        out.writeUTF(msg);
        }catch(IOException e){
            System.out.println("Excessao foi gerada no ENVIO de pacote");
        }
    }
    
    
    class Recv implements Runnable{
    
        private Socket rconn;
        
        private String lolmsg;
        
        public Recv(Socket soc){
            this.rconn = soc;
        }
        
        @Override
        public void run(){
            boolean erro = false;
            DataInputStream in; 

                    while (!erro) {
                        try {
                            in = new DataInputStream(this.rconn.getInputStream());
                            lolmsg = in.readUTF();
                            if (lolmsg == null) {
                                erro = true;
                                continue;
                            }
                            
                            Platform.runLater(new Runnable() { //!importante para modificar a ui em javafx
                                @Override
                                public void run() {
                                    flagMessage(lolmsg);
                                }
                            });
                        } catch (Exception e) {
                            erro = true;
                            System.out.println("Excessao foi gerada no recebimento de pacote");
                            Platform.runLater(new Runnable() { //!importante para modificar a ui em javafx
                                @Override
                                public void run() {
                                    flagMessage("PacketException");
                                }
                            });
                        }
                    }
                    System.out.println("Conexao finalizada!");
            
        }
    
    }
    
}
