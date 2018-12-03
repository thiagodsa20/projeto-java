package br.unipe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
	
	private ChatServer chat;
	
	public Client() {
		System.out.println("Iniciando client...");
		try {
			chat = (ChatServer) Naming.lookup("rmi://127.0.0.1:8080/chat");
			System.out.println("Cliente iniciado!");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Client client = new Client();
		Usuario usuario = new Usuario();
		String mensagem = "";
		
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
			
		try {
			System.out.println("Digite seu nick: ");
			usuario.setApelido(scan.nextLine());
			client.chat.enviar("Bem vindo ao chat " + usuario.getApelido() + "!");
			
			while(mensagem != client.chat.ler()) {
				System.out.println(client.chat.ler());
			}
			
			t.start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
