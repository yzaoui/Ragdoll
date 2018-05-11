An application for manipulating different types of ragdolls using direct manipulation.
Originally written in Java for CS349's Winter 2017 offering, and later converted to Kotlin.

Features:

- Direct manipulation to perform actions on ragdolls:
  - Click and hold a part of the doll, and drag to scale, translate, and rotate part.
- Three types of ragdolls available: Person, Tree, Dog.
  - The tree is randomly generated, where the trunk is translatable and each branch is rotatable.
  - The dog's torso is translatable, its limbs are all rotatable to some degree, its tail and ears are
    stretchable. 
- Menu bar:
  - File menu to reset current ragdoll, and quit application. The keyboard accelerator is
    Ctrl+R for resetting, and Ctrl+Q for quitting.
  - Ragdoll menu to change current ragdoll. The three available ragdolls are a Person, a Tree,
    and a Dog.
