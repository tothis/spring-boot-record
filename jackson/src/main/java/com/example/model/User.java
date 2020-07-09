package com.example.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李磊
 * @datetime 2020/3/16 22:26
 * @description
 */
@Data
// @JsonInclude(JsonInclude.Include.NON_EMPTY)
// value可配置多个@JsonIgnore ignoreUnknown = true json属性多于实体时不报错
// @JsonIgnoreProperties(/*value = {"delFlag", "password"}, */ignoreUnknown = true)
public class User {

    @JsonSetter("userId")
    private Long id;

    @JsonAlias("userName") // 接收前端表单key为'userName'的值
    private String name;

    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    /**
     * Access.WRITE_ONLY 只在序列化时使用
     * Access.READ_ONLY 只在反序列化时使用 等同@JsonAlias
     * Access.READ_WRITE 序列化和反序列化都使用
     * Access.AUTO 自动确定 一般情况等同于READ_WRITE(默认)
     */
    // 返回前端key为'time'
    @JsonProperty(value = "time", access = JsonProperty.Access.READ_WRITE)
    private Date date;

    private LocalDateTime localDateTime;

    /**
     * get方法和set方法分开使用@JsonFormat配置 可分别控制序列化和反序列化
     */
    private Date birthday;
    private String password;
    /**
     * 但@JsonIgnore放在set方法 并配合@JsonProperty使用却不生效
     *
     * @return
     */
    private String remark;
    // 接收前端表单key为'state'的值
    @JsonSetter("state")
    private Boolean delFlag;
    @JsonIgnore
    private Map<String, Object> other = new HashMap<>();

    @JsonFormat(pattern = "yyyy年MM月dd日")
    public Date getBirthday() {
        return birthday;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 不配合@JsonProperty使用 即时只放在get方法 反序列化时也会忽略此属性
     * 如下为只接收密码但不返回密码
     *
     * @return
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public String getRemark() {
        return remark;
    }

    @JsonIgnore
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonGetter("flag")
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 反序列化时未匹配属性 保存至此处
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void setOther(String key, Object value) {
        this.other.put(key, value);
    }

    // @JsonAnyGetter
    // public Map<String, Object> getOther() {
    //     return other;
    // }
}