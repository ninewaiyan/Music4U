package com.music4u.models;

public class Album {

	private Long id;
	private String name;
	private String image;
	private Long artist_id;
	private String artistName; // Add this property

	public Album(Long id, String name, String image, Long artist_id) {

		this.id = id;
		this.name = name;
		this.image = image;
		this.artist_id = artist_id;
	}

	public Album(String name, String image, Long artist_id) {

		this.name = name;
		this.image = image;
		this.artist_id = artist_id;
	}

	public Long getArtistId() {
		return artist_id;
	}

	public void setArtistId(Long user_id) {
		this.artist_id =  user_id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

}
