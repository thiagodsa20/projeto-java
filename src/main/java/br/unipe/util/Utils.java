package br.unipe.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.unipe.dao.MensagemDAO;
import br.unipe.dto.MensagemDTO;

public class Utils {
	
	public static void persistirMensagensTimer(List<MensagemDTO> mensagens) {
		MensagemDAO mensagemDAO = new MensagemDAO();
		final long TEMPO = (5000 * 60); //executa a cada 5 min
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.err.println("Salvando suas mensagens...");
                
                mensagens.forEach(m -> {
                	mensagemDAO.inserir(m);
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, TEMPO, TEMPO);
	}
	
	public static void persistirMensagens(List<MensagemDTO> mensagens) {
		MensagemDAO mensagemDAO = new MensagemDAO();
		  mensagens.forEach(m -> {
          	mensagemDAO.inserir(m);
          });
	}

}
