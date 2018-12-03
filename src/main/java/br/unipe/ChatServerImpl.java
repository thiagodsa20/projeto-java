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
	}

	@Override
	public synchronized void enviar(String mensagem) throws RemoteException {
		this.mensagem = mensagem;
	}

	@Override
	public void ler() throws RemoteException {
		String mensagemAntiga = "";
		while(mensagemAntiga != this.mensagem) {
			System.out.println(mensagem);
			mensagemAntiga = this.mensagem;
		}
	}
}
