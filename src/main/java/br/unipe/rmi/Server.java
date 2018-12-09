package br.unipe.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	
	public Server() {
		System.out.println("Iniciando servidor...");
		try {
			Registry registry = LocateRegistry.createRegistry(8080);
			ChatServer server = new ChatServerImpl();
			Naming.rebind("//127.0.0.1:8080/chat", server);
			System.out.println("Server ON!");
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}

}
