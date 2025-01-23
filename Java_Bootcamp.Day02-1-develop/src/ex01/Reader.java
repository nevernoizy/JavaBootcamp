import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.*;

public class Reader {
    private static Map<String, Integer> readFiles(String file1, String file2) throws FileNotFoundException {
        Map<String, Integer> words = new HashMap<>();
        Scanner in = new Scanner(new File(file1));
        while (in.hasNext()){
            words.put(in.next(),0);
        }
        Scanner in2 = new Scanner(new File(file2));
        while (in2.hasNext()){
            words.put(in2.next(),0);
        }
        return words;
    }
    private static void vectorFill(Map<String, Integer> words, String filename){
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()){
                String buff = in.next();
                    words.put(buff, words.get(buff)+1);
            }
        } catch(FileNotFoundException ex){
            System.out.println("wrong files");
            System.exit(-1);
        }
    }
    private static void printHelp(int[] array, int size){
        for(int i =0; i < size; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    private static void similarity(Vector<Integer> vector1, Vector<Integer> vector2){
        int numeratorAB = 0;
        double denumeratorA = 0;
        double denumeratorB = 0;
        for(int i = 0; i < vector1.size(); i++){
            numeratorAB += vector1.get(i)*vector2.get(i);
            denumeratorA += vector1.get(i)*vector1.get(i);
            denumeratorB += vector2.get(i)*vector2.get(i);
        }
        double similarityRes = numeratorAB/(Math.sqrt(denumeratorA) * Math.sqrt(denumeratorB));
        System.out.printf("Similarity = %.2f", similarityRes);
    }
    private static void createDictionary(Map<String, Integer> words){
        try(FileWriter writer = new FileWriter("dictionary.txt", false)){
            for(Map.Entry<String, Integer> item : words.entrySet()){
                writer.write(item.getKey());
                writer.append('\n');
            }
        } catch(Exception ex){
            System.out.println("error with writing in dictionary");
            System.exit(-1);
        }
    }
    public static void mainFunc() {
        Scanner in = new Scanner(System.in);
        String filename1 = in.nextLine();
        String filename2 = in.nextLine();
        Map<String, Integer> words = null;
        try {
            words = readFiles(filename1, filename2);
        } catch (FileNotFoundException ex) {
            System.out.println("wrong files");
            System.exit(-1);
        }
        Vector<Integer> vector1 = new Vector<Integer>();
        Vector<Integer> vector2 = new Vector<Integer>();
        Map<String, Integer> words1 = new HashMap<>(words);
        vectorFill(words, filename1);
        vectorFill(words1, filename2);
        for(Map.Entry<String, Integer> item : words.entrySet()){
            vector1.add(item.getValue());
        }
        for(Map.Entry<String, Integer> item : words1.entrySet()){
            vector2.add(item.getValue());
        }
        similarity(vector1, vector2);
        createDictionary(words);
    }
}
