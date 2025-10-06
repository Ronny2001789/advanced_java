(309)
Data Structures

Java makes sorting easy because it has built-in methods that do the work for you. You don’t need to write your own sorting code unless you’re learning how it works in class. Java also gives you tools to store and manage data, like lists you can keep adding to, lists that remove duplicates, or ways to sort things, like sorting classmates by how many times they forget to unmute during a call, or sorting pets by the number of tricks they’ve learned.

-------------------------------------------------------------------------------------------
(310)
Tracking song popularity on your jukebox

You've just started a new job managing the data for a jukebox at Lou’s Diner. The jukebox adds a song title to a simple text file every time a song is played. Your job is to work with that data to track which songs are popular and to help organize playlists. You won’t be building the whole program—other developers will handle things like reading the data from the file. For now, you’ll just be working with a simple list of songs that represents what the jukebox keeps recording. Since we don’t yet have the actual code that will read the real data, we’ll create some fake or “mock” data to test our program with. This helps us get started without having to wait for the full system to be built.


code
--------
To create this test data, we write a small class called MockSongs that has a method called getSongStrings(). This method is marked static, which means we don’t need to create an object to use it. Inside the method, we make a list of song titles using Java’s ArrayList, which lets us store strings in a list. 


We add six sample song titles to this list, including some duplicates. Then we return the list so we can use it in other parts of our code. Even though we use ArrayList to build the list, we return it as a more general type called List, which is a common Java practice. This mock data helps us test how our program will work with real song data later on.

-------------------------------------------------------------------------------------------
(311)
Your first job, sort the songs in alphabetical order

Since we’ve already created the mock data with the MockSongs class, this new code uses a class called Jukebox1 to get the list of songs by calling getSongStrings(), then prints the list to the screen. The program starts in the main method, which runs the go() method where the songs are stored in a list and displayed. Right now, the songs print in the order they were added, so they are not sorted alphabetically yet.

-------------------------------------------------------------------------------------------
(314)
Exploring the java.util API, List and Collections

In Java, when you use an ArrayList or any type of List, the elements stay in the order you added them. Sometimes, you might want to arrange the items in a certain order, like putting song titles in alphabetical order. Java makes this easy using tools from the java.util package, especially the List and Collections classes. 

The Collections class has helpful methods like Collections.sort(), which arranges a list in its natural order (for example, alphabetically for text). If you need to sort things differently—like by size, rating, or something else, you can use a Comparator to define your own sorting rules

------------------------------------------------------------------------------------------
(316)
But now you need Song objects, not just simple Strings

toString()
---------------------
When you use toString() on an object in Java, it returns a string representation that can be printed or displayed as text. By default, this method returns something like SongV2@6d06d69c, showing the class name and memory address, which isn’t very helpful. To make it clearer, we override toString() to return useful details like the song’s title. For example, printing a SongV2 object might originally show SongV2@6d06d69c, but after overriding toString(), it will print "Imagine", making the output easier to understand.

-----------------------------------------------------------------------------------------
(319)
The sort() method declaration

The sort() method uses something called generics, which is a way to write flexible code that can work with different types safely. The <T extends Comparable<? super T>> part means that the method works with any type T that knows how to compare itself with other objects of its type (so it can decide which comes before or after). This is important because sorting needs to know how to compare items. The (List<T> list) part means it sorts a list of those items. So basically, this method sorts a list of objects as long as those objects can be compared to each other, allowing Java to put them in order.

----------------------------------------------------------------------------------------
(320)
Generics means more type-safety

Generics make collections like ArrayList type-safe. That means if you write ArrayList<Fish>, the compiler will only let you add Fish objects. Before generics, collections could hold anything (Object), so mistakes (like adding a Car to a list of Fish) wouldn’t be caught until the program ran. With generics, mistakes are caught early, while you're writing the code.

----------------------------------------------------------------------------------------
(322)
Using generic CLASSES

When you use a generic class like ArrayList, you tell Java what type of objects the list should hold, for example, ArrayList<Song> means the list will hold only Song objects. In the documentation, you’ll see things like <E>, which is just a placeholder for the type you choose, "E" stands for "Element." So when you create new ArrayList<Song>(), everywhere the docs say E, you can mentally replace it with Song. This helps Java check your code and make sure you don’t accidentally add the wrong type of object into the list.

------------------------------------------------------------------------------------------
(323)
Using type parameters with ArrayList

In the code public boolean add(E o), the E is a type parameter, meaning it stands for the type of object the ArrayList is meant to hold. When you create an ArrayList<String>, Java replaces every E in the class with String, so add(E o) becomes add(String o). 

If you instead create an ArrayList<Dog>, then the method becomes add(Dog o). This is how generics work,  they let the same class handle many types safely, and the compiler makes sure you only add the correct type to the list.

