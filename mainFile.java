import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


/**
 * This class defines the conversion of a java file to a javadoc
 */
public class mainFile {
    /**
     * Defines the file to be read
     */
    static File file;

    /**
     * Stores class names data
     */
    static varClass className;
    /**
     * Stores fields data
     */
    static ArrayList<varClass> fieldList = new ArrayList<>();
    /**
     * Stores constructors data
     */
    static ArrayList<varClass> constructorList = new ArrayList<>();
    /**
     * Stores methods data
     */
    static ArrayList<varClass> methodList = new ArrayList<>();
    /**
     * fileString is converted to a Jsoup element
     */
    static Document document;

    /**
     * Used to test certain files repeteadly
     * 
     * @param scan Scanner instance used to check filename
     * @throws FileNotFoundException throws exception if config.txt doesn't exist
     */
    static boolean tester(Scanner scan) {
        try {
            File config = new File("config.txt");
            scan = new Scanner(config);
            if (scan.nextLine().equals("true")) {
                file = new File("./test/" + scan.nextLine().trim());
                return true;
            }
        } catch (FileNotFoundException e) {
        }
        return false;
    }

    /**
     * Takes in user input of file name
     * 
     * @param scan Scanner instance used to check filename
     * @return true, if the method executes
     */
    static boolean setup(Scanner scan) {
        scan = new Scanner(System.in);
        new File("./input_javadoc").mkdir();
        System.out.println("Please enter filename located in input_javadoc folder: ");
        String fileName = scan.nextLine();
        if (fileName.length() > 4
                && fileName.substring(fileName.length() - 5).equals(".html")) {
            file = new File("./input_javadoc/" + fileName);

            return true;
        }

        return false;

    }

    /**
     * converts given file to Jsoup element
     * 
     * @param scan Scanner instance used to parse through file
     * @throws FileNotFoundException throws exception if given file doesn't exist
     */
    static void setup2(Scanner scan) throws FileNotFoundException {
        scan = new Scanner(file);
        String fileString = "";
        while (scan.hasNextLine()) {
            fileString += scan.nextLine();
        }
        document = Jsoup.parse(fileString);

    }

