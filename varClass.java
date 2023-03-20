import java.util.ArrayList;
import java.util.Arrays;

public class varClass {
    //class or field or constructor or method
    String keyword;

    //visibility and type
    String modifier;

    //name of variable
    String varName;

    //description
    String description;

    //stores a list of all the available types
    ArrayList<String> tagType = new ArrayList<>();

    //stores a list of the description of all avaliable types
    ArrayList<String> tagDescription = new ArrayList<>();


    /**
     * tags: [ [tagType,tagDescription],[tagType,tagDescription] ]
     * 
     * @param keyword
     * @param modifier
     * @param varName
     * @param description
     * @param tags
     */
    varClass(String keyword, String modifier, String varName, String description,
            String[]... tags) {
        this.keyword = keyword;
        this.modifier = modifier;
        this.varName = varName;
        this.description = description;
        if (tags != null) {
            for (int i = 0; i < tags.length; i++) {
                switch (tags[i][0]) {

                    case "author":
                        tagType.add("author");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "exception":
                        tagType.add("exception");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "Parameters:":
                        tagType.add("param");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "Returns:":
                        tagType.add("return");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "see":
                        tagType.add("see");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "serial":
                        tagType.add("serial");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "serialData":
                        tagType.add("serialData");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "serialField":
                        tagType.add("serialField");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "since":
                        tagType.add("since");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "Throws:":
                        tagType.add("throws");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "version":
                        tagType.add("version");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "Overrides:":
                        tagType.add("override");
                        tagDescription.add(tags[i][1]);
                        continue;


                }
            }
        }
    }


    @Override
    public String toString() {
        String toReturn = "";
        toReturn += "Keyword: " + keyword + "\n";
        toReturn += "Modifier: " + modifier + "\n";
        toReturn += "VarName: " + varName + "\n";
        toReturn += "Description: " + description + "\n";
        for (int i = 0; i < tagType.size(); i++) {
            toReturn += "Tag: @" + tagType.get(i) + " " + tagDescription.get(i);
        }
        return toReturn;
    }
}
