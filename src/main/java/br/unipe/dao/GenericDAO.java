package br.unipe.dao;

public interface GenericDAO<T> {
	
	void inserir(T objeto);
	void atualizar(T objeto);
	void deletar(Long id);
	T buscarPorId(Long id);
}
