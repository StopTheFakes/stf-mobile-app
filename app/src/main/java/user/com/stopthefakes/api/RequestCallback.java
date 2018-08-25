package user.com.stopthefakes.api;


public interface RequestCallback {

	void onSuccess();
	void onError(Exception e);

}