------------------------------------------------------------------------------------------
(324)
Using generic METHODS

A generic method is a method that can work with different types, just like a generic class. If the class already uses a type like <E>, the method can use that same type. But even if the class doesn’t use generics, a method can still be generic by declaring its own type, written like <T> before the return type. 


For example, public <T> void print(T item) means that the method can accept any type (like String, Dog, etc.), and T will be replaced with whatever type you pass in. This makes your method reusable and type-safe.

----------------------------------------------------------------------------------------
(325)
Here’s where it gets weird...

When you see a method like public <T extends Animal> void takeThing(ArrayList<T> list), it means the method can accept an ArrayList of any type that is a subclass of Animal, including Dog, Cat, or Animal itself. This makes the method flexible, as long as the list holds some type of Animal, it’s allowed.

But if the method is written as public void takeThing(ArrayList<Animal> list), then it only accepts an ArrayList<Animal>. You cannot pass an ArrayList<Dog> or ArrayList<Cat> to it, even though those are subclasses of Animal. It feels like it breaks polymorphism, but that’s how generics work. Just remember: the first version is more flexible.

-------------------------------------------------------------------------------------------
(327)
Revisiting the sort() method

The sort() method only works on lists of objects that can be compared to each other, which means those objects must implement the Comparable interface. When a class implements Comparable, it tells Java how to compare its objects, so Java knows how to sort them. 


For example, String implements Comparable<String>, so lists of strings can be sorted. Even though the generic code uses the word extends (like <T extends Comparable<T>>), it means the type must implement the Comparable interface. Since your Song class doesn’t implement Comparable, Java can’t sort a list of songs yet.

------------------------------------------------------------------------------------------
(328)
In generics, “extends” means “extends or implements”

In Java generics, the word extends is used to set limits on a type parameter, but it actually means “is a subtype of” whether that subtype is a class or an interface. This lets you restrict a generic type to only subclasses of a class or only classes that implement a certain interface, using the same keyword. So, when you see <T extends Comparable<?>>, it means “T must be a type that implements the Comparable interface,” even though Comparable is an interface.

------------------------------------------------------------------------------------------
(329)
Finally we know what’s wrong... The Song class needs to implement Comparable

The reason you can’t sort your list of Song objects yet is because the Song class doesn’t implement the Comparable interface, which is required by the sort() method. To fix this, you need to make Song implement Comparable<Song> and define the compareTo() method. This method tells Java how to compare one Song to another to decide which should come before or after in the list. 

Inside compareTo(), you return a negative number if the current song is “less than” the other song, zero if they’re equal, or a positive number if it’s “greater.” The tricky part is deciding the rules for comparison—like sorting by title, artist, or bpm—so Java knows how to order the songs.

------------------------------------------------------------------------------------------
(330)
The new, improved, comparable Song class

The SongV3 class implements the Comparable<SongV3> interface, which requires defining the compareTo(SongV3 s) method. This method compares the current song’s title with another song’s title by calling the compareTo() method of the String class, allowing songs to be compared and sorted alphabetically by their titles.

------------------------------------------------------------------------------------------
(331)
We can sort the list, but...

When you sort a list, each object can usually only compare itself one way using compareTo(). But if you want to sort the same list in different ways, like by artist or by title, you use a Comparator. A Comparator is a separate tool that tells Java how to compare two objects.

When you give the sort() method a Comparator, it uses that to decide the order instead of the object’s own comparison method. If you don’t give a Comparator, it uses the object’s compareTo() method. This way, you can sort the same list in many different ways by using different Comparators.

------------------------------------------------------------------------------------------

(332)

Using a custom Comparator



The code creates an ArtistCompare object, which is a custom comparator that tells Java how to compare two SongV3 objects by their artist names. When songList.sort(artistCompare) is called, it sorts the list of songs using this comparator instead of the default comparison method. The compare method inside ArtistCompare uses the compareTo() method of the artist’s name (a String) to decide the order, arranging songs alphabetically by artist. Finally, printing the list shows the songs sorted by artist.

------------------------------------------------------------------------------------------

(336)

Sorting using only Comparators



Instead of using two different ways to sort songs (like Comparable for one and Comparator for another), we are using a class to help with sorting to make our code easier to understand. In this case, we created a class called TitleCompare to define how two songs should be compared based on their titles (like sorting alphabetically). Instead of mixing different ways to sort, we use only Comparator classes which are special kinds of classes that tell Java how to compare objects. So, we make a TitleCompare object from that class and use it to sort the list of songs with songList.sort(titleCompare). This keeps all our sorting methods organized and easy to change, no matter how we want the songs to be sorted.



-------------------------------------------------------------------------------------------

(337)

Just the code that matters



