package br.unifor.projetomusicas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trabalho.spring_boot_soap.CriarMusicaRequest;
import com.trabalho.spring_boot_soap.CriarPlaylistRequest;
import com.trabalho.spring_boot_soap.CriarUsuarioRequest;
import com.trabalho.spring_boot_soap.EditarMusicaRequest;
import com.trabalho.spring_boot_soap.EditarPlaylistRequest;
import com.trabalho.spring_boot_soap.EditarUsuarioRequest;
import com.trabalho.spring_boot_soap.RecuperarMusicaRequest;
import com.trabalho.spring_boot_soap.RecuperarPlaylistRequest;

import org.springframework.beans.factory.annotation.Autowired;

import br.unifor.projetomusicas.entity.Musica;
import br.unifor.projetomusicas.entity.Playlist;
import br.unifor.projetomusicas.entity.Usuario;
import br.unifor.projetomusicas.repository.MusicasRepository;
import br.unifor.projetomusicas.repository.PlaylistsRepository;
import br.unifor.projetomusicas.repository.UsuariosRepository;


@Component
public class RecursoService {
	
	@Autowired
	private PlaylistsRepository playlistRepository;
	
	@Autowired
	private UsuariosRepository usuarioRepository;
	
	@Autowired
	private MusicasRepository musicaRepository;
	
	
	@Transactional
	public com.trabalho.spring_boot_soap.Musica criarMusica(CriarMusicaRequest request) {
		com.trabalho.spring_boot_soap.Musica musicaRetorno = new com.trabalho.spring_boot_soap.Musica();
		List<com.trabalho.spring_boot_soap.Playlist> playlistsMusica = new ArrayList<>();
		
		musicaRetorno.setNome(request.getMusica().getNome());
		musicaRetorno.setArtista(request.getMusica().getArtista());
		
		List<Playlist> todasPlaylistsMusicaJpa = playlistRepository.findAll();
        List<Playlist> playlistsMusicaJpa  = new ArrayList<>();
		
		for (Integer id_playlist : request.getMusica().getPlaylists()) {
        	
        	Playlist playlist = playlistRepository.findById(id_playlist).get();
        	if(!todasPlaylistsMusicaJpa.contains(playlist)) {
        		playlistsMusicaJpa.add(playlist);
        		
        		com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();
        		
        		playlistRecurso.setIdPlaylist(playlist.getId());
        		playlistRecurso.setNome(playlist.getNome());
        		playlistRecurso.setIdUsuario(playlist.getUsuario().getId());
        		
        		playlistsMusica.add(playlistRecurso);
        	}
        }
		musicaRetorno.getPlaylists().addAll(playlistsMusica);
		
		Musica musicaJpa = new Musica();
		musicaJpa.setNome(musicaRetorno.getNome());
		musicaJpa.setArtista(musicaRetorno.getArtista());
		musicaJpa.setPlaylists(playlistsMusicaJpa);
		Musica musicaSalva = musicaRepository.save(musicaJpa);
		
		musicaRetorno.setIdMusica(musicaSalva.getId());
		
		return musicaRetorno;
	}
	
	@Transactional
	public com.trabalho.spring_boot_soap.Musica editarMusica(EditarMusicaRequest request) {
		com.trabalho.spring_boot_soap.Musica musicaRetorno = new com.trabalho.spring_boot_soap.Musica();
		List<com.trabalho.spring_boot_soap.Playlist> playlistsMusica = new ArrayList<>();
		
		Musica musicaJpa = musicaRepository.findById(request.getMusica().getIdMusica()).get();
		
		if(musicaJpa != null) {
			musicaRetorno.setNome(request.getMusica().getNome());
			musicaRetorno.setArtista(request.getMusica().getArtista());
			
			List<Playlist> todasPlaylistsMusicaJpa = playlistRepository.findAll();
	        List<Playlist> playlistsMusicaJpa  = new ArrayList<>();
			
			for (Integer id_playlist : request.getMusica().getPlaylists()) {
	        	
	        	Playlist playlist = playlistRepository.findById(id_playlist).get();
	        	if(!todasPlaylistsMusicaJpa.contains(playlist)) {
	        		playlistsMusicaJpa.add(playlist);
	        		
	        		com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();
	        		
	        		playlistRecurso.setIdPlaylist(playlist.getId());
	        		playlistRecurso.setNome(playlist.getNome());
	        		playlistRecurso.setIdUsuario(playlist.getUsuario().getId());
	        		
	        		playlistsMusica.add(playlistRecurso);
	        	}
	        }
			musicaRetorno.getPlaylists().addAll(playlistsMusica);
			
			musicaJpa.setNome(musicaRetorno.getNome());
			musicaJpa.setArtista(musicaRetorno.getArtista());
			musicaJpa.setPlaylists(playlistsMusicaJpa);
			Musica musicaSalva = musicaRepository.save(musicaJpa);
			
			musicaRetorno.setIdMusica(musicaSalva.getId());
		}
		
		return musicaRetorno;
	}
	
