(449)
Making a MidiEvent (song data)

A MidiEvent is an instruction in a MIDI music program that tells the computer both what to do and when to do it. For example, to play a note, the program first sends a NOTE ON message, which means "start playing this note," and later sends a NOTE OFF message to stop the note. These messages are stored in something called a Message, which says exactly what should happen, like playing Middle C. The MidiEvent links that message to a specific moment in time, telling the program exactly when to play or stop the note. So, to make music with MIDI, you always need a Message to say what to do and a MidiEvent to say when to do it.

-------------------------------------------------------------------------------------------
(450)
MIDI message: the heart of a MidiEvent

A MIDI message is like an instruction that tells the sequencer what to do, such as playing a note. The first part of the message always says what type of action it is, for example, the number 144 means “start playing a note” (NOTE ON). 

But to actually play the note, the sequencer also needs more details: which instrument or channel to use (like drums or piano), which exact note to play (like Middle C or D sharp), and how hard or fast to play it (called velocity). To create this message in code, you make a ShortMessage object and use its setMessage() method, giving it four pieces of information. However, the message itself only says what to do, you still need to put this message inside a MidiEvent, which tells the sequencer exactly when to perform that action.


-------------------------------------------------------------------------------------------
(452)
Version 2: Using command-line args to experiment with sounds

In this version, we use command-line arguments (called args) so you can change the instrument and the note without having to change the code itself. When you run the program, you type two numbers after the program’s name: the first number tells the program which instrument to play (like piano or guitar), and the second number tells it which note to play (like middle C or D sharp). 

This way, you can easily try out different sounds and notes by just typing different numbers each time you run the program. The program reads these numbers from args, converts them from text into numbers, and then uses them to play the music you want. If you forget to enter both numbers, the program reminds you to include them so it knows what to play.

-------------------------------------------------------------------------------------------