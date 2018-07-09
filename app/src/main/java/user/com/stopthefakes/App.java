package user.com.stopthefakes;

import android.app.Application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    public void setApplicationList(List<DbApplication> applicationList) {
        this.applicationList = applicationList;
    }

    private List<DbApplication> getHardcodedApplications() {

        List<DbApplication> applicationList = new ArrayList<>();
        List<String> cities = new ArrayList<>();
        cities.add("Moscow");


        String date = formatDate();

        DbApplication dbApplication1 = new DbApplication();
        dbApplication1.setId(1L);
        dbApplication1.setShordDescription("Detect instances of unlawful and / or improper use of the brand name in New York City at service centers, used car dealerships, car washes and other business entities dealing with auto sales and car maintenance.");
        dbApplication1.setHeader("Automobile brand logo");
        dbApplication1.setPhotosQuantity(100L);
        dbApplication1.setVideosQuantity(103L);
        List<String> cities1 = new ArrayList<>();
        cities1.add("New-York");
        cities1.add("Los-Angeles");
        cities1.add("Detroit");
        cities1.add("Portland");
        dbApplication1.setCitiesList(cities1);
        dbApplication1.setCountry("USA");
        dbApplication1.setDescription(getString(R.string.fake_string_description));
        dbApplication1.setRightToUser("The logo can only be used by monobrand car dealerships and official  service stations. There shoudn't be any logos, names or other signs associated with rival car brans beside our logo.");
        dbApplication1.setTipes("Look for all the car service centers, dealerships, car washes and other points of auto sales and car maintenance where you can see at the sign-board rival car brand logos used right along with our logo.\n");
        dbApplication1.setDate("22/11/2017");
        dbApplication1.setAlerts(new String[]{"You need to track down illegal use of our car brand's logo at service centers, garages, used car dealerships.", "We don't give permission to exploit our brand logo to the companies other than our official representatives or dealers. Notably, we don't authorize the use of our logo in any form by multibrand maintenance stations or trading companies.", "The use of the logo in ourdoor signs, banners, print or other advertising materials along with other automobile brand logos is not sanctioned."});
        dbApplication1.setType(0);
        dbApplication1.setAllType(0);
        dbApplication1.setImages(new int[]{R.drawable.icon1, R.drawable.icon1, R.drawable.icon1});
        dbApplication1.setTopik("automobile");
        dbApplication1.setSearchOpject("logo");
        dbApplication1.setPhotoVideo(new Value[]{new Value(1, "18.20"), new Value(2, "27.20")});
        dbApplication1.setAccepted(new String[]{"retail business", "promotional materials"});


        DbApplication dbApplication2 = new DbApplication();
        dbApplication2.setId(2L);
        dbApplication2.setShordDescription("Detect the occurrences of trade in women's handbags under \"ХХХ\" brand name outside the walls of the company stores in London.");
        dbApplication2.setHeader("Selling counterfeit \"ХХХ\" handbags in London");
        dbApplication2.setPhotosQuantity(100L);
        dbApplication2.setVideosQuantity(103L);
        List<String> cities2 = new ArrayList<>();
        cities2.add("London");
        dbApplication2.setCitiesList(cities2);
        dbApplication2.setCountry("England");
        dbApplication2.setDescription("Detect the occurrences of trade in women's handbags under \"ХХХ\" brand name outside the walls of the company stores in London.");
        dbApplication2.setRightToUser("The trade in \"ХХХ\" women's handbags is authorized within brand retail chain only. We provide the addresses of owr official stores as well as the photos of products in the attachment.");
        dbApplication2.setTipes("You might want to give closer look to pop-up retail spaces, at the squares and market places. Pay close attention to small shops offering a wide range of women's handbags and accessories. ");
        dbApplication2.setDate("10/10/2017");
        dbApplication2.setAlerts(new String[]{"You need to identify and record evidence of the cases of selling our products outside the brand store chain. Street vendors, stalls, small shops and retail chains are under suspicion the most.", "Our company doesn't work with dealers and representatives. All brand products are sold exclusively within our retail chain of boutiques under \"ХХХ\" shop sign. We provide the compete list of point of sales addresses in the attached file. There is no need to monitor these shops.", "We are interested both in outlets trading in our original and those selling counterfeit. You need to record the evidence of \"ХХХ\" women's handbags being placed at the product display stands, a price tag, the interior and design of the shop or sales point, the front of the building with a shop sign, if there is one. If you find a street vendor selling our products it's better not only to take his picture but to capture the location of his trading activity. Taking a video is desirable but not necessary."});
        dbApplication2.setType(0);
        dbApplication2.setAllType(1);
        dbApplication2.setImages(new int[]{R.drawable.icon21, R.drawable.icon22, R.drawable.icon21});
        dbApplication2.setTopik("clothing");
        dbApplication2.setSearchOpject("counterfeit goods");
        dbApplication2.setPhotoVideo(new Value[]{new Value(1, "23.30"), new Value(2, "31.20")});
        dbApplication2.setAccepted(new String[]{"retail business", "wholesale business"});

        DbApplication dbApplication4 = new DbApplication();
        dbApplication4.setId(4L);
        dbApplication4.setShordDescription("To check on the merchandise outlay on product display stands in \"Trade\" retail chain in Poland.");
        dbApplication4.setHeader("Monitoring the work of \"Trade\" retail chain in Poland");
        dbApplication4.setPhotosQuantity(100L);
        dbApplication4.setVideosQuantity(103L);
        List<String> cities3 = new ArrayList<>();
        cities3.add("Warsaw");
        cities3.add("Krakow");
        cities3.add("Wroclaw");
        cities3.add("Poznan");
        dbApplication4.setCitiesList(cities3);
        dbApplication4.setCountry("Poland");
        dbApplication4.setDescription(getString(R.string.fake_string_description));
        dbApplication4.setRightToUser("The product outlay at the display stands needs to comply with the company's standards.");
        dbApplication4.setTipes("If you see that the products are laid out in a perfunctory way and there are some empty spaces on the shelves you'll need to take several additional shots. We don't recommend to take pictures around the time of opening or closing, it's better to take them during the day.");
        dbApplication4.setDate("06/10/2017");
        dbApplication4.setAlerts(new String[]{"More than 256 shops comprise \"Trade\" retail chain in Poland. The list of addresses is provided in the attached file. The Doer has to choose one of the shops and take pictures of the display of products. A separate Alert with 10-15 photos should be created for each shop. The first two pictures have to show the entrance with a shop sign and a display window. Other pictures have to show the product display. There are 3-5 display stands per shop. You need to photograph each of them twice. The pictures should be in HD so as the product names can be readable. Talking to shop personnel is not allowed."});
        dbApplication4.setType(1);
        dbApplication4.setAllType(0);
        dbApplication4.setImages(new int[]{R.drawable.icon41});
        dbApplication4.setTopik("business");
        dbApplication4.setSearchOpject("control object");
        dbApplication4.setPhotoVideo(new Value[]{new Value(1, "18.20")});
        dbApplication4.setAccepted(new String[]{"retail business"});


        DbApplication dbApplication6 = new DbApplication();
        dbApplication6.setId(6L);
        dbApplication6.setHeader("Illegal use of Alex Smith's copyright material (pictures, videos, audios, etc.)");
        dbApplication6.setShordDescription("A famous rapper and public figure Alex Smith is looking for occurrences of illegal use of his copyright materials for commercial purposes.");
        dbApplication6.setPhotosQuantity(100L);
        dbApplication6.setVideosQuantity(103L);
        dbApplication6.setTipes("When looking for violations, the visual ads are of primary interest. We had also been notified that some of the clothes and accessories manufacturers misuse the artist's signature style and portraits. Their products are usually found in small shops and street vendors.");
        dbApplication6.setRightToUser("The companies and brands mentioned in the attachment have exclusive right to exploit  Alex Smith's copyright material for commercial purposes. ");
        dbApplication6.setAlerts(new String[]{"You need to report instances of illegal use of photos, audio tracks and videos by an American singer Alex Smith for commercial purposes. We've attached the list of companies and brands that legitimately collaborate with the artist. The use of his signature style, lyrics, mucisal works and other IP rights by other parties is considered to be in violation of the law.", "We accept all types of Alerts — with pictures of street and other types of ads, boardsigns, videos of other artists who used musical works without the author's consent. Mind that the Requestor is only interested in the US-based violations and english-speaking sites infringements. We don't need any other materials."});
        dbApplication6.setType(1);
        dbApplication6.setCountry("USA");
        dbApplication6.setDate("12/08/2017");
        List<String> cities6 = new ArrayList<>();
        cities6.add("New-York");
        cities6.add("Los-Angeles");
        cities6.add("Detroit");
        cities6.add("Portland");
        dbApplication6.setCitiesList(cities6);
        dbApplication6.setAllType(2);
        dbApplication6.setImages(new int[]{R.drawable.icon6, R.drawable.icon6});
        dbApplication6.setTopik("entertaiment");
        dbApplication6.setSearchOpject("photography");
        dbApplication6.setPhotoVideo(new Value[]{new Value(1, "27.60"), new Value(2, "33.50")});
        dbApplication6.setAccepted(new String[]{"retail business", "promotional materials", "iternet"});

        applicationList.add(dbApplication1);
        applicationList.add(dbApplication2);
        applicationList.add(dbApplication4);
        applicationList.add(dbApplication6);

        Collections.sort(applicationList, new Comparator<DbApplication>() {
            @Override
            public int compare(DbApplication dbApplication, DbApplication t1) {
                return t1.getDate().compareTo(dbApplication.getDate());
            }
        });

        return applicationList;
    }

    private String formatDate() {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String stringDate = df.format(Calendar.getInstance().getTime());
        return stringDate;
    }
}
