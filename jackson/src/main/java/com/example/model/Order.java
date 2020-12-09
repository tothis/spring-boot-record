package com.example.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.IOException;

/**
 * 订单
 *
 * @author 李磊
 * @since 1.0
 */
@Data
public class Order {

    /**
     * 订单状态
     */
    @JsonSerialize(using = OrderSerializer.class)
    private Integer orderState;

    @JsonGetter("orderStateText")
    public Integer getOrderState() {
        return orderState;
    }

    static class OrderSerializer extends JsonSerializer<Integer> {
        @Override
        public void serialize(Integer state, JsonGenerator j, SerializerProvider s) throws IOException {
            String stateStr;
            switch (state) {
                case 0:
                    stateStr = "待付款";
                    break;
                case 1:
                    stateStr = "代发货";
                    break;
                case 2:
                    stateStr = "已发货";
                    break;
                case 3:
                    stateStr = "已收货";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + state);
            }
            j.writeString(stateStr);
        }
    }
}