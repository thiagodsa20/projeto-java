package br.unipe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
	
	private Vector<String> mensagens;
	
	public ChatServerImpl() throws RemoteException {
		this.mensagens = new Vector<String>();
	}
	
	public void enviar(String mensagem) throws RemoteException {
		this.mensagens.add(mensagem);
	}
	
	public synchronized Vector<String> ler() throws RemoteException {
		return this.mensagens;
	}
}
