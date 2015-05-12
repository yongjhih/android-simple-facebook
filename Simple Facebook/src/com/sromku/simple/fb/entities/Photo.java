package com.sromku.simple.fb.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/photo
 */
public class Photo implements Publishable {

	public static final String ID = "id";
	public static final String ALBUM = "album";
	public static final String BACKDATED_TIME = "backdated_time";
	public static final String BACKDATED_TIME_GRANULARITY = "backdate_time_granularity";
	public static final String CREATED_TIME = "created_time";
	public static final String FROM = "from";
	public static final String HEIGHT = "height";
	public static final String ICON = "icon";
	public static final String IMAGES = "images";
	public static final String LINK = "link";
	public static final String PAGE_STORY_ID = "page_story_id";
	public static final String PICTURE = "picture";
	public static final String PLACE = "place";
	public static final String SOURCE = "source";
	public static final String UPDATED_TIME = "updated_time";
	public static final String WIDTH = "width";
	public static final String NAME = "name";
	public static final String MESSAGE = "message"; // same as NAME
	public static final String PRIVACY = "privacy";

	private String mId;
	private Album mAlbum;
	private Date mBackDatetime;
	private BackDatetimeGranularity mBackDatetimeGranularity;
	private Date mCreatedTime;
	private User mFrom;
	private GraphObject mGraphObject;
	private Integer mHeight;
	private String mIcon;
	private List<Image> mImages;
	private String mLink;
	private String mName;
	private String mPageStoryId;
	private String mPicture;
	private String mSource;
	private Date mUpdatedTime;
	private Integer mWidth;
	private Place mPlace;

	private String mPlaceId = null;
	private Parcelable mParcelable = null;
	private byte[] mBytes = null;
	private Privacy mPrivacy = null;

	private Photo(GraphObject graphObject) {

		if (graphObject == null)
			return;

		mGraphObject = graphObject;

		// id
		mId = Utils.getPropertyString(graphObject, ID);

		// album
		mAlbum = Album.create(graphObject.getPropertyAs(ALBUM, GraphObject.class));

		// back date time
		mBackDatetime = Utils.getDate(graphObject, BACKDATED_TIME);

		// back date time granularity
		String granularity = Utils.getPropertyString(graphObject, BACKDATED_TIME_GRANULARITY);
		mBackDatetimeGranularity = BackDatetimeGranularity.fromValue(granularity);

		// created time
		mCreatedTime = Utils.getDate(graphObject, CREATED_TIME);

		// from
		mFrom = Utils.createUser(graphObject, FROM);

		// height
		mHeight = Utils.getPropertyInteger(graphObject, HEIGHT);

		// icon
		mIcon = Utils.getPropertyString(graphObject, ICON);

		// image sources
		mImages = Utils.createList(graphObject, IMAGES, new Converter<Image>() {
			@Override
			public Image convert(GraphObject graphObject) {
				return Image.create(graphObject);
			}
		});

		// link
		mLink = Utils.getPropertyString(graphObject, LINK);

		// name
		mName = Utils.getPropertyString(graphObject, NAME);

		// page story id
		mPageStoryId = Utils.getPropertyString(graphObject, PAGE_STORY_ID);

		// picture
		mPicture = Utils.getPropertyString(graphObject, PICTURE);

		// source
		mSource = Utils.getPropertyString(graphObject, SOURCE);

		// updated time
		mUpdatedTime = Utils.getDate(graphObject, UPDATED_TIME);

		// width
		mWidth = Utils.getPropertyInteger(graphObject, WIDTH);

		// place
		mPlace = Place.create(graphObject.getPropertyAs(PLACE, GraphObject.class));

	}

	private Photo(Builder builder) {
		mName = builder.mName;
		mPlaceId = builder.mPlaceId;
		mParcelable = builder.mParcelable;
		mBytes = builder.mBytes;
		mPrivacy = builder.mPrivacy;
	}

	public static Photo create(GraphObject graphObject) {
		return new Photo(graphObject);
	}

	@Override
	public String getPath() {
		return GraphPath.PHOTOS;
	}

	@Override
	public Permission getPermission() {
		return Permission.PUBLISH_ACTION;
	}

	/**
	 * Get id of the photo
	 * 
	 * @return
	 */
	public String getId() {
		return mId;
	}

	public Album getAlbum() {
		return mAlbum;
	}

