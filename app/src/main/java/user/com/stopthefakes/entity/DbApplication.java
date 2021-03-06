package user.com.stopthefakes.entity;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DbApplication {

	private long id = 0;
	private String header = "";
	private long photosQuantity = 0;
	private long videosQuantity = 0;
	private List<String> citiesList = new ArrayList<>();
	private String country = "";
	private String description = "";
	private String rightToUser = "";
	private String tips = "";
	private String date = "";
	private int type = 0;
	private int allType = 0;
	private int expires = 0;
	private String expiresString = "";
	private String shordDescription = "";
	private String[] alerts = new String[]{};
	private int[] images = new int[]{};
	private Value[] photoVideo = new Value[]{};
	private String topik = "";
	private String searchOpject = "";
	private String[] accepted = new String[]{};


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


	public String getAlert(int pos) {
		return alerts.length > pos ? alerts[pos] : "";
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


	public String getTips() {
		return tips;
	}


	public void setTips(String tips) {
		this.tips = tips;
	}


	public void setExpires(int expires) {
		this.expires = expires;
	}


	public void setExpiresString(String expires) {
		this.expiresString = expires;
	}


	public String getExpiresString() {
		return this.expiresString;
	}


	public int getType() {
		return type;
	}


	public void setIsWaiting() {
		type = 0;
	}


	public void setIsTaken() {
		type = 1;
	}


	public void setIsExpired() {
		type = 2;
	}


	public void setAllIsWaiting() {
		allType = 0;
	}


	public void setAllIsTaken() {
		allType = 1;
	}


	public void setAllIsExpired() {
		allType = 2;
	}


	public boolean isWaiting() {
		return type == 0;
	}


	public boolean isTaken() {
		return type == 1;
	}


	public boolean isExpired() {
		return type == 2;
	}


	public static boolean isTypeWaiting(int type) {
		return type == 0;
	}


	public static boolean isTypeTaken(int type) {
		return type == 1;
	}


	public static boolean isTypeExpired(int type) {
		return type == 2;
	}


	public String getTimeLeft() {
		if (isExpired()) {
			return "00:00";
		}
		if (isTaken()) {
			int hours = expires / 60 / 60;
			int minutes = (expires - hours * 60 * 60) / 60;
			return hours + ":" + minutes;
		}
		return "";
	}


	public String getTimeLeftFromExpiresString() {
		int curSecs = (int) (new Date().getTime() / 1000);
		DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		String src = getExpiresString();
		if (src.length() > 0) {
			try {
				Date dateParsed = sourceFormat.parse(src);
				int secsTo = (int) (dateParsed.getTime() / 1000);
				if (secsTo < curSecs) {
					return "00:00";
				}
				int delta = secsTo - curSecs;
				int hours = delta / 60 / 60;
				int minutes = (delta - hours * 60 * 60) / 60;
				return hours + ":" + minutes;
			} catch (Exception e) {
				Log.e("Parse date", e.getMessage(), e);
			}
		}
		return "";
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