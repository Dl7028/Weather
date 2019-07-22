package cn.eoe.app.util;

/**
 * 上拉刷新监听接口，需要重写这四个方法
 * Created by 徐启 on 2019/5/11.
 */

public interface RefreshListener {
    void startRefresh(); //刷新
    void loadMore();    //加载
    void hintChange(String hint);  //提示文字
    void setWidthX(int x);  //设置x

}
