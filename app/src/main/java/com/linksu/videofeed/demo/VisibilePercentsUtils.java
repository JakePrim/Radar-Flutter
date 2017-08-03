package com.linksu.videofeed.demo;

import android.graphics.Rect;
import android.view.View;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/27 0027
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class VisibilePercentsUtils {

    private static VisibilePercentsUtils mVisibilePercentsUtils = null;

    private VisibilePercentsUtils() {
    }

    public static VisibilePercentsUtils getInstance() {
        if (mVisibilePercentsUtils == null) {
            synchronized (VisibilePercentsUtils.class) {
                if (mVisibilePercentsUtils == null) {
                    mVisibilePercentsUtils = new VisibilePercentsUtils();
                }
            }
        }
        return mVisibilePercentsUtils;
    }

    private final Rect mCurrentViewRect = new Rect();


    public int getVisibilityPercents(View view) {

        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);

        int height = view.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            // view is partially hidden behind the top edge
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        return percents;
    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }
}
