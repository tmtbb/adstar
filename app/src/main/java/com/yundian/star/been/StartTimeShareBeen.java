package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Null on 2017/6/4.
 */

public class StartTimeShareBeen {

    /**
     * result : 1
     * stastic : [{"timestamp":1496577896,"value":7},{"timestamp":1496577296,"value":15},{"timestamp":1496576696,"value":2},{"timestamp":1496576096,"value":10},{"timestamp":1496575496,"value":11},{"timestamp":1496574896,"value":10},{"timestamp":1496574296,"value":9},{"timestamp":1496573696,"value":16},{"timestamp":1496573096,"value":9},{"timestamp":1496572496,"value":1},{"timestamp":1496571896,"value":8},{"timestamp":1496571296,"value":9},{"timestamp":1496570696,"value":13},{"timestamp":1496570096,"value":7},{"timestamp":1496569496,"value":15},{"timestamp":1496568896,"value":19},{"timestamp":1496568296,"value":5},{"timestamp":1496567696,"value":18},{"timestamp":1496567096,"value":10},{"timestamp":1496566496,"value":12}]
     */

    private int result;
    private ArrayList<StasticBean> stastic;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArrayList<StasticBean> getStastic() {
        return stastic;
    }

    public void setStastic(ArrayList<StasticBean> stastic) {
        this.stastic = stastic;
    }

    public static class StasticBean implements Parcelable {
        /**
         * timestamp : 1496577896
         * value : 7
         */

        private int timestamp;
        private int value;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.timestamp);
            dest.writeInt(this.value);
        }

        public StasticBean() {
        }

        protected StasticBean(Parcel in) {
            this.timestamp = in.readInt();
            this.value = in.readInt();
        }

        public static final Parcelable.Creator<StasticBean> CREATOR = new Parcelable.Creator<StasticBean>() {
            @Override
            public StasticBean createFromParcel(Parcel source) {
                return new StasticBean(source);
            }

            @Override
            public StasticBean[] newArray(int size) {
                return new StasticBean[size];
            }
        };
    }
}
