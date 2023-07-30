package com.cqupt.mike.common;
//常量参数设置
public class Constants {
    //public final static String FILE_UPLOAD_DIC = "/opt/image/upload/";//上传文件的默认url前缀，根据部署设置自行修改
    public final static String FILE_UPLOAD_DIC ="G:\\java\\workspace\\mallModel\\upload\\";//上传文件的默认url前缀，根据部署设置自行修改

    public final static int INDEX_CAROUSEL_NUMBER = 5;//首页轮播图数量(可根据自身需求修改)

    public final static int INDEX_CATEGORY_NUMBER = 10;//首页一级分类的最大数量

    public final static int SEARCH_CATEGORY_NUMBER = 8;//搜索页一级分类的最大数量

    public final static int INDEX_COURSES_HOT_NUMBER = 4;//首页热卖课程数量
    public final static int INDEX_COURSES_NEW_NUMBER = 5;//首页新品课程数量
    public final static int INDEX_COURSES_RECOMMOND_NUMBER = 10;//首页推荐课程数量

    public final static int SHOPPING_CART_ITEM_TOTAL_NUMBER = 13;//购物车中课程的最大数量(可根据自身需求修改)

    public final static int SHOPPING_CART_ITEM_LIMIT_NUMBER = 5;//购物车中单个课程的最大购买数量(可根据自身需求修改)

    public final static String VERIFY_CODE_KEY = "VerifyCode";//验证码key

    public final static String MIKE_STUDENT_SESSION_KEY = "mikeStudent";//session中user的key

    public final static int COURSES_SEARCH_PAGE_LIMIT = 10;//搜索分页的默认条数(每页10条)

    public final static int ORDER_SEARCH_PAGE_LIMIT = 3;//我的订单列表分页的默认条数(每页3条)

    public final static int SELL_STATUS_UP = 0;//商品上架状态
    public final static int SELL_STATUS_DOWN = 1;//商品下架状态
}
