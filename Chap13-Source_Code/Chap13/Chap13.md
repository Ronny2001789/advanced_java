(421)
Risky Behavior

Sometimes things go wrong, like missing files or servers not responding, even if your code is correct. You can’t control everything, so you need to write code that can handle these problems. Before, most errors were just bugs we could fix. But now, we’re dealing with code that might fail at runtime. In this chapter, you’ll learn how to handle those situations by building a simple MIDI Music Player using the JavaSound API, which can be a bit unreliable.

-------------------------------------------------------------------------------------------
(422)
Let’s make a Music Machine

We’re going to create a simple music app that lets you make your own drum beats. You’ll learn how to use Java to play sounds, respond to clicks, and control timing. Don’t worry, you’ll get some of the visual (GUI) code ready-made, so you can focus on the fun and important parts of the logic.

The diagram shows what our finished BeatBox will look like. On the left, you have a list of instruments. Across the top are 16 beats. You make a beat pattern by checking boxes, each box tells an instrument to play on that beat. When you hit Start, your beat plays in a loop. You can speed it up, slow it down, or even send your beat to other users. You’ll also be able to load beats sent by others by clicking on their messages.

-------------------------------------------------------------------------------------------
(423)
We’ll start with the basics

Starting with the Basics
----------------------------
Before we build the full music app, we’ll focus on one part at a time. In this section, we’re starting with sounds, how to make your program play notes using Java. We’re not dealing with graphics (GUI), networking, or file input/output yet. 

Our goal is simple: get Java to play MIDI sounds through your computer speakers. And don’t worry if you’ve never worked with music code or MIDI before, everything you need to know will be explained as we go.

What is the JavaSound API and MIDI?
-------------------------------------
Java gives us a built-in tool called JavaSound, which lets us work with sound. We’ll be using the MIDI part of it, which is kind of like digital sheet music. A MIDI file doesn’t contain actual sound—it just tells a musical instrument what to play (like "play middle C and hold it for 2 beats"). 

A MIDI-capable instrument, like a keyboard (or even software on your computer), reads those instructions and plays the right sound. You can think of it like a web browser reading an HTML file—MIDI tells the instrument what to play, and the instrument creates the sound. In our project, we’ll use the built-in Java synthesizer, which acts like an instrument that lives inside your computer.

-------------------------------------------------------------------------------------------
(424)
First we need a Sequencer

What’s a Sequencer, and What Does This Code Do?
-----------------------------------------------
To play any MIDI sound in Java, we need something called a Sequencer. This is a built-in Java object that knows how to take MIDI instructions and send them to the right instruments to be played. In simple terms, the sequencer is what plays your music. 

The code here tries to get a sequencer using MidiSystem.getSequencer() and prints a message if it works. This is part of the javax.sound.midi package, so we import that at the top. The main() method creates an object of our class and runs the play() method to test it.

Why This Code Fails to Compile
--------------------------------
Even though the code looks fine, Java gives us an error when we try to compile it. That’s because getSequencer() might fail, Java knows this and forces us to handle the possible error, called a MidiUnavailableException. 

This type of error is known as a checked exception, which means we must either catch it using a try-catch block or declare it with throws. In this case, we tried to use a try block, but we didn’t catch the specific exception. That’s why the compiler stops us—it’s making sure we’re ready in case something goes wrong when asking for a sequencer.

-------------------------------------------------------------------------------------------
(426)
Methods in Java use exceptions to tell the calling code

The getSequencer() method can fail at runtime by throwing a MidiUnavailableException. This means it might not be able to get the MIDI device, for example, if it’s already in use. Because of this risk, Java requires the method to declare this possibility with a throws clause, so anyone calling it knows they need to handle the exception. This is part of Java’s exception-handling system, which helps your program deal with problems in a clean and organized way.

-------------------------------------------------------------------------------------------
(428)
An exception is an object... of type Exception

When you write risky code, you put it inside a try block. If something goes wrong, Java “throws” an exception, which is an object describing the problem. You can “catch” this exception using a catch block, where you try to handle or recover from the error. The catch block takes an Exception object as a parameter, just like a method argument. This lets you access information about the problem and decide what to do next.

Exceptions in Java are objects, and they follow a class hierarchy. All exceptions inherit from a class called Throwable, which provides useful methods like getMessage() to get error details and printStackTrace() to show where the error happened. Inside a catch block, what you do depends on the situation,

