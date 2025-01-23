import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Reader {
    private static String fileRead(String filename) throws Exception {
        byte[] array = new byte[8];
        FileInputStream fis = new FileInputStream(filename);
        fis.read(array, 0, 8);
        String hex = bytesToHex(array);
        fis.close();
        return hex;
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }

    private static Map<String, String> readSignature() throws Exception {
        Map<String, String> signature = new HashMap<String, String>();
        Scanner in = new Scanner(new File("signature.txt"));
        while (in.hasNextLine()) {
            String format = in.next();
            String formatCode = in.nextLine();
            formatCode = formatCode.replace(" ", "");
            format = format.replace(",", "");
            signature.put(format, formatCode);
        }
        return signature;
    }

    private static String consoleRead(){
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        return str;
    }

    public static void mainfunc(){
        String str = "";
        try(FileWriter writer = new FileWriter("result.txt", false)){
            while (1==1) {
                boolean writed = false;
                str = Reader.consoleRead();
                if(str.equals("42")){
                    break;
                }
                String hex = "";
                try {
                    hex = Reader.fileRead(str);
                } catch(Exception ex){
                    System.out.println("wrong file");
                    continue;
                }
                Map<String, String> signatureMap = Reader.readSignature();
                for (Map.Entry<String, String> item : signatureMap.entrySet()) {
                    if (hex.contains(item.getValue())) {
                        writer.write(item.getKey());
                        writer.append('\n');
                        System.out.println("PROCESSED");
                        writed = true;
                    }
                }
                if(!writed){
                    System.out.println("UNDEFINED");
                }
            }
        } catch(Exception ex){
            System.out.println("some problems");
        }
    }
}
