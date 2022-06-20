package br.unifor.projetomusicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unifor.projetomusicas.entity.Musica;
import br.unifor.projetomusicas.entity.Playlist;


public interface MusicasRepository extends JpaRepository<Musica, Integer> {

	List<Playlist> findMusicasByPlaylistsId(Integer idPlaylist);
}
