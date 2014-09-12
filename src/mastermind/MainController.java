
//Aprendi gerenciar eventos como keyevent na documentaçao da oracle http://docs.oracle.com/javafx/2/events/handlers.htm
//Aprendi associaçao de arquivos fxml com classes controller no youtube https://www.youtube.com/channel/UCnAdXkr17iQS8YcYl0LhPdw
//bom link para entender o padrao observer http://www.javaworld.com/article/2077258/learn-java/observer-and-observable.html
//source da propria oracle exemplificando drag and dropg com textos http://docs.oracle.com/javafx/2/drag_drop/HelloDragAndDrop.java.html

package mastermind;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author filipe_bvr
 */
public class MainController implements Initializable, Observer {
    
    @FXML
    private Label playingWith;
    @FXML
    private TextField chatTF;
    @FXML
    private TextArea chatTA;
    @FXML
    private ImageView bilharY;
    @FXML
    private ImageView bilharB;
    @FXML
    private ImageView bilharR;
    @FXML
    private ImageView bilharP;
    @FXML
    private ImageView bilharG;
    @FXML
    private ImageView bilharBw;
    @FXML
    private ImageView btnEnviar;
    @FXML
    private GridPane myGrid1;
    @FXML
    private GridPane myGrid2;
    @FXML
    private GridPane myGrid3;
    @FXML
    private GridPane myGrid4;
    @FXML
    private GridPane myGrid5;
    @FXML
    private GridPane myGrid6;
    @FXML
    private GridPane myGrid7;
    @FXML
    private GridPane myGrid8;
    @FXML
    private GridPane myGrid9;
    @FXML
    private GridPane myGrid10;
    @FXML
    private GridPane myGridX;
    @FXML
    private ImageView pinoB;
    @FXML
    private ImageView pinoP;
    @FXML
    private GridPane pinoGrid1;
    @FXML
    private GridPane pinoGrid2;
    @FXML
    private GridPane pinoGrid3;
    @FXML
    private GridPane pinoGrid4;
    @FXML
    private GridPane pinoGrid5;
    @FXML
    private GridPane pinoGrid6;
    @FXML
    private GridPane pinoGrid7;
    @FXML
    private GridPane pinoGrid8;
    @FXML
    private GridPane pinoGrid9;
    @FXML
    private GridPane pinoGrid10;
    @FXML
    private ImageView suaVez;
    
    //Array que armazena as referencias das imgs dropped para serem removidas qnd necessarias
    private final List<ImageView> bilharImgs = new ArrayList<>();
    
    //Array que armazena as referencias dos grids para realizar o controle das tentativas
    private final List<GridPane> gridLits = new ArrayList<>();
    
    //Array que armazena as referencias dos grids dos pinos para realizar o controle dos resultados
    private final List<GridPane> gridPinosLits = new ArrayList<>();
    
    private MainModel game;
    
    private Conexao appConn;
    //private Conexao chatConn;
    private Rmi appRmiConn;
    
