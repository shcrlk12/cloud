package com.kjwon.cloud.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kjwon.cloud.file.FileSetService;
import org.springframework.stereotype.Service;

import com.kjwon.cloud.file.FileDto;

@Service
public class FileSetServiceImpl implements FileSetService {

	@Override
	public List<FileDto> searchFileSet(String path){

		System.out.println(path);
		File file = new File(path);
		File[] files = file.listFiles();

		assert files != null;
		return Arrays.stream(files)
				.map(f -> {
						try {
							System.out.println(f.getName());
							return FileDto
									.builder()
									.fileName(f.getName())
									.fileSize(Files.size(f.toPath()))
									.fileType(f.isDirectory() ? 0 : 1) // 0은 디렉토리
									.build();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}
					} 
				).collect(Collectors.toList());
	}

}
