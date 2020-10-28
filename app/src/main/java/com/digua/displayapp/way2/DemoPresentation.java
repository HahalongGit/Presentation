package com.digua.displayapp.way2;

import android.app.Presentation;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.digua.displayapp.R;

/**
 *
 * Presentation实现
 *
 * @author RunningDigua
 * @date 2020/10/28
 */
public class DemoPresentation extends Presentation{

    // The content that we want to show on the presentation.
    private static final int[] PHOTOS = new int[]{
            R.drawable.frantic,
            R.drawable.photo1, R.drawable.photo2, R.drawable.photo3,
            R.drawable.photo4, R.drawable.photo5, R.drawable.photo6,
            R.drawable.sample_4,
    };

    public DemoPresentationContents mContents;

    public DemoPresentation(Context outerContext, Display display, DemoPresentationContents contents) {
        super(outerContext, display);
        mContents = contents;
    }

    /**
     * Sets the preferred display mode id for the presentation.
     */
    public void setPreferredDisplayMode(int modeId) {
        mContents.displayModeId = modeId;

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.preferredDisplayModeId = modeId;
        getWindow().setAttributes(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // Get the resources for the context of the presentation.
        // Notice that we are getting the resources from the context of the presentation.
        Resources r = getContext().getResources();

        // Inflate the layout.
        setContentView(R.layout.presentation_content);

        final Display display = getDisplay();
        final int displayId = display.getDisplayId();
        final int photo = mContents.photo;

        // Show a caption to describe what's going on.
        TextView text = findViewById(R.id.text);
        String textStr  = r.getString(R.string.presentation_photo_text, photo, displayId, display.getName());
        text.setText(textStr);

        // Show a n image for visual interest.
        ImageView image =findViewById(R.id.image);
        image.setImageDrawable(r.getDrawable(PHOTOS[photo]));

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);

        // Set the background to a random gradient.
        Point p = new Point();
        getDisplay().getSize(p);
        drawable.setGradientRadius(Math.max(p.x, p.y) / 2);
        drawable.setColors(mContents.colors);
        findViewById(android.R.id.content).setBackground(drawable);
    }

}
