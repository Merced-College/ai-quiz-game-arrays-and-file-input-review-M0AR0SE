/*Francisco Gil Mendoza
6/1/2026
Ai Quiz Game*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static final int NUMBER_OF_QUESTIONS = 10;   //Constant storing how many questions the quiz will load
    public static final int NUMBER_OF_CHOICES = 4;      //Constant storing how many answers each question has

    public static void main(String[] args) {
        String[] questions = new String[NUMBER_OF_QUESTIONS];                                   //Array to store all question text from the CSV file
        String[][] answers = new String[NUMBER_OF_QUESTIONS][NUMBER_OF_CHOICES];                //Dual dimmensional Array storing each question's four answer choices
        int[] correctAnswers = new int[NUMBER_OF_QUESTIONS];                                    //Array storing the index of the correct answer for each question

        readQuizFile(questions, answers, correctAnswers);       //Load all quiz data from the CSV file into the arrays

        Scanner input = new Scanner(System.in);                 //Scanner for reading user input during the quiz
        int score = 0;                                          //Tracks how many questions the user answers correctly

        System.out.println("Welcome to the AI Quiz Game!");                                 //Intro messages for the player
        System.out.println("Choose the correct answer by entering 1, 2, 3, or 4.\n");

        for (int i = 0; i < questions.length; i++) {                                          //Loop through each question in the quiz
            System.out.println("Question " + (i + 1) + ": " + questions[i]);                  //Display the current question number and text

            for (int j = 0; j < answers[i].length; j++) {                                     //Display all four answer choices for the current question
                System.out.println((j + 1) + ". " + answers[i][j]);
            }

            System.out.print("Your answer: ");          //Read the user's answer and convert it to a zero-based index
            int userAnswer = input.nextInt() - 1;

            if (userAnswer == correctAnswers[i]) {        //Check if the user's answer matches the correct answer
                System.out.println("Correct!\n");
                score++;                                  //Increases the score for correct answer
            } else {
                System.out.println("Incorrect.");
                System.out.println("The correct answer was: " + answers[i][correctAnswers[i]] + "\n");
            }
        }

        System.out.println("Quiz complete!");       //Display final score after all questions are answered
        System.out.println("Your final score is: " + score + " out of " + questions.length);

        // Enhancement: Added score percentage, feedback messages
        double percentage = ((double) score / questions.length) * 100;              //Calculate the user's percentage score
        System.out.println("Your score percentage is: " + percentage + "%");

        if (percentage == 100) {                                    //Give feedback based on performance
            System.out.println("Perfect score! Amazing job!");
        } else if (percentage >= 70) {
            System.out.println("Great work! You passed the quiz.");
        } else {
            System.out.println("Keep practicing — you’ll get it next time!");
        }

        input.nextLine();           // Clear leftover newline so nextline() works correctly
        //Enhancement: added replay prompt
        System.out.print("Would you like to play again? (yes/no): ");
        String playAgain = input.nextLine();

        while (!playAgain.equalsIgnoreCase("yes") && !playAgain.equalsIgnoreCase("no")) {
            System.out.print("Please enter 'yes' or 'no': ");
            playAgain = input.nextLine();
        }

        if (playAgain.equalsIgnoreCase("yes")) {
            System.out.println("Restart the program to play again!");
        }

        input.close();
    }

    public static void readQuizFile(String[] questions, String[][] answers, int[] correctAnswers) {
        try {
            File file = new File("ai_quiz_questions.csv");
            Scanner fileReader = new Scanner(file);

            fileReader.nextLine();              //Skip header row

            int index = 0;

            while (fileReader.hasNextLine() && index < questions.length) {
                String line = fileReader.nextLine();
                String[] data = line.split(",");

                questions[index] = data[0];

                for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
                    answers[index][i] = data[i + 1];
                }

                correctAnswers[index] = 0;              //Default correct answer index
                index++;
            }

            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("The quiz file could not be found.");
        }
    }
}