When we want to sort a list of songs by their titles, the main part is just one simple line that compares two song titles to decide their order. The rest of the code around it might look long or complicated, but it’s needed so the computer knows how to use this sorting rule properly. This rule is written inside a special type of class called a Comparator, which tells Java how to compare two songs.



Sometimes, this Comparator can be written right where we sort the list, which saves making a separate class, but even then, there’s some extra code to explain what the Comparator does. So, while sorting by title is simple in idea, Java’s way of writing it can look a bit complicated at first.



-------------------------------------------------------------------------------------------

(340)

Enter lambdas! Leveraging what the compiler can infer



A lambda in Java is a short way to write code that performs a single task, like a method, without creating a full class or method. It’s mostly used when you want to pass a small piece of behavior (like comparing two songs) directly into a method like sort(), without having to write a whole new class like TitleCompare. You can think of a lambda as a shortcut for writing one simple method, especially when using interfaces like Comparator.



----

Normally, when we sort a list of songs with songList.sort(titleCompare);, we pass in a Comparator object (like the TitleCompare class) that tells Java how to compare two songs by their title. But Java doesn’t actually care about the class name or how it's written, it only needs the comparison logic, which is just one line: one.getTitle().compareTo(two.getTitle()). 



So instead of writing a full class like TitleCompare, we can use a lambda expression to pass just that one line of logic directly into the sort() method, like this: songList.sort((one, two) -> one.getTitle().compareTo(two.getTitle()));. 



The compiler already knows one and two are Song objects because songList is a list of Song, so the lambda gives Java exactly what it needs—no extra code, no extra class, just the sorting rule written in a quick, clear way.



-------------------------------------------------------------------------------------------

(342)

Where did all that code go?



Because the Comparator interface only has one method we need to implement, which is compare(), Java lets us skip writing a whole class and instead use a lambda expression. That means we do not have to tell the compiler what the class or method looks like because it can figure that out on its own. All we need to do is give it the logic we want to run inside the method. So when we use a lambda like (one, two) -> one.getTitle().compareTo(two.getTitle()), Java knows we are trying to implement the compare() method of a Comparator and it takes care of the rest.



-------------------------------------------------------------------------------------------

(345)

We need a Set instead of a List



* List: Keeps items in order and allows duplicates, like two balls in different positions.
* Set: Only keeps unique items with no specific order, so duplicates like the second ball are removed.
* Map: Uses unique keys to find values, allowing duplicate values but no duplicate keys.

&nbsp;       Keys: "Ball1", "Ball2", "Fish", "Car" (the labels under the boxes)

&nbsp;       Values: Ball,    Ball,    Fish,  Car (the objects inside the boxes)



-------------------------------------------------------------------------------------------

(346)

The Collection API (part of it)



Maps are a key part of Java’s Collection Framework but differ from other collections because they do not extend the java.util.Collection interface. Instead, they define a structure that stores data as key-value pairs, where each unique key maps to a specific value. 



While interfaces like List, Set, and their subinterfaces extend Collection, the Map interface stands alone but is still considered part of the Collection API because it manages groups of objects in an organized way. Examples of Map implementations include HashMap, TreeMap, and LinkedHashMap, and they provide efficient lookup, insertion, and deletion based on keys rather than positions or duplicates like Lists or Sets.



-------------------------------------------------------------------------------------------

(347)

Using a HashSet instead of ArrayList



1. HashSet removes duplicates based on equals() and hashCode(). But in this case, it looks like duplicates ($10 and cassidy) are still present in the set output.
2. This usually means the SongV3 class does NOT properly override equals() and hashCode(). Without those, the HashSet can't correctly identify duplicates, so it treats each object as unique even if they have the same title.
3. HashSet does NOT maintain order, so the elements are printed in random order.



-------------------------------------------------------------------------------------------

(348)

What makes two objects equal?



Sometimes, two variables point to the exact same object in memory. This is called reference equality, and you can check it with ==. But other times, two different objects can still be considered equal if they have the same important information, like two songs with the same title. By default, Java treats every object as unique because it uses each object’s location in memory to tell them apart.



To make Java treat two different objects as equal when their important data matches, like song titles, you need to change how Java compares them by overriding the equals() and hashCode() methods. This tells Java what “equal” means for your objects. Without this, collections like Sets won’t recognize duplicates and will keep both objects, even if they have the same data.



-------------------------------------------------------------------------------------------

(349)
How a HashSet checks for duplicates: hashCode() and equals()



When you add an object to a HashSet, Java first uses the object’s hashCode() method to decide where to place it. If no existing object has the same hash code, Java assumes the new object is unique and adds it. But if another object already has that hash code, Java calls the equals() method to check if the two objects are truly equal. If equals() returns true, the HashSet knows the new object is a duplicate and does not add it. The add() method returns false in this case to let you know the object was not added because it was a duplicate.



-------------------------------------------------------------------------------------------

(350)

