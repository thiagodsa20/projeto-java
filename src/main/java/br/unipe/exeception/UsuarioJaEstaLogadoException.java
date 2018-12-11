package br.unipe.exeception;

public class UsuarioJaEstaLogadoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -156894504094428071L;
	
	public UsuarioJaEstaLogadoException() {
		super("Usuário já está logado!");
	}
}
