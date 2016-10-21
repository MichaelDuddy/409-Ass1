import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Countdown {
	
	static Scanner sc = new Scanner(System.in);

	static int gameMode = 1; //1 = Single; 2 = Multi;
	
	static int nLet;
	static int nNum;
	static int nCon;
	
	static Player p1;
	static Player p2;
	
	static int delay = 30000;
	
	static String hsName;
	static int hScore;
	
	public static void main(String[] args) {
		getHighscore();
		System.out.println("Welcome to Countdown 0.3 Alpha");
		System.out.println("1. New Game\n2. Resume previous game\n3. Quit");
		boolean first = false;
		while(!first){
			String game = sc.nextLine();
			if(game.equals("1")){
				System.out.println("1. Single-Player\n2. Multi-Player\n3. Quit");
				boolean second = false;
				String mode = sc.nextLine();
				while(!second){
					if(mode.equals("1")){
						gameMode = 1;
						getDesiredRounds();
						getPlayerNames();
						second = true;
					} else if(mode.equals("2")){
						gameMode = 2;
						getDesiredRounds();
						getPlayerNames();
						second = true;
					} else if(mode.equals("3")){
						System.exit(0);
					}
				}
				first = true;
			} else if(game.equals("2")){
				System.out.println("Resuming");
				first = true;
			} else if(game.equals("3")){
				System.exit(0);
			}
			else 
				System.out.println("Your input did not meet the requirements. Try again.");
		}
		playGame();
		System.out.println("\n--------------------------------------\nThe game is over, and the final score is :");
		printScore();
		saveHighscore();
		sc.close();
	}
	
	public static void playGame(){
		int playerToChoose = 1;
		int letCount = 1;
		while(nLet > 0){
			System.out.println("\n[LETTER ROUND #"+letCount+"]\n");
			playLettersRound(playerToChoose);
			if(gameMode == 2)
				playerToChoose++;
			nLet--;
			System.out.println("After this round, the score is : ");
			printScore();
			letCount++;
		}
		playerToChoose = 1;
		int numCount = 1;
		while(nNum > 0){
			System.out.println("\n[NUMBER ROUND #"+numCount+"]\n");
			playNumbersRound(playerToChoose);
			if(gameMode == 2)
				playerToChoose++;
			nNum--;
			System.out.println("After this round, the score is : ");
			printScore();
			numCount++;
		}
		int conCount = 1;
		while(nCon > 0){
			System.out.println("\n[CONUNDRUM ROUND #"+conCount+"]\n");
			playConundrumRound();
			conCount++;
			nCon--;
			System.out.println("After this round, the score is : ");
			printScore();
		}
	}
	
	public static void playLettersRound(int playerToChoose){
		if(playerToChoose%2 != 0){
			System.out.println(p1.getName()+", it is your turn to choose the letters !");
		} else {
			System.out.println(p2.getName()+", it is your turn to choose the letters !");
		}
		Letters let = new Letters();
		timer();
		if(gameMode == 2){
			if(playerToChoose%2 != 0){
				System.out.println(p1.getName()+", how long is your word ?");
				int lp1 = sc.nextInt();
				System.out.println(p2.getName()+", how long is yours ?");
				int lp2 = sc.nextInt();
				if(lp1 < lp2){
					System.out.println(p1.getName()+", what is your word ?");
					String word1 = sc.next();
					System.out.println("And yours, "+p2.getName()+" ?");
					String word2 = sc.next();
					if(let.isValidAnswer(word2)){
						System.out.println(p2.getName()+"'s answer is valid and since it is longer than "+p1.getName()+"'s, he is awarded "+let.getPoints(word2));
						p2.addPoints(let.getPoints(word2));
					} else {
						System.out.println("Although "+p2.getName()+"'s answer is longer, it is not a valid answer.");
						if(let.isValidAnswer(word1)){
							System.out.println(p1.getName()+"'s answer is, however, valid and gets "+p1.getName()+" "+let.getPoints(word1)+" points.");
							p1.addPoints(let.getPoints(word1));
						} else {
							System.out.println("Unfortunately, "+p1.getName()+"'s answer is not valid either, so nobody gets any points.");
						}
					}
				} else if(lp1 > lp2){
					System.out.println(p2.getName()+", what is your word ?");
					String word2 = sc.next();
					System.out.println("And yours, "+p1.getName()+" ?");
					String word1 = sc.next();
					if(let.isValidAnswer(word1)){
						System.out.println(p1.getName()+"'s answer is valid and since it is longer than "+p2.getName()+"'s, he is awarded "+let.getPoints(word1));
						p1.addPoints(let.getPoints(word1));
					} else {
						System.out.println("Although "+p1.getName()+"'s answer is longer, it is not a valid answer.");
						if(let.isValidAnswer(word2)){
							System.out.println(p2.getName()+"'s answer is, however, valid and gets "+p2.getName()+" "+let.getPoints(word2)+" points.");
							p2.addPoints(let.getPoints(word2));
						} else {
							System.out.println("Unfortunately, "+p2.getName()+"'s answer is not valid either, so nobody gets any points.");
						}
					}
				} else if(lp1 == lp2){
					System.out.println("Since they are of the same length, "+p1.getName()+", what is your word ?");
					String word1 = sc.next();
					System.out.println("And yours, "+p2.getName()+" ?");
					String word2 = sc.next();
					if(let.isValidAnswer(word1)){
						System.out.println(p1.getName()+"'s answer is valid and gets them "+let.getPoints(word1)+"points");
						p1.addPoints(let.getPoints(word1));
					} else {
						System.out.println(p1.getName()+"'s answer is not valid and gets them no points");
					}
					if(let.isValidAnswer(word2)){
						System.out.println(p2.getName()+"'s answer is valid and gets them "+let.getPoints(word2)+"points");
						p2.addPoints(let.getPoints(word2));
					} else {
						System.out.println(p2.getName()+"'s answer is not valid and gets them no points");
					}
				}
			} else {
				System.out.println(p2.getName()+", how long is your word ?");
				int lp2 = sc.nextInt();
				System.out.println(p1.getName()+", how long is yours ?");
				int lp1 = sc.nextInt();
				if(lp1 < lp2){
					System.out.println(p1.getName()+", what is your word ?");
					String word1 = sc.next();
					System.out.println("And yours, "+p2.getName()+" ?");
					String word2 = sc.next();
					if(let.isValidAnswer(word2)){
						System.out.println(p2.getName()+"'s answer is valid and since it is longer than "+p1.getName()+"'s, he is awarded "+let.getPoints(word2));
						p2.addPoints(let.getPoints(word2));
					} else {
						System.out.println("Although "+p2.getName()+"'s answer is longer, it is not a valid answer.");
						if(let.isValidAnswer(word1)){
							System.out.println(p1.getName()+"'s answer is, however, valid and gets "+p1.getName()+" "+let.getPoints(word1)+" points.");
							p1.addPoints(let.getPoints(word1));
						} else {
							System.out.println("Unfortunately, "+p1.getName()+"'s answer is not valid either, so nobody gets any points.");
						}
					}
				} else if(lp1 > lp2){
					System.out.println(p2.getName()+", what is your word ?");
					String word2 = sc.next();
					System.out.println("And yours, "+p1.getName()+" ?");
					String word1 = sc.next();
					if(let.isValidAnswer(word1)){
						System.out.println(p1.getName()+"'s answer is valid and since it is longer than "+p2.getName()+"'s, he is awarded "+let.getPoints(word1));
						p1.addPoints(let.getPoints(word1));
					} else {
						System.out.println("Although "+p1.getName()+"'s answer is longer, it is not a valid answer.");
						if(let.isValidAnswer(word2)){
							System.out.println(p2.getName()+"'s answer is, however, valid and gets "+p2.getName()+" "+let.getPoints(word2)+" points.");
							p2.addPoints(let.getPoints(word2));
						} else {
							System.out.println("Unfortunately, "+p2.getName()+"'s answer is not valid either, so nobody gets any points.");
						}
					}
				} else if(lp1 == lp2){
					System.out.println("Since they are of the same length, "+p2.getName()+", what is your word ?");
					String word2 = sc.next();
					System.out.println("And yours, "+p1.getName()+" ?");
					String word1 = sc.next();
					if(let.isValidAnswer(word2)){
						System.out.println(p2.getName()+"'s answer is valid and gets them "+let.getPoints(word2)+"points");
						p2.addPoints(let.getPoints(word2));
					} else {
						System.out.println(p2.getName()+"'s answer is not valid and gets them no points");
					}
					if(let.isValidAnswer(word1)){
						System.out.println(p1.getName()+"'s answer is valid and gets them "+let.getPoints(word1)+"points");
						p1.addPoints(let.getPoints(word1));
					} else {
						System.out.println(p1.getName()+"'s answer is not valid and gets them no points");
					}
				}
			}
		} else if(gameMode == 1) {
			System.out.println(p1.getName()+", what is your word ?");
			String word1 = sc.nextLine();
			if(let.isValidAnswer(word1)){
				System.out.println("That is a valid answer, "+p1.getName()+" and it gets you "+let.getPoints(word1)+" points.");
				p1.addPoints(let.getPoints(word1));
			} else {
				System.out.println("That is not a valid answer. You get no points.");
			}
		}
	}
	
	public static void playNumbersRound(int playerToChoose){
		if(playerToChoose%2 != 0){
			System.out.println(p1.getName()+", it is your turn to choose the numbers !");
		} else {
			System.out.println(p2.getName()+", it is your turn to choose the numbers !");
		}
		Numbers num = new Numbers();
		timer();
		if(gameMode == 2){
			if(playerToChoose%2 != 0){
				System.out.println(p1.getName()+", what is your solution ?");
				int ans1 = sc.nextInt();
				System.out.println("And yours, "+p2.getName());
				int ans2 = sc.nextInt();
				if(num.getDistance(ans1) < num.getDistance(ans2)){
					System.out.println(p1.getName()+", is closer. What is your solution ?");
					String sol1 = sc.next();
					if(num.isValidAnswer(sol1)){
						if(num.getPoints(sol1)!=0){
							System.out.println("That is a valid answer and gets you "+num.getPoints(sol1)+" points, "+p1.getName());
							p1.addPoints(num.getPoints(sol1));
						}
						else 
							System.out.println("Although it is a valid answer, it is not close enough to the target. You get no points.");
					} else {
						System.out.println("Unfortunately, "+p1.getName()+"'s answer is not valid in respect to the rules of the game.\n"+p2.getName()+",what is your solution ?");
						String sol2 = sc.next();
						if(num.isValidAnswer(sol2)){
							if(num.getPoints(sol2)!=0){
								System.out.println("That is a valid answer and gets you "+num.getPoints(sol2)+" points, "+p2.getName());
								p2.addPoints(num.getPoints(sol2));
							}
							else
								System.out.println("Although it is a valid answer, it is not close enough to the target. You get no points.");
						} else {
							System.out.println("Unfortunately, your answer is not valid in respect to the rules either. No points awarded");
						}
					}
				} else if(num.getDistance(ans1) > num.getDistance(ans2)){
					System.out.println(p2.getName()+", is closer. What is your solution ?");
					String sol2 = sc.next();
					if(num.isValidAnswer(sol2)){
						if(num.getPoints(sol2)!=0){
							System.out.println("That is a valid answer and gets you "+num.getPoints(sol2)+" points, "+p2.getName());
							p2.addPoints(num.getPoints(sol2));
						}
						else 
							System.out.println("Although it is a valid answer, it is not close enough to the target. You get no points.");
					} else {
						System.out.println("Unfortunately, "+p2.getName()+"'s answer is not valid in respect to the rules of the game.\n"+p1.getName()+",what is your solution ?");
						String sol1 = sc.next();
						if(num.isValidAnswer(sol1)){
							if(num.getPoints(sol1)!=0){
								System.out.println("That is a valid answer and gets you "+num.getPoints(sol1)+" points, "+p1.getName());
								p1.addPoints(num.getPoints(sol1));
							}
							else
								System.out.println("Although it is a valid answer, it is not close enough to the target. You get no points.");
						} else {
							System.out.println("Unfortunately, your answer is not valid in respect to the rules either. No points awarded");
						}
					}
				} else if(num.getDistance(ans1) == num.getDistance(ans2)){
					System.out.println("You are both equally close to the target !");
					System.out.println(p1.getName()+", what is your solution ?");
					String sol1 = sc.next();
					System.out.println("And yours, "+p2.getName()+" ?");
					String sol2 = sc.next();
					if(num.isValidAnswer(sol1)){
						if(num.getPoints(sol1)!=0){
							System.out.println("That is a valid answer and gets you "+num.getPoints(sol1)+" points, "+p1.getName());
							p1.addPoints(num.getPoints(sol1));
						}
						else
							System.out.println("Although it is a valid answer, it is not close enough to the target. You get no points.");
					} else {
						System.out.println("Unfortunately, your answer is not valid in respect to the rules of the game. No points awarded");
					}
					if(num.isValidAnswer(sol2)){
						if(num.getPoints(sol2)!=0){
							System.out.println("That is a valid answer and gets you "+num.getPoints(sol2)+" points, "+p2.getName());
							p2.addPoints(num.getPoints(sol2));
						}
						else
							System.out.println("Although it is a valid answer, it is not close enough to the target. You get no points.");
					} else {
						System.out.println("Unfortunately, your answer is not valid in respect to the rules either. No points awarded");
					}
				}
			}
		} else if(gameMode == 1){
			System.out.println("What is your solution, "+p1.getName()+" ?");
			String sol1 = sc.nextLine();
			if(num.isValidAnswer(sol1)){
				if(num.getPoints(sol1)!=0){
					System.out.println("That is a valid answer and gets you "+num.getPoints(sol1)+" points, "+p1.getName());
					p1.addPoints(num.getPoints(sol1));
				}
				else
					System.out.println("Although it is a valid answer, it is not close enough to the target. You get no points.");
			} else {
				System.out.println("Unfortunately, your answer is not valid in respect to the rules of the game. No points awarded");
			}
		}
	}
	
	public static void playConundrumRound(){
		Conundrum cd = new Conundrum();
		cd.generateAnagram();
		System.out.println("Your conundrum is : "+cd.anagram+". You now have 30 seconds to solve it.");
		timer();
		System.out.println("What is your answer ?");
		String ans = sc.nextLine();
		if(cd.checkAnswer(ans)){
			System.out.println("That is correct ! You have been awarded 10 points.");
			p1.addPoints(10);
			if(gameMode == 2)
				p2.addPoints(10);
		} else {
			System.out.println("Your solution is incorrect and no points have been awarded.");
		}
	}
	
	public static void getDesiredRounds(){
		System.out.println("How many Letter rounds would you like to have ?");
		String rounds = sc.nextLine();
		if(Integer.parseInt(rounds) > 0){
			nLet = Integer.parseInt(rounds);
		} else {
			System.out.println("Not a valid number or some shenanigan. Program exiting.");
			System.exit(0);
		}
		
		System.out.println("What about Number rounds ?");
		rounds = sc.nextLine();
		if(Integer.parseInt(rounds) > 0){
			nNum = Integer.parseInt(rounds);
		} else {
			System.out.println("Not a number or some shenanigan. Program exiting.");
			System.exit(0);
		}
		
		System.out.println("And Conundrum ?");
		rounds = sc.nextLine();
		if(Integer.parseInt(rounds) > 0){
			nCon = Integer.parseInt(rounds);
		} else {
			System.out.println("Not a number or some shenanigan. Program closing.");
			System.exit(0);
		}
	}
	
	public static void getPlayerNames(){
		if(gameMode == 1){
			System.out.println("Could you tell me your name ?");
			String name = sc.nextLine();
			p1 = new Player(name);
			System.out.println("Hello, "+name+"!");
		} else if(gameMode == 2){
			System.out.println("Could you tell me your names ?\nPlayer 1 :");
			String name = sc.nextLine();
			p1 = new Player(name);
			System.out.println("Player 2:");
			name = sc.nextLine();
			p2 = new Player(name);
		}
	}
	
	public static void timer(){
		try {
			Thread.sleep(delay);
		}
		catch (InterruptedException ex) 
		{ 
			//nada
		}
	}
	
	public static void printScore(){
		if(gameMode == 1){
			System.out.println(p1.getName()+" : "+p1.getScore());
		} else if(gameMode == 2){
			System.out.println(p1.getName()+" : "+p1.getScore()+"\n"+p2.getName()+" - "+p2.getScore());
		}
	}
	
	public static void getHighscore(){
		try{
			File file = new File("highscore.txt");
			if(file.exists()){
				BufferedReader r = new BufferedReader(new FileReader("highscore.txt"));
				String line = r.readLine();
				String splitLine[] = line.split(":");
				hsName = splitLine[0];
				hScore = Integer.parseInt(splitLine[1]);
				r.close();
			}
		} catch (Exception e){
			//nada
		}
	}
	
	public static void saveHighscore(){
		try {
			File file = new File("highscore.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter writer = new FileWriter("highscore.txt");
			BufferedWriter bw = new BufferedWriter(writer);
			if(gameMode == 2){
				if(p1.getScore() >= p2.getScore()){
					if(p1.getScore() > hScore)
						bw.write(p1.getName()+":"+p1.getScore());
				} else if(p2.getScore() > p1.getScore()){
					if(p2.getScore() > hScore)
						bw.write(p2.getName()+":"+p2.getScore());
				}
			} else {
				if(p1.getScore() > hScore)
					bw.write(p1.getName()+":"+p1.getScore());
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}