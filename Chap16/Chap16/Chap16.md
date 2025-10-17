(539)
Saving Objects (and Text)

Objects in programming have two important parts: state **(the data they hold)** and behavior **(the actions they can perform)**. Behavior is defined in the class, while each object keeps its own state. When you want to save an object’s state, like saving a game or storing a chart, you have two choices.

You can manually save each piece of data from the object to a file, which is slow and complicated, or you can use an easier method where the whole object is converted into a format that can be saved **(called flattening or serializing)** and later restored (called inflating or deserializing). Sometimes you have to save data in a special way so other programs can read it, so both methods are important to learn. Since saving and loading data can cause errors, it’s also important to know how to handle exceptions properly.

-----------------------------------------------------------------------------------------
(540)
Capture the beat

When you create something important in your program, like a pattern, you want to save it safely so it’s not lost if your computer crashes. Saving means giving your work a name and choosing where to store it, so you can open it again later without problems. There are different ways to save your program’s data depending on how you want to use it. One common way is called serialization, which saves the whole object so it can be restored exactly as it was. Another way is to write plain-text files with separators (like tabs or commas) so other programs, like spreadsheets or databases, can easily read the data.

Besides these two methods, you can save data in other formats too, such as raw bytes or specific data types like numbers and booleans. The important thing to remember is that saving data usually means writing it to a file or sending it over a network, and loading means reading it back. This chapter focuses on these basic input/output methods for when you aren’t using a full database, helping you understand how to save and restore your program’s information in different ways.

------------------------------------------------------------------------------------------
(541)
Saving state

Imagine you have a game with characters that grow stronger, weaker, and gather weapons over time. Since you don’t want to start over every time you play, you need a way to save and restore your characters’ progress. One option is to save the characters as plain-text files, writing each character’s details on a separate line with commas separating their attributes. This makes the file easy for humans to read but can cause problems when loading, because it’s easy to mix up the order or values, leading to mistakes like assigning the wrong weapon to the wrong character.

The other option is to save the characters using serialization, which writes the objects in a special format that’s harder for humans to read but much safer and easier for the program to restore exactly as they were. Serialized files keep all the details intact and in order, so the game can quickly load your characters without confusion. Although serialized files look like a jumble of symbols to us, this method helps avoid errors when saving and loading complex objects like game characters.

-----------------------------------------------------------------------------------------
(543)
Data moves in streams from one place to another

Java’s I/O system uses two kinds of streams: connection streams and chain streams. Connection streams connect directly to sources like files or network sockets, while chain streams only work when connected to other streams. Usually, you use both together, connection streams handle the actual data flow (like writing bytes to a file), and chain streams handle higher-level tasks (like turning objects into data). 

For example, a FileOutputStream writes bytes to a file, but if you want to save an object, you connect an ObjectOutputStream to it. When you call writeObject() on the ObjectOutputStream, it converts the object into bytes and sends them through the FileOutputStream to be saved.

This design follows good object-oriented principles where each stream does one job: FileOutputStream writes bytes, and ObjectOutputStream converts objects to data. Chaining streams like this gives you a lot of flexibility. You can mix and match streams to handle different tasks, instead of relying on one all, purpose stream. This means you can create custom ways to save and load data, with the data moving step-by-step from objects to bytes and then to files.

-----------------------------------------------------------------------------------------
(544)
What really happens to an object

When an object is created on the heap, it has state, which means the values stored in its instance variables make it unique from other objects of the same class. When you serialize an object, like saving a Foo object with width and height values, these variable values are saved to a file (for example, "foo.ser"). 

To do this, you create a FileOutputStream to connect to the file, then chain an ObjectOutputStream to it and tell it to write the object. Serialization saves the data needed so the object can be restored later with the same state, including information about its class type. This way, when you load the file, the object can be “brought back to life” exactly as it was.

-----------------------------------------------------------------------------------------
(545)
But what exactly IS an object’s state?

An object’s state is all the information it holds that makes it different from other objects, which comes from its instance variables. For example, a Car object might include an Engine object and a list of Tire objects as part of its state, and each of these objects contains their own data as well. When you save the Car, you need to save not only the Car’s own data but also the data inside the Engine and Tires, since they are part of the whole state. This ensures that when you restore the Car later, you get back an object with all the same information as before, even though it will be stored in a different location in memory.

