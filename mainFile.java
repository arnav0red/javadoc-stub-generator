import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.OutputStream;

public class mainFile {
    static File file;
    static String fileString = "";
    static boolean test = true;
    static varClass className;
    static ArrayList<varClass> fieldList = new ArrayList<>();
    static ArrayList<varClass> constructorList = new ArrayList<>();
    static ArrayList<varClass> methodList = new ArrayList<>();
    static Document document;

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

    static void setup2() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            fileString += scanner.nextLine();
        }
        document = Jsoup.parse(fileString);
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

    static void convertToClass() {
        String desc;
        try {
            desc = document
                    .selectXpath(
                            "//section[@class='class-description']/div[@class='block']")
                    .text();

        } catch (Exception e) {
            desc = "";
        }
        className = new varClass("class",
                document.selectXpath(
                        "//div[@class='type-signature']/span[@class='modifiers']").text(),
                document.title(), desc);


        Elements fieldElements = document.selectXpath(
                "//section[@class='field-details']/*/*/section[@class='detail']");
        for (int i = 0; i < fieldElements.size(); i++) {
            String description;
            try {
                description = document.selectXpath(
                        "//section[@class='field-details']/*/*/*/div[@class='block']")
                        .get(i).text();

            } catch (Exception e) {
                description = "";
            }
            fieldList.add(new varClass("field", document.selectXpath(
                    "//section[@class='field-details']/*/*/*/*/span[@class='modifiers']")
                    .get(i).text(),
                    document.selectXpath(
                            "//section[@class='field-details']/*/*/*/*/span[@class='element-name']")
                            .get(i).text(),
                    description

            ));

        }

        //constructors
        Elements constructorElements = document.selectXpath(
                "//section[@class='constructor-details']/*/*/section[@class='detail']");
        for (int i = 0; i < constructorElements.size(); i++) {
            String description;
            try {
                description = document.selectXpath(
                        "//section[@class='constructor-details']/*/*/*/div[@class='block']")
                        .get(i).text();

            } catch (Exception e) {
                description = "";
            }
            constructorList.add(new varClass("constructor", document.selectXpath(
                    "//section[@class='constructor-details']/*/*/*/*/span[@class='modifiers']")
                    .get(i).text(),
                    document.selectXpath(
                            "//section[@class='constructor-details']/*/*/*/*/span[@class='element-name']")
                            .get(i).text(),
                    description));
        }

        //methods
        Elements methodElements = document.selectXpath(
                "//section[@class='method-details']/*/*/section[@class='detail']");
        ArrayList<String[]> tempTagList = new ArrayList<>();
        for (int i = 0; i < methodElements.size(); i++) {
            String description;
            try {
                description = document.selectXpath(
                        "//section[@class='method-details']/*/*/*/div[@class='block']")
                        .get(i).text();

            } catch (Exception e) {
                description = "";
            }
            for (Element j : methodElements.get(i).getElementsByClass("notes")) {
                //tempTagList.add(j.text());
                for (int k = 0; k < j.parent().getElementsByTag("dd").size(); k++) {
                    System.out.println(document.selectXpath(
                            "//section[@class='method-details']/*/*/*/*/span[@class='element-name']")
                            .get(i).text() + " "
                            + j.parent().getElementsByTag("dd").get(k).text());
                }
            }
            methodList.add(new varClass("method", document.selectXpath(
                    "//section[@class='method-details']/*/*/*/*/span[@class='modifiers']")
                    .get(i).text(),
                    document.selectXpath(
                            "//section[@class='method-details']/*/*/*/*/span[@class='element-name']")
                            .get(i).text(),
                    description));
        }


    }

    static void createJavaFile() {

    }

    public static void main(String[] args) {

        try {
            if (test) {
                tester();
            } else if (!setup()) {
                System.out.println("Invalid filename");
                return;
            }
            setup2();
            convertToClass();
            archiveText(methodList.toString(), "test.txt", false);
            //System.out.println(fieldList.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }



    }
}
