package com.kjwon.cloud.file;

import java.util.List;

import com.kjwon.cloud.file.FileDto;


public interface FileSetService {

	public List<FileDto> searchFileSet(String path);
}
