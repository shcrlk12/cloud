package com.kjwon.cloud.file;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FileDto {
	private String fileName;
	
	private LocalDateTime regDt;
	
	private long fileSize;
	private int fileType;
}
