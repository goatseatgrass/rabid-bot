package com.github.goatseatgrass.MyFirstBot;

public class MemberAttack {
/*1. Cat
 * 2. Mag
 * 3. Latte
 * 4. Panda
 * 5. Halt
 * 6. Snek
 * 7. Wolf
 */
	
	public static void Dadbot(String[]message) {
		
	}
	
    public static boolean nou(String message) {
		String[]breakdown = message.split(" ");
		boolean shut = false;
		boolean wolf = false;
		for (int i=0; i<breakdown.length; i++) {
			if (breakdown[i].equalsIgnoreCase("shut"))
				{shut = true;}
			if (breakdown[i].equalsIgnoreCase("dog")||breakdown[i].equalsIgnoreCase("wolf")||breakdown[i].equalsIgnoreCase("wolfie"))
			{wolf = true;}
		}
    	return shut&wolf;
    }
    
    public static boolean dad(String message) {
		String[]breakdown = message.split(" ");
		boolean I = false;
		boolean am = false;
		if (breakdown.length>=1){
			if (breakdown[0].equalsIgnoreCase("I"))
				{I = true;}
			if (breakdown[1].equalsIgnoreCase("am"))
				{am = true;}
			if (breakdown[0].equalsIgnoreCase("I'm"))
				{I = true;
				am = true;}
		}
    	return I&am;
    }
    
    public static boolean ann(String message) {
		String[]breakdown = message.split(" ");
		boolean announce = false;
		if (breakdown.length>=1){
			if (breakdown[0].equalsIgnoreCase("!announce"))
				{announce = true;}
		}
    	return announce;
    }
    
    public static String dadAnswer(String message) {
    	String reply = "Hi";
    	String[]breakdown = message.split(" ");
    	if (breakdown[0].equalsIgnoreCase("I'm")) {
    		for (int i=1; i<breakdown.length; i++) {
    			reply+= " " + breakdown[i];
    		}
    	}
    	
    	if (breakdown[0].equalsIgnoreCase("I")) {
    		for (int i=2; i<breakdown.length; i++) {
    			reply+= " " + breakdown[i];
    		}
    	}
    	reply+= ", I'm Cat Whore!";
    return reply;
    }
}
