package cn.eoe.app.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 访问网络的类
 * Created by 徐启 on 2019/5/5.
 */

public class HttpsSendsRequests {

    public  static boolean startRefresh = false;
    /**
     * 在新线程中发送网络请求
     *
     * @param address  网络地址
     * @param listener HttpCallBackListener接口的实现类;
     *                 onFinish方法为访问成功后的回调方法;
     *                 onError为访问不成功时的回调方法
     */
    public  static void sentHttpRequest(final String address, final IHttpCallBackListener listener) {


        new Thread(new Runnable() {
            @Override
            public void run() {                                                            //新线程，访问网络成功后回调HttpCallBackListener
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");                                            //网络请求方式
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    if(startRefresh) {
                        try {
                            Thread.sleep(3000);
                            startRefresh = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }

                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }

                  /*  if(startRefresh){
                        Thread.sleep(3000);
                    }*/
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onError();
                    }
                } /*catch (InterruptedException e) {
                    e.printStackTrace();
                }*/ finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