    /**
     * Used to write a given string to a file
     * 
     * @param stringVar
     * @param file
     * @param append
     */
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
        {
            String desc;
            String type = "";

            String param = (document.selectXpath(
                    "//section[@class='class-description']/*/span[@class='extends-implements']")
                    .get(0).text());
            if (param.contains("implements")) {
                param = param.substring(0, param.indexOf("implements")) + " "
                        + param.substring(param.indexOf("implements"), param.length());
            }

            try {
                desc = document.selectXpath(
                        "//section[@class='class-description']/div[@class='block']")
                        .text();

            } catch (IndexOutOfBoundsException e) {
                desc = "";
            }
            className = new varClass("class",
                    document.selectXpath(
                            "//div[@class='type-signature']/span[@class='modifiers']")
                            .text(),
                    type, document.title(), param, desc);
        }
        //fields
        {
            Elements elements = document.selectXpath(
                    "//section[@class='field-details']/*/*/section[@class='detail']");
            for (int i = 0; i < elements.size(); i++) {
                String description;
                ArrayList<String[]> tempTagList = new ArrayList<>();
                String[][] convertToString = null;
                String varName = elements.get(i).children().get(0).text();
                String type = elements.get(i).children().get(1)
                        .getElementsByClass("return-type").text();
                String modifiers = elements.get(i).children().get(1)
                        .getElementsByClass("modifiers").text();

                try {
                    description = elements.get(i).children().get(2)
                            .getElementsByClass("block").text();

                } catch (IndexOutOfBoundsException e) {
                    description = "";
                }

                try {
                    String paraType = "";
                    for (Element j : elements.get(i).getElementsByClass("notes").get(0)
                            .children()) {

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
                fieldList.add(new varClass("field", modifiers, type, varName, "",
                        description, convertToString

                ));

            }
        }
        {
            //constructors
            Elements elements = document.selectXpath(
                    "//section[@class='constructor-details']/*/*/section[@class='detail']");
            for (int i = 0; i < elements.size(); i++) {
                String description = "";
                ArrayList<String[]> tempTagList = new ArrayList<>();
                String[][] convertToString = null;
                String varName = elements.get(i).children().get(0).text();
                String param;
                String modifiers = elements.get(i).children().get(1)
                        .getElementsByClass("modifiers").text();


                try {
                    param = elements.get(i).children().get(1)
                            .getElementsByClass("parameters").text();
                } catch (IndexOutOfBoundsException e) {
                    param = "()";
                }
                if (param.trim().isEmpty()) {
                    param = "()";
                }
                String type = "";


                try {
                    description = elements.get(i).children().get(2)
                            .getElementsByClass("block").text();

                } catch (IndexOutOfBoundsException e) {
                    description = "";
                }
                try {
                    String paraType = "";
                    for (Element j : elements.get(i).getElementsByClass("notes").get(0)
                            .children()) {

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
                constructorList.add(new varClass("constructor", modifiers, type, varName,
                        param, description, convertToString));
            }
        }
        {
            //methods
            Elements elements = document.selectXpath(
                    "//section[@class='method-details']/*/*/section[@class='detail']");

            for (int i = 0; i < elements.size(); i++) {
                ArrayList<String[]> tempTagList = new ArrayList<>();
                String description = "";
                String[][] convertToString = null;
                String varName = elements.get(i).children().get(0).text();
                String type = elements.get(i).children().get(1)
                        .getElementsByClass("return-type").text();
                String param;
                String modifiers = elements.get(i).children().get(1)
                        .getElementsByClass("modifiers").text();
                try {
                    param = elements.get(i).children().get(1)
                            .getElementsByClass("parameters").text();
                } catch (IndexOutOfBoundsException e) {
                    param = "()";
                }
                if (param.trim().isEmpty()) {
                    param = "()";
                }

                try {
                    description = elements.get(i).children().get(2)
                            .getElementsByClass("block").text();

                } catch (IndexOutOfBoundsException e) {
                    description = "";
                }
                try {
                    String paraType = "";
                    for (Element j : elements.get(i).getElementsByClass("notes").get(0)
                            .children()) {

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
                methodList.add(new varClass("method", modifiers, type, varName, param,
                        description, convertToString));
            }

        }
    }

    static void createJavaFile() throws FileNotFoundException {
        new File("./created_javadoc").mkdir();
        FileOutputStream outputStream =
                new FileOutputStream("./created_javadoc/" + className.varName + ".java");
        PrintWriter outputScanner = new PrintWriter(outputStream);
        outputScanner.write("/**\n" + className.description + "*/\n");
        outputScanner.write(className.modifier + " " + className.varName + " ");
        if (!className.param.split(" ")[1].equals("Object")) {
            outputScanner.write(className.param);
        }
        outputScanner.write("{\n");
        for (int i = 0; i < fieldList.size(); i++) {
            outputScanner.write("/**\n" + fieldList.get(i).description + "\n");
            for (int j = 0; j < fieldList.get(i).tagType.size(); j++) {
                outputScanner.write("@" + fieldList.get(i).tagType.get(j) + " "
                        + fieldList.get(i).tagDescription.get(j));
            }
            outputScanner.write("*/\n");
            outputScanner.write(fieldList.get(i).modifier + " " + fieldList.get(i).type
                    + " " + fieldList.get(i).varName + ";\n");
        }
        for (int i = 0; i < constructorList.size(); i++) {
            outputScanner.write("/**\n" + constructorList.get(i).description + "\n");
            for (int j = 0; j < constructorList.get(i).tagType.size(); j++) {
                outputScanner.write("@" + constructorList.get(i).tagType.get(j) + " "
                        + constructorList.get(i).tagDescription.get(j));
            }
            outputScanner.write("*/\n");
            outputScanner.write(constructorList.get(i).modifier + " "
                    + constructorList.get(i).type + " " + constructorList.get(i).varName
                    + constructorList.get(i).param + "{}\n");

        }

        for (int i = 0; i < methodList.size(); i++) {
            boolean doesOverride = false;
            outputScanner.write("/**\n" + methodList.get(i).description + "\n");
            for (int j = 0; j < methodList.get(i).tagType.size(); j++) {
                if (methodList.get(i).tagType.get(j).equals("override")) {
                    doesOverride = true;
                }
                outputScanner.write("@" + methodList.get(i).tagType.get(j) + " "
                        + methodList.get(i).tagDescription.get(j));
            }
            outputScanner.write("*/\n");
            if (doesOverride) {
                outputScanner.write("@Override\n");
            }
            outputScanner.write(methodList.get(i).modifier + " " + methodList.get(i).type
                    + " " + methodList.get(i).varName + methodList.get(i).param + "{}\n");
        }

        outputScanner.write("}");

        outputScanner.close();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {

            if (tester(scan)) {
                System.out.println("Testing");
            } else if (!setup(scan)) {
                System.out.println("Invalid filename\nPress enter to end");
                scan = new Scanner(System.in);
                scan.nextLine();
                scan.close();
                return;
            }
            setup2(scan);
            convertToClass();
            createJavaFile();

            scan = new Scanner(System.in);
            System.out.println("COMPLETED: Javadoc has been created\nPress enter to end");
            scan.nextLine();
            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nPress enter to end");
            scan = new Scanner(System.in);
            scan.nextLine();
            scan.close();
            return;
        }



    }
}
