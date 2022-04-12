package com.fscut.courier.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于返回前端数据封装
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessUtil {

	private Integer status;
	private String msg;
	private Object obj;

}

