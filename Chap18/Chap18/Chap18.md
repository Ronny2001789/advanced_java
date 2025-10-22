(639)
Dealing with Concurrency Issues

Doing more than one thing at the same time can be difficult, and it's the same when writing programs that use multiple threads. While it may seem easy to write code that uses threads, making sure it works correctly can be much harder. In this chapter, you will learn about common problems that can happen when threads run at the same time, and how to avoid them. You'll also explore some helpful tools from Java's java.util.concurrent package that make it easier to write safe and reliable multithreaded code. Finally, you’ll learn how to create objects that don’t change, which are safer to use with threads. By the end, you’ll have a good set of tools to help you work with concurrency in Java.

-------------------------------------------------------------------------------------
(640)
What could possibly go wrong?

What could possibly go wrong? When two or more threads run at the same time in a program, they might try to use or change the same data at once. This can cause problems like data getting mixed up, wrong results, or parts of the program behaving in unexpected ways. For example, one thread might be in the middle of updating a value while another thread is trying to read it, which can lead to confusion or errors. These kinds of issues happen because threads are not always aware of what the others are doing, and they can interfere with each other if not managed carefully.

-------------------------------------------------------------------------------------
(641)
Marriage in Trouble.

Ryan and Monica’s problem is a great example of a race condition, which happens when two parts of a program (or in this case, two people) try to use or change the same data at the same time, and the result depends on who gets there first. Both of them looked at the bank account and saw $100, so they each thought it was safe to spend. But they didn’t know what the other person was doing at the same time. 

Since Ryan hasn't completed his purchase yet, Monica still saw the full $100. She spent the money, and then Ryan also completed his payment, and boom, they went over the limit. This is exactly the kind of problem you can get in programming when multiple threads try to access and change the same data at the same time. 

The program can behave unpredictably, just like Ryan and Monica’s shared account. These situations can be very tricky to fix unless you find a way to make sure the data is either safely shared or can’t be changed while someone else is using it, which is where immutable data and proper synchronization come in.

-------------------------------------------------------------------------------------
(642)
The Ryan and Monica problem, in code

The situation with Ryan and Monica is now shown using code. In this example, two threads **(one for Ryan and one for Monica)** are sharing a single bank account object. The code has two main parts: a **BankAccount** class that holds the balance and methods to check and spend money, and a **RyanAndMonicaJob** class that represents the actions each person takes, like checking the balance and deciding whether to spend. Each job runs in its own thread and simulates what Ryan or Monica would do.

To run the code, we first create one **RyanAndMonicaJob** for each person and give them the same bank account to use. Then we use something called an **ExecutorService** to run both jobs at the same time, each in its own thread. This means both Ryan and Monica could be checking and spending money at almost the same time. Even though the code checks the balance before spending, there’s still a risk: if both threads check the balance before either spends, they might both think there's enough money and end up overdrawing the account. This shows how sharing data between threads can lead to problems if not handled carefully.

-------------------------------------------------------------------------------------
(643)
The Ryan and Monica example

This code example brings the Ryan and Monica situation into programming using threads. The BankAccount class represents the shared bank account, starting with $100. There’s only one account object, which means both Ryan and Monica are using the same account at the same time. 

The RyanAndMonicaJob class represents what each person does, checking the balance and trying to spend money. In the main method, we create one job for Ryan (who wants to spend $50) and one for Monica (who wants to spend $100), and we run them using two threads in an ExecutorService. This setup allows both jobs to run at the same time, just like Ryan and Monica shopping online at the same time.

Each thread runs the goShopping() method, where it checks the balance and then spends the money if there's enough. The problem is, both threads might check the balance before either one spends anything, so they both think there's enough money, and both spend, causing the account to be overdrawn. This is called a race condition, where two threads "race" to use shared data, and the outcome depends on the timing. To help us understand what's happening while the program runs, we added print statements to show each step.

-------------------------------------------------------------------------------------
(646)
We need to check the balance and spend the money as one atomic thing

When Ryan and Monica both try to use the bank account at the same time, we run into a problem, they might both check the balance before either spends any money. This leads to a race condition, where both think there’s enough money, and they both spend, which can overdraw the account. The reason this happens is that checking the balance and spending are two separate steps, and something can change in between those steps if another thread jumps in.

To fix this, we use the synchronized keyword to lock the entire shopping action, so that only one person (thread) can check the balance and spend money at a time. In the code, we wrap the if check and the spend() call in a synchronized(account) block. This means when one thread is using the bank account, no other thread can enter that block until the first one finishes. This makes the whole shopping action atomic, it happens all at once, without interruption, and this is what protects the shared data from being changed at the wrong time. Just synchronizing getBalance() and spend() separately wouldn’t help, because it’s the combination of the two steps that needs to happen without interference.

