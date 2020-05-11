package mabel_tech.com.scanovate_demo.UI.Face;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateApp;
import mabel_tech.com.scanovate_demo.UI.base.BaseActivity;

public class tip2Face extends BaseActivity {

    public static final int REQUEST_CODE = 20;
    private static Context context;
    Button nextscreen;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip2_face_enroll);
        context = this;
        initViews();

    }

    void initViews() {
        nextscreen = findViewById(R.id.btn_siguiente1);
        nextscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FaceActivity.class);
                startActivityForResult(intent, FaceActivity.CAMERA_REQUEST_CODE);
                finish();
            }
        });
    }
}