	@Transactional
	public com.trabalho.spring_boot_soap.Playlist criarPlaylist(CriarPlaylistRequest request) {
		com.trabalho.spring_boot_soap.Playlist playlistRetorno = new com.trabalho.spring_boot_soap.Playlist();
		List<com.trabalho.spring_boot_soap.Musica> musicasPlaylist = new ArrayList<>();
		
		Usuario usuario =  usuarioRepository.findById(request.getPlaylist().getIdUsuario()).get();
		Playlist playlistJpa = new Playlist();
		
		playlistJpa.setUsuario(usuario);
		playlistJpa.setNome(request.getPlaylist().getNome());
		
		playlistRetorno.setIdUsuario(request.getPlaylist().getIdUsuario());
		playlistRetorno.setNome(request.getPlaylist().getNome());
		
		List<Musica> todasMusicasPlaylistJpa = musicaRepository.findAll();
		List<Musica> musicasPlaylistJpa = new ArrayList<>();
		
		for (Integer id_musica : request.getPlaylist().getMusicas()) {
			Musica musica = musicaRepository.findById(id_musica).get();
			if(!todasMusicasPlaylistJpa.contains(musica)) {				
				com.trabalho.spring_boot_soap.Musica musicaRecurso = new com.trabalho.spring_boot_soap.Musica();
				
				musicaRecurso.setIdMusica(musica.getId());
				musicaRecurso.setNome(musica.getNome());
				musicaRecurso.setArtista(musica.getArtista());
				
				musicasPlaylist.add(musicaRecurso);
				musicasPlaylistJpa.add(musica);
			}
		}
		
		musicasPlaylist.forEach(musicaPlaylist -> playlistRetorno.getMusicas().add(musicaPlaylist));
		playlistJpa.setMusicas(musicasPlaylistJpa);
		
		Playlist playlistSalvo = playlistRepository.save(playlistJpa);
		playlistRetorno.setIdPlaylist(playlistSalvo.getId());
		
		return playlistRetorno;
	}
	
	@Transactional
	public com.trabalho.spring_boot_soap.Playlist editarPlaylist(EditarPlaylistRequest request) {
		com.trabalho.spring_boot_soap.Playlist playlistRetorno = new com.trabalho.spring_boot_soap.Playlist();
		List<com.trabalho.spring_boot_soap.Musica> musicasPlaylist = new ArrayList<>();
		
		Usuario usuario =  usuarioRepository.findById(request.getPlaylist().getIdUsuario()).get();
		Playlist playlistJpa = playlistRepository.findById(request.getPlaylist().getIdPlaylist()).get();
		
		if(usuario != null && playlistJpa != null) {
			playlistJpa.setUsuario(usuario);
			playlistJpa.setNome(request.getPlaylist().getNome());
			
			playlistRetorno.setIdUsuario(request.getPlaylist().getIdUsuario());
			playlistRetorno.setNome(request.getPlaylist().getNome());
			
			List<Musica> todasMusicasPlaylistJpa = musicaRepository.findAll();
			List<Musica> musicasPlaylistJpa = new ArrayList<>();
			
			for (Integer id_musica : request.getPlaylist().getMusicas()) {
				Musica musica = musicaRepository.findById(id_musica).get();
				if(!todasMusicasPlaylistJpa.contains(musica)) {				
					com.trabalho.spring_boot_soap.Musica musicaRecurso = new com.trabalho.spring_boot_soap.Musica();
					
					musicaRecurso.setIdMusica(musica.getId());
					musicaRecurso.setNome(musica.getNome());
					musicaRecurso.setArtista(musica.getArtista());
					
					musicasPlaylist.add(musicaRecurso);
					musicasPlaylistJpa.add(musica);
				}
			}
			
			musicasPlaylist.forEach(musicaPlaylist -> playlistRetorno.getMusicas().add(musicaPlaylist));
			playlistJpa.setMusicas(musicasPlaylistJpa);
			
			Playlist playlistSalvo = playlistRepository.save(playlistJpa);
			playlistRetorno.setIdPlaylist(playlistSalvo.getId());
		}
		
		return playlistRetorno;
	}
	
