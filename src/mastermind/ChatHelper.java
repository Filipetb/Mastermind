
package mastermind;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Filipe
 */
public class ChatHelper extends UnicastRemoteObject implements ItfChatHelper {
    
    public NotifyHelper teste;
    private String otherPlayerName;
    private String myName;
    private String message;
     
    public boolean hasMessage() {
        return this.message != null;
    }

    public String getOtherPlayerName() {
        return otherPlayerName;
    }

    public String getMessage() {
        return message;
    }
    
    public ChatHelper() throws RemoteException{
        super();
        System.out.println("Servidor Chat criado.");
    }
    public ChatHelper(String myname) throws RemoteException{
        super();
        this.teste = new NotifyHelper();
        this.myName = myname;
        this.message = null;
        this.otherPlayerName = null;
        System.out.println("Servidor Chat criado.");
    }

    @Override
    public String setPlayersName(String name) throws RemoteException {
        this.otherPlayerName = name;
        this.teste.notifyTheController(this);
        return this.myName;  
    }

    @Override
    public String setMessage(String msg) throws RemoteException {
        this.message = msg;
        this.teste.notifyTheController(this);
        return msg;
    }
}