-----------------------------------------------------------------------------------------
(546)
When an object is serialized

When an object is serialized, all the other objects it refers to through its instance variables are also serialized automatically. This includes not just the directly connected objects, but also any objects those objects refer to, and so on. This chain of connected objects is called the object graph, and Java serialization saves the entire graph starting from the original object. 

For example, if you have a Kennel object that holds an array of Dog objects, and each Dog has a Collar and a name (which is a String), all of these related objects are included in the serialization process. This means that when you save the Kennel, you're also saving every object it touches, so that when you restore it later, you get the full structure back exactly as it was.

-----------------------------------------------------------------------------------------
(547)
If you want your class to be serializable, implement Serializable

In Java, the Serializable interface is called a marker interface because it doesn't have any methods you need to implement. Its main job is to "mark" a class as something that can be saved (serialized) to a file or sent over a network. When a class implements Serializable, it tells Java that objects of that class can be turned into a series of bytes and later restored (deserialized). Also, if a class’s parent already implements Serializable, then the child class is automatically serializable too, even if it doesn’t say so explicitly.

In the code example, there's a class called Square that implements Serializable, meaning objects of this class can be saved to a file. The class has two fields: width and height. In the main method, we create a Square object and use FileOutputStream and ObjectOutputStream to write (serialize) the object to a file called foo.ser. The writeObject(mySquare) line is where serialization happens. If the Square class didn’t implement Serializable, this line would throw an error at runtime.

-----------------------------------------------------------------------------------------
(548)
Serialization is all or nothing

Serialization in Java is an "all-or-nothing" process, meaning the entire object graph must be serializable for it to succeed. If any object within the structure isn't serializable, like a Duck object inside a Pond, the whole serialization process fails with an error.

 In the example, Pond implements Serializable, but its Duck instance does not. So when trying to serialize a Pond, it throws a NotSerializableException, because Java can't safely and fully capture the state of the object. This design ensures data integrity; partial or inconsistent serialization (like a Dog missing ears or a collar changing size) could lead to corrupted or unpredictable behavior.

------------------------------------------------------------------------------------------
(549)
Mark an instance variable as transient

If an instance variable in a class can’t or shouldn’t be saved during serialization, because it’s not serializable or holds runtime, specific data like network connections or threads, you can mark it with the transient keyword. This tells Java to skip that variable during the serialization process. 

For example, in a Chat class, marking a String currentID as transient means it won’t be written to the output stream, while other serializable fields like userName will be. Transient is useful when a class includes variables from other classes that don’t implement Serializable, or when saving that data wouldn’t make sense outside the running program

------------------------------------------------------------------------------------------
(557)
Using the serialVersionUID

When a Java object is serialized, it’s stamped with a serialVersionUID, a version identifier derived from the class’s structure. During deserialization, the JVM checks if the serialVersionUID of the serialized object matches the current class's version. If they differ, because the class has changed, deserialization fails with an exception.

To avoid this, developers can explicitly declare a serialVersionUID in the class. This tells the JVM, “this class is still compatible,” even if it has changed. However, this only works if the developer takes responsibility for ensuring backward compatibility, such as handling default values for newly added fields. The serialver tool in the JDK can be used to generate this ID for a class, and including it is a best practice when there's any chance the class will evolve after objects have already been serialized.

------------------------------------------------------------------------------------------
(559)
Writing a String to a Text File

Saving data in Java can be done in different ways, depending on what your program needs. One common method is called serialization, which lets you save and reload entire objects between runs of a program. But sometimes, you need to save your data in a plain, human, readable text file, especially if other programs (like Excel or a non-Java app) need to read it. For example, a Java web program might collect user input and save it to a text file, which someone else can open later in a spreadsheet. In these cases, instead of saving full Java objects, you save text (usually Strings) using a FileWriter.

The code example below shows how to write a simple string to a file using FileWriter. In the main method, a FileWriter object is created for a file named "Foo.txt". The write() method is then used to write the text "hello foo!" to that file. After writing, the close() method is called to make sure the file is properly saved and closed. A try-catch block is used to handle errors (called IOExceptions) in case something goes wrong while writing. Compared to saving objects with ObjectOutputStream, writing plain text with FileWriter is simpler and easier to read.

