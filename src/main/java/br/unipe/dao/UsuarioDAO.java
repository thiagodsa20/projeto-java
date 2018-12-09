package br.unipe.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.unipe.dto.UsuarioDTO;
import br.unipe.util.ConexaoUtil;

public class UsuarioDAO implements GenericDAO<UsuarioDTO> {
	
	private Connection conexao;
	
	public UsuarioDAO() {
		conexao = ConexaoUtil.getInstance().getConnection();
	}
	
	public void inserir(UsuarioDTO usuarioDTO) {
		String sql = " INSERT INTO T_USUARIO (APELIDO, SENHA, ONLINE) VALUES (?, ?, ?) ";
		
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, usuarioDTO.getApelido());
			st.setString(2, usuarioDTO.getSenha());
			st.setString(3, usuarioDTO.getOnline());
			st.execute();
			ConexaoUtil.getInstance().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
		
	}

	public void atualizar(UsuarioDTO usuarioDTO) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE T_USUARIO ");
		sql.append(" SET APELIDO = ?, ");
		sql.append(" SENHA = ?, ");
		sql.append(" ONLINE = ?");
		sql.append(" WHERE ID_USUARIO = ? ");
		
		try {
			PreparedStatement st = conexao.prepareStatement(sql.toString());
			st.setString(1, usuarioDTO.getApelido());
			st.setString(2, usuarioDTO.getSenha());
			st.setString(3, usuarioDTO.getOnline());
			st.setLong(4, usuarioDTO.getId());
			st.execute();
			ConexaoUtil.getInstance().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
	}

	public void deletar(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM T_USUARIO ");
		sql.append(" WHERE ID_USUARIO = ?, ");
		
		
		try {
			PreparedStatement st = conexao.prepareStatement(sql.toString());
			st.setLong(1, id);
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
	}

	public UsuarioDTO buscarPorId(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT APELIDO, SENHA, ONLINE ");
		sql.append(" FROM T_USUARIO ");
		sql.append(" WHERE ID_USUARIO = ? ");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(id);
		try {
			PreparedStatement st = conexao.prepareStatement(sql.toString());
			st.setLong(1, id);
			ResultSet resultSet = st.executeQuery(sql.toString());
			while(resultSet.next()) {
				usuarioDTO.setApelido(resultSet.getString(1));
				usuarioDTO.setSenha(resultSet.getString(2));
				usuarioDTO.setOnline(resultSet.getString(3));
			}
			return usuarioDTO;
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
		
		return null;
	}
	
	public UsuarioDTO buscarPorApelido(String apelido) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ID_USUARIO, APELIDO, SENHA, ONLINE");
		sql.append(" FROM T_USUARIO");
		sql.append(" WHERE APELIDO = ?");
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		try {
			PreparedStatement st = conexao.prepareStatement(sql.toString());
			st.setString(1, apelido);
			ResultSet resultSet = st.executeQuery();
			
			while(resultSet.next()) {
				usuarioDTO.setId(resultSet.getLong(1));
				usuarioDTO.setApelido(resultSet.getString(2));
				usuarioDTO.setSenha(resultSet.getString(3));
				usuarioDTO.setOnline(resultSet.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
		
		return usuarioDTO;
	}
	
	public String isOnline(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ONLINE ");
		sql.append(" FROM T_USUARIO ");
		sql.append(" WHERE APELIDO = ? ");
		
		String online = null;
		
		try {
			PreparedStatement st = conexao.prepareStatement(sql.toString());
			st.setLong(1, id);
			ResultSet resultSet = st.executeQuery();
			
			while(resultSet.next()) {
				online = resultSet.getString(3);
			}
			ConexaoUtil.getInstance().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
		
		return online;
	}
	
	public void setTodosUsuariosOffline() {
		String sql = " UPDATE T_USUARIO SET ONLINE = ? ";
		
		try {
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, "N");
			st.execute();
			ConexaoUtil.getInstance().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ConexaoUtil.getInstance().rollback();
		} finally {
			ConexaoUtil.getInstance().closeConnection();
		}
	}
}
