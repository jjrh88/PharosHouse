package mabel_tech.com.scanovate_demo.UI.Document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateApp;
import mabel_tech.com.scanovate_demo.UI.Face.tip2Face;
import mabel_tech.com.scanovate_demo.UI.base.BaseActivity;

public class tip1Doc extends BaseActivity {

    public static final int REQUEST_CODE = 20;
    private static Context context;
    Button nextScreen;

    public static void setContext(Context context) {
        tip1Doc.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip1_doc_enroll);
        context = this;
        initViews();

    }

    void initViews() {
        nextScreen = findViewById(R.id.btn_siguiente1);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, tip2Doc.class);
                startActivityForResult(intent, tip2Doc.REQUEST_CODE);
                finish();
            }
        });
    }
}