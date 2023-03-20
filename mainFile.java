import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

        } catch (IndexOutOfBoundsException e) {
            desc = "";
        }
        className = new varClass("class",
                document.selectXpath(
                        "//div[@class='type-signature']/span[@class='modifiers']").text(),
                document.title(), desc);

        {
            Elements fieldElements = document.selectXpath(
                    "//section[@class='field-details']/*/*/section[@class='detail']");
            for (int i = 0; i < fieldElements.size(); i++) {
                String description;
                try {
                    description = document.selectXpath(
                            "//section[@class='field-details']/*/*/*/div[@class='block']")
                            .get(i).text();

                } catch (IndexOutOfBoundsException e) {
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
        }
        {
            //constructors
            Elements constructorElements = document.selectXpath(
                    "//section[@class='constructor-details']/*/*/section[@class='detail']");
            for (int i = 0; i < constructorElements.size(); i++) {
                String description;
                ArrayList<String[]> tempTagList = new ArrayList<>();
                String[][] convertToString = null;
                String constructorVarName = document.selectXpath(
                        "//section[@class='method-details']/*/*/*/*/span[@class='element-name']")
                        .get(i).text();

                try {
                    description = document.selectXpath(
                            "//section[@class='constructor-details']/*/*/*/div[@class='block']")
                            .get(i).text();

                } catch (IndexOutOfBoundsException e) {
                    description = "";
                }
                try {
                    String paraType = "";
                    for (Element j : constructorElements.get(i)
                            .getElementsByClass("notes").get(0).children()) {
                        //tempTagList.add(j.text());
                        //System.out.println("SIZE "+j.childrenSize());

                        if (j.tagName().equals("dt")) {
                            paraType = j.text();

                            continue;
                        }
                        if (j.tagName().equals("dd")) {
                            tempTagList.add(new String[] {paraType, j.text() + "\n"});

                        }

                    }



                } catch (IndexOutOfBoundsException e) {
                    tempTagList = null;
                }
                if (tempTagList != null) {
                    convertToString = new String[tempTagList.size()][2];
                    for (int x = 0; x < tempTagList.size(); x++) {
                        convertToString[x][0] = tempTagList.get(x)[0];
                        convertToString[x][1] = tempTagList.get(x)[1];

                    }
                }
                constructorList.add(new varClass("constructor", document.selectXpath(
                        "//section[@class='constructor-details']/*/*/*/*/span[@class='modifiers']")
                        .get(i).text(), constructorVarName, description,
                        convertToString));
            }
        }
        {
            //methods
            Elements methodElements = document.selectXpath(
                    "//section[@class='method-details']/*/*/section[@class='detail']");

            for (int i = 0; i < methodElements.size(); i++) {
                ArrayList<String[]> tempTagList = new ArrayList<>();
                String description;
                String[][] convertToString = null;
                String methodVarName = document.selectXpath(
                        "//section[@class='method-details']/*/*/*/*/span[@class='element-name']")
                        .get(i).text();
                try {
                    description = document.selectXpath(
                            "//section[@class='method-details']/*/*/*/div[@class='block']")
                            .get(i).text();

                } catch (IndexOutOfBoundsException e) {
                    description = "";
                }
                try {
                    String paraType = "";
                    for (Element j : methodElements.get(i).getElementsByClass("notes")
                            .get(0).children()) {

                        if (j.tagName().equals("dt")) {
                            paraType = j.text();

                            continue;
                        }
                        if (j.tagName().equals("dd")) {
                            tempTagList.add(new String[] {paraType, j.text() + "\n"});

                        }

                    }



                } catch (IndexOutOfBoundsException e) {
                    tempTagList = null;
                }
                if (tempTagList != null) {
                    convertToString = new String[tempTagList.size()][2];
                    for (int x = 0; x < tempTagList.size(); x++) {
                        convertToString[x][0] = tempTagList.get(x)[0];
                        convertToString[x][1] = tempTagList.get(x)[1];

                    }
                }
                methodList.add(new varClass("method", document.selectXpath(
                        "//section[@class='method-details']/*/*/*/*/span[@class='modifiers']")
                        .get(i).text(), methodVarName, description, convertToString));
            }

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

            archiveText(constructorList.toString(), "test.txt", false);


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }



    }
}
