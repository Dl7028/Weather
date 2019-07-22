package cn.eoe.app.entity;

/**
 * 天气实况的实体类
 * Created by 徐启 on 2019/5/6.
 */

public class ActualWeather {
    public  String locationName ; //地名
    public  String locationId;  //地方id
    public  String nowText ;
    public  String nowTemperature ;
    public  String nowFeelsLike;
    public  String nowPressure;
    public  String nowHumidity;
    public  String nowVisibility;
    public  String nowWindDirection;
    public  String nowWindScale;
    public String nowCode;

   public ActualWeather(){
        this.locationId = "";
        this.locationName = "";
        this.nowText = "";
        this.nowTemperature = "";
        this.nowFeelsLike = "";
        this.nowPressure = "";
        this.nowHumidity = "";
        this.nowVisibility = "";
        this.nowWindDirection = "";
       this.nowCode = "";

    }





    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setNowFeelsLike(String nowFeelsLike) {
        this.nowFeelsLike = nowFeelsLike;
    }

    public void setNowHumidity(String nowHumidity) {
        this.nowHumidity = nowHumidity;
    }

    public void setNowPressure(String nowPressure) {
        this.nowPressure = nowPressure;
    }

    public void setNowTemperature(String nowTemperature) {
        this.nowTemperature = nowTemperature;
    }

    public void setNowText(String nowText) {
        this.nowText = nowText;
    }

    public void setNowVisibility(String nowVisibility) {
        this.nowVisibility = nowVisibility;
    }

    public void setNowWindDirection(String nowWindDirection) {
        this.nowWindDirection = nowWindDirection;
    }

    public void setNowWindScale(String nowWindScale) {
        this.nowWindScale = nowWindScale;
    }



    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getNowFeelsLike() {
        return nowFeelsLike;
    }

    public String getNowHumidity() {
        return nowHumidity;
    }

    public String getNowPressure() {
        return nowPressure;
    }

    public String getNowTemperature() {
        return nowTemperature;
    }

    public String getNowText() {
        return nowText;
    }

    public String getNowVisibility() {
        return nowVisibility;
    }

    public String getNowWindDirection() {
        return nowWindDirection;
    }

    public String getNowWindScale() {
        return nowWindScale;
    }

    public String getNowCode() {
        return nowCode;
    }

    public void setNowCode(String nowCode) {
        this.nowCode = nowCode;
    }
}
