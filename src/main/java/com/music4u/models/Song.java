package com.music4u.models;

public class Song{

	private Long id;
	private String name;
	private String path;
	private Long album_id;
	private Long artist_id;
	private String albumName; // To store the album name
    private String artistName; // To store the artist name

	public Song(Long id, String name, String path, Long album_id) {

		this.id = id;
		this.name = name;
		this.path = path;
		this.album_id = album_id;
	}
	
	public Song(Long id, String name, String path, Long album_id,String albumName,String artistName) {

		this.id = id;
		this.name = name;
		this.path = path;
		this.album_id = album_id;
		this.albumName = albumName;
		this.artistName = artistName;
	}

	public Song(String name, String path, Long album_id) {

		this.name = name;
		this.path = path;
		this.album_id = album_id;
	}

	public Long getAlbumId() {
		return album_id;
	}

	public void setAlbumId(Long user_id) {
		this.album_id =  user_id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	 public String getAlbumName() {
	        return albumName;
	    }

	    public void setAlbumName(String albumName) {
	        this.albumName = albumName;
	    }

	    public String getArtistName() {
	        return artistName;
	    }

	    public void setArtistName(String artistName) {
	        this.artistName = artistName;
	    }

}
