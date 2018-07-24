package user.com.stopthefakes.utils;

import java.util.regex.Pattern;


public class RegExtPatterns {

	public static final Pattern EMAIL = Pattern.compile(
		"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
	);

	public static final Pattern PASSWORD = Pattern.compile("^[.\\dA-z!@#$%^&*()_+=-]+$");

}