    private boolean isDialogPresent = false; //evitar excessao e que a app se feche inesperadamente ao fim de jogo
         
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }

    public void initData(String data) {
        String[] dataSplited = data.split(":");
        this.game = new MainModel(dataSplited[0]);
        //this.appConn = new Conexao(dataSplited[1],Integer.parseInt(dataSplited[2]));
        //this.appConn.addObserver(this);
        
        try{ this.appRmiConn = new Rmi(dataSplited[1], this.game); }catch(RemoteException e){}
        this.appRmiConn.chatMets.teste.addObserver(this);
        
        //É necessario setar o tamanho do array para 4(evitar nullpointerException e IndexOutOfBouds)
        this.bilharImgs.add(new ImageView());
        this.bilharImgs.add(new ImageView());
        this.bilharImgs.add(new ImageView());
        this.bilharImgs.add(new ImageView());
        
        //adicionando as referencias dos grids para facilitar o gerenciamento das tentativas(game.attempt)
        this.gridLits.add(myGrid1);
        this.gridLits.add(myGrid2);
        this.gridLits.add(myGrid3);
        this.gridLits.add(myGrid4);
        this.gridLits.add(myGrid5);
        this.gridLits.add(myGrid6);
        this.gridLits.add(myGrid7);
        this.gridLits.add(myGrid8);
        this.gridLits.add(myGrid9);
        this.gridLits.add(myGrid10);
        
        //adicionando as referencias dos grids para facilitar o gerenciamento das tentativas(game.attempt)
        this.gridPinosLits.add(pinoGrid1);
        this.gridPinosLits.add(pinoGrid2);
        this.gridPinosLits.add(pinoGrid3);
        this.gridPinosLits.add(pinoGrid4);
        this.gridPinosLits.add(pinoGrid5);
        this.gridPinosLits.add(pinoGrid6);
        this.gridPinosLits.add(pinoGrid7);
        this.gridPinosLits.add(pinoGrid8);
        this.gridPinosLits.add(pinoGrid9);
        this.gridPinosLits.add(pinoGrid10);
        
    }
    
    public void startConnection(){
        /*
        this.game.setOtherPlayerName(this.appConn.startConn(this.game.getMyName()));
        this.playingWith.setText("Jogando com: "+this.game.getOtherPlayerName());
        this.game.setChallenger(this.appConn.isIsServer()); 
        this.game.setMyTurn(this.appConn.isIsServer());
        */
        String oPName = this.appRmiConn.start();
        
        if(oPName != null)
        {
            this.game.setOtherPlayerName(oPName);
            this.playingWith.setText("Jogando com: "+this.game.getOtherPlayerName());
        }
         
        this.game.setChallenger(this.appRmiConn.isIsServer()); 
        this.game.setMyTurn(this.appRmiConn.isIsServer());
        
        if(this.game.isMyTurn())
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Dialogs.create().lightweight()
                        .title("MasterMind - Dev by Filipe Torres")
                        .masthead(null)
                        .message("Você começa, defina a senha!")
                        .showInformation();
                }
            });
        }
    }

    public void closeConnection(){
        this.appConn.closeConn();
    }

    public void bilharSetOnDragDetected(MouseEvent event) {
        /* drag was detected, start drag-and-drop gesture*/
        
                /* allow any transfer mode */
                ImageView curIV = (ImageView)event.getSource();
                Dragboard db = curIV.startDragAndDrop(TransferMode.COPY);
                
                /* put a image on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putImage(curIV.getImage());
                //content.putUrl(bilharY.getImage().impl_getUrl()); //Infelizmente este metodo está obsoleto no Java 8
                db.setContent(content);
                
                event.consume();
    }
    
    public void gridSetOnDragOver(DragEvent event) {
        
            /* accept if it has a image data */
            if (event.getDragboard().hasImage()) {
                /* allow for copying */
                event.acceptTransferModes(TransferMode.COPY);
            }
       
        event.consume();
    }
    
    public void gridSetOnDragDropped(DragEvent event) {
        
        boolean success = false;
        GridPane curGrid = (GridPane)event.getGestureTarget();
        
        /* verifica se soltou sobre o objeto correto */
        if( ( this.gridLits.get(this.game.getAttempt()-1) == curGrid && !this.game.isChallenger() ) ||
            ( this.myGridX == curGrid && this.game.isChallenger() )
          )
        {
                /* if there is a image data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                if (db.hasImage()) {
                    
                    Image dropImg = null;
                    int gridPosDrop = (int) (event.getX()*4/curGrid.getPrefWidth());
                    success = true;
                    int prevPos = -1;
                    
                    if(event.getGestureSource() == this.bilharY){
                        
                        prevPos = this.game.curPassIndexOf(9);
                        if (prevPos != -1){
                            curGrid.getChildren().remove(this.bilharImgs.get(prevPos));
                            //this.bilharImgs.set(prevPos, new ImageView());
                            this.game.setCurPassPos(prevPos, 0);
                        }
                        
                        dropImg = new Image("fxml/images/bilhar9_dropped.png");
                        this.game.setCurPassPos(gridPosDrop, 9);
                        
                    }else if(event.getGestureSource() == this.bilharB){
                        
                        prevPos = this.game.curPassIndexOf(10);
                        if(prevPos != -1){
                            curGrid.getChildren().remove(this.bilharImgs.get(prevPos));
                            this.game.setCurPassPos(prevPos, 0);
                        }
                        
                        dropImg = new Image("fxml/images/bilhar10_dropped.png");
                        this.game.setCurPassPos(gridPosDrop, 10);
                        
                    }else if(event.getGestureSource() == this.bilharR){
                        
                        prevPos = this.game.curPassIndexOf(11);
                        if(prevPos != -1){
                            curGrid.getChildren().remove(this.bilharImgs.get(prevPos));
                            this.game.setCurPassPos(prevPos, 0);
                        }
                        
                        dropImg = new Image("fxml/images/bilhar11_dropped.png");
                        this.game.setCurPassPos(gridPosDrop, 11);
                        
                    }else if(event.getGestureSource() == this.bilharP){
                        
                        prevPos = this.game.curPassIndexOf(12);
                        if(prevPos != -1){
                            curGrid.getChildren().remove(this.bilharImgs.get(prevPos));
                            this.game.setCurPassPos(prevPos, 0);
                        }
                        
                        dropImg = new Image("fxml/images/bilhar12_dropped.png");
                        this.game.setCurPassPos(gridPosDrop, 12);
                        
                    }else if(event.getGestureSource() == this.bilharG){
                        
                        prevPos = this.game.curPassIndexOf(14);
                        if(prevPos != -1){
                            curGrid.getChildren().remove(this.bilharImgs.get(prevPos));
                            this.game.setCurPassPos(prevPos, 0);
                        }
                        
                        dropImg = new Image("fxml/images/bilhar14_dropped.png");
                        this.game.setCurPassPos(gridPosDrop, 14);
                        
                    }else if(event.getGestureSource() == this.bilharBw){
                        
                        prevPos = this.game.curPassIndexOf(15);
                        if(prevPos != -1){
                            curGrid.getChildren().remove(this.bilharImgs.get(prevPos));
                            this.game.setCurPassPos(prevPos, 0);
                        }
                        
                        dropImg = new Image("fxml/images/bilhar15_dropped.png");
                        this.game.setCurPassPos(gridPosDrop, 15);
                        
                    }else{ success = false;}
                    
                    if(success == true)
                    {
                        curGrid.getChildren().remove(this.bilharImgs.get(gridPosDrop));
                        this.bilharImgs.set(gridPosDrop, new ImageView(dropImg));
                        curGrid.add(this.bilharImgs.get(gridPosDrop), gridPosDrop,0);
                    }
                    
                }
        }
                
        
                event.setDropCompleted(success);
                
                System.out.println(success);
                
                event.consume();
    }
    
    
    public void pinoOnDragDropped(DragEvent event) {
        boolean success = false;
        GridPane curGrid = (GridPane)event.getGestureTarget();
        
        if(  this.gridPinosLits.get(this.game.getAttempt()-2) == curGrid && this.game.isChallenger()  )
        {
            Dragboard db = event.getDragboard();
            if (db.hasImage()) 
            {
                Image dropImg = null;
                success = true;
                int gridPosDrop = (int) (event.getX()*4/curGrid.getPrefWidth()); 
                
                if(event.getGestureSource() == this.pinoB){
                    dropImg = new Image("fxml/images/pino1_dropped.png");
                    this.game.setCurResultPos(gridPosDrop, 1);
                }else if(event.getGestureSource() == this.pinoP){
                    dropImg = new Image("fxml/images/pino2_dropped.png");
                    this.game.setCurResultPos(gridPosDrop, 2);
                }else{ success = false; }
                
                if(success == true)
                {
                    curGrid.add(new ImageView(dropImg), gridPosDrop,0);
                }
                
            }
        }
        
        event.setDropCompleted(success);
                
            System.out.println(success);
                
        event.consume();
    }
    
    public void fillGrid(String bilhars){
        GridPane curGrid = this.gridLits.get(this.game.getAttempt()-2);
        String[] bilhar = bilhars.split("-");
        Image dropImg;
        for (int i = 0; i < bilhar.length; i++) {
            dropImg = new Image("fxml/images/bilhar"+bilhar[i]+"_dropped.png");
            curGrid.add(new ImageView(dropImg), i, 0);
        }
    }
    
    public void fillFinalGrid(String bilhars ){
        String[] bilhar = bilhars.split("-");
        Image dropImg;
        for (int i = 0; i < bilhar.length; i++) {
            dropImg = new Image("fxml/images/bilhar"+bilhar[i]+"_dropped.png");
            this.myGridX.add(new ImageView(dropImg), i, 0);
        }
    } 
    
    public void fillPinoGrid(String pinos){
        GridPane curGrid = this.gridPinosLits.get(this.game.getAttempt()-2);
        String[] pino = pinos.split("-");
        Image dropImg;
        for (int i = 0; i < pino.length; i++) {
            if(!pino[i].equals("0"))
            {
                dropImg = new Image("fxml/images/pino"+pino[i]+"_dropped.png");
                curGrid.add(new ImageView(dropImg), i, 0);
            }
        }
    }
    
    public void enviarOnMouseEntered(MouseEvent event) {
        
        this.btnEnviar.setImage(new Image("fxml/images/enviarBtn_h.png"));
        
        event.consume();
    }
    
    public void enviarOnMouseExited(MouseEvent event) {
        
        this.btnEnviar.setImage(new Image("fxml/images/enviarBtn.png"));
        
        event.consume();
    }
    
    public void enviarOnMousePressed(MouseEvent event) {
        
        this.btnEnviar.setImage(new Image("fxml/images/enviarBtn_p.png"));
        
        event.consume();
    }
    
    public void enviarOnMouseClicked(MouseEvent event) {
        
        System.out.println("CLICKED"); 
        
        if(!this.game.isMyTurn())
        {
            Dialogs.create().lightweight()
                    .title("MasterMind - Dev by Filipe Torres")
                    .masthead(null)
                    .message("Aguarde seu adversário jogar.")
                    .showInformation();
            return;
        }
        
        if(this.game.isChallenger())
        {
            if(this.game.getAttempt() == 1)
            {
                if(this.game.checkCurPass()){
                    this.appConn.send("CanStart");

                    this.bilharB.setVisible(false);
                    this.bilharBw.setVisible(false);
                    this.bilharY.setVisible(false);
                    this.bilharR.setVisible(false);
                    this.bilharG.setVisible(false);
                    this.bilharP.setVisible(false);
                    
                    //this.chatTA.appendText("Mastermind: Senha definida, aguarde seu adversário.\n");
                    
                    //this.pinoB.setVisible(true);
                   // this.pinoP.setVisible(true);
                    
                    this.toggleTurn();

                    this.game.setAttempt(2);
                }else{
                    Dialogs.create().lightweight()
                    .title("MasterMind - Dev by Filipe Torres")
                    .masthead(null)
                    .message("Você deve definir uma senha completa antes de enviar.")
                    .showInformation();
                }
            }else{
                
                this.game.setAttempt(this.game.getAttempt()+1);
                
                //verifica se o jogo acabou
                if(this.game.curResultToString().equals("2-2-2-2"))
                {
                    this.appConn.send("result:"+this.game.curResultToString()+":"+this.game.curPassToString());
                    
                    this.isDialogPresent = true;
                    Dialogs.create().lightweight()
                    .title("MasterMind - Dev by Filipe Torres")
                    .masthead("Seu adversario acertou a senha!")
                    .message("Aperte ok para finalizar a aplicaçao.")
                    .showInformation();
                    
                    System.exit(0);
                }else if(this.game.getAttempt() > 11){
                    
                    this.appConn.send("result:"+this.game.curResultToString()+":"+this.game.curPassToString());
                    
                    this.isDialogPresent = true;
                    Dialogs.create().lightweight()
                    .title("MasterMind - Dev by Filipe Torres")
                    .masthead("Parabens, sua senha foi forte, seu adversário perdeu.")
                    .message("Aperte ok para finalizar a aplicaçao.")
                    .showInformation();
                    
                    System.exit(0);
                }else{
                    this.appConn.send("result:"+this.game.curResultToString());
                }
                
                this.game.setCurResult(new int[4]);
                this.toggleTurn();
            }
        }else{ //se for o desafiado: player que tenta acertar a senha
            if(this.game.checkCurPass()){
                if(this.game.getAttempt() < 11)
                {
                    this.appConn.send("pass:"+this.game.curPassToString());
                
                    this.game.setAttempt(this.game.getAttempt()+1);
                    this.game.setCurPass(new int[4]);
                    
                    this.toggleTurn();
                    
                    //this.chatTA.appendText("Mastermind: Senha enviada, aguarde seu adversário.\n");
                }  
            }else{
                Dialogs.create().lightweight()
                .title("MasterMind - Dev by Filipe Torres")
                .masthead(null)
                .message("Você deve definir uma senha completa antes de enviar.")
                .showInformation();
            }
        }
        
        this.btnEnviar.setImage(new Image("fxml/images/enviarBtn_h.png"));
        
        event.consume();
    }
    
    public void toggleTurn(){
        
        this.game.setMyTurn(!this.game.isMyTurn());
        
        if(this.game.isChallenger()){
            
            this.pinoB.setVisible(this.game.isMyTurn());
            this.pinoP.setVisible(this.game.isMyTurn());
            //this.btnEnviar.setDisable(!this.game.isMyTurn()); 
            
        }else {
            
            this.bilharB.setVisible(this.game.isMyTurn());
            this.bilharBw.setVisible(this.game.isMyTurn());
            this.bilharY.setVisible(this.game.isMyTurn());
            this.bilharR.setVisible(this.game.isMyTurn());
            this.bilharG.setVisible(this.game.isMyTurn());
            this.bilharP.setVisible(this.game.isMyTurn());
            //this.btnEnviar.setDisable(!this.game.isMyTurn()); 
            
        }
        
    }
    
    
    public void showIsYourTurn(){
        //this.suaVez.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            Double opacity = 1.0;
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        suaVez.setOpacity(opacity);
                    }
                });
                opacity -= 0.1;
                if(opacity < 0.1)
                    timer.cancel();
            }
            
        },0, 350);
    }
    
    
    public void sendTextToChat(KeyEvent event) throws RemoteException{
        if(event.getCode() == KeyCode.ENTER)
        {
            if(this.chatTF.getText().length()>0){
               // this.appConn.send("chat:"+this.chatTF.getText());
                String text = this.game.getMyName()+": "+this.appRmiConn.chatHelper.setMessage(this.chatTF.getText());
                this.chatTA.appendText(text+"\n");
                this.chatTF.clear();
            }
        }
    }
    
    private void manageAllMsg(String msg){
        //String[] dataMsg = msg.split(":"); 
        String dataMsg;
        
        System.out.println("manage: "+msg);
        
        if(msg.indexOf("chat:") == 0)
        {
            dataMsg = msg.substring(5);
            this.chatTA.appendText(this.game.getOtherPlayerName()+": "+dataMsg+"\n");
        }else if(msg.indexOf("pass:") == 0){
            dataMsg = msg.substring(5);
            this.fillGrid(dataMsg);
            //this.chatTA.appendText("Mastermind: É sua vez.\n");
            this.showIsYourTurn();
            this.toggleTurn();
        }else if(msg.indexOf("CanStart") == 0){
            System.out.println("gogogo");
            this.fillFinalGrid("X-X-X-X");
            //this.chatTA.appendText("Mastermind: Senha gerada pelo adversário, é sua vez.\n");
            this.showIsYourTurn();
            this.toggleTurn();
        }else if(msg.indexOf("result:") == 0){
            dataMsg = msg.substring(7);
            
            if(dataMsg.indexOf("2-2-2-2") == 0)
            {
                String[] res = dataMsg.split(":");
                this.fillPinoGrid(res[0]);
                this.fillFinalGrid(res[1]);
                this.isDialogPresent = true;
                Dialogs.create().lightweight()
                    .title("MasterMind - Dev by Filipe Torres")
                    .masthead("Parabens, voce acertou a senha!")
                    .message("Aperte ok para finalizar a aplicaçao.")
                    .showInformation();
                    
                System.exit(0);
            }else if(this.game.getAttempt()> 10)
            {
                String[] res = dataMsg.split(":");
                this.fillPinoGrid(res[0]);
                this.fillFinalGrid(res[1]);
                this.isDialogPresent = true;
                Dialogs.create().lightweight()
                    .title("MasterMind - Dev by Filipe Torres")
                    .masthead("Que pena, voce nao conseguiu adivinhar a senha :(")
                    .message("Aperte ok para finalizar a aplicaçao.")
                    .showInformation();
                    
                System.exit(0);
            }else{
                this.fillPinoGrid(dataMsg);
                //this.chatTA.appendText("Mastermind: É sua vez.\n");
                this.showIsYourTurn();
                this.toggleTurn();
            }
        }else if(msg.indexOf("PacketException") == 0){
                //caso o outro jogar desconecte no momento que ja tem um dialogo aberto (evitar excessoes e finalizaçao inesperada)
                if(!this.isDialogPresent)
                {
                    Dialogs.create().lightweight()
                            .title("MasterMind - Dev by Filipe Torres")
                            .masthead("Ops, o outro jogador parece ter se desconectado.")
                            .message("Aperte ok para finalizar a aplicaçao.")
                            .showInformation();

                    System.exit(0);
                }
        }
    }
    
    //@Override
    public void update(Observable o, Object arg) {
        //manageAllMsg(appConn.getMessage());
        //System.out.println(this.appRmiConn.chatMets.getOtherPlayerName());
        //System.out.println(arg);
        if(arg instanceof ChatHelper)
        {
            System.out.println("CHAT!");
            ChatHelper chatObj = (ChatHelper)arg;
            if(this.game.getOtherPlayerName() == null)
            {
                this.appRmiConn.server_lookups();
                this.game.setOtherPlayerName(chatObj.getOtherPlayerName());
                this.playingWith.setText("Jogando com: "+this.game.getOtherPlayerName());
                
            }else{
                this.chatTA.appendText(this.game.getOtherPlayerName()+": "+chatObj.getMessage()+"\n");
            }
        } else if(arg instanceof MainHelper)
        {
            
        }
    }
    
}
