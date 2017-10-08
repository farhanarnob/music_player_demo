package com.teamtreehouse.musicmachine.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class Poem implements Parcelable {
    public static final Creator<Poem> CREATOR = new Creator<Poem>() {
        @Override
        public Poem createFromParcel(Parcel in) {
            return new Poem(in);
        }

        @Override
        public Poem[] newArray(int size) {
            return new Poem[size];
        }
    };
    int mak;
    String tag;

    protected Poem(Parcel in) {
        mak = in.readInt();
        tag = in.readString();
    }

    public int getMak() {
        return mak;
    }

    public void setMak(int mak) {
        this.mak = mak;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mak);
        dest.writeString(tag);
    }
}
