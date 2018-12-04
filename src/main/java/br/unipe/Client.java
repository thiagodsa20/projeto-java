package br.unipe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
	
	private static ChatServer conexao;
	
	public static ChatServer getConexao() {
		if (conexao == null) {
			System.out.println("Iniciando client...");
			try {
				conexao = (ChatServer) Naming.lookup("//127.0.0.1:8080/chat");
				System.out.println("Cliente iniciado!");
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
		}
		return conexao;
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		Usuario usuario = new Usuario();
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) {
						int qntMensagens = Client.getConexao().ler().size();
						if(qntMensagens < Client.getConexao().ler().size()) {
							String a = Client.getConexao().ler().get(0);
							System.out.println(Client.getConexao().ler().lastElement());
						}
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		
		t.start();
		
		try {
			String input = "";
			System.out.println("Digite seu apelido: ");
			input = scan.nextLine();
			usuario.setApelido(input);
			Client.getConexao().enviar("Bem vindo ao chat "+ usuario.getApelido());
			
			
			while(input != "sair") {
				input = scan.nextLine();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
