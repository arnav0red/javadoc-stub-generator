import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.OutputStream;

public class mainFile {
    static File file;
    static boolean test = true;
    static String className;
    static ArrayList<varClass> list = new ArrayList<>();
    static ArrayList<String> fieldList = new ArrayList<>();
    static ArrayList<String> constructorList = new ArrayList<>();
    static ArrayList<String> methodList = new ArrayList<>();
    Document document;

    static void tester() throws FileNotFoundException {
        File config = new File("config.txt");
        Scanner scanner = new Scanner(config);
        file = new File("./example javadocs/" + scanner.nextLine());

    }

    static boolean setup() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter filename: ");
        String fileName = scanner.nextLine();
        if (fileName.length() > 4
                && fileName.substring(fileName.length() - 5).equals(".html")) {
            file = new File(fileName);
            return true;
        }


        return false;

    }

    static String extractValue(String valToExtract) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String currLine;
        while (scanner.hasNextLine()) {
            currLine = scanner.nextLine().trim();
            if (currLine.length() >= valToExtract.length() + 2
                    && currLine.subSequence(0, valToExtract.length() + 2)
                            .equals("<" + valToExtract + ">")) {
                return currLine.substring(("<" + valToExtract + ">").length(),
                        currLine.indexOf("</" + valToExtract + ">"));
            }
        }
        return "";

    }

    static void archiveText(String stringVar, String file, boolean append) {
        try {

            FileOutputStream fileOutputStream = new FileOutputStream("./" + file, append);
            for (int i = 0; i < stringVar.length(); i++) {
                fileOutputStream.write(stringVar.charAt(i));
            }

            fileOutputStream.write('\n');


            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    static boolean convertListToClass() {
        for (int i = 0; i < fieldList.size(); i++) {
            if (fieldList.get(i).contains(
                    "<div class=\"member-signature\"><span class=\"modifiers\">")) {
                System.out.println(fieldList.get(i));
            }
        }
        return false;
    }

    static boolean convertFileToList() {
        String currline;

        try {
            className = (extractValue("title"));
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                currline = scanner.nextLine().trim();
                if (currline.equals("<!-- =========== FIELD SUMMARY =========== -->")) {
                    while (!currline
                            .equals("<!-- ========= CONSTRUCTOR DETAIL ======== -->")) {
                        fieldList.add(currline);
                        currline = scanner.nextLine().trim();
                    }
                }
                if (currline.equals("<!-- ========= CONSTRUCTOR DETAIL ======== -->")) {
                    while (!currline
                            .equals("<!-- ============ METHOD DETAIL ========== -->")) {
                        constructorList.add(currline);
                        currline = scanner.nextLine().trim();
                    }
                }
                if (currline.equals("<!-- ============ METHOD DETAIL ========== -->")) {
                    while (!currline
                            .equals("<!-- ========= END OF CLASS DATA ========= -->")) {
                        methodList.add(currline);
                        currline = scanner.nextLine().trim();
                    }
                    break;
                }



            }

            return true;
        } catch (

        Exception e) {
            e.printStackTrace();
            return false;
        }



    }

    public static void main(String[] args) {

        try {
            if (test) {
                tester();
            } else if (!setup()) {
                System.out.println("Invalid filename");
                return;
            }
            if (!convertFileToList()) {
                System.out.println("Invalid javadoc");
                return;
            }
            if (!convertListToClass()) {
                System.out.println("Invalid Elements");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }



    }
}
