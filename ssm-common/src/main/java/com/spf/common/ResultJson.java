package com.spf.common;

import java.util.List;

public class ResultJson<T> {
	/**
	 * 状态码
	 */
	private Integer code;
	/**
	 * 返回信息
	 */
	private String maseege;
	/**
	 * 数据
	 */
	private T data;
	/**
	 * 数据-集合
	 */
	private List<T> datas;

	public void success() {
		this.code = 200;
		this.maseege = "成功";
	}

	public void fail() {
		this.code = 400;
		this.maseege = "失败";
	}

	/**
	 * 状态码获取
	 * @return code
	 */
	public Integer getCode() {
		return this.code;
	}

	/**
	 * 状态码设置
	 * @param code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * 返回信获取息
	 * @return maseege 息
	 */
	public String getMaseege() {
		return this.maseege;
	}

	/**
	 * 返回信设置息
	 * @param maseege 息
	 */
	public void setMaseege(String maseege) {
		this.maseege = maseege;
	}

	/**
	 * 数据
	 * @return 数据
     */
	public T getData() {
		return data;
	}

	/**
	 * 数据
	 * @param data 数据
     */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 数据-集合
	 * @return 数据-集合
     */
	public List<T> getDatas() {
		return datas;
	}

	/**
	 * 数据-集合
	 * @param datas 数据-集合
     */
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}