------------------------------------------------------------------------------------------
(560)
Text file example: e-Flashcards

e-Flashcards are like regular flashcards you use for studying, but on a computer. Each card has a question and an answer to help you memorize facts. In this Java example, you create a small program that lets you make, save, and review flashcards. There are three parts: QuizCardBuilder lets you type in questions and answers and save them to a file, QuizCardPlayer lets you open the file and go through the cards, and QuizCard is a simple class that holds the question and answer for each card.

-----------------------------------------------------------------------------------------
(562)
Part 1

This code defines the QuizCardBuilder class, which creates a graphical user interface (GUI) for building quiz cards. When the program starts, the main method calls the go() method to set up the window. Inside go(), a JFrame is created to serve as the main application window titled "Quiz Card Builder." 

A JPanel is added to this frame, containing labeled text areas for the user to input a question and its corresponding answer. These text areas are scrollable, making it easier to enter longer text. The interface also includes a "Next Card" button that, when clicked, triggers the creation of a new quiz card.

Additionally, a menu bar is created with a "File" menu that contains two options: "New" to clear the current list of quiz cards, and "Save" to save the created cards to a file. These menu items are connected to their respective actions via event listeners. Finally, the frame size is set, and the window is made visible to the user, completing the setup of the quiz card builder interface.

------------------------------------------------------------------------------------------
(563)
Part 2

The createScroller method takes a JTextArea and wraps it inside a JScrollPane, configuring the scroll pane to always show a vertical scrollbar but never show a horizontal one, which improves usability when entering long text. The createTextArea method creates and returns a configured JTextArea with line wrapping and a specified font, ensuring the text area is visually consistent and easy to read.

The nextCard method takes the text from the question and answer areas, creates a new QuizCard object, adds it to the list of cards, and then clears the text fields for the next entry. The saveCard method does a similar thing but also opens a file chooser dialog to let the user pick a location to save all quiz cards to a file by calling the saveFile method. The clearAll method empties the card list and clears the input fields, while clearCard only clears the input fields and sets focus back to the question area for convenience.

Finally, saveFile takes a File object and writes all stored quiz cards into it. Each card's question and answer are written on a single line separated by a slash (/), with each card on a new line. This method handles IOExceptions gracefully by printing an error message if the file cannot be written. Together, these methods provide the essential logic for creating, managing, and saving quiz cards in the application.

------------------------------------------------------------------------------------------
(564)
The java.io.File class

The java.io.File class lets you work with file and folder names on your computer, but it doesn’t let you read or write the file’s contents. Think of a File like an address that shows where the file is stored, not the actual file itself. Even though newer ways to handle files exist, you’ll still see File used a lot. It helps you check if a file or folder exists, create new folders, list files inside a folder, or delete files. To actually open and read or write files, you need other classes that use the File object to know where the file is.

-----------------------------------------------------------------------------------------
(565)
The beauty of buffers

When writing data to a file, it’s much more efficient to use a buffer, which is like a shopping cart that holds many items before you carry them all at once. Without a buffer, every time you write something to a file, it would be like carrying one item to the car at a time, slow and tiring. The BufferedWriter class helps by storing what you want to write in memory until the buffer is full, then it sends all that data to the file at once. This reduces the number of times the program has to access the disk, which is much slower than working with data in memory.

In Java, you create a BufferedWriter by chaining it to a FileWriter, like this: BufferedWriter writer = new BufferedWriter(new FileWriter(aFile));. You mainly use the BufferedWriter to write data because it controls when the data is actually sent to the file. If you want to send the data before the buffer is full, you can call writer.flush() to force it to write immediately. When you’re done, closing the BufferedWriter will also close the FileWriter automatically, so you don’t need to worry about closing both separately.

-----------------------------------------------------------------------------------------
(566)
Reading from a text file

Reading text from a file in Java is straightforward. You use a File object to represent the file's location, a FileReader to connect to the file, and a BufferedReader to make reading more efficient by using a buffer (it only reads from the file when the buffer is empty). The usual pattern for reading lines from a file is to use a while loop that keeps reading until there’s nothing left, in Java that means the readLine() method returns null. This is the standard way to read most types of text data, one line at a time.

