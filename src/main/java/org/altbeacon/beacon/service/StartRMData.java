/**
 * Radius Networks, Inc.
 * http://www.radiusnetworks.com
 *
 * @author David G. Young
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.altbeacon.beacon.service;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.altbeacon.beacon.Region;

import java.io.Serializable;

/**
 *
 * Internal class used to transfer ranging and monitoring data between the BeaconService and client
 *
 * @hide
 */
public class StartRMData implements Serializable, Parcelable {
    private static final String SCAN_PERIOD_KEY = "scanPeriod";
    private static final String BETWEEN_SCAN_PERIOD_KEY = "betweenScanPeriod";
    private static final String BACKGROUND_FLAG_KEY = "backgroundFlag";
    private static final String CALLBACK_PACKAGE_NAME_KEY = "callbackPackageName";
    private static final String REGION_KEY = "region";
    private static final String HAS_MIDCYCLE_RANG_UPDATE_KEY = "hasMidCycleRangUpdate";
    private static final String RANGE_UPDATE_PERIOD_KEY = "rangeUpdatePeriod";
    private static final String BETWEEN_RANGE_UPDATE_PERIOD_KEY = "betweenRangeUpdatePeriod";

    private Region mRegion;
    private long mScanPeriod;
    private long mBetweenScanPeriod;
    private boolean hasMidCycleRangUpdate;
    private long rangeUpdatePeriod;
    private long betweenRangeUpdatePeriod;
    private boolean mBackgroundFlag;
    private String mCallbackPackageName;

    private StartRMData() {
    }

    public StartRMData(@NonNull Region region, @NonNull String callbackPackageName) {
        this.mRegion = region;
        this.mCallbackPackageName = callbackPackageName;

        this.hasMidCycleRangUpdate = false;
    }
    public StartRMData(long scanPeriod, long betweenScanPeriod, boolean backgroundFlag) {
        this.mScanPeriod = scanPeriod;
        this.mBetweenScanPeriod = betweenScanPeriod;
        this.mBackgroundFlag = backgroundFlag;

        this.hasMidCycleRangUpdate = false;
    }

    public StartRMData(long scanPeriod, long betweenScanPeriod, boolean backgroundFlag, long rangeUpdatePeriod, long betweenRangeUpdatePeriod) {
        this.mScanPeriod = scanPeriod;
        this.mBetweenScanPeriod = betweenScanPeriod;
        this.mBackgroundFlag = backgroundFlag;

        this.hasMidCycleRangUpdate = true;
        this.rangeUpdatePeriod = rangeUpdatePeriod;
        this.betweenRangeUpdatePeriod = betweenRangeUpdatePeriod;
    }

    public StartRMData(@NonNull Region region, @NonNull String callbackPackageName, long scanPeriod, long betweenScanPeriod, boolean backgroundFlag) {
        this.mScanPeriod = scanPeriod;
        this.mBetweenScanPeriod = betweenScanPeriod;
        this.mRegion = region;
        this.mCallbackPackageName = callbackPackageName;
        this.mBackgroundFlag = backgroundFlag;

        this.hasMidCycleRangUpdate = false;
    }

    public StartRMData(@NonNull Region region, @NonNull String callbackPackageName, long scanPeriod, long betweenScanPeriod, boolean backgroundFlag, long rangeUpdatePeriod, long betweenRangeUpdatePeriod) {
        this.mScanPeriod = scanPeriod;
        this.mBetweenScanPeriod = betweenScanPeriod;
        this.mRegion = region;
        this.mCallbackPackageName = callbackPackageName;
        this.mBackgroundFlag = backgroundFlag;

        this.hasMidCycleRangUpdate = true;
        this.rangeUpdatePeriod = rangeUpdatePeriod;
        this.betweenRangeUpdatePeriod = betweenRangeUpdatePeriod;
    }