-------------------------------------------------------------------------------------
(647)
Using an object’s lock

In Java, every object has a built-in lock, and this lock is used when you use the synchronized keyword. Think of the lock as having a single key. When a thread wants to run a synchronized method or enter a synchronized block on an object, it first has to get the key to that object’s lock. If no other thread is using the object at that time, the thread can grab the key and go in. But if another thread is already inside a synchronized method on that same object, the key is taken, so the second thread has to wait until the first one finishes and gives the key back.

Importantly, the lock belongs to the object, not the method. That means if an object has multiple synchronized methods (like method1() and method2()), only one thread can be inside any of those methods at a time. If one thread is in method1(), another thread can’t enter method2() until the first one is done. This prevents multiple threads from accessing or changing the object's data at the same time, which helps avoid race conditions. Synchronization is not about locking the data itself, it’s about locking the code that accesses the data, making sure only one thread can run that code at a time.

-------------------------------------------------------------------------------------
(649)
It’s important to lock the correct object

Instead of having each person (or thread) check the balance and spend money separately, we’ve moved that logic into the BankAccount class itself. Now, the spend() method handles both the balance check and the deduction, all in one place. This is better because the BankAccount is the shared object, so it should be responsible for keeping itself safe when used by multiple threads.

To make sure only one thread can use the spend() method at a time, we added the synchronized keyword to it. This means the entire action of checking the balance and spending money becomes atomic, it can only happen all at once, with no other thread interrupting. So now, Ryan and Monica can call spend() directly, and the method itself will safely handle everything, preventing race conditions or overdrawing the account

-------------------------------------------------------------------------------------
(650)
The dreaded “Lost Update” problem

The “Lost Update” problem is a type of race condition, which happens when multiple threads try to change the same variable at the same time. In this case, the program is trying to increment a balance 1,000 times using many threads. You might expect the final result to be 1,000, but often it will be less, that's because the operation balance++ is not actually a single action. **It's really three steps:** read the value, add one, and write the new value back. If two threads do this at the same time, they might both read the same original value before either one updates it, so one update gets lost.

This is what’s happening in the **LostUpdate code:** 1,000 threads are each trying to call increment(), but because there's no synchronization, many of those increments interfere with each other. Some threads end up writing over another thread’s update without knowing it. The result is a final balance that’s less than 1,000. This shows why synchronization is so important in multithreaded programs — to make sure each update to shared data happens safely, without being interrupted or overwritten by other threads.

-------------------------------------------------------------------------------------
(652)
Make the increment() method atomic.

Using synchronized on the increment() method solves the "Lost Update" problem because it ensures that only one thread can access the method at a time. This means the steps involved in balance++, reading the current value, adding 1, and writing it back, are treated as a single, uninterruptible action. Without synchronization, multiple threads might read the same value before either writes it back, causing some updates to be lost. By synchronizing the method, we guarantee that each increment is completed fully before another thread can make changes, keeping the data accurate and thread-safe.

-------------------------------------------------------------------------------------
(654)
Deadlock, a deadly side of synchronization

**What is deadlock and why it matters:**
-------------------------------------
Deadlock is a serious problem that can happen when using synchronized code in Java. It occurs when two or more threads are each holding a lock (or "key") on different objects, and each thread is waiting for the other to release its lock. Since neither thread can continue until the other gives up its lock, they both end up stuck, permanently waiting. This situation is called deadlock, and it can completely freeze part of your program. Java doesn’t have a built-in way to detect or fix deadlock, so it’s the programmer’s job to design their code carefully to avoid it.


**Example and real-world connection:**
----------------------------------
For example, imagine Thread A has the lock on object foo, and Thread B has the lock on object bar. Now, if Thread A tries to lock bar and Thread B tries to lock foo, both will end up waiting forever, because each is holding the lock the other needs. This situation is hard to recover from without restarting the program. While some systems like databases have built-in rollback mechanisms to handle deadlocks, Java does not, so you must plan ahead when using synchronization, and avoid designs where threads depend on multiple locks at once in conflicting ways.

-------------------------------------------------------------------------------------
(655)
You don’t always have to use synchronized