	public Date getBackDateTime() {
		return mBackDatetime;
	}

	public BackDatetimeGranularity getBackDatetimeGranularity() {
		return mBackDatetimeGranularity;
	}

	public Date getCreatedTime() {
		return mCreatedTime;
	}

	public User getFrom() {
		return mFrom;
	}

	public Integer getHeight() {
		return mHeight;
	}

	public String getIcon() {
		return mIcon;
	}

	public List<Image> getImages() {
		return mImages;
	}

	public String getJSONString() {
		return mGraphObject.getInnerJSONObject().toString();
	}

    public GraphObject getGraphObject() {
        return mGraphObject;
    }

	public String getLink() {
		return mLink;
	}

	public String getName() {
		return mName;
	}

	public String getPageStoryId() {
		return mPageStoryId;
	}

	public String getPicture() {
		return mPicture;
	}

	public Place getPlace() {
		return mPlace;
	}

	public String getSource() {
		return mSource;
	}

	public Date getUpdatedTime() {
		return mUpdatedTime;
	}

	public Integer getWidth() {
		return mWidth;
	}

	/**
	 * Is used for publishing action
	 */
	public Parcelable getParcelable() {
		return mParcelable;
	}

	/**
	 * Is used for publishing action
	 */
	public String getPlaceId() {
		return mPlaceId;
	}

	public Bundle getBundle() {
		Bundle bundle = new Bundle();

		// add description
		if (mName != null) {
			bundle.putString(MESSAGE, mName);
		}

		// add place
		if (mPlaceId != null) {
			bundle.putString(PLACE, mPlaceId);
		}

		// add privacy
		if (mPrivacy != null) {
			bundle.putString(PRIVACY, mPrivacy.getJSONString());
		}

		// add image
		if (mParcelable != null) {
			bundle.putParcelable(PICTURE, mParcelable);
		} else if (mBytes != null) {
			bundle.putByteArray(PICTURE, mBytes);
		}

		return bundle;
	}

	public enum BackDatetimeGranularity {
		YEAR("year"),
		MONTH("month"),
		DAY("day"),
		HOUR("hour"),
		MIN("min"),
		NONE("none");

		private String mValue;

		private BackDatetimeGranularity(String value) {
			mValue = value;
		}

		public String getValue() {
			return mValue;
		}

		public static BackDatetimeGranularity fromValue(String value) {
			for (BackDatetimeGranularity granularityEnum : values()) {
				if (granularityEnum.mValue.equals(value)) {
					return granularityEnum;
				}
			}
			return BackDatetimeGranularity.NONE;
		}
	}

	/**
	 * Builder for preparing the Photo object to be published.
	 */
	public static class Builder {
		private String mName = null;
		private String mPlaceId = null;

		private Parcelable mParcelable = null;
		private byte[] mBytes = null;
		private Privacy mPrivacy = null;

		public Builder() {
		}

		/**
		 * Set photo to be published
		 * 
		 * @param bitmap
		 */
		public Builder setImage(Bitmap bitmap) {
			mParcelable = bitmap;
			return this;
		}

		/**
		 * Set photo to be published
		 * 
		 * @param file
		 */
		public Builder setImage(File file) {
			try {
				mParcelable = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
			} catch (FileNotFoundException e) {
				Logger.logError(Photo.class, "Failed to create photo from file", e);
			}
			return this;
		}

		/**
		 * Set photo to be published
		 * 
		 * @param bytes
		 */
		public Builder setImage(byte[] bytes) {
			mBytes = bytes;
			return this;
		}

		/**
		 * Add name/description to the photo
		 * 
		 * @param name
		 *            The name/description of the photo
		 */
		public Builder setName(String name) {
			mName = name;
			return this;
		}

		/**
		 * Add place id of the photo
		 * 
		 * @param placeId
		 *            The place id of the photo
		 */
		public Builder setPlace(String placeId) {
			mPlaceId = placeId;
			return this;
		}

		/**
		 * Add privacy setting to the photo
		 * 
		 * @param privacy
		 *            The privacy setting of the photo
		 * @see com.sromku.simple.fb.entities.Privacy
		 */
		public Builder setPrivacy(Privacy privacy) {
			mPrivacy = privacy;
			return this;
		}

		public Photo build() {
			return new Photo(this);
		}
	}

}
