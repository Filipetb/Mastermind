/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mastermind;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author filipe_bvr
 */
public interface ItfHelper  extends Remote{
    String inverter(String msg) throws RemoteException;
}
