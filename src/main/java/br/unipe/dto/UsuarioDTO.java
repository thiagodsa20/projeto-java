package br.unipe.dto;

import br.unipe.dao.UsuarioDAO;
import br.unipe.exeception.UsuarioJaEstaLogadoException;

public class UsuarioDTO {
	
	private Long id;
	private String apelido;
	private String senha;
	private String online;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	
	public boolean existe() {
		UsuarioDTO usuarioDTO = new UsuarioDAO().buscarPorApelido(getApelido());
		if(usuarioDTO.getApelido() != null) {
			return true;
		}
		return false;
	}
	public boolean fazLogin(String senha) {
		UsuarioDTO usuarioDTO = new UsuarioDAO().buscarPorApelido(getApelido());
		try {
			if (usuarioDTO.isOnline()) {
				throw new UsuarioJaEstaLogadoException();
			}
			if(!senha.equals(usuarioDTO.getSenha())) {
				System.out.println("Senha incorreta!");
				return false;
			}
			System.out.println("Autenticação realizada com sucesso!");
			
			usuarioDTO.setOnline("S");
			setOnline("S");
			new UsuarioDAO().atualizar(usuarioDTO);
			
			return true;
		} catch (UsuarioJaEstaLogadoException e) {
			System.out.println("Usuário já está logado!");
		}
		return false;
	}
	public boolean isOnline() {
		String online = new UsuarioDAO().isOnline(getId());
		if(!"S".equals(online)){
			return false;
		}
		return true;
	}
}