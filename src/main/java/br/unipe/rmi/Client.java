package br.unipe.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import br.unipe.dao.UsuarioDAO;
import br.unipe.dto.UsuarioDTO;

public class Client {
	
	private static ChatServer conexao;
	
	public static ChatServer getConexao() {
		if (conexao == null) {
			System.out.println("Iniciando client...");
			try {
				conexao = (ChatServer) Naming.lookup("//localhost:8080/chat");
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
		UsuarioDTO usuario = new UsuarioDTO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
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
		
		try {
			String input = "";
			boolean autenticado = true;
			
			System.out.println("Digite seu apelido: ");
			input = scan.nextLine();
			usuario.setApelido(input);
			
			if(usuario.existe()) {
				System.out.println("Informe sua senha:");
				input = scan.nextLine();
				autenticado = usuario.fazLogin(input);
			} else {
				try {
					System.out.println("Cria uma senha: ");
					input = scan.nextLine();
					usuario.setSenha(input);
				} catch (Exception e) {
					e.printStackTrace();
				}
				usuario.setOnline("S");
				usuarioDAO.inserir(usuario);
			}
			
			if(autenticado) {
				conexao.enviar("Bem vindo ao chat "+ usuario.getApelido());
				
				t.start();
				System.out.println("Digite sua primeira mensagem: ");
				
				while(input != "/sair") {
					input = scan.nextLine();
					conexao.enviar(usuario.getApelido() +" diz: "+ input);
				}
				usuario.setOnline("N");
				usuarioDAO.atualizar(usuario);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
