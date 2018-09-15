package com.prim_player_cc.source;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.prim_player_cc.log.PrimLog;

import java.util.HashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc set player source data 播放资源类
 * Parcelable 序列化比Serializable 性能高出几倍，如果序列化大量数据推荐使用Parcelable
 * @time 2018/7/24 - 下午2:47
 */
public class PlayerSource<T extends Parcelable> implements Parcelable {

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

    public PlayerSource(T data) {
        this.data = data;
    }

    private String tag;

    /**
     * set only player id
     */
    private String id;

    /**
     * play video path:url
     */
    private String url;

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
    private HashMap<String, Object> otherData;

    /**
     * set video headers
     */
    private HashMap<String, String> headers;

//    private FileDescriptor fileDescriptor;//TODO 序列化存在问题 暂时不使用

    private AssetFileDescriptor assetFileDescriptor;

    /**
     * set Parcelable
     */
    private T data;

    protected PlayerSource(Parcel in) {
        tag = in.readString();
        id = in.readString();
        url = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
        title = in.readString();
        startPos = in.readLong();
        otherData = in.readHashMap(HashMap.class.getClassLoader());
        headers = in.readHashMap(HashMap.class.getClassLoader());
//        fileDescriptor = in.readFileDescriptor();
        assetFileDescriptor = in.readParcelable(AssetFileDescriptor.class.getClassLoader());
        String dataClassName = in.readString();
        try {
            data = in.readParcelable(Class.forName(dataClassName).getClassLoader());
        } catch (ClassNotFoundException e) {
            if (PrimLog.LOG_OPEN) {
                e.printStackTrace();
            }
        }

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
//        dest.write(fileDescriptor);
        dest.writeParcelable(assetFileDescriptor, flags);
        dest.writeString(data.getClass().getName());
        dest.writeParcelable(data, flags);

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

    public AssetFileDescriptor getAssetFileDescriptor() {
        return assetFileDescriptor;
    }

    public void setAssetFileDescriptor(AssetFileDescriptor assetFileDescriptor) {
        this.assetFileDescriptor = assetFileDescriptor;
    }
}
