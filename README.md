# MyShelfie Board Game - Software Engineering Project

<img src="https://www.craniocreations.it/storage/media/products/54/112/My_Shelfie_box_ITA-ENG.png" width="260" align="right" />

MyShelfie Board Game is the final test of **"Software Engineering"** course of **"Engineering of Computing Systems"** held at Politecnico di Milano (2022/2023).

Final Grade: 30L/30L

**Teacher**: Pierluigi San Pietro

## The Team
* [Valentino Guerrini](https://github.com/IoSonoDue2)
* [Marta Giliberto](https://github.com/marta23gili)
* [Patrick Poggi](https://github.com/PatrickPoggi)
* [Paolo Gennaro](https://github.com/zFireFist01)

## Project specification
The project consists of a Java version of the board game *MyShelfie*, made by Cranio Creations. You can find the real game [here](https://www.craniocreations.it/prodotto/my-shelfie).

Project requirements: [link](https://github.com/IoSonoDue2/ing-sw-2023-Gennaro-Poggi-Giliberto-Guerrini/tree/main/Resources/Requisiti.pdf?raw=true).

## Implemented functionalities

### Main functionalities
| Functionality                    | Status |
|:---------------------------------|:------:|
| Complete rules                   |   ✅    |
| RMI                              |   ✅    |
| Socket                           |   ✅    |
| CLI _(Command Line Interface)_   |   ✅    |
| GUI _(Graphical User Interface)_ |   ✅    |


### Advanced functionalities
| Functionality                | Status |
|:-----------------------------|:------:|
| Chat                         |   ✅    |
| Simultaneous games           |   ✅    |
| Persistence                  |   ⛔    |
| Resilience to disconnections |   ✅    |


⛔ Not implemented(or Work in Progress) &nbsp;&nbsp;&nbsp;&nbsp; ✅ Implemented


## Usage

### Requirements

Regardless of the operating system, you must have installed the following programs:
- Java 17
- Maven []

#### Windows
On Windows it is needed to:
- Set system visual scaling to 100%.

### Compile Instructions
1. Clone this repository:
    ```shell
   git clone https://github.com/IoSonoDue2/ing-sw-2023-Gennaro-Poggi-Giliberto-Guerrini.git
   ```
2. Move to the repository folder.
3. Build the code with maven and move the jar files from `target` to a new directory of your choice:
    ```shell
    mvn clean package 
    ```
4. Move to the that directory and execute the server and/or the client:
    ```shell
    java -jar Server.jar
    java -jar Client.jar
    ```
    
### Run Instructions
1. Clone this repository:
    ```shell
   git clone https://github.com/IoSonoDue2/ing-sw-2023-Gennaro-Poggi-Giliberto-Guerrini.git
   ```
2. Move to the repository folder.

3. Move to the directory "deliverables\final\jar" and execute the server and a client:
    ```shell
    java -jar Server.jar
    java -jar Client.jar
    ```