In the example, the program first creates a File pointing to "MyText.txt", then connects that to a FileReader, and chains a BufferedReader to it for faster access. Inside the loop, readLine() reads one line from the file and stores it in the line variable. As long as line is not null, it gets printed out. Once all lines are read, the loop ends, and the reader is closed. This setup reads the entire file, line by line.

------------------------------------------------------------------------------------------
(568)
Part 1

In the go() method, the program sets up the main window (a JFrame) for a simple quiz card player. It creates a panel to hold components, adds a large, non-editable text area (JTextArea) for displaying questions or answers, and places it inside a scroll pane so the user can scroll through the text. A "Show Question" button is added, which will trigger the next quiz card when clicked. The method also creates a menu bar with a "File" menu and a "Load card set" option that lets the user load a set of quiz cards from a file. Finally, it adds everything to the window, sets the size, and makes the window visible.

------------------------------------------------------------------------------------------
(569)
Part 2

The nextCard() method controls what happens when the user clicks the button. If the answer should be shown (because the question was already displayed), it updates the text area to show the answer and changes the button label to "Next Card." If the answer was just shown, it moves on to the next card by checking if there are more cards left. If there are, it displays the next question; if not, it shows a message saying that was the last card and disables the button so the user can't click it anymore. The showNextCard() method handles the actual displaying of the next question from the list.

The open() method allows the user to choose a file containing quiz cards. It opens a file chooser dialog, and once a file is selected, it calls loadFile(). This method reads each line of the file, where each line represents one quiz card in the format “question/answer.” For every line, it calls makeCard() to split the line into a question and answer and create a QuizCard object. These cards are stored in a list. After the file is fully read, it automatically shows the first question to get the quiz started.

------------------------------------------------------------------------------------------
(570)
Parsing with String split()

When you read quiz cards from a file, each line contains both the question and the answer, separated by a forward slash (/). To separate them in Java, you can use the split() method from the String class. This method breaks a string into parts wherever it finds the character you tell it to use, in this case, the /. 

For example, the string "What is blue + yellow?/green" would be split into two parts: "What is blue + yellow?" and "green". The result is stored in a String array, so you can access each part individually. This is how the program knows what part of the line is the question and what part is the answer.

-------------------------------------------------------------------------------------------
(572)
NIO.2 and the java.nio.file package

The java.nio.file package is used in Java to work with files and folders more easily. A Path object represents the location of a file or directory on your computer. You create one using Paths.get(), which takes the file name and path as arguments. This path can point to a file in the same folder or in a subfolder. The Path is like a label showing where the file is, but it doesn’t give access to the file’s content.

To actually write to a file, you use Files.newBufferedWriter() and pass it a Path object. This gives you a BufferedWriter, which is used to write text into the file efficiently. For example, you can create a writer like this:

BufferedWriter writer = Files.newBufferedWriter(myPath);
When you're done writing, you should close the writer to save everything properly. These classes (Path, Paths, and Files) are all you need to read or write simple text files in a modern and clean way.

-----------------------------------------------------------------------------------------
(573)
Path, Paths, and Files (messing with directories)

In Java, you can create and organize folders (called directories) and move files around—not just manually using your computer, but also using code. This can be useful if you're building something like an installer that automatically sets up the right folder structure for your app. While working with files and directories in Java can get complicated (you need to understand file paths, permissions, and more), it's okay to start with some simple examples to understand the basics.

The program uses Java's Path and Files classes to do the work. First, it defines the paths for the folders and files. Then, it creates the folders using Files.createDirectory(). Finally, it moves two files, MyApp.class and MyMedia.jpeg, into their new locations using Files.move(). If anything goes wrong (like if a folder already exists), the program will print out an error message. This gives you a feel for how Java can manage files and directories behind the scenes.

----------------------------------------------------------------------------------------
(574)
Part 1
Finally, a closer look at finally

In Java, a finally block is used to ensure that important cleanup code—like closing files or network connections, runs no matter what happens in the try or catch blocks, even if an exception is thrown. In the provided example, a method attempts to save a list of QuizCard objects to a file by writing each card’s question and answer using a BufferedWriter. 

However, multiple parts of this process can fail: creating the FileWriter, writing each line, or closing the writer itself. If any of these throw an exception, the program jumps to the catch block and may skip closing the writer, potentially causing a resource leak. 

