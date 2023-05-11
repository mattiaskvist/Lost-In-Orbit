# Lost in Orbit

**Project description**

![alt text](https://cdn.discordapp.com/attachments/499631203452977173/1106308837625057411/image.png)

- Name: Lost in Orbit

Lost in Orbit is a 2D platformer game where the player has to make their way to their ship in a solar system after colliding with an asteroid. The game will be developed in Java using the Swing framework in order to create a Game Window and a Game Panel with the help of JFrame, JPanel and Graphics. Lost in Orbit includes enemies to defeat in order to make your way through the game as well as having collectible items on each stage. The game keeps track of the time taken to complete the game as well as counting the amount of collectibles than the player has collected during their playthtough. There are currently three planets with three levels each, adding up to a total of nine levels. The first stage of each planet is marked as a checkpoint, meaning that if the player dies on a specific planet they will have to start over from the first level of the current planet. 

![alt text](https://cdn.discordapp.com/attachments/499631203452977173/1106309288059748432/image.png)

The project is divided into different packages containing multiple classes with different functionalities. Some examples contain the main package which is responsible for the game classes such as the GameWindow, GamePanel and Game class while the inputs package handles keyboard and mouse inputs. 

Current packages:

- buttons (Manages UI buttons such as the pause button)
- entities (Manages players and enemies)
- inputs (Handles keyboard & mouse inputs)
- levels (Manages levels)
- main (The main classes)
- utilz (Resource imports)
- menus (Manages menu elements such as the main menu)

The project also uses free assets from itch.io such as the player/enemy sprites, the tiles used for the level design and potentially music. 
Current assets used: 

https://pixelfrog-assets.itch.io/treasure-hunters

https://cainos.itch.io/pixel-art-platformer-village-props

Collaborators: Nima Cheraghi & Mattias Kvist

TA: Ludwig Franklin

# Documentation and instructions

Lost in orbit utilizes keyboard inputs in order for the player to navigate around inside the game as well as being able to attack the enemies. The controls can be found in the main menu and pause screen. 
**Controls**

Combat & Movement
- A | Move left
- D | Move right
- W & Spacebar | Jump
- I | Attack

Pause/Menu/Game Over screen/Win screen
- ESC | Pause game
- W & S | Navigate inside the menu
- Enter | Select current highlighted option

The game is set to 60 FPS on launch in case of performance issues. You can set the FPS to 120 in options inside the Main Menu or the Pause screen if your computer can handle it well.

**Installation**

Java is required in order to run the game on your computer. If you do not have java installed on your computer, you can download it from: https://www.oracle.com/java/technologies/downloads/

If you have installed java on your computer, follow the next steps:

- On the GitHub page, click on "Code" and then press "Download ZIP" in order to download the files to your computer.
![alt text](https://cdn.discordapp.com/attachments/499631203452977173/1106314104303517706/image.png)
- After installing the ZIP folder, extract all files and you should have a folder called "Lost-in-Orbit-main"
- Enter the folder until you see the files inside.
![alt text](https://cdn.discordapp.com/attachments/499631203452977173/1106314529891156068/image.png)
- To the launch the game, press the lost_in_orbit executable jar file and you should now be able to play the game.

 **NOTE: DO NOT MOVE THE JAR FILE OUTSIDE OF THE FOLDER, OTHERWISE THE GAME WILL NOT BE ABLE TO LAUNCH**. 
