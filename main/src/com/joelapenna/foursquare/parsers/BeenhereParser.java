/**
 * Copyright 2009 Joe LaPenna
 */

package com.joelapenna.foursquare.parsers;

import com.joelapenna.foursquare.Foursquare;
import com.joelapenna.foursquare.error.FoursquareError;
import com.joelapenna.foursquare.error.FoursquareParseException;
import com.joelapenna.foursquare.types.Beenhere;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import java.io.IOException;

/**
 * Auto-generated: 2009-08-01 10:38:31.668690
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 * @param <T>
 */
public class BeenhereParser extends AbstractParser<Beenhere> {
    private static final String TAG = "BeenhereParser";
    private static final boolean DEBUG = Foursquare.PARSER_DEBUG;

    @Override
    public Beenhere parseInner(XmlPullParser parser) throws XmlPullParserException, IOException,
            FoursquareError, FoursquareParseException {
        try {
            parser.require(XmlPullParser.START_TAG, null, "beenhere");
        } catch (XmlPullParserException e) {
            if (parser.getName().equals("error")) {
                throw new FoursquareError(parser.getText());
            }
        }

        Beenhere beenhere = new Beenhere();

        while (parser.nextTag() == XmlPullParser.START_TAG) {
            if (DEBUG) Log.d(TAG, "Tag Name: " + String.valueOf(parser.getName()));

            String name = parser.getName();
            if ("friends".equals(name)) {
                beenhere.setFriends(Boolean.valueOf(parser.nextText()));

            } else if ("me".equals(name)) {
                beenhere.setMe(Boolean.valueOf(parser.nextText()));

            } else {
                // Consume something we don't understand.
                if (DEBUG) Log.d(TAG, "Found tag that we don't recognize: " + name);
                skipSubTree(parser);
            }
        }
        return beenhere;
    }
}