for example, if a server is down, you might try connecting to another server; if a file is missing, you could ask the user to locate it. Handling exceptions this way helps your program deal with problems smoothly without crashing.

-------------------------------------------------------------------------------------------
(429)
If it’s your code that catches the exception, then whose code throws it?

Who throws and who catches exceptions?
-------------------------------------------
When you call a risky method, a method that might fail and declares an exception, it’s that method’s job to throw the exception if something goes wrong. Your code, which calls that method, is responsible for catching and handling the exception. Even if you wrote both methods, what matters is understanding which method throws the exception and which one handles it.

Declaring and throwing exceptions
------------------------------------
When a method might throw an exception, it must say so clearly by using a throws clause in its declaration. Inside the method, if the risky condition happens, it creates and throws a new exception object. This tells Java that something bad happened and the exception needs to be handled somewhere.

Catching exceptions and recovering
---------------------------------------------------
The code that calls the risky method uses a try-catch block to catch the exception. Inside the catch block, you can handle the error—for example, print an error message or recover. If you can’t fix the problem, you should at least print a stack trace using printStackTrace(). This helps you understand what went wrong and where in the code the error happened.

-------------------------------------------------------------------------------------------
(433)
Finally: for the things you want to do no matter what

When you're cooking, you always need to turn the oven off when you're done, whether the cooking succeeds or fails. Similarly, in programming, there are times when you must run certain code no matter what happens. In exception handling, this is where the finally block comes in. Instead of writing cleanup code (like turning off the oven) in both the try and catch blocks, you can put it in the finally block, which always runs, whether an exception occurred or not.

Here’s how it works: if the code in the try block runs without any problems, the program skips the catch block and runs the finally block. If an exception happens, the catch block runs first, then the finally block. Even if there's a return statement in the try or catch, the finally block will still run before the method finishes. This makes finally perfect for tasks like closing files, releasing resources, or turning off ovens, things that must happen no matter what.

-------------------------------------------------------------------------------------------
(435)
Did we mention that a method can throw more than one exception?

In this code, we have two classes: Laundry and WashingMachine. The Laundry class has a method called doLaundry() that may throw two types of checked exceptions: PantsException and LingerieException. These represent different kinds of errors that could occur during the laundry process. Because these are checked exceptions, any method that calls doLaundry() must handle them, either by catching them or declaring them. 

In the WashingMachine class, the go() method creates a Laundry object and calls doLaundry() inside a try block. If a PantsException is thrown, it is caught by the first catch block, where appropriate recovery code can be added. If a LingerieException is thrown instead, the second catch block handles it.

-------------------------------------------------------------------------------------------
(436)
Exceptions are polymorphic

Exceptions are just objects that can be thrown, and like other objects, they support polymorphism. For example, a LingerieException can be assigned to a ClothingException reference, or a PantsException to an Exception reference. This means methods can declare a superclass of the exceptions they might throw, instead of listing every specific one. Similarly, a catch block can handle a superclass of multiple exceptions, so you don’t need a separate catch for each type if one can cover them all.

-------------------------------------------------------------------------------------------
(437)
Just because

You can write your exception-handling code to catch all exceptions using a single catch block with the superclass Exception. This approach allows you to handle any type of exception that might be thrown. However, just because you can catch everything with one generic catch doesn’t mean you should. Using a general catch block makes it hard to tell what went wrong, because it treats all exceptions the same, even though different problems might need different solutions.

If your program needs to handle certain exceptions differently, it’s better to write a separate catch block for each specific type. For example, you might recover from a TeeShirtException one way and a LingerieException another. Then, you can add a more general ClothingException catch to handle the rest. This way, your code is more precise and easier to debug or update, because each exception is handled according to what actually went wrong.

-------------------------------------------------------------------------------------------
(438)
Multiple catch blocks must be ordered from smallest to biggest

When catching exceptions, the higher a class is in the inheritance tree, the bigger the “basket” it represents — meaning it can catch more types of exceptions. For example, a ClothingException catch block can catch any exceptions that are subclasses of it, like ShirtException, PantsException, or LingerieException. 

