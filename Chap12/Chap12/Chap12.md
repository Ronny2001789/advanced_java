(369)
Lambdas and Streams: 

A stream in Java is a way to work with data, like items in a list, by processing them one by one in a clean and simple way. Instead of writing loops, you can use streams to filter, sort, or change data. A lambda is a short piece of code you can pass into a method, kind of like a quick function, that tells the stream what to do with each item. Together, streams and lambdas let you write code that focuses on what you want to happen, not all the steps to make it happen.

-------------------------------------------------------------------------------------------
(373)
Small errors in common code can be hard to spot: 

Small mistakes in for loops can be hard to notice because many loops look similar but don’t always work correctly. While compiler errors and exceptions help spot some problems, incorrect output can be tricky to find just by reading code. Using methods like forEach helps by removing repetitive code and focusing only on what we want to do with each item, reducing mistakes. Building on this idea, Java 8 introduced the Streams API, which offers powerful tools to work with collections in a new way, letting us create a clear “recipe” of what we want to do with our data instead of writing all the steps ourselves.

-------------------------------------------------------------------------------------------
(374)
Building blocks of common operations: 

When working with collections (like lists or groups of items), we often want to do similar things no matter what type of items are inside. For example, we might want to get only the items that meet a certain condition, change each item in the same way, remove repeated items, or sort the items in a specific order. These common actions can be given names so it's easier to understand what each one does.

Example:
-----------
If you have a list of numbers: [5, 2, 2, 8, 1]

  1. Filter: keep only numbers greater than 3 → [5, 8]

  2. Map: add 1 to every number → [6, 3, 3, 9, 2]

  3. Remove duplicates → [5, 2, 8, 1]

  4. Sort → [1, 2, 2, 5, 8]

-------------------------------------------------------------------------------------------
(375)
Introducing the Streams API: 

The Streams API is a tool in Java that helps us work with collections of data (like lists) in a clear and easy way. It lets us do things like filter, sort, or change the items in a collection using simple steps that are easy to read. Each operation (like filter or map) has a name that tells us what it does, so the code becomes more understandable. It’s like telling the computer what to do with the data, step by step.

java.util.stream.Stream:
-------------------------
 1. distinct()        – Use this when you want to remove duplicates from your collection.
 2. filter(Predicate) – Use this to select only the elements that match a condition.
 3. limit()           – Use this to keep only the first few elements from the collection.
 4. map(Function)     – Use this to change or transform each item in the collection.
 5. skip()            – Use ignore or skip the first few elements in the collection.
 6. sorted()          – Use this to arrange the elements in natural order (e.g., numbers from smallest to largest, or strings alphabetically).

-------------------------------------------------------------------------------------------
(376)
Getting started with Streams: 

To use stream methods in Java, you first need to create a Stream object. For example, if you have a List of Strings, you can call the .stream() method on it to get a Stream of those Strings. You can then call methods like limit(4) to specify how many elements you want. But instead of giving you those elements right away, it returns another Stream, which just stores the operation.

If you try to print the result of limit(), you’ll see something like java.util.stream.SliceOps, which represents the Stream, not the data itself. That’s because methods like limit() are intermediate operations, they describe what to do, but don’t do it yet. To actually get results, you need a terminal operation like collect() or forEach() that tells Java to run the Stream operations and produce the output.

-------------------------------------------------------------------------------------------
(377)
Streams are like recipes: 

1. This method checks if any element in the stream matches a certain condition. It returns 
   true if at least one element passes the test (i.e., matches the condition); otherwise, 
   it returns false.

2. This simply counts the number of elements in the stream and returns that number as a 
   long value. It's useful when you want to know how many items are in the stream after 
   applying some filters or other operations.

3. This method is used to gather the elements of a stream into another form, like a List, 
   Set, or even a single value (like a sum). It performs what's called a reduction, meaning 
   it combines all the elements using a specific method provided by the Collector.

4. This method returns the first element in the stream, if one exists. Since there might 
   not be any elements, it returns an Optional, which is a special object that may or may 
   not contain a value.

-------------------------------------------------------------------------------------------
(382)
Yes, because Streams are lazy: 

Intermediate operations in Java streams (like filter, map, or sorted) don’t actually do anything right away, they just define what should happen later. These steps are "lazy," meaning they wait until a terminal operation (like collect or count) is called. The terminal operation then runs the whole stream pipeline at once, efficiently applying all the steps in a single pass through the data instead of going over it multiple times.

-------------------------------------------------------------------------------------------
(389)
Lambda expressions are objects: 

A lambda expression in Java is just a short way to write code that behaves like an object specifically, an object of a functional interface (an interface with just one abstract method). When you pass a lambda into a method, you're really passing an object that has one method, and that method is what gets run later. So if you want a method to accept a lambda, it needs a parameter of a functional interface type. Inside the method, Java will call that one method (the "single abstract method") on the lambda, which is how your code runs.

-------------------------------------------------------------------------------------------
(393)
A lambda might not return anything: 

A lambda expression in Java can be used when you want to pass a block of code as data. Sometimes, this block of code doesn’t return anything. In those cases, the method it’s being passed to is declared as void. This means the lambda only performs an action, like printing something to the screen, without giving anything back. For example, when using forEach() to print elements in a list, the lambda doesn’t return a value, it just runs.

A lambda might have zero, one, or many parameters:

Lambdas can also take zero, one, or more parameters depending on what the method they’re implementing expects. If there are no parameters, you use empty brackets like (). If there's one parameter, you can skip the parentheses, and if there are two or more, you must include parentheses and separate the parameters with commas. Adding types (like String) is optional unless the compiler needs them to understand the code.

-------------------------------------------------------------------------------------------
(394)
How can I tell if a method takes a lambda?: 

When you're learning about lambdas in Java, it helps to know which methods can accept them. A method can take a lambda as an argument if one of its parameters is a functional interface, an interface with just one abstract method. 

This is what makes lambdas work in Java: the lambda provides the code for that one method. To spot this in real code, look at the method's parameter types. If you see types like Predicate, Consumer, or Function, those are functional interfaces, so you can use a lambda there!

-------------------------------------------------------------------------------------------
(397)
Functional interfaces in the wild: 

In this section, we go through how to tell if an interface can be used with a lambda expression. An interface can be used with a lambda if it’s a functional interface, meaning it has exactly one abstract method. 

Even if the interface looks like it has many methods, most of them might be default, static, or inherited from Object—and those don’t count because they already have a method body. 

For example, the Comparator interface includes several methods, but only compare() is abstract, so it’s still a functional interface. To check if an interface supports lambdas, just look for a single abstract method.

-------------------------------------------------------------------------------------------
(402)
Let’s Rock!: 

A Predicate in Java is a special kind of function that takes one input and returns either true or false. It’s used when you want to test something. For example, if you want to check if a song is in the "Rock" genre, the Predicate would return true for rock songs and false for others. In streams, we use Predicates with methods like .filter() to decide which items to keep.


-------------------------------------------------------------------------------------------
