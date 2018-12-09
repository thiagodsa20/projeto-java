package br.unipe.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ChatServer extends Remote {
	
	public Vector<String> ler() throws RemoteException;
	public void enviar(String mensagem) throws RemoteException;
}
