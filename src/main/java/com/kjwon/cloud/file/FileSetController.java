package com.kjwon.cloud.file;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kjwon.cloud.exception.response.Success;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@CrossOrigin
class FileSetController{

	private final String location = "D:\\cloudTest";
	private final FileSetService fileSetService;
	
	@GetMapping("/api/v1/fileList")
	public Success<List<FileDto>> searchFileSet(String path) {
		String localPath;

		if(path == null)
			localPath = this.location;
		else
			localPath = this.location + "\\" + path;

		List<FileDto> fileDtos = fileSetService.searchFileSet(localPath);

		return new Success<>(fileDtos);
	}
	
	@GetMapping("/api/v1/fileDownload")
	public ResponseEntity<Object> fileDownload(String path, String userName, String fileName) {
		
		String localPath = "";
		if(path == null)
			localPath =  this.location + "\\" + fileName ;
		else
			localPath = this.location + "\\" + path + "\\"+ fileName ;
		
		System.out.println(localPath);

		try {
			Path filePath = Paths.get(localPath);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));
			
			File file = new File(localPath);

			HttpHeaders headers = new HttpHeaders();
			
			String name = file.getName();
			
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(String.format("%1$s", URLEncoder.encode(name, String.valueOf(StandardCharsets.UTF_8)))).build());
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	@PostMapping("/api/v1/postFile")
	public ResponseEntity<Object> postFile(MultipartFile uploadFile, String path) throws IOException, ServletException{

		File savefile = new File(this.location + "\\" + path + "\\" + uploadFile.getOriginalFilename());

		uploadFile.transferTo(savefile);

		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}