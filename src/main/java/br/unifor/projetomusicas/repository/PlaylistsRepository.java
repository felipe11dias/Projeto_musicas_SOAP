package br.unifor.projetomusicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unifor.projetomusicas.entity.Playlist;


public interface PlaylistsRepository extends JpaRepository<Playlist, Integer> {
	
	List<Playlist> findPlaylistsByMusicasId(Integer idMusica);

}
