package main.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.MonsterTriggers;
import main.Trigger;
import monsters.ClinkMonster;
import monsters.DittaMonster;

class MonsterTriggersTest {

	@Test
	void test() {
		Trigger[] clinkMonsterTriggers = new Trigger[] {Trigger.ONFAINT, Trigger.STARTOFBATTLE};
		ClinkMonster clinkMonster = new ClinkMonster();
	
		Trigger[] clinkTriggers = MonsterTriggers.getTriggers(clinkMonster);
		assertArrayEquals(clinkTriggers, clinkMonsterTriggers);
		
		Trigger[] dittaMonsterTriggers = new Trigger[] {Trigger.ONFAINT, Trigger.STARTOFBATTLE, Trigger.ONHURT};
		DittaMonster dittaMonster = new DittaMonster();
	
		Trigger[] dittaTriggers = MonsterTriggers.getTriggers(dittaMonster);
		assertArrayEquals(dittaTriggers, dittaMonsterTriggers);
	}

}
