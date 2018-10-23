package com.prim_player_cc.source_cc;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.prim_player_cc.log.PrimLog;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc set player source data 播放资源类
 * Parcelable 序列化比Serializable 性能高出几倍，如果序列化大量数据推荐使用Parcelable
 * @time 2018/7/24 - 下午2:47
 */
public class PlayerSource implements Parcelable {

    public PlayerSource() {
    }

    public PlayerSource(String url) {
        this.url = url;
    }

    public PlayerSource(Uri uri) {
        this.uri = uri;
    }

    public PlayerSource(String tag, String url) {
        this.tag = tag;
        this.url = url;
    }

    public PlayerSource(String tag, Uri uri) {
        this.tag = tag;
        this.uri = uri;
    }

    public PlayerSource(String tag, String id, String url, String title) {
        this.tag = tag;
        this.id = id;
        this.url = url;
        this.title = title;
    }

    public PlayerSource(Object data) {
        this.data = data;
    }

    /**
     * set only player source tag
     */
    private String tag;

    /**
     * set only player source id
     */
    private String id;

    /**
     * play video path:url
     */
    private String url;

    /**
     * video thumbnail path
     */
    private String thumbnailUrl;

    /**
     * play video path:uri
     */
    private Uri uri;

    /**
     * play video title
     */
    private String title;

    /**
     * start play video,seek position
     */
    private long startPos;

    /**
     * extended field,if you want set other some data,you can set this field
     */
    private HashMap otherData;

    /**
     * set video headers
     */
    private HashMap headers;

    /**
     * Parcelable Serializable
     */
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Uri mUri;

    public boolean isPlayerSource() {
        String url = getUrl();
        Uri uri = getUri();
        if (!TextUtils.isEmpty(url)) {
            this.mUri = Uri.parse(url);
            return true;
        } else if (uri != null) {
            this.mUri = uri;
            return true;
        }
        return false;
    }

    public Uri getVideoUri() {
        return mUri;
    }

    protected PlayerSource(Parcel in) {
        tag = in.readString();
        id = in.readString();
        url = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
        title = in.readString();
        startPos = in.readLong();
        otherData = in.readHashMap(HashMap.class.getClassLoader());
        headers = in.readHashMap(HashMap.class.getClassLoader());
        String dataClassName = in.readString();
        try {
            if (data instanceof Parcelable) {
                data = in.readParcelable(Class.forName(dataClassName).getClassLoader());
            } else if (data instanceof Serializable) {
                data = in.readSerializable();
            } else {
                data = in.readValue(Object.class.getClassLoader());
            }
        } catch (ClassNotFoundException e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
        }
        thumbnailUrl = in.readString();

    }

    public static final Creator<PlayerSource> CREATOR = new Creator<PlayerSource>() {
        @Override
        public PlayerSource createFromParcel(Parcel in) {
            return new PlayerSource(in);
        }

        @Override
        public PlayerSource[] newArray(int size) {
            return new PlayerSource[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tag);
        dest.writeString(id);
        dest.writeString(url);
        dest.writeParcelable(uri, flags);
        dest.writeString(title);
        dest.writeLong(startPos);
        dest.writeMap(otherData);
        dest.writeMap(headers);
        dest.writeString(data.getClass().getName());
        if (data instanceof Parcelable) {
            dest.writeParcelable((Parcelable) data, flags);
        } else if (data instanceof Serializable) {
            dest.writeSerializable((Serializable) data);
        } else {
            dest.writeValue(data);
        }
        dest.writeString(thumbnailUrl);

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public HashMap<String, Object> getOtherData() {
        return otherData;
    }

    public void setOtherData(HashMap<String, Object> otherData) {
        this.otherData = otherData;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSource source = (PlayerSource) o;
        return startPos == source.startPos &&
                ObjectEquals(tag, source.tag) &&
                ObjectEquals(id, source.id) &&
                ObjectEquals(url, source.url) &&
                ObjectEquals(thumbnailUrl, source.thumbnailUrl) &&
                ObjectEquals(uri, source.uri) &&
                ObjectEquals(title, source.title) &&
                ObjectEquals(otherData, source.otherData) &&
                ObjectEquals(headers, source.headers) &&
                ObjectEquals(data, source.data) &&
                ObjectEquals(mUri, source.mUri);
    }

    @Override
    public int hashCode() {
        return hash(tag, id, url, thumbnailUrl, uri, title, startPos, otherData, headers, data, mUri);
    }

    private int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    private boolean ObjectEquals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}
