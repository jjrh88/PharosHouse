package mabel_tech.com.scanovate_demo.UI.Document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateApp;
import mabel_tech.com.scanovate_demo.UI.Face.FaceActivity;
import mabel_tech.com.scanovate_demo.UI.base.BaseActivity;

public class tip2Doc extends BaseActivity {

    public static final int REQUEST_CODE = 20;
    private static Context context;
    Button nextscreen;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip2_doc_enroll);
        context = this;
        initViews();

    }

    void initViews() {
        nextscreen = findViewById(R.id.btn_siguiente1);
        nextscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DocActivity.class);
                startActivityForResult(intent, DocActivity.REQUEST_CODE);
                finish();
            }
        });
    }
}