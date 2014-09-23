import java.awt.List;

public class QuestionGen {
	
		public List getQuestion(){			
			int minimum = 1;
			int maximum = 7;
			int rando = minimum + (int)(Math.random()*maximum);
			List ret = new List();
			String question = new String();
			String answer = new String();
			
			if(rando == 1){
				question = "What has roots as nobody sees, " +
						"Is taller than trees " +
						"Up, up it goes, " +
						"And yet never grows?";
				answer = "mountain";				
			}
			if(rando == 2){
				question = "This thing all things devours: " +
						"Birds, beasts, trees, flowers; " +
						"Gnaws iron, bites steel; " +
						"Grinds hard stones to meal; " +
						"Slays king, ruins town, " +
						"And beats high mountain down? ";
				answer = "time";
			}
			if(rando == 3){
				question = "Alive without breath, " +
						"As cold as death; " +
						"Never thirsty, ever drinking, " +
						"All in mail never clinking? ";
				answer = "fish";
			}
			if(rando == 4){
				question = "Thirty white horses on a red hill, " +
						"First they champ, " +
						"Then they stamp, " +
						"Then they stand still ? ";
				answer = "teeth";
			}
			if(rando == 5){
				question = "A box without hinges, key or lid, " +
						"Yet golden treasure inside is hid ? ";
				answer = "egg";
			}
			if(rando == 6){
				question ="Voiceless it cries, " +
						"Wingless flutters, " +
						"Toothless bites, " +
						"Mouthless mutters?";
			    answer = "wind";
			}
			if(rando == 7){
				question = "Speak friend and enter? ";
			    answer = "friend";
			}
			ret.add(question);
			ret.add(answer);
			return ret;			
		}
}
