package br.unipe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	
	public Server() {
		System.out.println("Iniciando servidor...");
		try {
			Registry r = LocateRegistry.createRegistry(80);
			ChatServer chat = new ChatServerImpl();
			Naming.rebind("rmi://localhost/MiniChat", chat);
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("Server ON!");
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
