package com.example.kushalgupta.opencvtest3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
    JavaCameraView jvc;
Mat mRgba, imgGray, imgCanny;
private static String TAG="MainActivity";
BaseLoaderCallback baseLoaderCallback=new BaseLoaderCallback(this) {
    @Override
    public void onManagerConnected(int status) {
        switch (status){
            case BaseLoaderCallback.SUCCESS:

                jvc.enableView();
                break;

                default:
                    super.onManagerConnected(status);
                    break;

        }



    }
};

    static {


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jvc=findViewById(R.id.jcv);

        jvc.setVisibility(SurfaceView.VISIBLE);

        jvc.setCvCameraViewListener(this);



    }

    @Override
    protected void onPause() {
        super.onPause();

        if (jvc != null) {

            jvc.disableView();
        }




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (jvc != null) {

            jvc.disableView();
        }

    }

    @Override
protected void onResume(){
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "static initializer: open cv initialised");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);

        }else {

            Log.d(TAG, "static initializer: not");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9,this,baseLoaderCallback);
        }


    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    mRgba=new Mat(height,width, CvType.CV_8UC4);
        imgGray=new Mat(height,width, CvType.CV_8UC1);
        imgCanny=new Mat(height,width, CvType.CV_8UC1);

    }

    @Override
    public void onCameraViewStopped() {

    mRgba.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//imgGray=inputFrame.gray();
    mRgba=inputFrame.rgba();


//Imgproc.cvtColor(mRgba,imgGray,Imgproc.COLOR_RGB2GRAY);
//Imgproc.Canny(mRgba,imgCanny,50,150);
        return mRgba;
    }




}
