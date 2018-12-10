package br.unipe.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import br.unipe.dao.MensagemDAO;
import br.unipe.dao.UsuarioDAO;
import br.unipe.dto.MensagemDTO;
import br.unipe.dto.UsuarioDTO;
import br.unipe.util.Utils;

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
		Scanner scan = new Scanner(System.in);
		UsuarioDTO usuario = new UsuarioDTO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		MensagemDAO mensagemDAO = new MensagemDAO();
		MensagemDTO mensagem = new MensagemDTO();
		ChatServer conexao = Client.getConexao();
		Calendar c = Calendar.getInstance();
		
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
				conexao.enviar(usuario.getApelido() + " está ONLINE!");
				usuario = usuarioDAO.buscarPorApelido(usuario.getApelido());
				mensagem.setIdUsuario(usuario.getId());
				
				t.start();
				System.out.println("Digite sua primeira mensagem: ");
				
				ArrayList<MensagemDTO> mensagens = new ArrayList<>();
				
				while(!input.equals("/sair")) {
					input = scan.nextLine();
					conexao.enviar(usuario.getApelido() +" diz: "+ input);
					
					mensagem = new MensagemDTO();
					mensagem.setIdUsuario(usuario.getId());
					mensagem.setConteudo(input);
					mensagem.setData(c.getTime());
					
					mensagens.add(mensagem);
				}
				
				Utils.persistirMensagens(mensagens);
				usuario.setOnline("N");
				usuarioDAO.atualizar(usuario);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		scan.close();
	}
}
