package br.unifor.projetomusicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unifor.projetomusicas.entity.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	

}
