/*
 * Classe que vai armazenar os estados do game.
 */

package mastermind;

/**
 *
 * @author filipe_bvr
 */
public class MainModel {
    
    private String myName;
    private String otherPlayerName;
    private int attempt;
    private int[] curPass; 
    private int[] curResult;
    private boolean challenger;
    private boolean myTurn;
    
    public MainModel(String name)
    {
        this.attempt = 1;
        this.myName = name;
        this.curPass = new int[4];
        this.curResult = new int[4];
        this.myTurn = false;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getOtherPlayerName() {
        return otherPlayerName;
    }

    public void setOtherPlayerName(String otherPlayerName) {
        this.otherPlayerName = otherPlayerName;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public int[] getCurPass() {
        return curPass;
    }
    
    public int curPassIndexOf(int find){
        for (int i = 0; i < this.curPass.length; i++) {
            if(this.curPass[i] == find){ return i; }
        }
        return -1;
    }

    public void setCurPass(int[] curPass) {
        this.curPass = curPass;
    }

    public void setCurPassPos(int pos, int value) {
        if(pos > 3 || pos < 0)
            return;
        this.curPass[pos] = value;
    }
    /**
    * 
    * @deprecated Utilize setCurPassPos em seu lugar!
    */
    @Deprecated
    public void addToCurPass(int pos, int value){
        this.curPass[pos] = value;
    }
    
    public boolean isChallenger() {
        return challenger;
    }

    public void setChallenger(boolean challenger) {
        this.challenger = challenger;
    }
    /**
    *  
    * @return true se todos os valores de curPass forem diferentes de 0
    */
    public boolean checkCurPass(){
        for(int value : this.curPass)
            if(value == 0) return false;
        
        return true;
    }
    /**
    *  
    * @return Retorna a representaçao em string dos valores do array Ex: "0-9-10-2"
    */
    public String curPassToString(){
        String res = "";
        for(int value : this.curPass)
            res += value+"-";
        res = res.substring(0, res.length()-1);
        return res;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public int[] getCurResult() {
        return curResult;
    }

    public void setCurResult(int[] curResult) {
        this.curResult = curResult;
    }
    
    public void setCurResultPos(int pos, int value) {
        if(pos > 3 || pos < 0)
            return;
        this.curResult[pos] = value;
    }
    
    /**
    *  
    * @return Retorna a representaçao em string dos valores do array Ex: "0-9-10-2"
    */
    public String curResultToString(){
        String res = "";
        for(int value : this.curResult)
            res += value+"-";
        res = res.substring(0, res.length()-1);
        return res;
    }
}
