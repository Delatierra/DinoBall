# DinoBall
This is a java game, using a game library as starting point. The library is from Sven Paniz and the resources are fom various websites.

Game description: DinoBall

Game idea
In DinoBall, the player plays a dinosaur whose goal is to find and collect its five eggs.
and collect them. The dinosaur has to jump onto different platforms and find the right
find the right path to reach all the eggs. During this journey, the dinosaur must
dodge fireballs that damage its life points.

Aim of the game
The main objective of the game is to collect all five eggs while avoiding the fireballs.
dodge the fireballs. The dinosaur starts with 10 life points. On contact with a fireball
the dinosaur loses one life point. The game is lost when all life points
are used up. If all five eggs are collected, the player has won the game.

Controls
The controls are based on the Panitz.game2d library and are controlled using the arrow keys:
- Up arrow: Jump
- Left arrow: Move left
- Right arrow: Move to the right
- Down arrow: Stops the movement
  
Special features and their implementation
- Animations: In my game, the animations are brought to life through the use of
spritesheets to bring them to life. A spritesheet is basically an image file that combines a
frames of an animation in a structured matrix. Each of these
frames captures a certain pose or movement of the character and thus enables
smooth transitions.
The animation is controlled via intelligent frame updating. Here
the aniTick counter plays a central role, as it determines the time between frame changes.
between frame changes. The aniIndex, on the other hand, records the current frame of the animation. This
structure ensures that the animations always run smoothly and in sync with the game speed.
synchronized with the speed of the game.
What is particularly remarkable is that the player character changes its animation state
dynamically, depending on what he is currently doing - be it walking (WALK), jumping (JUMP)
or resting (IDLE). For each of these states there is a separate series of frames in the
spritesheet, which makes the character's movements appear lively and realistic.
realistic.

- Level design: The game uses a string-based level representation that allows you to
easy modification and creation of levels. The level design consists of
Earth and step blocks with the background image of a yellow sunset.

- Collision detection: Collision detection has been implemented to
ensure that the dinosaur avoids fireballs and can jump onto platforms.
can jump onto platforms.

References
- Library Panitz.game2d: The game controls and basic mechanics are based on the library
library provided by the professor Sven Paniz.
- Images: All images used are royalty-free and from the following sources:
o dino.png: https://creationr.itch.io/pet-dino-16-x-16
o egg.gif: https://annivilus.itch.io/pet-eggs
o BG.png: https://dinosdouisen.itch.io/jungle-platformer-game-tileset
o wall.png, wall1.png, wall2.png: https://dinosdouisen.itch.io/jungle-platformergame-tileset
o barrel.gif: https://phdev2024.itch.io/fireball-32x32-gif
