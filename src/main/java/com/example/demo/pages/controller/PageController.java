package com.example.demo.pages.controller;

import com.example.demo.common.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class PageController {

	@RequestMapping(value = "/")
	public String index() {
		return "demo";
	}

	private static String ROOT_FOLDER = "E:/img";
	private String UPLOADED_FOLDER = "E:/img/upload";

	@GetMapping("/upload")
	public String indexUpload() {
		return "upload";
	}

	@RequestMapping(value = "upload",method = {RequestMethod.POST}) // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file, ModelMap redirectAttributes) {
		File dir0 = new File(ROOT_FOLDER);
		if(!dir0.exists() || !dir0.isDirectory()) {
			throw new BusinessException(-500,"target Directory is not exists");
		}
		// 目录不存在则新建 否则写文件报错
		File dir = new File(UPLOADED_FOLDER);
		if (!dir.exists()) {
			boolean bool = dir.mkdir();
			if(!bool){
				redirectAttributes.addAttribute("message", "Make directory failed! ");
				return "/uploadStatus";
			}
		}

		if (file.isEmpty()) {
			redirectAttributes.addAttribute("message", "Please select a file to upload");
			return "/uploadStatus";
		}
		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			// 带上文件名重组路径

			Path path = Paths.get(UPLOADED_FOLDER + File.separator + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/uploadStatus";
	}
}