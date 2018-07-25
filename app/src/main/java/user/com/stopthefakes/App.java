package user.com.stopthefakes;

//import android.annotation.SuppressLint;
import android.app.Application;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.entity.Value;


public class App extends Application {

	private static App mApp;

	private List<DbApplication> applicationList;


	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;

		applicationList = getHardcodedApplications();
	}


	public static App getApp() {
		return mApp;
	}


	public void setApp(App app) {
		mApp = app;
	}


	public List<DbApplication> getApplicationList() {
		return applicationList;
	}


	private List<DbApplication> getHardcodedApplications() {
		List<DbApplication> applicationList = new ArrayList<>();

		DbApplication app = new DbApplication();

		app.setId(1L);
		app.setShordDescription("Detect instances of unlawful and / or improper use of the brand name in New York City at service centers, used car dealerships, car washes and other business entities dealing with auto sales and car maintenance.");
		app.setHeader("|| Automobile brand logo");
		app.setPhotosQuantity(100L);
		app.setVideosQuantity(103L);
		List<String> cities = new ArrayList<>();
		cities.add("New-York");
		cities.add("Los-Angeles");
		cities.add("Detroit");
		cities.add("Portland");
		app.setCitiesList(cities);
		app.setCountry("USA");
		app.setDescription(getString(R.string.fake_string_description));
		app.setRightToUser("The logo can only be used by monobrand car dealerships and official  service stations. There shoudn't be any logos, names or other signs associated with rival car brans beside our logo.");
		app.setTipes("Look for all the car service centers, dealerships, car washes and other points of auto sales and car maintenance where you can see at the sign-board rival car brand logos used right along with our logo.\n");
		app.setDate("22/11/2017");
		app.setAlerts(new String[]{"You need to track down illegal use of our car brand's logo at service centers, garages, used car dealerships.", "We don't give permission to exploit our brand logo to the companies other than our official representatives or dealers. Notably, we don't authorize the use of our logo in any form by multibrand maintenance stations or trading companies.", "The use of the logo in ourdoor signs, banners, print or other advertising materials along with other automobile brand logos is not sanctioned."});
		app.setType(0);
		app.setAllType(0);
		app.setImages(new int[]{R.drawable.icon1, R.drawable.icon1, R.drawable.icon1});
		app.setTopik("automobile");
		app.setSearchOpject("logo");
		app.setPhotoVideo(new Value[]{new Value(1, "18.20"), new Value(2, "27.20")});
		app.setAccepted(new String[]{"retail business", "promotional materials"});
		applicationList.add(app);

		app = new DbApplication();
		app.setId(2L);
		app.setShordDescription("Detect the occurrences of trade in women's handbags under \"ХХХ\" brand name outside the walls of the company stores in London.");
		app.setHeader("|| Selling counterfeit \"ХХХ\" handbags in London");
		app.setPhotosQuantity(100L);
		app.setVideosQuantity(103L);
		cities = new ArrayList<>();
		cities.add("London");
		app.setCitiesList(cities);
		app.setCountry("England");
		app.setDescription("Detect the occurrences of trade in women's handbags under \"ХХХ\" brand name outside the walls of the company stores in London.");
		app.setRightToUser("The trade in \"ХХХ\" women's handbags is authorized within brand retail chain only. We provide the addresses of owr official stores as well as the photos of products in the attachment.");
		app.setTipes("You might want to give closer look to pop-up retail spaces, at the squares and market places. Pay close attention to small shops offering a wide range of women's handbags and accessories. ");
		app.setDate("10/10/2017");
		app.setAlerts(new String[]{"You need to identify and record evidence of the cases of selling our products outside the brand store chain. Street vendors, stalls, small shops and retail chains are under suspicion the most.", "Our company doesn't work with dealers and representatives. All brand products are sold exclusively within our retail chain of boutiques under \"ХХХ\" shop sign. We provide the compete list of point of sales addresses in the attached file. There is no need to monitor these shops.", "We are interested both in outlets trading in our original and those selling counterfeit. You need to record the evidence of \"ХХХ\" women's handbags being placed at the product display stands, a price tag, the interior and design of the shop or sales point, the front of the building with a shop sign, if there is one. If you find a street vendor selling our products it's better not only to take his picture but to capture the location of his trading activity. Taking a video is desirable but not necessary."});
		app.setType(0);
		app.setAllType(1);
		app.setImages(new int[]{R.drawable.icon21, R.drawable.icon22, R.drawable.icon21});
		app.setTopik("clothing");
		app.setSearchOpject("counterfeit goods");
		app.setPhotoVideo(new Value[]{new Value(1, "23.30"), new Value(2, "31.20")});
		app.setAccepted(new String[]{"retail business", "wholesale business"});
		applicationList.add(app);

		app = new DbApplication();
		app.setId(4L);
		app.setShordDescription("To check on the merchandise outlay on product display stands in \"Trade\" retail chain in Poland.");
		app.setHeader("|| Monitoring the work of \"Trade\" retail chain in Poland");
		app.setPhotosQuantity(100L);
		app.setVideosQuantity(103L);
		cities = new ArrayList<>();
		cities.add("Warsaw");
		cities.add("Krakow");
		cities.add("Wroclaw");
		cities.add("Poznan");
		app.setCitiesList(cities);
		app.setCountry("Poland");
		app.setDescription(getString(R.string.fake_string_description));
		app.setRightToUser("The product outlay at the display stands needs to comply with the company's standards.");
		app.setTipes("If you see that the products are laid out in a perfunctory way and there are some empty spaces on the shelves you'll need to take several additional shots. We don't recommend to take pictures around the time of opening or closing, it's better to take them during the day.");
		app.setDate("06/10/2017");
		app.setAlerts(new String[]{"More than 256 shops comprise \"Trade\" retail chain in Poland. The list of addresses is provided in the attached file. The Doer has to choose one of the shops and take pictures of the display of products. A separate Alert with 10-15 photos should be created for each shop. The first two pictures have to show the entrance with a shop sign and a display window. Other pictures have to show the product display. There are 3-5 display stands per shop. You need to photograph each of them twice. The pictures should be in HD so as the product names can be readable. Talking to shop personnel is not allowed."});
		app.setType(1);
		app.setAllType(0);
		app.setImages(new int[]{R.drawable.icon41});
		app.setTopik("business");
		app.setSearchOpject("control object");
		app.setPhotoVideo(new Value[]{new Value(1, "18.20")});
		app.setAccepted(new String[]{"retail business"});
		applicationList.add(app);

		app = new DbApplication();
		app.setId(6L);
		app.setHeader("|| Illegal use of Alex Smith's copyright material (pictures, videos, audios, etc.)");
		app.setShordDescription("A famous rapper and public figure Alex Smith is looking for occurrences of illegal use of his copyright materials for commercial purposes.");
		app.setPhotosQuantity(100L);
		app.setVideosQuantity(103L);
		app.setTipes("When looking for violations, the visual ads are of primary interest. We had also been notified that some of the clothes and accessories manufacturers misuse the artist's signature style and portraits. Their products are usually found in small shops and street vendors.");
		app.setRightToUser("The companies and brands mentioned in the attachment have exclusive right to exploit  Alex Smith's copyright material for commercial purposes. ");
		app.setAlerts(new String[]{"You need to report instances of illegal use of photos, audio tracks and videos by an American singer Alex Smith for commercial purposes. We've attached the list of companies and brands that legitimately collaborate with the artist. The use of his signature style, lyrics, mucisal works and other IP rights by other parties is considered to be in violation of the law.", "We accept all types of Alerts — with pictures of street and other types of ads, boardsigns, videos of other artists who used musical works without the author's consent. Mind that the Requestor is only interested in the US-based violations and english-speaking sites infringements. We don't need any other materials."});
		app.setType(1);
		app.setCountry("USA");
		app.setDate("12/08/2017");
		cities = new ArrayList<>();
		cities.add("New-York");
		cities.add("Los-Angeles");
		cities.add("Detroit");
		cities.add("Portland");
		app.setCitiesList(cities);
		app.setAllType(2);
		app.setImages(new int[]{R.drawable.icon6, R.drawable.icon6});
		app.setTopik("entertaiment");
		app.setSearchOpject("photography");
		app.setPhotoVideo(new Value[]{new Value(1, "27.60"), new Value(2, "33.50")});
		app.setAccepted(new String[]{"retail business", "promotional materials", "iternet"});
		applicationList.add(app);

		Collections.sort(applicationList, new Comparator<DbApplication>() {
			@Override
			public int compare(DbApplication dbApplication, DbApplication t1) {
				return t1.getDate().compareTo(dbApplication.getDate());
			}
		});

		return applicationList;
	}


	/*private String formatDate() {
		@SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		return df.format(Calendar.getInstance().getTime());
	}*/

}