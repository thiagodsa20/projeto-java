package br.unipe;

public class NaoFoiPossivelConectarException extends Exception {
	
	public NaoFoiPossivelConectarException() {
		super("N�o foi poss�vel conectar ao servidor!");
	}
}
