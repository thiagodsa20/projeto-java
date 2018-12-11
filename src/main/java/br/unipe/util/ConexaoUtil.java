package br.unipe.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoUtil {
	
	private static ConexaoUtil conexao;
	
	public static ConexaoUtil getInstance() {
		if(conexao == null) {
			conexao = new ConexaoUtil();
		}
		return conexao;
	}
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/chatrmi?createDatabaseIfNotExist=true", "root", "");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void closeConnection() {
		try {
			getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rollback() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void commit() {
		try {
			getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
