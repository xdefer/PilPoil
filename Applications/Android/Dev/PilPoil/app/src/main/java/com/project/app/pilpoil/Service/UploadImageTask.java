package com.project.app.pilpoil.Service;

import android.os.AsyncTask;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class UploadImageTask extends AsyncTask<String, Void, String> {

    private String filePath;
    public UploadImageTask(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlImg = null;
        Cloudinary cloudinary = new Cloudinary("cloudinary://786239969984472:6zbImcBbWO1WGqAp3pK_sq6Ba1w@lookas33");

        try{
            FileInputStream fileInputStream = new FileInputStream(new File(this.filePath));

            //cloudinary.url().transformation(new Transformation().width(100).height(150).crop("fill")).generate("sample.jpg");
            //urlImg = cloudinary.url().imageTag("toto.jpg");
            Map temp = cloudinary.uploader().upload(fileInputStream, ObjectUtils.emptyMap());

            //cloudinary.uploader().upload(fileInputStream, ObjectUtils.asMap("public_id","temp"));
            //urlImg = cloudinary.url().generate("BIDON.jpg");
            //System.out.println(temp.get("url"));
            urlImg = (String) temp.get("url");

        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            e.printStackTrace();
        }
        return urlImg;
    }
}
