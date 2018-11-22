import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Grid grid = new Grid(3, 3);
        grid.printGridInfo();
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the path and file name to generate the Knowledge Base(eg.. \"./kb.pl\") to: ");
        String path = sc.nextLine();
        grid.writeKB(path);
        sc.close();
        System.out.println();
        System.out.println("Knowledge Base was generated successfully to: " + Paths.get(path).toAbsolutePath().normalize().toString());
    }
}
