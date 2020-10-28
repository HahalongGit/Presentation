package com.digua.displayapp.way2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 *
 * @author RunningDigua
 * @date 2020/10/28
 */
public class DemoPresentationContents implements Parcelable{

    final int photo;
    final int[] colors;
    int displayModeId;

    public static final Parcelable.Creator<DemoPresentationContents> CREATOR =
            new Parcelable.Creator<DemoPresentationContents>() {
                @Override
                public DemoPresentationContents createFromParcel(Parcel in) {
                    return new DemoPresentationContents(in);
                }

                @Override
                public DemoPresentationContents[] newArray(int size) {
                    return new DemoPresentationContents[size];
                }
            };

    public DemoPresentationContents(int photo) {
        this.photo = photo;
        colors = new int[]{
                ((int) (Math.random() * Integer.MAX_VALUE)) | 0xFF000000,
                ((int) (Math.random() * Integer.MAX_VALUE)) | 0xFF000000};
    }

    private DemoPresentationContents(Parcel in) {
        photo = in.readInt();
        colors = new int[]{in.readInt(), in.readInt()};
        displayModeId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(photo);
        dest.writeInt(colors[0]);
        dest.writeInt(colors[1]);
        dest.writeInt(displayModeId);
    }
}
