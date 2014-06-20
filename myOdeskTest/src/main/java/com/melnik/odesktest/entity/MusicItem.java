package com.melnik.odesktest.entity;

public class MusicItem
{

	private String title;
	private String name;
	private String album;
	private String artist;
	private String dataUrl;
	private String folderName;
	private int duration;
	private long albumId;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAlbum()
	{
		return album;
	}

	public void setAlbum(String album)
	{
		this.album = album;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getDataUrl()
	{
		return dataUrl;
	}

	public void setDataUrl(String dataUrl)
	{
		this.dataUrl = dataUrl;
	}

	public String getFolderName()
	{
		return folderName;
	}

	public void setFolderName(String folderName)
	{
		this.folderName = folderName;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public long getAlbumId()
	{
		return albumId;
	}

	public void setAlbumId(long albumId)
	{
		this.albumId = albumId;
	}

}
