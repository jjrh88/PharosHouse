package mabel_tech.com.scanovate_demo.liveness.main.viewsadapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.liveness.main.PermissionsRequestedListener;
import mabel_tech.com.scanovate_demo.liveness.main.StartActivityButtonData;

class DemosGeneratorVH extends RecyclerView.ViewHolder {

    private static final String TAG = DemosGeneratorVH.class.getSimpleName();
    private PermissionsRequestedListener permissionsRequestedListener;
    private Button startActivityBtn;

    DemosGeneratorVH(View itemView, PermissionsRequestedListener permissionsRequestedListener) {
        super(itemView);
        this.permissionsRequestedListener = permissionsRequestedListener;
        startActivityBtn = itemView.findViewById(R.id.startActivityBtn);
    }

    void setUIDataOnView(final StartActivityButtonData data) {
        startActivityBtn.setText(data.getTitle());
        startActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick invoked for " + data.getTitle());
                permissionsRequestedListener.onPermissionsRequested(v, data);

            }
        });
        if (data.getButtonDecorationDrawableRes() != 0) {
            startActivityBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, data.getButtonDecorationDrawableRes(), 0);
            startActivityBtn.setCompoundDrawablePadding(20);
        }
        Log.d(TAG, data + " is presented in Scanovate's main activity's button list as a button");
    }
}