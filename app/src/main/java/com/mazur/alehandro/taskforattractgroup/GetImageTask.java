package com.mazur.alehandro.taskforattractgroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alehandro on 5/9/2016.
 */
class GetImageTask extends AsyncTask<String,Void,Bitmap> {
    Context context;
    Bitmap b;
    ImageView bmImage;
    URL url=null;
    int imageHeight, imageWidth;

    public GetImageTask(ImageView bmImage, int imageHeight, int imageWidth){
        this.bmImage=bmImage;
        this.imageHeight=imageHeight;
        this.imageWidth=imageWidth;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            url = new URL(params[0]);
            InputStream inputStream= new BufferedInputStream(url.openStream());
            b= BitmapFactory.decodeStream(inputStream);
            b = getResizedBitmap(b, imageHeight,imageWidth );
            } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        bmImage.setImageBitmap(bitmap);

    }


    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }



}