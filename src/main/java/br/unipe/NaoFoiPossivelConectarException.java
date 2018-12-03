package br.unipe;

public class NaoFoiPossivelConectarException extends Exception {
	
	public NaoFoiPossivelConectarException() {
		super("Não foi possível conectar ao servidor!");
	}
}
