package geek.me.javaapi.util;

import org.apache.tomcat.util.ExceptionUtils;

import java.io.*;
import java.nio.file.Files;

public class FileUtil {

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public static void writeFile(String path,String con){
        try {
            File f = new File(path);
            File dir = f.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();

            }
            FileWriter fileWriter = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(con);
            bw.close();
        }catch (IOException ioe){

        }finally {

        }
    }
    public static void copyMp3File(String s, String tgtpath) {
        //获取源文件的名称
        String postFix = s.substring(s.lastIndexOf(".")); //目标文件后缀
        copyFile(s,tgtpath+postFix);
    }
    public static void copyFile(String s, String tgtpath) {




        System.out.println("源文件:"+s);
//        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        System.out.println("目标文件地址:"+tgtpath);
        try
        {

            File dir = new File(tgtpath).getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileInputStream fis = new FileInputStream(s);//创建输入流对象
            FileOutputStream fos = new FileOutputStream(tgtpath); //创建输出流对象

            byte datas[] = new byte[1024*8];//创建搬运工具
            int len = 0;//创建长度
            while((len = fis.read(datas))!=-1)//循环读取数据
            {
                fos.write(datas,0,len);
            }
            fis.close();//释放资源
            fis.close();//释放资源
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
