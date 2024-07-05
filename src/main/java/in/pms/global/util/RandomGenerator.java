package in.pms.global.util;

import java.util.Random;

public interface RandomGenerator {

	public static String generateRandom(int ilength_p, int iflag_p) {
	String random = "";
	char [] cLower = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	char [] cSpecial = {'/','@','#','%','$','!','*'};
	char [] cNoSimiliar = {'a','b','h','d','w','f','g','h','x','j','k','a','m','n','y','p','q','r','s','t','u','v','w','x','y','z'};
	char [] cNosimilarNumbers = {'2','3','4','5','6','7','8','9'};
	Random rObj = new Random();
	Random genRandom = new Random();
	int rnum = 0;
	for(int iCounter=0; iCounter<ilength_p; iCounter++){
		if(iflag_p==4){
			rnum = genRandom.nextInt(25);
			random = random+ cLower[rnum];
		}
		else{
			int iCurrentRandom = 0;
			if(iflag_p==5){
				iCurrentRandom = rObj.nextInt(3);
			}
			else{
				iCurrentRandom = rObj.nextInt(iflag_p+1);
			}
			if(iCurrentRandom==0){		//Numeric
				if(iflag_p==5){
					rnum = genRandom.nextInt(8);
					random = random + cNosimilarNumbers[rnum];
				}
				else{
					rnum = genRandom.nextInt(9);
					random = random + rnum;
				}
			}
			if(iCurrentRandom==1){			//Lower case alphabet
				rnum = genRandom.nextInt(25);
				if(iflag_p==5){
					random = random+ cNoSimiliar[rnum];
				}
				else{
					random = random+ cLower[rnum];
				}
			}
			if(iCurrentRandom==2){			//Upper Case alphabet
				rnum = genRandom.nextInt(25);
				if(iflag_p==5){
					random = random + cNoSimiliar[rnum];
				}
				else{
					random = random + Character.toUpperCase(cLower[rnum]);
				}
			}
			if(iCurrentRandom==3){	//Special character
				if(iflag_p==5){
					rnum = genRandom.nextInt(25);
					random = random+ cNoSimiliar[rnum];
				}
				else{
					rnum = genRandom.nextInt(cSpecial.length);
					random = random+ cSpecial[rnum];
				}
			}
			
		}
	}
	return random;
}
}