package com.example.xiaxiao.ziyue;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn,btn2,btn3,btn4;
    TextView tv,tv2;
    int i=0;
    String fileUrl="";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobIniter.init(this);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.btn:
                final Story story=new Story(1,"xiaxiao","小红帽和大灰狼的故事"+(i++),"https://pic.36krcnd.com/avatar/201610/10080525/jklunez5738zdi7b.jpg!heading");
                story.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            tv.setText("save ok. " + story.getStoryIntroduce());
                        } else {
                            tv.setText("save error: "+e.getMessage());
                        }
                    }
                });

                break;
            case R.id.btn2:
                BmobQuery<Story> query=new BmobQuery<Story>();
                query.addWhereEqualTo("tellerName", "xiaxiao");
                query.setLimit(11);
                query.findObjects(new FindListener<Story>() {
                    @Override
                    public void done(List<Story> list, BmobException e) {
                        if (e==null) {
                            StringBuffer sb = new StringBuffer();
                            for (Story s1:list) {
                                sb.append(s1.toString()+"\n\n");
                            }
                            tv.setText("query ok :\n"+sb.toString());
                        }
                    }
                });
                break;
            case R.id.btn3:
                // Storage Permissions


                /**
                 * Checks if the app has permission to write to device storage
                 *
                 * If the app does not has permission then the user will be prompted to
                 * grant permissions
                 *
                 * @param activity
                 */

                /*String path = "/sdcard/DCIM/Camera/xiao.jpg";
                File f = new File(path);
                if (f.exists()) {
                    tv.setText("f is exist"+f.getAbsolutePath()+f.getName()+f.isFile());
                    final BmobFile file = new BmobFile(f);
                    Toast.makeText(this,""+(file==null),Toast.LENGTH_SHORT).show();*/
                verifyStoragePermissions(this);
                    String picPath = "/sdcard/DCIM/Camera/xiao.jpg";
                    final BmobFile bmobFile = new BmobFile(new File(picPath));
                    bmobFile.uploadblock(new UploadFileListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                tv.setText("上传文件成功:" + bmobFile.getFileUrl());
                                fileUrl=bmobFile.getFileUrl();
                            }else{
                                tv.setText("上传文件失败：" + e.getMessage());
                            }

                        }

                        @Override
                        public void onProgress(Integer value) {
                            // 返回的上传进度（百分比）
                            tv2.setText(""+value);
                        }
                    });
                    /*file.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                tv.setText("file upload ok : " + file.getFileUrl());
                            } else {
                                tv.setText("file upload error.");
                            }
                        }

                        @Override
                        public void onProgress(Integer value) {
                            tv2.setText(""+value);

                        }
                    });
                }*/
                break;
            case R.id.btn4:
                BmobFile bfile = new BmobFile("haha.jpg", "", fileUrl);
                bfile.download(new File("/sdcard/hahama.jpg"),new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null) {
                            tv.setText("download ok  "+s);
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {
                        tv2.setText(""+integer+"   "+l);
                    }
                });
                break;
            default :
                break;
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
