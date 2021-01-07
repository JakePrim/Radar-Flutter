package com.prim.oa.global;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 */
public class Constant {
    public static final String POST_STAFF = "员工";
    public static final String POST_FM = "部门经理";
    public static final String POST_GM = "总经理";
    public static final String POST_CASHIER = "财务";

    public static List<String> getPosts() {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add(POST_STAFF);
        strings.add(POST_FM);
        strings.add(POST_GM);
        strings.add(POST_CASHIER);
        return strings;
    }

    public static List<String> getLists() {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("交通");
        strings.add("餐饮");
        strings.add("住宿");
        strings.add("办公");
        return strings;
    }

    /**
     * 报销单状态
     */
    public static final String CLAIMVOUCHER_CREATE = "新创建";
    public static final String CLAIMVOUCHER_SUBMIT = "已提交";
    public static final String CLAIMVOUCHER_APPROVED = "已审核";
    public static final String CLAIMVOUCHER_BACK = "已打回";
    public static final String CLAIMVOUCHER_TERMINATED = "已终止";
    public static final String CLAIMVOUCHER_RECHECK = "待复审";
    public static final String CLAIMVOUCHER_PAID = "已打款";

    /**
     * 审核额度
     */
    public static final double LIMIT_CHECK = 5000.00;

    /**
     * 报销单处理
     */
    public static final String DEAL_CREATE = "创建";
    public static final String DEAL_SUBMIT = "提交";
    public static final String DEAL_UPDATE = "修改";
    public static final String DEAL_BACK = "打回";
    public static final String DEAL_REJECT = "拒绝";
    public static final String DEAL_PASS = "通过";
    public static final String DEAL_PAID = "打款";
}