	@Transactional
	public com.trabalho.spring_boot_soap.Usuario editarUsuario(EditarUsuarioRequest request) {
        com.trabalho.spring_boot_soap.Usuario usuarioRetorno = new com.trabalho.spring_boot_soap.Usuario();
        List<com.trabalho.spring_boot_soap.Musica> musicasPlaylist = new ArrayList<>();
        List<com.trabalho.spring_boot_soap.Playlist> playlistsUsuario = new ArrayList<>();
        
        Usuario usuarioJpa = usuarioRepository.findById(request.getUsuario().getIdUsuario()).get();
        
        if(usuarioJpa != null) {
        	usuarioRetorno.setNome(request.getUsuario().getNome());
        	usuarioRetorno.setIdade(request.getUsuario().getIdade());
        	usuarioRetorno.setIdUsuario(request.getUsuario().getIdUsuario());
        	
        	List<Playlist> todasPlaylistsUsuarioJpa = playlistRepository.findAll();
        	List<Playlist> playlistsUsuarioJpa  = new ArrayList<>();
        	
        	for (Integer id_playlist : request.getUsuario().getPlaylists()) {
        		
        		Playlist playlist = playlistRepository.findById(id_playlist).get();
        		if(!todasPlaylistsUsuarioJpa.contains(playlist)) {
        			playlistsUsuarioJpa.add(playlist);
        			
        			for (Musica musica : playlist.getMusicas()) {
        				com.trabalho.spring_boot_soap.Musica musicaRecurso = new com.trabalho.spring_boot_soap.Musica();
        				
        				musicaRecurso.setIdMusica(musica.getId());
        				musicaRecurso.setNome(musica.getNome());
        				musicaRecurso.setArtista(musica.getArtista());
        				
        				musicasPlaylist.add(musicaRecurso);
        			}
        			
        			com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();
        			
        			playlistRecurso.setIdPlaylist(playlist.getId());
        			playlistRecurso.setNome(playlist.getNome());
        			playlistRecurso.setIdUsuario(playlist.getUsuario().getId());
        			
        			musicasPlaylist.forEach(musicaPlaylist -> playlistRecurso.getMusicas().add(musicaPlaylist));
        			
        			playlistsUsuario.add(playlistRecurso);
        		}
        		
        	}
        	usuarioRetorno.getPlaylists().addAll(playlistsUsuario);
        	
        	usuarioJpa.setNome(usuarioRetorno.getNome());
        	usuarioJpa.setIdade(usuarioRetorno.getIdade());
        	playlistsUsuarioJpa.forEach(playlistUsuarioJpa -> usuarioJpa.getPlaylists().add(playlistUsuarioJpa));
        	usuarioRepository.save(usuarioJpa);
        }
        
        return usuarioRetorno;
    }
	
