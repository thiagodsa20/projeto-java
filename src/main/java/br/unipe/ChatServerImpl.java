package br.unipe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2637287996882942816L;
	
	private String mensagem;
	
	public ChatServerImpl() throws RemoteException {
		super();
		this.mensagem = "";
	}

	@Override
	public void enviar(String mensagem) throws RemoteException {
		this.mensagem = mensagem;
	}

	@Override
	public String ler() throws RemoteException {
		return mensagem;
	}
}
