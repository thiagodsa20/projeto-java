package br.unipe;

public class Usuario {
	
	private Long id;
	private String apelido;
	private String senha;
	
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
	
	public void fazerLogin(String senha) {
		if(this.senha == senha && !isLogado()) {
			
		}
	}
	
	public boolean isLogado() {
		
		return true;
	}
}