To prevent that, the writer.close() call should be placed inside a finally block, which will always execute, ensuring that the file is properly closed whether or not an exception occurs

----------------------------------------------------------------------------------------
(575)
Part 2

This finally block ensures that the writer is closed no matter what happens in the try block. It protects the program from crashing and provides a message if the writer fails to close properly, ensuring that resources are released safely and cleanly.

----------------------------------------------------------------------------------------
(576)
The try-with-resources (TWR), statement

What is try-with-resources?
--------------------------------
The try-with-resources statement, introduced in Java 7, is a cleaner and safer way to manage resources such as files, sockets, or database connections. It automatically closes any resources declared in the parentheses of the try statement when the block is exited, regardless of whether an exception was thrown. This eliminates the need to explicitly close resources in a finally block and reduces the risk of resource leaks caused by forgotten or improperly handled close() calls.

Old Style – try-catch-finally
--------------------------------
In the older style of Java I/O handling, resources like BufferedWriter had to be declared outside the try block, and then explicitly closed inside a finally block. This approach requires more code and is error-prone, especially because even the call to close() can throw an exception, which means you need another try-catch inside the finally block. In the old version of the saveFile method, the BufferedWriter is first set to null, then initialized inside the try, and finally closed in the finally block with another nested try-catch, just to safely handle potential closing errors.

New Style – try-with-resources and Key Differences
---------------------------------------------------
With the try-with-resources approach, the BufferedWriter is declared and initialized directly in the try parentheses. This means the JVM will automatically call close() on it once the try block finishes, no need for a finally block or a nested try-catch. This not only simplifies the code but also makes it more robust and readable. The functionality remains the same, writing quiz card data to a file, but the newer style handles resource cleanup for you, making your code cleaner and less error-prone.

----------------------------------------------------------------------------------------
(577)
Autocloseable, the very small catch

In Java, many input/output (I/O) classes, like BufferedWriter and BufferedReader, implement an interface called AutoCloseable, which means they can automatically close themselves when you’re done using them. This is super helpful because when working with files or other resources, you need to close them properly to avoid problems like memory leaks or locked files. To make this easier, Java 7 introduced the try-with-resources (TWR) statement, where you declare your I/O objects inside parentheses right after try. This way, Java automatically closes those resources for you, even if an error happens, so you don’t have to write extra cleanup code yourself.

You can also declare multiple resources inside the same try-with-resources statement by separating them with semicolons, like a BufferedWriter and a BufferedReader at the same time. When the try block finishes, Java closes those resources in the reverse order of how you declared them. If you want, you can still add catch or finally blocks for handling exceptions or extra cleanup, and Java will handle closing the resources safely. Just remember, only classes that implement the AutoCloseable interface can be used inside a try-with-resources statement. This makes your code cleaner and safer when dealing with file and resource management!

-----------------------------------------------------------------------------------------
(579)
Saving a BeatBox pattern

In BeatBox, the drum pattern is just a bunch of checkboxes. Each box shows if a drum sound plays at a certain beat. To save your pattern, we just save which boxes are checked (true) or not (false) in a list. Later, we can load that list to put the pattern back. Next, instead of saving patterns to a file, we’ll learn how to send them over the internet to share with friends.

This code saves the current state of 256 checkboxes into a file. It creates a boolean array called checkboxState to store whether each checkbox is checked (true) or not (false). It then goes through all the checkboxes one by one, checks if each is selected, and updates the array accordingly. After that, it opens a file called "Checkbox.ser" and writes the whole boolean array to this file using an object output stream, which lets the program save the data so it can be loaded later. If there’s an error while saving, the program prints the error details.

-----------------------------------------------------------------------------------------
(580)
Restoring a BeatBox pattern

This code restores the saved state of the checkboxes in the BeatBox app when the user clicks the "restore" button. It reads a previously saved file (Checkbox.ser) that contains a boolean array, where each value represents whether a specific checkbox (out of 256) was selected. It then goes through each checkbox in the GUI and sets its state (checked or not) based on the values in that array. After updating the checkboxes, it stops the current music sequence and starts a new one using the restored pattern. Essentially, it reloads a saved beat pattern so the user can hear or edit it again.

-------------------------------------------------------------------------------------------