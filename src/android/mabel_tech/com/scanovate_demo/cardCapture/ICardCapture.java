package mabel_tech.com.scanovate_demo.cardCapture;

import android.app.Activity;
import android.graphics.Bitmap;

public interface ICardCapture {

    interface CardCaptureHandler {

        void success(Bitmap bitmap);
        void failure(String msg);
    }

    void capture(Activity activity, CardCaptureHandler handler);
}
