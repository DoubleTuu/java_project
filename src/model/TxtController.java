package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TxtController {

    public static String read(String path)
    {
//        File file = new File("C:\\Users\\戴尔\\Desktop\\java_project\\resource\\file\\test.txt");
        File file = new File(path);
        String string = txt2String(file);
        System.out.println(string);
        return string;
        // todo 可以进行后续各种操作
    }

    public static String txt2String(File file)
    {
        StringBuilder result = new StringBuilder();
        try{
            // 构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            // 使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
