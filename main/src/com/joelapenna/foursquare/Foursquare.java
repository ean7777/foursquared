/**
 * Copyright 2009 Joe LaPenna
 */

package com.joelapenna.foursquare;

import com.joelapenna.foursquare.error.FoursquareCredentialsError;
import com.joelapenna.foursquare.error.FoursquareError;
import com.joelapenna.foursquare.error.FoursquareException;
import com.joelapenna.foursquare.types.CheckinResult;
import com.joelapenna.foursquare.types.City;
import com.joelapenna.foursquare.types.Credentials;
import com.joelapenna.foursquare.types.Data;
import com.joelapenna.foursquare.types.Group;
import com.joelapenna.foursquare.types.Tip;
import com.joelapenna.foursquare.types.User;
import com.joelapenna.foursquare.types.Venue;
import com.joelapenna.foursquare.types.classic.Auth;
import com.joelapenna.foursquared.FoursquaredSettings;

import android.util.Log;

import java.io.IOException;

/**
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class Foursquare {
    private static final String TAG = "Foursquare";
    public static final boolean DEBUG = FoursquaredSettings.API_DEBUG;
    public static final boolean PARSER_DEBUG = FoursquaredSettings.PARSER_DEBUG;

    private String mPhone;
    private String mPassword;
    private FoursquareHttpApi mFoursquare;
    private FoursquareHttpApiV1 mFoursquareV1;

    @V1
    @Classic
    public Foursquare(boolean debug) {
        mFoursquare = new FoursquareHttpApi();
        if (debug) {
            if (DEBUG) Log.d(TAG, "Using DEBUG domain.");
            mFoursquareV1 = new FoursquareHttpApiV1("10.0.2.2:8080");
        } else {
            mFoursquareV1 = new FoursquareHttpApiV1();
        }
    }

    public void setCredentials(String phone, String password) {
        mPhone = phone;
        mPassword = password;
        mFoursquare.setCredentials(phone, password);
        mFoursquareV1.setCredentials(phone, password);
    }

    @V1
    public void setOAuthToken(String token, String secret) {
        mFoursquareV1.setOAuthTokenWithSecret(token, secret);
    }

    @V1
    public void setOAuthConsumerCredentials(String oAuthConsumerKey, String oAuthConsumerSecret) {
        mFoursquareV1.setOAuthConsumerCredentials(oAuthConsumerKey, oAuthConsumerSecret);
    }

    public void clearAllCredentials() {
        setCredentials(null, null);
        setOAuthToken(null, null);
    }

    @V1
    @Classic
    public boolean hasCredentials() {
        return mFoursquare.hasCredentials() && mFoursquareV1.hasCredentials()
                && mFoursquareV1.hasOAuthTokenWithSecret();
    }

    @V1
    public Credentials authExchange() throws FoursquareException, FoursquareError,
            FoursquareCredentialsError, IOException {
        if (mFoursquareV1 == null) {
            throw new NoSuchMethodError(
                    "authExchange is unavailable without a consumer key/secret.");
        }
        return mFoursquareV1.authExchange(mPhone, mPassword);
    }

    @Classic
    public Auth login() throws FoursquareException, FoursquareError, FoursquareCredentialsError,
            IOException {
        if (DEBUG) Log.d(TAG, "login()");
        return mFoursquare.login(mPhone, mPassword);

    }

    @V1
    public Tip addTip(String vid, String text, String type) throws FoursquareException,
            FoursquareError, IOException {
        return mFoursquareV1.addtip(vid, text, type);
    }

    @V1
    public Venue addVenue(String name, String address, String crossstreet, String city,
            String state, String zip, String cityid, String phone) throws FoursquareException,
            FoursquareError, IOException {
        return mFoursquareV1.addvenue(name, address, crossstreet, city, state, zip, cityid, phone);
    }

    @V1
    public CheckinResult checkin(String venueName, String venueId, String shout, boolean isPrivate,
            boolean twitter) throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV1.checkin(venueName, venueId, shout, isPrivate, twitter);
    }

    @V1
    public Group checkins(String cityId) throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV1.checkins(cityId);
    }

    @V1
    public City checkCity(String geolat, String geolong) throws FoursquareException,
            FoursquareError, IOException {
        return mFoursquareV1.checkcity(geolat, geolong);
    }

    @V1
    public Data switchCity(String cityId) throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV1.switchcity(cityId);
    }

    @V1
    public Group tips(String geolat, String geolong, int limit) throws FoursquareException,
            FoursquareError, IOException {
        return mFoursquareV1.tips(geolat, geolong, limit);
    }

    @Classic
    public Data update(String status, String tipid) throws FoursquareException, FoursquareError,
            IOException {
        return mFoursquare.update(status, tipid);
    }

    @V1
    public User user(String user, boolean mayor, boolean badges) throws FoursquareException,
            FoursquareError, IOException {
        return mFoursquareV1.user(user, mayor, badges);
    }

    @V1
    public Venue venue(String id) throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV1.venue(id);
    }

    @V1
    public Group venues(String geolat, String geolong, String query, int radius, int limit)
            throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV1.venues(geolat, geolong, query, radius, limit);
    }

    /**
     * This api is supported in the V1 API documented at:
     * http://groups.google.com/group/foursquare-api/web/api-documentation
     */
    @interface V1 {
    }

    /**
     * This api was reverse engineered from the iPhone app.
     */
    @interface Classic {
    }

}