# MyShelfie Board Game - Software Engineering Project

<img src="https://www.craniocreations.it/storage/media/products/54/112/My_Shelfie_box_ITA-ENG.png" width="260" align="right" />

MyShelfie Board Game is the final test of **"Software Engineering"** course of **"Engineering of Computing Systems"** held at Politecnico di Milano (2022/2023).

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
- Only for CLI:
  - Run the `chcp 65001` command in the CMD. This enables UTF-8 encoding.

### Instructions
1. Clone this repository:
    ```shell
   git clone https://github.com/IoSonoDue2/ing-sw-2023-Gennaro-Poggi-Giliberto-Guerrini.git
   ```
2. Move to the repository folder.
3. Build the client package and move it from `target` to a new directory:

    ```shell
    mvn clean package -P client
    ```
4. Build the server package and move it from `target` to a new directory:

    ```shell
    mvn clean package -P server
    ```
5. Move to the new directory and execute the server and a client:
    ```shell
    java -jar server.jar
    java -jar client.jar
    ```
