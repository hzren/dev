package com.start.common.jdbc-app.common.jdbc;

import java.io.Serializable;

import com.start-app.common.entity.UploadDo;

public interface UploadDoDao<T extends UploadDo, ID extends Serializable> extends BaseRepository<T, ID> {

	T findByBucketAndStoredKey(String bucket, String fileKey);
	
}
