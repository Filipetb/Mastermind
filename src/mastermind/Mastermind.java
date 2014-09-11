/*
Dependencias: ControlsFX.org
1 - clique no botão direito do mouse sobre o projeto. 
2 - selecione a opção properties. 
3 - na janela que se abrirá, seleciona a opção libraries. 
4 - clique no botão "add JAR/Folder". 
5 - selecione o arquivo. 
6 - clique ok. 
*/

package mastermind;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.dialog.Dialogs;


/**
 *
 * @author filipe_bvr
 */
public class Mastermind extends Application {
    
    public String initDialogs(){
        
        String result = null;
       
        Optional<String> response = Dialogs.create().lightweight()
        .title("MasterMind - Dev by Filipe Torres")
        .masthead("Digite seu nome para ser melhor identificado")
        .message("Por favor, insira seu nome:")
        .showTextInput();

        // One way to get the response value.
        if (response.isPresent()) {
            result = (response.get().length()>0 ? response.get() : "NONAME");
        }else{
            System.exit(0);
        }
        
        Optional<String> response2 = Dialogs.create().lightweight()
        .title("MasterMind - Dev by Filipe Torres")
        .masthead("Precisamos do ip para conectarmos")
        .message("Por favor, insira o ip:")
        .showTextInput("127.0.0.1");
        
        // One way to get the response value.
        if (response2.isPresent()) {
            result += ":"+(response2.get().length()>0 ? response2.get() : "127.0.0.1");
        }else{
            System.exit(0);
        }
        
        Optional<String> response3 = Dialogs.create().lightweight()
        .title("MasterMind - Dev by Filipe Torres")
        .masthead("Quase lá, so falta definir a porta\nAtençao: Ao apertar OK, a app irá travar até que se conecte a um jogador.")
        .message("Por favor, insira a porta de conexao:")
        .showTextInput("1270");
        
        // One way to get the response value.
        if (response3.isPresent()) {
            result += ":"+(response3.get().length()>0 ? response3.get() : "1270");
        }else{
            System.exit(0);
        }
        
        return result;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));

        Parent root = loader.load();        
        MainController mainc = loader.getController();  
        
        
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent window)
            {
                 mainc.initData(initDialogs());
                 mainc.startConnection();
            }
        });
        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent window)
            {
                try{
                 mainc.closeConnection();
                }catch(NullPointerException e){
                    System.exit(0);
                }
            }
        });
      
        
        
        Scene scene = new Scene(root, 800-10, 573-10);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("MasterMind - Dev by Filipe Torres");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
