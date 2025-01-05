package com.backend.backend.service;

import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

@Service
public class ImageProcessingService {
    private String getAbsolutePath(String relativePath){
        // ImageJは実行フォルダを認識しないので，絶対パスに直して渡す
        String executionPath = System.getProperty("user.dir");
        String absolutePath = Paths.get(executionPath, relativePath).toString();
        return absolutePath;
    }

    private String createOutputPath(String inputPath){
        // 最後のピリオド(.)位置を取得し，その前に"_edited"を挿入
        StringBuilder sb = new StringBuilder();
        sb.append(inputPath);
        int index = inputPath.lastIndexOf(".");
        sb.insert(index, "_edited");
        return sb.toString();
    }

    public String convertColorToGrey(String inputPath){
        ij.ImageJ imageJ = new ij.ImageJ();
        String outputPath = createOutputPath(inputPath);

        try {
            ImagePlus image = IJ.openImage(getAbsolutePath(inputPath));
            if(image == null){
                throw new Exception("画像が読み込めません："+inputPath);
            }

            ImageProcessor processor =  image.getProcessor();
            processor = processor.convertToByte(true);

            ImagePlus editedImage = new ImagePlus("Edited Image", processor);
            IJ.save(editedImage, getAbsolutePath(outputPath));
        } catch (Exception e) {
            System.err.println("error: "+e.getMessage());
            e.printStackTrace();
        }
        imageJ.quit();
        return outputPath;
    }
}
