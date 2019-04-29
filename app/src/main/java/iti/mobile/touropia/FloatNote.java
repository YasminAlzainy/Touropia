package iti.mobile.touropia;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class FloatNote extends Service {
    private WindowManager windowManager;
    private View floatingView, collapsedView, expandedView;
    private WindowManager.LayoutParams params;
    private TextView textView_Note;

    public FloatNote() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget_layout, null);

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        windowManager.addView(floatingView, params);

        expandedView = floatingView.findViewById(R.id.Layout_Expended);
        collapsedView = floatingView.findViewById(R.id.Layout_Collapsed);
        textView_Note = floatingView.findViewById(R.id.textView_Note);

        floatingView.findViewById(R.id.Widget_Close_Icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });

        expandedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                collapsedView.setVisibility(View.VISIBLE);
                textView_Note.setText("Hello this is my note!");
                expandedView.setVisibility(View.GONE);

            }
        });

        floatingView.findViewById(R.id.MainParentRelativeLayout).setOnTouchListener(new View.OnTouchListener() {
            int X_Axis, Y_Axis;
            float TouchX, TouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        X_Axis = params.x;
                        Y_Axis = params.y;
                        TouchX = event.getRawX();
                        TouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:

                        collapsedView.setVisibility(View.GONE);
                        expandedView.setVisibility(View.VISIBLE);
                        return true;

                    case MotionEvent.ACTION_MOVE:

                        params.x = X_Axis + (int) (event.getRawX() - TouchX);
                        params.y = Y_Axis + (int) (event.getRawY() - TouchY);
                        windowManager.updateViewLayout(floatingView, params);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
    }


}
