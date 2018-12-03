package br.unipe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
	public void enviar(String mensagem) throws RemoteException;
	public void ler() throws RemoteException;
}
