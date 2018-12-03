package br.unipe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
	
	private ChatServer chat;
	
	public Client() {
		System.out.println("Iniciando client");
		try {
			chat = (ChatServer) Naming.lookup("rmi://locahost/MiniChat");
			System.out.println("Cliente iniciado");
			if (chat == null) {
				throw new NaoFoiPossivelConectarException();
			}
		} catch (RemoteException | MalformedURLException | NotBoundException | NaoFoiPossivelConectarException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Client client = new Client();
		Usuario usuario = new Usuario();
		
		System.out.println("Digite seu nick");
		usuario.setApelido(scan.nextLine());
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Scanner scan = new Scanner(System.in);
				String input = null;
				while(input != null && input != "Sair") {
					input = scan.nextLine();
					try {
						client.chat.enviar(input);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		t.start();
	}
}