**Introducing atomic variables as an alternative:**
-----------------------------------------------
You don’t always have to use synchronized to make your code thread-safe. Synchronization can slow things down and sometimes cause deadlocks, so Java offers a better option for simple shared data: atomic variables. These are special classes like AtomicInteger, AtomicLong, or AtomicBoolean that allow safe access and updates to variables across multiple threads. For example, if you just need to count something (like in the Lost Update problem), AtomicInteger lets multiple threads safely increment a value without losing updates, and you don’t need to use the synchronized keyword at all.


**More advanced use with compare-and-swap:**
----------------------------------------
Atomic variables also support a more advanced technique called **compare and swap (CAS)**, using the compareAndSet() method. This lets you say: "Only update the value if it still matches what I expect." This is useful for avoiding race conditions when doing slightly more complex updates. It’s not as simple as increment(), but it still avoids full synchronization and lets you write fast, thread-safe code. In fact, even the Ryan and Monica problem could be solved using atomic variables instead of synchronizing the whole method.

-------------------------------------------------------------------------------------
(656)
Compare-and-swap with atomic variables

You can use atomic variables like AtomicInteger along with a method called compareAndSet (CAS) to solve problems like Ryan and Monica’s without using locks. The idea is to say, “Only update the balance if it’s still the value I expect.” 

For example, if you think the balance is $100 and want to spend $50, you tell the atomic variable to change the value to $50 only if it’s still $100. If another thread has already changed the balance, the update won’t go through, and you can decide what to do next (like retrying). 

This approach is called optimistic locking because it assumes things will go fine but handles it if they don’t,  and it’s often faster than locking everything, especially when updates are rare and most threads are just reading.

------------------------------------------------------------------------------------
(657)
Ryan and Monica, going atomic

In this version, the BankAccount uses an AtomicInteger to safely manage the balance without using synchronization. When Ryan or Monica wants to spend money, the method first checks if there's enough balance using get(). If there is, it tries to update the balance using compareAndSet, which will only work if the balance hasn’t changed since it was last read. If another thread (like the other person) spent money in the meantime, the update will fail, and the person is told the money couldn’t be spent. This way, both threads can safely try to spend money without accidentally overdrawing the account, and without needing locks.

------------------------------------------------------------------------------------
(658)
Writing a class for immutable data

An immutable object is an object that cannot be changed after it is created. This means once you set its data, it stays the same forever. In programs where many threads run at the same time, using immutable objects helps avoid problems because no thread can change the shared data by mistake. To make an object immutable, you usually mark its fields as final so they can only be set once, and you don’t provide any methods that change those fields. Also, making the class final stops other classes from changing it.

Making objects immutable is a simple way to keep your program safe when sharing data between threads. Since the data never changes, you don’t have to worry about one thread messing up what another thread is doing. Immutable objects only have methods to get the data, not change it. However, if an immutable object contains other objects, those must also be immutable to keep everything safe and unchangeable.

-----------------------------------------------------------------------------------
(659)
Using immutable objects

When multiple parts of a program (like threads) try to change the same data at the same time, it can cause hard-to-find bugs called race conditions. One way to avoid this is by using immutable data, data that can’t be changed after it’s created. With immutable objects, you don’t have to worry about someone else changing the data while you’re using it, which makes things much safer and simpler. If you need to "update" the data, you don’t actually change it, you just create a new object with the new values. The old version can still be safely used by others, and if it’s no longer needed, the system will clean it up automatically.

-----------------------------------------------------------------------------------
(662)
More problems with shared data

This program creates a list of chat messages (chatHistory) and uses multiple threads to add messages and print the list at the same time. The ConcurrentReaders class starts a thread pool with three threads and repeatedly runs three tasks: one that adds a new Chat object to the list and two that print the current chat history. 

The Chat class is marked final and holds a chat message and the exact time it was created; once a Chat object is made, its data can’t be changed (making it immutable). While immutability keeps each chat message thread-safe, the chatHistory list itself is not thread-safe, so adding and reading at the same time could cause problems like data corruption or crashes if run repeatedly—this shows the importance of using thread-safe collections or synchronization when sharing mutable data like ArrayList between threads.

-----------------------------------------------------------------------------------
(665)
CopyOnWriteArrayList

CopyOnWriteArrayList is a method that helps avoid race conditions by using immutability. Instead of modifying the original list when a thread writes to it, the list creates a new copy with the updated data, and only after the changes are complete does it replace the old version. Meanwhile, any threads that are reading the list continue working with the original, unchanged version, so they aren’t affected by the update. This ensures safe, consistent access to data without the need for manual synchronization. Because it copies the entire list on every write, it works best in situations where there are many reads but few writes.

---------------------------------------------------------------------------------