	@Transactional
	public com.trabalho.spring_boot_soap.Usuario criarUsuario(CriarUsuarioRequest request) {
        com.trabalho.spring_boot_soap.Usuario usuarioRetorno = new com.trabalho.spring_boot_soap.Usuario();
        List<com.trabalho.spring_boot_soap.Musica> musicasPlaylist = new ArrayList<>();
        List<com.trabalho.spring_boot_soap.Playlist> playlistsUsuario = new ArrayList<>();
        
        usuarioRetorno.setNome(request.getUsuario().getNome());
        usuarioRetorno.setIdade(request.getUsuario().getIdade());
        
        List<Playlist> todasPlaylistsUsuarioJpa = playlistRepository.findAll();
        List<Playlist> playlistsUsuarioJpa  = new ArrayList<>();

        for (Integer id_playlist : request.getUsuario().getPlaylists()) {
        	
        	Playlist playlist = playlistRepository.findById(id_playlist).get();
        	if(!todasPlaylistsUsuarioJpa.contains(playlist)) {
        		playlistsUsuarioJpa.add(playlist);
        		
        		for (Musica musica : playlist.getMusicas()) {
        			com.trabalho.spring_boot_soap.Musica musicaRecurso = new com.trabalho.spring_boot_soap.Musica();
        			
        			musicaRecurso.setIdMusica(musica.getId());
        			musicaRecurso.setNome(musica.getNome());
        			musicaRecurso.setArtista(musica.getArtista());
        			
        			musicasPlaylist.add(musicaRecurso);
        		}
        		
        		com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();
        		
        		playlistRecurso.setIdPlaylist(playlist.getId());
        		playlistRecurso.setNome(playlist.getNome());
        		playlistRecurso.setIdUsuario(playlist.getUsuario().getId());
        		
        		musicasPlaylist.forEach(musicaPlaylist -> playlistRecurso.getMusicas().add(musicaPlaylist));
        		
        		playlistsUsuario.add(playlistRecurso);
        	}
            
        }
        usuarioRetorno.getPlaylists().addAll(playlistsUsuario);
        
        Usuario usuarioJpa = new Usuario();
        usuarioJpa.setNome(usuarioRetorno.getNome());
        usuarioJpa.setIdade(usuarioRetorno.getIdade());
        playlistsUsuarioJpa.forEach(playlistUsuarioJpa -> usuarioJpa.getPlaylists().add(playlistUsuarioJpa));
        Usuario usuarioSalvo = usuarioRepository.save(usuarioJpa);
        
        usuarioRetorno.setIdUsuario(usuarioSalvo.getId());
        return usuarioRetorno;
    }
	
	public List<com.trabalho.spring_boot_soap.Musica> recuperarMusicasPorPlaylist(RecuperarPlaylistRequest request) {

        Optional<Playlist> playlist = playlistRepository.findById(request.getId());

        List<com.trabalho.spring_boot_soap.Musica> musicasRetorno = new ArrayList<>();

        for (Musica musica : playlist.get().getMusicas()) {
            com.trabalho.spring_boot_soap.Musica musicaRecurso = new com.trabalho.spring_boot_soap.Musica();

            musicaRecurso.setIdMusica(musica.getId());
            musicaRecurso.setNome(musica.getNome());
            musicaRecurso.setArtista(musica.getArtista());

            musicasRetorno.add(musicaRecurso);
        }

        return musicasRetorno;
    }

	public List<com.trabalho.spring_boot_soap.Playlist> recuperaPlaylistPorMusica(RecuperarMusicaRequest request) {

		Optional<Musica> musica = musicaRepository.findById(request.getId());

        List<com.trabalho.spring_boot_soap.Playlist> playlistRetorno = new ArrayList<>();

        for (Playlist playlist : musica.get().getPlaylists()) {
            com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();

            playlistRecurso.setIdPlaylist(playlist.getId());
            playlistRecurso.setNome(playlist.getNome());
            playlistRecurso.setIdUsuario(playlist.getUsuario().getId());

            playlistRetorno.add(playlistRecurso);
        }

        return playlistRetorno;
    }

    public List<com.trabalho.spring_boot_soap.Musica> listarTodasMusicas() {
        List<Musica> musicas = musicaRepository.findAll();

        List<com.trabalho.spring_boot_soap.Musica> musicasRetorno = new ArrayList<>();

        for (Musica musica : musicas) {
            com.trabalho.spring_boot_soap.Musica musicaRecurso = new com.trabalho.spring_boot_soap.Musica();

            musicaRecurso.setIdMusica(musica.getId());
            musicaRecurso.setNome(musica.getNome());
            musicaRecurso.setArtista(musica.getArtista());

            musicasRetorno.add(musicaRecurso);
        }

        return musicasRetorno;
    }

