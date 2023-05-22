package model;

import jdk.dynalink.beans.StaticClass;

import static java.time.InstantSource.system;
import static model.TxtController.read;

//棋盘大小   （棋子名称 owner 数量 当前位置）* 16  谁的turn   rounds  行棋步骤
//9 7 Lion BLUE 1 8 6 Lion RED 1 0 0 blue 2 0 0 1 0
public class Save_and_Load
{
    public static int Chessboard_row;
    public static int Chessboard_col;
    public static String [] name = new String[20];
    public static String [] owner = new String[20];
    public static int [] num = new int[20];
    public static int [] row = new int[20];
    public static int [] col = new int[20];
    public static String temp;
    public static String warning;
    public static String turn;
    public static int rounds;
    public static String [][][] step = new String[1000][2][2];
    public static String[] archives= new String[10000];
    public static int archive_num=0;
    public static int which=-1;
    public static void addarchive_num()
    {
        archive_num++;
    }
    public static String getname(int x)
    {
        return name[x];
    }
    public static String getowner(int x)
    {
        return owner[x];
    }
    public static int getnum(int x)
    {
        return num[x];
    }
    public static int getrow(int x)
    {
        return row[x];
    }
    public static int getcol(int x)
    {
        return col[x];
    }
    public static int getrank(String name)
    {
        if(name=="Elephant")
        {
            return 8;
        }
        else if(name=="Lion")
        {
            return 7;
        }
        else if (name == "Tiger")
        {
            return 6;
        }
        else if(name =="Leopard")
        {
            return 5;
        }
        else if(name =="Wolf")
        {
            return 4;
        }
        else if(name =="Dog")
        {
            return 3;
        }
        else if(name =="Cat")
        {
            return 2;
        }
        else if(name =="Mouse")
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    public static void work(String path)
    {
        temp=read(path);
        Chessboard_row=Character.getNumericValue(temp.charAt(2));
        Chessboard_col=Character.getNumericValue(temp.charAt(4));
//        System.out.println(Chessboard_row);
//        System.out.println(Chessboard_col);
        if(Chessboard_row!=9)
        {
            warning="102";
        }
        else if(Chessboard_col!=7)
        {
            warning="102";
        }
        else
        {
            which=-1;
            int i=6;
            int j=0;
            for(;i<temp.length();)
            {
                if(temp.charAt(i)=='b'||temp.charAt(i)=='r')
                {
                    break;
                }
                if(temp.charAt(i)>='A'&&temp.charAt(i)<='Z')
                {
                    which++;
                    j=i+1;
                    while(temp.charAt(j)>='a'&&temp.charAt(j)<='z')
                    {
                        j++;
                    }
                    name[which]=temp.substring(i,j);
                    i=j+1;
                    j=i+1;
                    while(temp.charAt(j)>='A'&&temp.charAt(j)<='Z')
                    {
                        j++;
                    }
                    owner[which]=temp.substring(i,j);
                    i=j+1;
                    num[which]=Character.getNumericValue(temp.charAt(i));
                    i+=2;
                    row[which]=Character.getNumericValue(temp.charAt(i));
                    i+=2;
                    col[which]=Character.getNumericValue(temp.charAt(i));
                    i+=2;
                }
            }
            if(temp.charAt(i)=='b')
            {
                turn="blue";
                i+=5;
            }
            else
            {
                turn="red";
                i+=4;
            }
            j=i;
            while(temp.charAt(j)>='0'&&temp.charAt(j)<='9')
            {
                j++;
            }
            rounds=Integer.parseInt(temp.substring(i,j));
            j++;
            for(int t=1;t<rounds;t++)
            {
                step[t][0][0]=temp.substring(j+(t-1)*8,j+(t-1)*8+1);
                step[t][0][1]=temp.substring(j+2+(t-1)*8,j+2+(t-1)*8+1);
                step[t][1][0]=temp.substring(j+4+(t-1)*8,j+4+(t-1)*8+1);
                step[t][1][1]=temp.substring(j+6+(t-1)*8,j+6+(t-1)*8+1);
            }
        }

    }


}
