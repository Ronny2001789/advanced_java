(674)
Final BeatBox client program

Part 1
------
We are importing the necessary libraries for sound (MIDI), networking, and the user interface, then creating the BeatBoxFinal class. Inside the class, we set up parts of the graphical interface like checkboxes, message areas, and a list to show incoming messages. We also define instruments and their MIDI codes so users can create beats, and set up networking tools (like input/output streams and maps) to allow users to share and play each other’s beat patterns. The program prepares to use a MIDI sequencer to handle music playback.

-----------------------------------------------------------------------------------------
(675)
Part 2
-------

In the main method, the program starts by creating an instance of the BeatBoxFinal class and calling the startUp method, passing in the user's name from the command-line argument. The startUp method saves the user's name, then tries to connect to a server at 127.0.0.1 on port 4242. If the connection is successful, it sets up input and output streams for sending and receiving data, and starts a background thread (RemoteReader) to listen for incoming messages. If the connection fails, it prints a message saying the user will play alone. After handling the network connection, it initializes the MIDI setup and calls buildGUI() to create the visual interface.

The buildGUI method creates the main window for the BeatBox using Swing. It sets up the layout, adds a vertical box with buttons for starting and stopping the beat, changing the tempo, and sending messages. There's also a text area where users can type a message to send along with their beat pattern. This interface allows the user to control playback and interact with others in a simple, music-focused UI.

-------------------------------------------------------------------------------------------
(676)
Part 3
------
A list is created to show incoming messages, with a listener that responds when the user selects an item. The list is placed inside a scrollable pane and added to the button panel. Alongside, labels for each instrument name are added vertically on the left side of the window. The main window layout arranges these components with the list on the right and instrument names on the left.

A 16 by 16 grid panel is created in the center, filled with 256 checkboxes that let the user select which beats to play for each instrument. The window size is set, packed to fit the components, and then made visible. The setUpMidi method initializes the MIDI sequencer, opens it, creates a new music sequence and track, and sets the tempo to 120 beats per minute to prepare for music playback.

------------------------------------------------------------------------------------------
(677)
Part 4
------
The buildTrackAndStart method creates a new music track based on which checkboxes are selected. It goes through each instrument and each beat position, checking if the corresponding checkbox is selected. For each selected box, it adds the instrument’s MIDI code to a list, or null if the beat should be silent. Then it uses these lists to build the MIDI track. After setting up all the instruments, it configures the sequencer to play the sequence continuously at 120 beats per minute and starts playback.

The changeTempo method adjusts the playback speed by multiplying the current tempo by a given factor, allowing the user to speed up or slow down the beat. The sendMessageAndTracks method gathers the current state of all checkboxes into a boolean array to represent the beat pattern and sends it along with the user’s message to the server. If there’s a problem sending data, it prints an error message. After sending, the user’s message input area is cleared and ready for new text.

------------------------------------------------------------------------------------------
(678)
Part 5
-------
The MyListSelectionListener class listens for when the user selects an item from the incoming messages list. When a selection is made, it gets the corresponding saved beat pattern from a map and updates the checkboxes to match that pattern using the changeSequence method. It then stops the current sequencer and rebuilds the track to play the newly selected beat.

The makeTracks method takes a list of instrument keys and creates MIDI events for each one that should be played, adding “note on” and “note off” events to the track at the correct times. The helper method makeEvent creates these individual MIDI events using the given command, channel, and timing details, handling any exceptions that might occur.

-------------------------------------------------------------------------------------------
(679)
Part 6
-------
The RemoteReader class runs in a separate thread and continuously listens for objects sent from the server. When it receives an object, it expects the first to be a user name (a string) and the second to be a boolean array representing that user’s beat pattern. It saves this data in a map and updates the list of incoming messages shown in the user interface, so the user can see and select beats shared by others. If any errors occur while reading data, it prints the error details.

NB!! - Java allows you to catch multiple exception types with the same catch block using a Multi-catch separator (|). In this case either catch an IOException or a ClassNotFoundException, and handle both of them using the same code block.

------------------------------------------------------------------------------------------
(681)
Final BeatBox server program

Part 1
-------
The MusicServer class sets up a server that listens for connections from multiple clients on port 4242. When a client connects, it creates an output stream to send data back to that client and adds it to a list of client streams. The server uses a thread pool to handle each client connection separately, allowing multiple clients to connect and communicate at the same time. Each time a new client connects, the server prints a message to confirm the connection.

The tellEveryone method sends two objects (usually a username and a beat pattern) to every connected client by writing the objects to all the stored output streams. This way, all clients receive updates from each other through the server. If an error occurs while sending data to any client, the error is printed, but the server continues running and sending to others.

-----------------------------------------------------------------------------------------
(682)
Part 2
-------
The ClientHandler class manages communication with a single client. When created, it sets up an input stream to receive data from the client’s socket. This allows the server to listen for objects sent by that client, such as their username and beat pattern.

In the run method, the handler continuously reads pairs of objects from the client: first the username, then the beat sequence. After reading each pair, it calls tellEveryone to broadcast these objects to all connected clients. If the connection is lost or an error occurs while reading, it prints the error and stops handling that client.

-------------------------------------------------------------------------------------------