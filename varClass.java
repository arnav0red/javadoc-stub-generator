import java.util.ArrayList;

/**
 * This class keeps track of all the values connected to a class/field/constructor/method
 */
public class varClass {
    /**
     * Whether the given instance is an example of class or field or constructor or method
     */
    public String keyword;

    /**
     * visibility of instance
     */
    public String modifier;

    /**
     * type of instance
     */
    public String type;

    /**
     * name of variable
     */
    public String varName;

    /**
     * all the parameters given / extends for classes
     */
    public String param;

    /**
     * description written in the javadoc
     */
    public String description;

    /**
     * stores a list of given tag types
     */
    public ArrayList<String> tagType = new ArrayList<>();

    /**
     * stores a list of the description of given tags
     */
    public ArrayList<String> tagDescription = new ArrayList<>();


    /**
     * * Assigns the values to the corresponding variables
     * 
     * @param keyword     class/field/constructor/method
     * @param modifier    visibility (Eg: public static)
     * @param type        type of variable (Eg: int)
     * @param varName     name of variable (Eg: numObjects)
     * @param param       assigned parameters/ extends class (Eg: (int a, int b), extends
     *                    Objects)
     * @param description attached javadoc description
     * @param tags        attached tags (Eg: '@param)
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
                    case "Since:":
                        tagType.add("since");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "Throws:":
                        tagType.add("throws");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "Overrides:":
                        tagType.add("override");
                        tagDescription.add(tags[i][1]);
                        continue;
                    case "Specified by:":
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
