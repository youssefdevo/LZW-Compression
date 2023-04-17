import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.Integer;

public class Main {
    public static void main(String args[]) {
        System.out.println("1-Compression\n2-Decompression");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        //Compression
        if (option == 1) {
            //read input from file
            String input = "";
            try {
                File Obj = new File("data.txt");
                Scanner Reader = new Scanner(Obj);
                input = Reader.nextLine();
                Reader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error has occurred.");
                e.printStackTrace();
            }

            int index = 65;
            //Dictionary
            HashMap<String, Integer> dic = new HashMap<>();

            //Store ASCII code for alphabet
            for (char c = 'A'; c <= 'Z'; c++)
                dic.put(String.valueOf(c), index++);

            index = 128;
            int maxNum = 0;
            ArrayList<Integer> ans = new ArrayList<Integer>();

            boolean enter = false;
            String search = ""+input.charAt(0);
            for (int i = 1; i < input.length(); i++) {

                //if not found this window
                if (!dic.containsKey(search + input.charAt(i))) {
                    if (i == input.length() - 1)
                        enter = true;
                    //add the tag
                    ans.add(dic.get(search));
                    maxNum = Math.max(dic.get(search),maxNum);
                    // add window to the dictionary
                    dic.put(search + input.charAt(i), index++);

                    //update the window with new symbol to search for
                    search = "" + input.charAt(i);
                }
                else
                    //update the window with add new symbol
                    search = search + input.charAt(i);
            }
            if (!enter){
                ans.add(dic.get(search));
                maxNum = Math.max(dic.get(search),maxNum);
            }


            try {
                FileWriter Writer = new FileWriter("Output.txt");
                Writer.write("Tags: ");
                for (int i :ans)
                    Writer.write(i+" ");

                //Calculate the size in bits
                int count = 0;
                while (maxNum != 0) {
                    count++; maxNum >>= 1;
                }
                int original = input.length()*8,compressed = count * ans.size();
                Writer.write("\n\nThe Original size = "+original+" bits"+ "\nThe Compressed size = "+compressed+" bits");
                Writer.close();
            }
            catch (IOException e) {
                System.out.println("An error has occurred.");
                e.printStackTrace();
            }
            System.out.println("Successfully data compressed into the file.");
        }



        //Decompression
        else {
            ArrayList<Integer>tags = new ArrayList<Integer>();
            try {
                File Obj = new File("data_D.txt");
                Scanner Reader = new Scanner(Obj);
                while (Reader.hasNextLine()) {
                    tags.add(Integer.valueOf(Reader.nextLine()));
                }
                Reader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error has occurred.");
                e.printStackTrace();
            }
            int index = 65;
            HashMap<Integer, String> dic_de = new HashMap<>();
            for (char c = 'A'; c <= 'Z'; c++) {
                dic_de.put(index++, String.valueOf(c));
            }
            index = 128;
            String ans = "";
            String last = "";
            last = dic_de.get(tags.get(0));
            ans=ans+last;
            tags.remove(0);
            for (Integer x:tags) {
                if (!dic_de.containsKey(x)) {
                    dic_de.put(index, last + last.charAt(0));
                }
                String cur = dic_de.get(x);
                ans = ans + cur;
                dic_de.put(index++, last + cur.charAt(0));
                last = cur;
            }
            try {
                FileWriter Writer2 = new FileWriter("OutputDataAfter_Decompressed.txt");
                Writer2.write("Data after Decompressed: " + ans);
                Writer2.close();
            }
            catch (IOException e) {
                System.out.println("An error has occurred.");
                e.printStackTrace();
            }
            System.out.println("Successfully data compressed into the file.");
        }
    }
}








//Slides test case
//compression
//ABAABABBAABAABAAAABABBBBBBBB

//Decompression
/*14
65
66
65
128
128
129
131
134
130
129
66
138
139
138*/