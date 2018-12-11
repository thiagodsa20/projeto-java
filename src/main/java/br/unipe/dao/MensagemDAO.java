package br.unipe.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.unipe.dto.MensagemDTO;
import br.unipe.util.ConexaoUtil;

public class MensagemDAO {
	
	private Connection conexao;
	
	public MensagemDAO() {
		conexao = ConexaoUtil.getInstance().getConnection();
	}
	
	public void inserir(MensagemDTO mensagem) {
		String sql = "INSERT INTO T_MENSAGEM (ID_USUARIO, CONTEUDO, DATA) VALUES (?, ?, ?)";
		
		try {
			//Preparando consulta para enviar para o banco
			PreparedStatement st = conexao.prepareStatement(sql);
			
			//Setando parametros
			st.setLong(1, mensagem.getIdUsuario());
			st.setString(2, mensagem.getConteudo());
			st.setDate(3, new Date(mensagem.getData().getTime()));
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
	}
	
//	public void atualizar(MensagemDTO mensagem) {
//		StringBuilder sql = new StringBuilder();
//		
//		sql.append(" UPDATE T_MENSAGEM ");
//		sql.append(" SET ID_USUARIO = ?,");
//		sql.append(" CONTEUDO = ?");
//		sql.append(" DATE = ?");
//		
//		try {
//			conexao.prepareStatement(sql.toString());
//			ConexaoUtil.getInstance().commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			ConexaoUtil.getInstance().rollback();
//		} finally {
//			ConexaoUtil.getInstance().closeConnection();
//		}
//	}
}