    public List<com.trabalho.spring_boot_soap.Playlist> listarTodasPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();

        List<com.trabalho.spring_boot_soap.Playlist> playlistRetorno = new ArrayList<>();
        List<com.trabalho.spring_boot_soap.Musica> musicasPlaylist = new ArrayList<>();

        for (Playlist playlist : playlists) {

            for (Musica musica : playlist.getMusicas()) {
                com.trabalho.spring_boot_soap.Musica musicaRecurso = new com.trabalho.spring_boot_soap.Musica();

                musicaRecurso.setIdMusica(musica.getId());
                musicaRecurso.setNome(musica.getNome());
                musicaRecurso.setArtista(musica.getArtista());

                musicasPlaylist.add(musicaRecurso);
            }

            com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();

            playlistRecurso.setIdPlaylist(playlist.getId());
            playlistRecurso.setNome(playlist.getNome());
            playlistRecurso.setIdUsuario(playlist.getUsuario().getId());

            musicasPlaylist.forEach(musicaPlaylist -> playlistRecurso.getMusicas().add(musicaPlaylist));

            playlistRetorno.add(playlistRecurso);

        }

        return playlistRetorno;
    }

    public List<com.trabalho.spring_boot_soap.Playlist> recuperarPlaylistsPorUsuario(RecuperarPlaylistRequest request) {
        Optional<Usuario> usuario = usuarioRepository.findById(request.getIdUsuario());

        List<Playlist> playlists = usuario.get().getPlaylists();

        List<com.trabalho.spring_boot_soap.Playlist> playlistRetorno = new ArrayList<>();
        List<com.trabalho.spring_boot_soap.Musica> musicas = new ArrayList<>();

        for (Playlist playlist: playlists) {
        	RecuperarPlaylistRequest filtro = new RecuperarPlaylistRequest();

        	filtro.setId(playlist.getId());

            musicas = recuperarMusicasPorPlaylist(filtro);

            com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();

            playlistRecurso.setIdPlaylist(playlist.getId());
            playlistRecurso.setNome(playlist.getNome());
            playlistRecurso.setIdUsuario(playlist.getUsuario().getId());

            musicas.forEach(musicaPlaylist -> playlistRecurso.getMusicas().add(musicaPlaylist));


            playlistRetorno.add(playlistRecurso);

        }

        return playlistRetorno;
    }

    public List<com.trabalho.spring_boot_soap.Usuario> listarTodasUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<com.trabalho.spring_boot_soap.Usuario> usuarioRetorno = new ArrayList<>();
        List<com.trabalho.spring_boot_soap.Playlist> playlistRetorno = new ArrayList<>();
        List<com.trabalho.spring_boot_soap.Musica> musicas = new ArrayList<>();

        for (Usuario usuario : usuarios) {

            for (Playlist playlist : usuario.getPlaylists()) {
            	RecuperarPlaylistRequest filtro = new RecuperarPlaylistRequest();

            	filtro.setId(playlist.getId());

                musicas = recuperarMusicasPorPlaylist(filtro);

                com.trabalho.spring_boot_soap.Playlist playlistRecurso = new com.trabalho.spring_boot_soap.Playlist();

                playlistRecurso.setIdPlaylist(playlist.getId());
                playlistRecurso.setNome(playlist.getNome());
                playlistRecurso.setIdUsuario(playlist.getUsuario().getId());

                musicas.forEach(musicaPlaylist -> playlistRecurso.getMusicas().add(musicaPlaylist));


                playlistRetorno.add(playlistRecurso);

            }

            com.trabalho.spring_boot_soap.Usuario usuarioRecurso = new com.trabalho.spring_boot_soap.Usuario();

            usuarioRecurso.setIdUsuario(usuario.getId());
            usuarioRecurso.setNome(usuario.getNome());
            usuarioRecurso.setIdade(usuario.getIdade());

            playlistRetorno.forEach(playlistRetornoUsuario -> usuarioRecurso.getPlaylists().add(playlistRetornoUsuario));

            usuarioRetorno.add(usuarioRecurso);
        }

        return usuarioRetorno;
    }

}
