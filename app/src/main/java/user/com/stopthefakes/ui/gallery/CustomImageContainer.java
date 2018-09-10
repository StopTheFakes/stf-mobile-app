package user.com.stopthefakes.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CustomImageContainer extends LinearLayout {

	Context mContext;

	List<RoundedImageView> mImageViews = new ArrayList<>();
	List<Bitmap>           mResources  = new ArrayList<>();
	List<String>           mPaths      = new ArrayList<>();

	IClickListener mClickListener;

	int selectedPosition = -1;

	float density;


	public CustomImageContainer(Context context) {
		super(context);
		init(context);
	}


	public CustomImageContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}


	public CustomImageContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}


	private void init(Context context) {
		mContext = context;
		density = mContext.getResources().getDisplayMetrics().density;
	}


	public interface IClickListener {
		void onClick(int position);
		void hideImage();
	}


	public void setClickListener(IClickListener clickListener) {
		mClickListener = clickListener;
	}


	public void removeSelectedItemAndReturnIsEmpty() {
		removeView(mImageViews.get(selectedPosition));
		mImageViews.remove(selectedPosition);
		mResources.remove(selectedPosition);
		mPaths.remove(selectedPosition);
		selectedPosition = -1;
		mClickListener.hideImage();
		reIndex();
	}


	public Bitmap getSelectedDrawable() {
		return mResources.get(selectedPosition);
	}


	public void addImageAndReturnIsFirstImage(File file) {
		mPaths.add(file.getPath());

		Bitmap bitmap = file.getPath().contains(".3gp")
			? ThumbnailUtils.createVideoThumbnail(file.getPath(), MediaStore.Images.Thumbnails.MINI_KIND)
			: BitmapFactory.decodeFile(file.getAbsolutePath());

		Matrix matrix = new Matrix();

		matrix.postRotate(90);

		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

		final float density = getResources().getDisplayMetrics().density;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
		lp.setMargins((int) (density * 8), (int) (density * 4), 0, 0);
		final RoundedImageView child = new RoundedImageView(mContext);
		child.setCornerRadius(10f);
		child.setScaleType(ImageView.ScaleType.CENTER_CROP);
		child.setImageBitmap(rotatedBitmap);
		mResources.add(rotatedBitmap);
		child.setLayoutParams(lp);

		addView(child);
		mImageViews.add(child);
		child.setTag(mImageViews.size() - 1);
		child.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (selectedPosition != (Integer) child.getTag()) {
					if (selectedPosition != -1) {
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
						lp.setMargins((int) (density * 8), (int) (density * 4), 0, 0);
						mImageViews.get(selectedPosition).setLayoutParams(lp);
					}

					selectedPosition = (Integer) child.getTag();

					LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
					lp2.setMargins((int) (density * 8), 0, 0, (int) (density * 4));
					mImageViews.get(selectedPosition).setLayoutParams(lp2);
				} else {
					mClickListener.hideImage();
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
					lp.setMargins((int) (density * 8), (int) (density * 4), 0, 0);
					mImageViews.get(selectedPosition).setLayoutParams(lp);
					selectedPosition = -1;
				}

				if (mClickListener != null && selectedPosition != -1) {
					mClickListener.onClick(selectedPosition);
				} else if (mClickListener != null && selectedPosition == -1) {
					mClickListener.hideImage();
				}
			}
		});

		reIndex();
	}


	public void addImage(int id) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);

		final float density = getResources().getDisplayMetrics().density;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
		lp.setMargins((int) (density * 8), (int) (density * 4), 0, 0);
		final RoundedImageView child = new RoundedImageView(mContext);
		child.setCornerRadius(10f);
		child.setScaleType(ImageView.ScaleType.CENTER_CROP);
		child.setImageBitmap(bitmap);
		mResources.add(bitmap);
		child.setLayoutParams(lp);

		addView(child);
		mImageViews.add(child);
		child.setTag(mImageViews.size() - 1);
		child.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (selectedPosition != (Integer) child.getTag()) {
					if (selectedPosition != -1) {
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
						lp.setMargins((int) (density * 8), (int) (density * 4), 0, 0);
						mImageViews.get(selectedPosition).setLayoutParams(lp);
					}

					selectedPosition = (Integer) child.getTag();
					LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
					lp2.setMargins((int) (density * 8), 0, 0, (int) (density * 4));
					mImageViews.get(selectedPosition).setLayoutParams(lp2);
				} else {
					mClickListener.hideImage();
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
					lp.setMargins((int) (density * 8), (int) (density * 4), 0, 0);
					mImageViews.get(selectedPosition).setLayoutParams(lp);
					selectedPosition = -1;
				}

				if (mClickListener != null && selectedPosition != -1) {
					mClickListener.onClick(selectedPosition);
				} else if (mClickListener != null && selectedPosition == -1) {
					mClickListener.hideImage();
				}
			}
		});

		if (mImageViews.size() == 1) {
			selectedPosition = 0;
			LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams((int) (density * 75), (int) (density * 75));
			lp2.setMargins((int) (density * 8), 0, 0, (int) (density * 8));
			mImageViews.get(selectedPosition).setLayoutParams(lp2);
		}
	}


	private void reIndex() {
		for (int i = 0; i < mImageViews.size(); i++) {
			mImageViews.get(i).setTag(i);
		}
	}


	public List<RoundedImageView> getImageViews() {
		return mImageViews;
	}

}