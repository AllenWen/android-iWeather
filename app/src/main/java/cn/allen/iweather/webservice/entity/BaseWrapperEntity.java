package cn.allen.iweather.webservice.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/10
 * Email: wenxueguo@medlinker.com
 * Description:
 */

public class BaseWrapperEntity<T extends BaseEntity> {
    @SerializedName("status_code")
    private int status_code;
    @SerializedName("status")
    private String status;
    @SerializedName("results")
    private List<T> results;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BaseWrapperEntity{" +
                "status_code=" + status_code +
                ", status='" + status + '\'' +
                ", results=" + results +
                '}';
    }
}
