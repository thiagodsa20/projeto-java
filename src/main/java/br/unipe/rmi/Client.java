package br.unipe.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import br.unipe.Usuario;

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
		ChatServer conexao = Client.getConexao();
		
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					int qntMensagens = conexao.ler().size();
					while(true) {
						if(qntMensagens < Client.getConexao().ler().size()) {
							System.out.println(conexao.ler().lastElement());
							qntMensagens = conexao.ler().size();
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
			conexao.enviar("Bem vindo ao chat "+ usuario.getApelido());
			System.out.println("Digite sua mensagem");
			
			while(input != "sair") {
				input = scan.nextLine();
				conexao.enviar(usuario.getApelido() +" diz: "+ input);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
