package mabel_tech.com.scanovate_demo.UI.Face;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateApp;
import mabel_tech.com.scanovate_demo.UI.GetConfig.GetConfigActivity;
import mabel_tech.com.scanovate_demo.UI.base.BaseActivity;

public class tip1Face extends BaseActivity {

    public static final int REQUEST_CODE = 20;
    private static Context context;
    Button nextScreen;

    public static void setContext(Context context) {
        tip1Face.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip1_face_enroll);
        context = this;
        initViews();

    }

    void initViews() {
        nextScreen = findViewById(R.id.btn_siguiente1);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, tip2Face.class);
                startActivityForResult(intent, tip2Face.REQUEST_CODE);
                finish();
            }
        });
    }
}