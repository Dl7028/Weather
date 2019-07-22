package cn.eoe.app.https;



/**
 * 访问网络的回调接口
 * Created by 徐启 on 2019/5/5.
 */

    public interface IHttpCallBackListener {

        void onFinish(String string);             //当Http访问完成时回调onFinish方法

        void onError();                          //当Http访问失败时回调onError方法
    }

