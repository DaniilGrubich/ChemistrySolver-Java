import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    private String FileAddress;

    public ReadFile(String fileAddress)
    {
        FileAddress = fileAddress;
    }

    public ArrayList<String> Read() throws IOException
    {
        Scanner file = new Scanner(new File(FileAddress));
        ArrayList<String> line = new ArrayList<String>();


        while (file.hasNext())
        {
            line.add(file.nextLine());
        }
        file.close();

        return line;
    }
}
