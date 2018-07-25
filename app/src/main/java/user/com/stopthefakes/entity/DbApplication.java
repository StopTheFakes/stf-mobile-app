package user.com.stopthefakes.entity;

import java.util.List;


public class DbApplication {

	private long id;
	private String header;
	private long photosQuantity;
	private long videosQuantity;
	private List<String> citiesList;
	private String country;
	private String description;
	private String rightToUser;
	private String tipes;
	private String date;
	private int type;
	private int allType;
	private String shordDescription;
	private String[] alerts;
	private int[] images;
	private Value[] photoVideo;
	private String topik;
	private String searchOpject;
	private String[] accepted;


	public String[] getAccepted() {
		return accepted;
	}


	public void setAccepted(String[] accepted) {
		this.accepted = accepted;
	}


	public String getTopik() {
		return topik;
	}


	public void setTopik(String topik) {
		this.topik = topik;
	}


	public String getSearchOpject() {
		return searchOpject;
	}


	public void setSearchOpject(String searchOpject) {
		this.searchOpject = searchOpject;
	}


	public Value[] getPhotoVideo() {
		return photoVideo;
	}


	public void setPhotoVideo(Value[] photoVideo) {
		this.photoVideo = photoVideo;
	}


	public int getAllType() {
		return allType;
	}


	public void setAllType(int allType) {
		this.allType = allType;
	}


	public int[] getImages() {
		return images;
	}


	public void setImages(int[] images) {
		this.images = images;
	}


	public String[] getAlerts() {
		return alerts;
	}


	public void setAlerts(String[] alerts) {
		this.alerts = alerts;
	}


	public String getShordDescription() {
		return shordDescription;
	}


	public void setShordDescription(String shordDescription) {
		this.shordDescription = shordDescription;
	}


	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public long getPhotosQuantity() {
		return photosQuantity;
	}


	public void setPhotosQuantity(long photosQuantity) {
		this.photosQuantity = photosQuantity;
	}


	public long getVideosQuantity() {
		return videosQuantity;
	}


	public void setVideosQuantity(long videosQuantity) {
		this.videosQuantity = videosQuantity;
	}


	public List<String> getCitiesList() {
		return citiesList;
	}


	public void setCitiesList(List<String> citiesList) {
		this.citiesList = citiesList;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getRightToUser() {
		return rightToUser;
	}


	public void setRightToUser(String rightToUser) {
		this.rightToUser = rightToUser;
	}


	public String getTipes() {
		return tipes;
	}


	public void setTipes(String tipes) {
		this.tipes = tipes;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public Long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}

}