At the very top is the Exception class, which can catch any exception, including those you might not expect. However, catching everything with Exception is usually only done in testing or very general error handling, because it doesn’t tell you exactly what went wrong.

When you have multiple catch blocks, you need to order them from the most specific exception to the most general. That means catching a TeeShirtException first, then a ShirtException, and finally a ClothingException. If you put a general catch block first, it will catch the exception before the specific catch blocks get a chance. Proper ordering makes sure that each exception is handled at the right level of detail.

-------------------------------------------------------------------------------------------
(439)
You can’t put bigger baskets above smaller baskets

When you write several catch blocks, the order matters. You must put the specific exceptions first (the “smaller baskets” that catch fewer types), and put the general exceptions later (the “bigger baskets” that catch more types). Java won’t let you put a big, general catch before a small, specific one because the big catch would catch everything first and the small one would never get used. The JVM checks each catch block in order and uses the first one that can handle the exception. However, exceptions at the same level of the hierarchy (called siblings), like PantsException and LingerieException, can be placed in any order since they don’t overlap in what they catch.

-------------------------------------------------------------------------------------------
(441)
When you don’t want to handle an exception...

If you don’t want to handle an exception in your method, you can “duck” it by declaring that your method throws it. This means you don’t catch the exception yourself, instead you tell whoever calls your method, “Hey, this method might throw this exception, so you need to handle it.” You do this by adding a throws declaration in your method signature. Even though you’re not actually throwing the exception yourself, you’re passing the responsibility up to the caller.

When a risky method actually throws an exception, it stops running immediately and passes the exception to the method that called it. If that caller also ducks the exception without catching it, it too stops running and passes the exception further up the call stack. This process continues until some method catches the exception or the program ends with an error. Ducking is useful when you want to avoid handling exceptions yourself and let someone else higher up in the program take care of them.

-------------------------------------------------------------------------------------------
(442)
Ducking (by declaring) only delays the inevitable

Sometimes when a method throws an exception, instead of handling it, it just “ducks” it by declaring it with throws. This means the method says, “I’m not dealing with this exception , whoever called me has to handle it.” But if the caller also ducks the exception without handling it, the exception keeps going up the chain.

For example, imagine a program with three methods: main(), foo(), and doLaundry(). The doLaundry() method throws a ClothingException. The foo() method calls doLaundry() but doesn’t catch the exception; instead, it ducks it by declaring throws ClothingException. Then, main() calls foo() and also ducks the exception without catching it. Since neither foo() nor main() catches the exception, it bubbles all the way up to the JVM (Java Virtual Machine).

When the JVM gets an uncaught exception like this, it doesn’t know how to fix it, so it shuts down the program and shows an error. That’s why, even if you duck exceptions in your methods, someone — usually main() or another high-level method, eventually has to catch and handle them, or else your program will crash.

-------------------------------------------------------------------------------------------
(446)
Making actual sound

When working with MIDI in Java, it's important to understand that MIDI itself is not sound, it's just data that tells a music system what to play and how to play it. Think of it like sheet music. The sheet music doesn’t produce sound on its own; it needs to be read by a musician or a system that knows how to interpret it and turn it into real sound. Similarly, in Java, MIDI data must be sent through a MIDI device, in our case, a software synthesizer, that reads the instructions and produces actual sound through your speakers. This is what Java Sound does behind the scenes.

To make sound using MIDI in Java, you need four main components. First is the Sequencer, which is like the player or conductor. It reads the song and controls the timing and playback of everything. Second is the Sequence, which represents the whole song, all the MIDI data for one complete piece of music. The Sequence is made up of one or more Tracks, and each Track can hold a series of musical events. For simplicity, the book focuses on songs with just a single Track, which is enough for basic MIDI playback. Finally, within each Track, you have MIDI Events. These are the actual instructions, like “play middle C now,” or “change the instrument to a flute.” The Sequencer reads these events and uses a software synthesizer to convert them into sound.

So, to actually play music in Java using MIDI, you set up a Sequencer to play a Sequence, add a Track to that Sequence, and then fill the Track with MIDI Events that define what should be played and when. Once everything is set up, you tell the Sequencer to start, and the music plays through your computer’s speakers. The key idea to remember is that MIDI is just instructions, and without a Sequencer and a software synthesizer, those instructions can’t be turned into sound.

-------------------------------------------------------------------------------------------
