# Monster Fighter
A monster fighting game for SENG201-2022 by Jackie Jone and Harrison Tyson

## Import into Eclipse
 - Open `Eclipse`
 - `File > Import > Existing Projects into Workspace`
 - Select `Next`
 - Go to `Select root directory > Browse`
 - Open project `hty13_jjo134_MonsterFighter` - make sure it is selected under  `Projects`
 - Select `Finish`

The source code should now be inside Eclipse.

## Building Source Code

Once you have the source code in Eclipse in order to build as a .jar

 - Right-Click on the project `hty13_jjo134_MonsterFighter` from `Project Explorer`
 - Select `Export`
 - Select `Java > Runnable JAR file`
 - Select `Next`
 - Under `Launch configuration` select the `Main` class
 - Set an export destination
 - Under `Library handling` select `Extract required libraries into generated JAR`
 - Select `Finish`

 You should now have an executable .jar file of the program.

## Running the program
Once you have an executable .jar of the program

 - Install `Java 17` if not already

### Using the supplied JAR file
 - Open the `.zip` file in a new Terminal
 - Run `java -jar hty13_jjo134_MonsterFighter.jar`

### Using your exported JAR file
- Open the export directory in a new Terminal
- Run `java -jar <name of exported JAR>`

## Additional Information

### Monsters
There are 6 different Monsters in the game.
Each monster has the following stats:
- Attack - amount of damage dealt in battle
- Health - amount of damage it can receive before dying
- Speed - when battling the monster with the highest speed attacks first

Alongside this each monster has a special ability that can trigger during certain events in battle
The possible events are as follows:
- NOABILITY - the ability will never trigger
- STARTOFBATTLE - triggers once when the battle starts
- BEFOREATTACK - triggers every time before a monster attacks
- AFTERATTACK - triggers every time after a monster attacks
- ONHURT - triggers every time a monster receives damage (but doesn't faint)
- ONFAINT - triggers once when a monster faints

#### Types of Monsters

**Clink**: COMMON
	- **Ability:** Loses 1 Attack, gains 1 Health

**Ditta**: COMMON
	- **Ability**: Copies the stats of the first enemy

**Gil**: RARE
	- **Ability**: Boosts the attack of the ally behind it by 50% of it's attack

**Jynx**: RARE
	- **Ability**: Copies the health of the healthiest ally

**Lucifer**: LEGENDARY
	- **Ability**: Swaps it's attack and health stats

**Teddy**: LEGENDARY
	- **Ability**: Randomly heals an ally for 1 health

### Items
There are 6 different key items in the game. Each item has a rarity which determines it's cost
and effectiveness

A **COMMON stat boost** will boost a stat by **1**
A **RARE stat boost** will boost a stat by **3**
A **LEGENDARY stat boost** will boost a stat by **5**

In addition to this there are two extra items:
- **Random Trigger**
A Random Trigger item is a **rare** item which randomly assigns the ability trigger of the
monster it is applied to
- **Select Trigger**
- A Select Trigger item is a **legendary** item that specifies what ability trigger it will give
- to the monster it is applied to

## References
### Images
#### Monsters
[Clink Monster](https://publicdomainvectors.org/en/free-clipart/Purple-monster/37504.html): publicdomainvectors

[Ditta Monster](https://publicdomainvectors.org/en/free-clipart/Vector-graphics-of-blue-cartoon-creature/29376.html): publicdomainvectors

[Gil Monster](https://pixabay.com/vectors/logo-monster-cartoon-technology-5492440/): pixabay

[Jynx Monster](https://freesvg.org/monster-02): freesvg

[Lucifer Monster](https://publicdomainvectors.org/en/free-clipart/Red-flying-monster/81412.html): publicdomainvectors

[Teddy Monster](https://publicdomainvectors.org/en/free-clipart/Cute-toothless-monster/81416.html): freesvg

#### Items
[All Items](https://freesvg.org/potions): freesvg

#### Misc
[Battle Symbol](https://freesvg.org/zeimusu-crossed-swords): freesvg