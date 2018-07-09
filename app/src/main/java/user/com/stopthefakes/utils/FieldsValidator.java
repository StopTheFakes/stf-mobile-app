package user.com.stopthefakes.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;

import user.com.stopthefakes.R;

public class FieldsValidator {

    private Context context;

    public FieldsValidator(Context context) {
        this.context = context;
    }

    private String getString(@StringRes int id) {
        return context.getString(id);
    }

    @Nullable
    public String isEmailValid(String email) {
        if (!RegExtPatterns.EMAIL.matcher(email.trim()).matches()) {
            return getString(R.string.invalid_email_error);
        }
        return null;
    }

    @Nullable
    public boolean isEmailValidBool(String email) {
        return RegExtPatterns.EMAIL.matcher(email.trim()).matches();
    }

    public String isPasswordValid(String password) {
        if (StringUtil.isEmpty(password)) {
            return getString(R.string.empty_field_error);
        }
        if (StringUtil.isEmpty(password.trim())) {
            return getString(R.string.error_empty_after_trim_field_error);
        }
        if (!RegExtPatterns.PASSWORD.matcher(password).matches()) {
            return getString(R.string.error_password_matcher);
        }
        return null;
    }

    public static void errorWatcher(EditText view, String error) {
        Drawable icon = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_error_primary_24dp);
        icon.setBounds(new Rect(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight()));
        if (error != null) {
            view.setCompoundDrawables(null, null, icon, null);
            view.setError(error, icon);
        } else {
            view.setCompoundDrawables(null, null, null, null);
            view.setError(null, null);
        }
    }
}
