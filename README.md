# Java Chat Application

[![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/Java%20Swing-5382A1?logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Socket.IO](https://img.shields.io/badge/Java%20Networking-0F52BA?logo=oracle&logoColor=white)](https://docs.oracle.com/javase/8/docs/api/java/net/package-summary.html)
[![NetBeans](https://img.shields.io/badge/NetBeans-1B75BC?logo=apachenetbeanside&logoColor=white)](https://netbeans.apache.org/)

![WhatsApp Image 2025-10-28 at 12 23 29_0b42facc](https://github.com/user-attachments/assets/2b71ab3b-d3d6-4c9c-b33d-4642d201a317)


## Overview

This project is a desktop chat application that demonstrates a classic client-server architecture using core Java sockets. The UI is built with Swing components and styled to resemble a modern messaging client. The application showcases how to maintain a persistent connection between two peers, render message bubbles with timestamps, and broadcast updates to the GUI in real time.

## Key Features

- Real-time messaging over TCP using `ServerSocket`, `Socket`, and UTF streams.
- Swing-based layout with custom-styled message bubbles and status indicators.
- Timestamping of every message using `SimpleDateFormat`.
- Graceful shutdown via UI controls.
- Lightweight dependency footprint—relies solely on the JDK and bundled assets.

## Architecture

- **Server (`src/chatting/application/Server.java`)**: Accepts a single client connection, listens for messages, and updates the UI on receipt.
- **Client (`src/chatting/application/Client.java`)**: Connects to the server, sends messages from the UI, and renders responses.
- **Shared UI Helpers**: `formatLabel` centralizes message bubble formatting so that both outbound and inbound messages share consistent styling.
- **Assets (`src/chatting/icons/`)**: PNG icons referenced via the classpath to enhance the chat header visuals.

## Requirements

- Java Development Kit (JDK) 8 or newer
- PowerShell (default on Windows) or any terminal capable of running `javac` and `java`
- Optional: Apache NetBeans for IDE-based development (`nbproject` metadata is provided)

## Setup

1. Clone or download the repository.
2. Ensure the JDK binaries (e.g., `javac`, `java`) are on your `PATH`.
3. From the project root (`C:\Users\PARAS\javachatapp`), compile the sources:

   ```powershell
   javac -d build\classes -cp src src\chatting\application\Server.java src\chatting\application\Client.java
   ```

   The command outputs `.class` files into `build\classes` while keeping resource lookups intact via the `src` directory.

## Running the Application

Open two terminal windows.

1. **Start the server**:

   ```powershell
   java -cp "build\classes;src" chatting.application.Server
   ```

2. **Launch the client** in the second terminal:

   ```powershell
   java -cp "build\classes;src" chatting.application.Client
   ```

3. Interact with the GUI to send messages between the two peers.

> Tip: Re-run the compilation step whenever you change a source file.

## Project Structure

```
src/
  chatting/application/
	 Client.java      # Client-side UI and socket handling
	 Server.java      # Server-side UI and socket handling
  chatting/icons/    # PNG assets referenced by the UI
build/classes/       # Generated .class files (after compilation)
nbproject/           # NetBeans project metadata
```

## Customization Ideas

- Add user authentication or multiple concurrent clients using threads.
- Persist chat history to disk or a database.
- Enhance the UI with additional themes or emoji support.
- Introduce typing indicators or message delivery receipts.

## Troubleshooting

- **`java.lang.ClassNotFoundException`**: Verify the `-cp "build\classes;src"` argument includes both compiled classes and resource directories.
- **Icons missing**: Confirm the `src` directory remains on the classpath so `ClassLoader.getSystemResource` can resolve the PNG files.
- **Port conflicts**: Change the port number in both `Server.java` and `Client.java` if port `6001` is already in use.

## License

This project does not currently specify a license. Consider adding one to clarify usage rights.