    public long getScanPeriod() { return mScanPeriod; }
    public long getBetweenScanPeriod() { return mBetweenScanPeriod; }
    public boolean hasMidCycleRangeUpdates() { return hasMidCycleRangUpdate; }
    public long getRangeUpdatePeriod() { return rangeUpdatePeriod; }
    public long getBetweenRangeUpdatePeriod() {return betweenRangeUpdatePeriod;}
    public String getCallbackPackageName() {
        return mCallbackPackageName;
    }
    public boolean getBackgroundFlag() { return mBackgroundFlag; }
    public int describeContents() {
        return 0;
    }
    public Region getRegionData() { return mRegion; }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(mRegion, flags);
        out.writeString(mCallbackPackageName);
        out.writeLong(mScanPeriod);
        out.writeLong(mBetweenScanPeriod);
        out.writeByte((byte) (mBackgroundFlag ? 1 : 0));
        out.writeByte((byte) (hasMidCycleRangUpdate ? 1 : 0));
        out.writeLong(rangeUpdatePeriod);
        out.writeLong(betweenRangeUpdatePeriod);
    }

    public static final Parcelable.Creator<StartRMData> CREATOR
            = new Parcelable.Creator<StartRMData>() {
        public StartRMData createFromParcel(Parcel in) {
            return new StartRMData(in);
        }

        public StartRMData[] newArray(int size) {
            return new StartRMData[size];
        }
    };

    private StartRMData(Parcel in) {
        mRegion = in.readParcelable(StartRMData.class.getClassLoader());
        mCallbackPackageName = in.readString();
        mScanPeriod = in.readLong();
        mBetweenScanPeriod = in.readLong();
        mBackgroundFlag = in.readByte() != 0;
        hasMidCycleRangUpdate = in.readByte() != 0;
        rangeUpdatePeriod = in.readLong();
        betweenRangeUpdatePeriod = in.readLong();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(SCAN_PERIOD_KEY, this.mScanPeriod);
        bundle.putLong(BETWEEN_SCAN_PERIOD_KEY, this.mBetweenScanPeriod);
        bundle.putBoolean(BACKGROUND_FLAG_KEY, this.mBackgroundFlag);
        bundle.putString(CALLBACK_PACKAGE_NAME_KEY, this.mCallbackPackageName);
        bundle.putBoolean(HAS_MIDCYCLE_RANG_UPDATE_KEY, this.hasMidCycleRangUpdate);
        bundle.putLong(BETWEEN_RANGE_UPDATE_PERIOD_KEY, this.betweenRangeUpdatePeriod);
        bundle.putLong(RANGE_UPDATE_PERIOD_KEY, this.rangeUpdatePeriod);
        if (mRegion != null) {
            bundle.putSerializable(REGION_KEY, mRegion);
        }
        return bundle;
    }

    public static StartRMData fromBundle(@NonNull Bundle bundle) {
        bundle.setClassLoader(Region.class.getClassLoader());
        boolean valid = false;
        StartRMData data = new StartRMData();
        if (bundle.containsKey(REGION_KEY)) {
            data.mRegion = (Region)bundle.getSerializable(REGION_KEY);
            valid = true;
        }
        if (bundle.containsKey(SCAN_PERIOD_KEY)) {
            data.mScanPeriod = (Long) bundle.get(SCAN_PERIOD_KEY);
            valid = true;
        }
        if (bundle.containsKey(BETWEEN_SCAN_PERIOD_KEY)) {
            data.mBetweenScanPeriod = (Long) bundle.get(BETWEEN_SCAN_PERIOD_KEY);
        }
        if (bundle.containsKey(BACKGROUND_FLAG_KEY)) {
            data.mBackgroundFlag = (Boolean) bundle.get(BACKGROUND_FLAG_KEY);
        }
        if (bundle.containsKey(CALLBACK_PACKAGE_NAME_KEY)) {
            data.mCallbackPackageName = (String) bundle.get(CALLBACK_PACKAGE_NAME_KEY);
        }
        if (bundle.containsKey(HAS_MIDCYCLE_RANG_UPDATE_KEY)) {
            data.hasMidCycleRangUpdate = (Boolean) bundle.get(HAS_MIDCYCLE_RANG_UPDATE_KEY);
        }
        if (bundle.containsKey(RANGE_UPDATE_PERIOD_KEY)) {
            data.rangeUpdatePeriod = (Long) bundle.get(RANGE_UPDATE_PERIOD_KEY);
        }
        if (bundle.containsKey(BETWEEN_RANGE_UPDATE_PERIOD_KEY)) {
            data.betweenRangeUpdatePeriod = (Long) bundle.get(BETWEEN_RANGE_UPDATE_PERIOD_KEY);
        }
        if (valid) {
            return data;
        }
        else {
            return null;
        }
    }
}
