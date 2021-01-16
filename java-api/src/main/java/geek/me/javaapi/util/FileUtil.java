package geek.me.javaapi.util;

import java.io.*;

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
}
