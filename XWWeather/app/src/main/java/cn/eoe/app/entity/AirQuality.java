package cn.eoe.app.entity;

/**
 *空气质量的实体类
 * Created by 徐启 on 2019/5/6.
 */

public class AirQuality {
    public String quality;//空气质量类别，有“优、良、轻度污染、中度污染、重度污染、严重污染”6类
    public String aqi;//空气质量指数(AQI)
    public String locationName;
    public String path;
    public String pm25;
    public String pm10;
    public String so2;
    public String no2;
    public String co;
    public String o3;

    public AirQuality(){
        this.quality = "";
        this.aqi = "";
        this.locationName = "";
        this.path = "";
        this.pm25 = "";
        this.pm10 = "";
        this.so2 = "";
        this.no2 = "";
        this.co = "";
        this.o3 = "";
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getAqi() {
        return aqi;
    }

    public String getQuality() {
        return quality;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }
}
