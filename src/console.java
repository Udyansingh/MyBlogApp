import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import org.bson.Document;
import org.bson.types.Binary;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

public class console {
    private static final String databaseName = "myblogapp";
    private static final com.mongodb.client.MongoClient mongoClient = connect.getMongoClient();
    private static final com.mongodb.client.MongoDatabase database = mongoClient.getDatabase(databaseName);

    public static String[] blogNames(String collectionName) {
        MongoCollection<Document> collections = database.getCollection(collectionName);
        String[] blogNames = new String[(int) collections.countDocuments()];
        int j = 0;
        for (Document documentl : collections.find()) {
            blogNames[j] = (String) documentl.get("fileName");
            j++;
        }
        return blogNames;
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String userName;
        System.out.println("\n+----------------------------------------------------------------+");
        System.out.println("|                 Enter Your UserName (In UpperCase)             |");
        System.out.println("+----------------------------------------------------------------+");
        System.out.print("-> ");
        userName = input.nextLine();
        try {
            database.createCollection(userName);
            System.out.println(
                    "\n| -> WELCOME " + userName.toUpperCase() + " <- |\n");
        } catch (Exception exception) {
            System.out.println(
                    "\n| -> WELCOME " + userName.toUpperCase() + " <- |\n");
        }
        MongoCollection<Document> collections = database.getCollection(userName);
        int option1 = 0;
        while (option1 != 5) {
            System.out.println("\n+----------------------------------------------------------------+");
            System.out.println("|                                                                |");
            System.out.println("|                     WELCOME TO MY BLOG APP                     |");
            System.out.println("|                                                                |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                                                                |");
            System.out.println("| -> PLEASE SELECT AN OPTION:                                    |");
            System.out.println("|                                                                |");
            System.out.println("| 1. CREATE BLOG                                                 |");
            System.out.println("|                                                                |");
            System.out.println("| 2. READ BLOG                                                   |");
            System.out.println("|                                                                |");
            System.out.println("| 3. UPDATE BLOG                                                 |");
            System.out.println("|                                                                |");
            System.out.println("| 4. DELETE BLOG                                                 |");
            System.out.println("|                                                                |");
            System.out.println("| 5. EXIT                                                        |");
            System.out.println("|                                                                |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.print("\n-> ");
            String path = "";
            if (input.hasNextInt()) {
                option1 = input.nextInt();
            } else {
                option1 = 0;
                input.next();
            }
            switch (option1) {
                case 1:
                    input.nextLine();
                    System.out.println("\n< Enter Your Blog Name >\n");
                    System.out.print("-> ");
                    String fileName = input.nextLine();
                    File file = new File(path);
                    do {
                        System.out.println("\n< Enter path to your Blog file >\n");
                        System.out.print("-> ");
                        path = input.nextLine();
                        file = new File(path);
                        if (!file.exists() || file.isDirectory()) {
                            System.out.println("\nInvalid Path or File Does not Exsist !");
                        }
                    } while (!file.exists());
                    byte[] fileData = Files.readAllBytes(file.toPath());
                    Document document = new Document();
                    document.put("fileName", fileName);
                    document.put("fileData", fileData);
                    int option2 = 0;
                    do {
                        System.out.println("\n+--------------------------------+");
                        System.out.println("|           UPLOAD BLOG          |");
                        System.out.println("| 1. YES                         |");
                        System.out.println("| 2. No                          |");
                        System.out.println("+--------------------------------+");
                        System.out.print("\n-> ");
                        if (input.hasNextInt()) {
                            option2 = input.nextInt();
                            if (option2 != 1 && option2 != 2) {
                                System.out.println("\nInvalid Input !");
                            }
                        } else {
                            System.out.println("\nInvalid Input !");
                            input.next();
                        }
                    } while (option2 != 1 && option2 != 2);
                    switch (option2) {
                        case 1:
                            collections.insertOne(document);
                            System.out.println("\n...BLOG UPLOADED !");
                            break;
                        case 2:
                            break;
                    }
                    break;
                case 2:
                    if (collections.countDocuments() == 0) {
                        System.out.println("\nPlease Create a Blog First !");
                    } else {
                        String[] blogNamesF = blogNames(userName);
                        System.out.println("+----------------------------------------------------------------+");
                        System.out.println("|                           YOUR BLOGS                           |");
                        for (int i = 0; i < blogNamesF.length; i++) {
                            System.out.println((i + 1) + " -> " + blogNamesF[i]);
                        }
                        System.out.println("+----------------------------------------------------------------+");
                        int optionF = 0;
                        do {
                            System.out.println("\n< Enter the number of the blog you want to READ >");
                            System.out.print("\n-> ");
                            if (input.hasNextInt()) {
                                optionF = input.nextInt();
                                if (optionF > blogNamesF.length || optionF < 1) {
                                    System.out.println("\nInvalid Input !");
                                }
                            } else {
                                System.out.println("\nInvalid Input !");
                                input.next();
                            }
                        } while (optionF > blogNamesF.length || optionF < 1);
                        String reqBlog = blogNamesF[optionF - 1];
                        document = collections.find(Filters.eq("fileName", reqBlog)).first();
                        Binary binary = (Binary) document.get("fileData");
                        fileData = binary.getData();
                        System.out.println("\n--> " + reqBlog + "\n");
                        System.out.println(new String(fileData));
                    }
                    break;
                case 3:
                    if (collections.countDocuments() == 0) {
                        System.out.println("\nPlease Create a Blog First !");
                    } else {
                        String[] blogNamesF = blogNames(userName);
                        System.out.println("+----------------------------------------------------------------+");
                        System.out.println("|                           YOUR BLOGS                           |");
                        for (int i = 0; i < blogNamesF.length; i++) {
                            System.out.println((i + 1) + " -> " + blogNamesF[i]);
                        }
                        System.out.println("+----------------------------------------------------------------+");
                        int optionF = 0;
                        do {
                            System.out.println("\n< Enter the number of the blog you want to UPDATE >");
                            System.out.print("\n-> ");
                            if (input.hasNextInt()) {
                                optionF = input.nextInt();
                                if (optionF > blogNamesF.length || optionF < 1) {
                                    System.out.println("\nInvalid Input !");
                                }
                            } else {
                                System.out.println("\nInvalid Input !");
                                input.next();
                            }
                        } while (optionF > blogNamesF.length || optionF < 1);
                        String updBlog = blogNamesF[optionF - 1];
                        File file_update = new File(path);
                        input.nextLine();
                        do {
                            System.out.println("\n< Enter path to your Updated Blog file >\n");
                            System.out.print("-> ");
                            path = input.nextLine();
                            file_update = new File(path);
                            if (!file_update.exists() || file_update.isDirectory()) {
                                System.out.println("\nInvalid Path or File Does not Exsist !");
                            }
                        } while (!file_update.exists());
                        fileData = Files.readAllBytes(file_update.toPath());
                        document = new Document();
                        document.put("fileName", updBlog);
                        document.put("fileData", fileData);
                        int option3 = 0;
                        do {
                            System.out.println("\n+--------------------------------+");
                            System.out.println("|           UPDATE BLOG          |");
                            System.out.println("| 1. YES                         |");
                            System.out.println("| 2. No                          |");
                            System.out.println("+--------------------------------+");
                            System.out.print("\n-> ");
                            if (input.hasNextInt()) {
                                option3 = input.nextInt();
                                if (option3 != 1 && option3 != 2) {
                                    System.out.println("\nInvalid Input !");
                                }
                            } else {
                                System.out.println("\nInvalid Input !");
                                input.next();
                            }
                        } while (option3 != 1 && option3 != 2);
                        switch (option3) {
                            case 1:
                                collections.replaceOne(
                                        new Document("fileName", updBlog),
                                        document,
                                        new ReplaceOptions().upsert(false));
                                System.out.println("\n...BLOG UPDATED !");
                                break;
                            case 2:
                                break;
                        }
                    }
                    break;
                case 4:
                    if (collections.countDocuments() == 0) {
                        System.out.println("\nPlease Create a Blog First !");
                    } else {
                        String[] blogNamesF = blogNames(userName);
                        System.out.println("+----------------------------------------------------------------+");
                        System.out.println("|                           YOUR BLOGS                           |");
                        for (int i = 0; i < blogNamesF.length; i++) {
                            System.out.println((i + 1) + " -> " + blogNamesF[i]);
                        }
                        System.out.println("+----------------------------------------------------------------+");
                        int optionF = 0;
                        do {
                            System.out.println("\n< Enter the number of the blog you want to DELETE >");
                            System.out.print("\n-> ");
                            if (input.hasNextInt()) {
                                optionF = input.nextInt();
                                if (optionF > blogNamesF.length || optionF < 1) {
                                    System.out.println("\nInvalid Input !");
                                }
                            } else {
                                System.out.println("\nInvalid Input !");
                                input.next();
                            }
                        } while (optionF > blogNamesF.length || optionF < 1);
                        String delBlog = blogNamesF[optionF - 1];
                        document = collections.find(Filters.eq("fileName", delBlog)).first();
                        int option3 = 0;
                        do {
                            System.out.println("\n+--------------------------------+");
                            System.out.println("|           DELETE BLOG          |");
                            System.out.println("| 1. YES                         |");
                            System.out.println("| 2. No                          |");
                            System.out.println("+--------------------------------+");
                            System.out.print("\n-> ");
                            if (input.hasNextInt()) {
                                option3 = input.nextInt();
                                if (option3 != 1 && option3 != 2) {
                                    System.out.println("\nInvalid Input !");
                                }
                            } else {
                                System.out.println("\nInvalid Input !");
                                input.next();
                            }
                        } while (option3 != 1 && option3 != 2);
                        switch (option3) {
                            case 1:
                                collections.deleteOne(document);
                                System.out.println("\n...BLOG DELETED !");
                                break;
                            case 2:
                                break;
                        }
                    }
                    break;
                case 0:
                    System.out.println("\nInvalid Input !");
                    break;
            }
        }
        System.out.println("\nYou are Logged Out !\n");
        mongoClient.close();
        input.close();
    }
}
