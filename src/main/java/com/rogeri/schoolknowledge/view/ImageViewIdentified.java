package com.rogeri.schoolknowledge.view;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Ivan on 15/03/2016.
 */
public class ImageViewIdentified extends ImageView {
    private int id;
    public ImageViewIdentified(Context context, int id) {
        super(context);
        this.id = id;
    }

    public int getIdentifier() {
        return this.id;
    }

    public void setIdentifier(int id) {
        this.id = id;
    }
}
