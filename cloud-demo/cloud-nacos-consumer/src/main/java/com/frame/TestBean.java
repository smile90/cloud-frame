package com.frame;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: smile
 * @date: 2019/06/13
 */
@Data
public class TestBean implements Serializable {
    private String testStr;
    private int testInt;
    private Test2Bean test2Bean;
}