The Song class with overridden hashCode() and equals()



The equals method checks if one SongV4 object is equal to another by comparing their titles. It first casts the incoming object to a SongV4 type and then returns true if both song titles are the same. The hashCode method returns the hash code of the song’s title, which ensures that two songs with the same title will have the same hash code. Together, these methods help Java collections like HashSet recognize songs with the same title as duplicates.



-------------------------------------------------------------------------------------------

(352)
If we want the set to stay sorted, we’ve got TreeSet



A TreeSet is like a HashSet because it removes duplicates, but it also automatically keeps the elements sorted. If you don’t give it a custom sorting rule (called a Comparator), it uses the object’s compareTo() method to decide the order. This is useful when you want both uniqueness and sorting at the same time. You can also tell the TreeSet to sort by something else (like BPM) by passing in a Comparator



------------------------------------------------------------------------------------------

(354)

TreeSet elements MUST be comparable



**(Comparable):**

A TreeSet needs to know how to sort the elements you add. One way to do this is by having your class (like Book) implement the Comparable interface. This means you must write a compareTo() method that defines how one object should be compared to another. If you forget to do this, the code might compile, but it will crash at runtime when the TreeSet tries to compare two objects and doesn’t know how.



**(Comparator):**

Another option is to use a Comparator. This is a separate class that tells the TreeSet how to sort the objects. You pass the Comparator to the TreeSet constructor. This is helpful if you don’t want to change the class itself or if you want to sort the same objects in different ways (like by title, author, or price). Either way, TreeSet must have one of these to work correctly.



------------------------------------------------------------------------------------------

(355)

We’ve seen Lists and Sets, now we’ll use a Map



A Map lets you store and look up values using unique keys, like a dictionary. Each entry has a key and a value. You use put(key, value) to add data and get(key) to retrieve it. Keys must be unique, but values can repeat. HashMap is a common type of Map.



-------------------------------------------------------------------------------------------
(356)

Creating and filling collections



Creating and filling collections like Lists, Sets, or Maps often involves writing the same code repeatedly. Sometimes, we want to make sure that once a collection is created, it can’t be changed. This is called an unmodifiable collection. To make creating these collections easier and safer, Java provides convenience factory methods added in Java 9 that let you create and fill collections in one simple step instead of writing many lines of code.



-------------------------------------------------------------------------------------------

(357)

Convenience Factory Methods for Collections 



Convenience Factory Methods for Collections are tools that make it simple to create Lists, Sets, or Maps already filled with data. These methods are very handy for most situations, but if they don’t fit your needs, you can still use the usual way by creating collections and adding items with constructors and add() or put() methods. 



One important thing to remember is that collections created with these factory methods cannot be changed later. You cannot add, remove, or sort items. Also, these collections are not the usual ArrayList, HashSet, or HashMap, but they behave correctly according to their interfaces. For example, a List keeps order and a Set prevents duplicates.



To create a List, you can use List.of() with all the items inside parentheses, like List.of("somersault", "cassidy", "$10"). Similarly, you can create Sets with Set.of() and Maps with Map.of() for up to 10 entries by listing keys and values. For Maps with more than 10 entries or when you want clearer key-value pairs, you can use Map.ofEntries(), which takes entries made with Map.entry(). These methods make creating collections quick, readable, and safe since the collections are unmodifiable.



-------------------------------------------------------------------------------------------

(358)

Finally, back to generics



Generics in Java let us create methods that work with different types, but using them with polymorphism (related classes like Dog and Cat) can be tricky. For example, if a method takes a List of Animals, you can pass in Dogs and Cats because they are Animals. Inside the method, you can only use the methods defined in the Animal class, not the ones specific to Dog or Cat.



In the example, the method takeAnimals takes a List<Animal> and calls the eat() method on each Animal. This works perfectly because both Dog and Cat inherit the eat() method from Animal. When the program runs, it prints "animal eating" for each animal, showing that the method successfully handles different types of animals through polymorphism while using generics.



-------------------------------------------------------------------------------------------

(359)

But will it work with List<Dog>?



It doesn’t work because the method takeAnimals expects a List of Animal objects, but in Java generics, a List of Dog objects is not considered a subtype of a List of Animal objects. Even though Dog is a subclass of Animal, generics are fixed, which means List<Dog> and List<Animal> are treated as completely different types, so you can’t pass a List<Dog> where a List<Animal> is expected.



-------------------------------------------------------------------------------------------

(360)

What could happen if it were allowed...?



If the compiler allowed you to pass a List<Dog> to a method that expects a List<Animal>, it could cause serious problems. For example, inside the method, someone might add a Cat to the list. That would be bad because the original list was meant to hold only Dog objects, but now it would contain a Cat too. This breaks the rules of type safety and could lead to errors later in the program.



-------------------------------------------------------------------------------------------









