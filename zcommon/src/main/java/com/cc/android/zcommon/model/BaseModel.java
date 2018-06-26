package com.cc.android.zcommon.model;

import java.io.Serializable;

/**
 * All the model's base model.
 *
 * @Author LiuLiWei
 */
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The database's primary key.
	 */
	public String _id;
	/**
	 * The database's time column.
	 */
	public String time;
}
