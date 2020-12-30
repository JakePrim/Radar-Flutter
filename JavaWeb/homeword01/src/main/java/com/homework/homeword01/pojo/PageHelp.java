package com.homework.homeword01.pojo;

import com.homework.homeword01.utils.Constant;

import java.util.Arrays;
import java.util.List;

/**
 * 分页的辅助类
 *
 * @param <T>
 */
public class PageHelp<T> {
    private int pageSize;
    private int totalCount;
    private int currentPage;
    private int startIndex;
    private int[] indexes = new int[0];
    private int nextIndex;
    private int previousIndex;
    private int pageCount;
    private List<T> items;
    private int lastIndex;
    private String currentUrl;

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public PageHelp(List<T> items, int totalCount, int startIndex,int pageSize) {
        setPageSize(pageSize);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }

    public PageHelp(List<T> items, int totalCount, int startIndex) {
        pageSize = Constant.PAGE_SIZE;
        setPageSize(pageSize);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            int count = totalCount / pageSize;
            if (totalCount % pageSize > 0) {
                count++;
            }
            indexes = new int[count];
            for (int i = 0; i < count; i++) {
                indexes[i] = pageSize * i;
            }
        } else {
            this.totalCount = 0;
        }
    }

    /**
     * 得到总记录数
     *
     * @return
     */
    public int getTotalCount() {
        return totalCount;
    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    /**
     * 得到分页索引的数组
     *
     * @return
     */
    public int[] getIndexes() {
        return indexes;
    }

    public void setStartIndex(int startIndex) {
        if (totalCount <= 0) {
            this.startIndex = 0;
        } else if (startIndex >= totalCount) {
            this.startIndex = indexes[indexes.length - 1];
        } else if (startIndex < 0) {
            this.startIndex = 0;
        } else {
            this.startIndex = indexes[startIndex / pageSize];
        }
    }

    /**
     * 当前页
     *
     * @return
     */
    public int getStartIndex() {
        return startIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    /**
     * 下一页
     *
     * @return
     */
    public int getNextIndex() {
        int nextIndex = getStartIndex() + pageSize;
        if (nextIndex >= totalCount) {
            return getStartIndex();
        } else {
            return nextIndex;
        }
    }

    public void setPreviousIndex(int previousIndex) {
        this.previousIndex = previousIndex;
    }

    /**
     * 上一页
     *
     * @return
     */
    public int getPreviousIndex() {
        int previousIndex = getStartIndex() - pageSize;
        if (previousIndex < 0) {
            return 0;
        } else {
            return previousIndex;
        }
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        int count = totalCount / pageSize;
        if (totalCount % pageSize > 0)
            count++;
        return count;
    }

    public int getCurrentPage() {
        return getStartIndex() / pageSize + 1;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getLastIndex() {
        if (indexes.length == 0) {
            return 0;
        } else {
            return indexes[indexes.length - 1];
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 得到已分页好的结果集
     *
     * @return
     */
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageHelp{" +
                "pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", startIndex=" + startIndex +
                ", indexes=" + Arrays.toString(indexes) +
                ", nextIndex=" + nextIndex +
                ", previousIndex=" + previousIndex +
                ", pageCount=" + pageCount +
                ", items=" + items +
                ", lastIndex=" + lastIndex +
                ", currentUrl='" + currentUrl + '\'' +
                '}';
    }
}