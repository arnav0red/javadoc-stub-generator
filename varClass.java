import java.util.ArrayList;
import java.util.Arrays;

public class varClass {
    //class or field or constructor or method
    public String keyword;

    //visibility 
    public String modifier;

    //type
    public String type;

    //name of variable
    public String varName;

    //all the parameters given
    public String param;

    //description
    public String description;

    //stores a list of all the available types
    public ArrayList<String> tagType = new ArrayList<>();

    //stores a list of the description of all avaliable types
    public ArrayList<String> tagDescription = new ArrayList<>();


    /**
     * tags: [ [tagType,tagDescription],[tagType,tagDescription] ]
     * 
     * @param keyword
     * @param modifier
     * @param varName
     * @param description
     * @param tags
     */
    varClass(String keyword, String modifier, String type, String varName, String param,
            String description, String[]... tags) {
        this.keyword = keyword;
        this.modifier = modifier;
        this.type = type;
        this.varName = varName;
        this.param = param;
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
                    case "See Also:":
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
        toReturn += "Type: " + type + "\n";

        toReturn += "VarName: " + varName + "\n";
        toReturn += "Param: " + param + "\n";

        toReturn += "Description: " + description + "\n";
        for (int i = 0; i < tagType.size(); i++) {
            toReturn += "Tag: @" + tagType.get(i) + " " + tagDescription.get(i);
        }
        return toReturn;
    }
}
