package user.com.stopthefakes.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.ui.offender.OffenderActivity;

public class GalleryMainActivity extends BaseActivity {

    private static final int PHOTO = 1;
    private static final int VIDEO = 2;
    File directory;

    private int needMakePhoto = 5;
    private int needRecordSec = 10;

    @BindView(R.id.mainImage)
    ImageView mMainImage;
    @BindView(R.id.imagesLayout)
    CustomImageContainer mImageContainer;
    @BindView(R.id.deleteItemButton)
    ImageButton mDeleteImageButton;
    @BindView(R.id.makePhotoButton)
    ImageView mMakePhotoButton;
    @BindView(R.id.makeVideoButton)
    ImageView mMakeVideoButton;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.imageContainer)
    View mMainImageContainer;
    @BindView(R.id.photosCountTextView)
    TextView mPhotosCountTextView;
    @BindView(R.id.surfaceContainer)
    View surfaceContainer;
    @BindView(R.id.video_timer)
    TextView mVideoTimer;
    @BindView(R.id.sendSignalButton)
    Button sendSignalButton;

    File video;

    int selectedCamera = -1;

    Camera camera = null;
    MediaRecorder mediaRecorder;
    boolean clickStart = false;
    long millis;

    long startTime;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if (mVideoTimer != null) {
                mVideoTimer.setText(String.format("REC %02d:%02d", minutes, seconds));
            }

            timerHandler.postDelayed(this, 500);
        }
    };


    public static Intent newInstance(Context context) {
        return new Intent(context, GalleryMainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);
        setUnbinder(ButterKnife.bind(this));

        startPreview();

        int id = (int) getIntent().getExtras().get("id");
        if (id % 2 == 0) {
            onMakePhoto();
        } else {
            onMakeVideo();
        }


        mImageContainer.setClickListener(new CustomImageContainer.IClickListener() {
            @Override
            public void onClick(int position) {
                mMainImage.setImageBitmap(mImageContainer.getSelectedDrawable());
                mMainImageContainer.setVisibility(View.VISIBLE);
                surfaceContainer.setVisibility(View.GONE);
            }

            @Override
            public void hideImage() {
                mMainImageContainer.setVisibility(View.GONE);
                surfaceContainer.setVisibility(View.VISIBLE);
            }
        });


        createDirectory();
    }

    @OnClick(R.id.returnBackTitle)
    void clickBack() {
        onBackPressed();
    }

    @OnClick(R.id.makePhotoButton)
    void clickMakePhoto() {
        if (mImageContainer.getImageViews().size() < needMakePhoto) {
            surfaceContainer.setVisibility(View.VISIBLE);
            mPhotosCountTextView.setText("photo " + mImageContainer.getImageViews().size() + "/" + needMakePhoto);
        }
    }

    @OnClick(R.id.makeVideoButton)
    void clickMakeVideo() {
        surfaceContainer.setVisibility(View.VISIBLE);
        mPhotosCountTextView.setText("photo " + mImageContainer.getImageViews().size() + "/" + needMakePhoto);
    }

    @OnClick(R.id.deleteItemButton)
    public void deleteImage() {
        mImageContainer.removeSelectedItemAndReturnIsEmpty();
        mMainImage.setImageDrawable(null);
        mMainImageContainer.setVisibility(View.GONE);
        sendSignalButton.setEnabled(false);
        surfaceContainer.setVisibility(View.VISIBLE);
        camera.startPreview();
        if (selectedCamera == PHOTO) {
            mMakePhotoButton.setBackgroundResource(R.drawable.rounded_take_in_work_button);
            mMakePhotoButton.setImageResource(R.drawable.ic_photo_camera_white);
        } else {
            mMakeVideoButton.setBackgroundResource(R.drawable.rounded_take_in_work_button);
            mMakeVideoButton.setImageResource(R.drawable.ic_antique_cinema_camera_white);
        }
        mPhotosCountTextView.setText("photo " + mImageContainer.getImageViews().size() + "/" + needMakePhoto);
    }

    @OnClick(R.id.sendSignalButton)
    public void clickSendSignal() {
        startActivity(new Intent(this, OffenderActivity.class));
    }


    public void onMakePhoto() {
        mVideoTimer.setVisibility(View.GONE);
        mPhotosCountTextView.setVisibility(View.VISIBLE);
        surfaceView.setVisibility(View.VISIBLE);

        mMakePhotoButton.setBackgroundResource(R.drawable.rounded_take_in_work_button);
        mMakePhotoButton.setImageResource(R.drawable.ic_photo_camera_white);
        mMakeVideoButton.setBackgroundResource(R.drawable.rounded_button_white);
        mMakeVideoButton.setImageResource(R.drawable.ic_antique_cinema_camera);
        selectedCamera = 1;
    }


    protected void setDisplayOrientation(Camera camera, int angle) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[]{angle});
        } catch (Exception e1) {
        }
    }

    public void onMakeVideo() {
        mVideoTimer.setVisibility(View.VISIBLE);
        mPhotosCountTextView.setVisibility(View.GONE);

        surfaceView.setVisibility(View.VISIBLE);
        mMakeVideoButton.setBackgroundResource(R.drawable.rounded_take_in_work_button);
        mMakeVideoButton.setImageResource(R.drawable.ic_antique_cinema_camera_white);
        mMakePhotoButton.setBackgroundResource(R.drawable.rounded_button_white);
        mMakePhotoButton.setImageResource(R.drawable.ic_photo_camera);
        selectedCamera = 2;

    }

    @OnClick(R.id.surfaceView)
    public void clickSufrace() {
        if (selectedCamera == PHOTO) {
            camera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    try {
                        File mypath = generateFile(PHOTO);
                        FileOutputStream fos = new FileOutputStream(mypath);
                        fos.write(data);
                        fos.close();


                        mImageContainer.addImageAndReturnIsFirstImage(mypath);
                        mPhotosCountTextView.setText("photo " + mImageContainer.getImageViews().size() + "/" + needMakePhoto);
                        if (mImageContainer.getImageViews().size() == needMakePhoto) {
                            surfaceContainer.setVisibility(View.GONE);
                            mMakePhotoButton.setBackgroundResource(R.drawable.rounded_button_white);
                            mMakePhotoButton.setImageResource(R.drawable.ic_photo_camera);
                            sendSignalButton.setEnabled(true);
                            return;
                        }

                        camera.startPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            if (clickStart) {
                timerHandler.removeCallbacks(timerRunnable);
                clickStart = false;
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        try {
                            File mypath = generateFile(PHOTO);
                            FileOutputStream fos = new FileOutputStream(mypath);
                            fos.write(data);
                            fos.close();


                            mImageContainer.addImageAndReturnIsFirstImage(mypath);
                            mPhotosCountTextView.setText("photo " + mImageContainer.getImageViews().size() + "/" + needMakePhoto);
                            surfaceContainer.setVisibility(View.GONE);
                            mMakePhotoButton.setBackgroundResource(R.drawable.rounded_button_white);
                            mMakePhotoButton.setImageResource(R.drawable.ic_photo_camera);
                            sendSignalButton.setEnabled(true);

                            return;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } else {
                if(mImageContainer.getImageViews().size() == 1){
                    return;
                }
                clickStart = true;
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);


            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private boolean prepareVideoRecorder() {

        camera.unlock();
        try {
            camera.setPreviewDisplay(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder = new MediaRecorder();
        video = generateFile(VIDEO);
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile
                .get(CamcorderProfile.QUALITY_LOW));
        mediaRecorder.setOutputFile(video.getAbsolutePath());
        mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            camera.lock();
        }
    }

    private File generateFile(int type) {
        File file = null;
        switch (type) {
            case PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
            case VIDEO:
                file = new File(directory.getPath() + "/" + "video_"
                        + System.currentTimeMillis() + ".3gp");
                break;
        }
        return file;
    }

    private void createDirectory() {
        directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }


    private void startPreview() {

        camera = Camera.open();
        setDisplayOrientation(camera, 90);
        SurfaceHolder mHolder = surfaceView.getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });


        try {
            camera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

