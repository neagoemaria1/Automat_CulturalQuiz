package main;

import java.util.ArrayList;
import java.util.List;

public class CulturalQuizThread extends Thread {

	private List<QuestionModel> questions;
	private CulturalQuizGUI quizGUI;
	private QuizState currentState;
	private QuizState lastState;
	private String finalMessage;
	
	public CulturalQuizThread(CulturalQuizGUI quizGUI) {
		this.quizGUI = quizGUI;
		lastState = null;
		questions = new ArrayList<>();
		questions.add(new QuestionModel("What is the traditional dance of Hawaii?",
				List.of("A) Hula", "B) Flamenco", "C) Salsa"), 0, QuizState.QUESTION1));
		questions.add(new QuestionModel("In which country is the famous Oktoberfest beer festival held annually?",
				List.of("A) Italy", "B) Austria", "C) Germany"), 2, QuizState.QUESTION2));
		questions.add(new QuestionModel("Who painted the famous artwork \"Starry Night\"?",
				List.of("A) Leonardo da Vinci", "B) Vincent van Gogh", "C) Pablo Picasso"), 1, QuizState.QUESTION3));
		questions.add(new QuestionModel("Which country is known for inventing sushi?",
				List.of("A) China", "B) Japan", "C) India"), 1, QuizState.QUESTION4));
		questions.add(new QuestionModel("Which city is famous for its Carnival celebration?",
				List.of("A) Rio de Janeiro", "B) Venice", "C) Sydney"), 0, QuizState.QUESTION5));

		questions.add(new QuestionModel("Who was the first President of the United States?",
				List.of("A) Thomas Jefferson", "B) George Washington", "C) Abraham Lincoln"), 1,
				QuizState.QUESTION_H1));

		questions.add(new QuestionModel("In which year did World War II end?", List.of("A) 1945", "B) 1918", "C) 1939"),
				0, QuizState.QUESTION_H2));

		questions.add(new QuestionModel("Who is known as the 'Father of Computer Science'?",
				List.of("A) Alan Turing", "B) Bill Gates", "C) Steve Jobs"), 0, QuizState.QUESTION_H3));

		questions.add(new QuestionModel("Which ancient civilization built the pyramids?",
				List.of("A) Roman", "B) Greek", "C) Egyptian"), 2, QuizState.QUESTION_H4));

		questions.add(new QuestionModel("What event marked the beginning of the French Revolution?",
				List.of("A) Storming of the Bastille", "B) Reign of Terror", "C) Execution of Louis XVI"), 0,
				QuizState.QUESTION_H5));

	}

	@Override
	public void run() {
		while (true) {
			currentState = quizGUI.getCurrentState();
			if (currentState != lastState) {
				lastState = currentState;
				updateGUI(currentState);
			}
		}
	}

	private void updateGUI(QuizState currentState) {
		switch (currentState) {
		case START:
			quizGUI.setStartPanel();
			break;
		case CORRECT_ANSWER:
			quizGUI.setInformativeMessagePanel("Your Answer is Correct!", true);
			break;
		case WRONG_ANSWER:
			quizGUI.setInformativeMessagePanel("Your Answer is Wrong! You have to start again...", false);

			break;
		case CHOOSE_CATEGORY:
			quizGUI.setCategoryPanel();	
			break;
		case QUESTION1:
		case QUESTION2:
		case QUESTION3:
		case QUESTION4:
		case QUESTION5:
		case QUESTION_H1:
		case QUESTION_H2:
		case QUESTION_H3:
		case QUESTION_H4:
		case QUESTION_H5:
			for (QuestionModel q : questions) {
				if (q.getState().equals(currentState)) {
					quizGUI.setQuestionPanel(q);
					break;
				}
			}
			break;
		case FINAL:
			   finalMessage = "Congratulations! You managed to complete the General Knowledge Section!";
			quizGUI.setFinalPanel(finalMessage);
			break;
		case FINAL_H:
			 finalMessage = "Congratulations! You managed to complete the History Section!";
			quizGUI.setFinalPanel(finalMessage);
			break;
		default:
			currentState = QuizState.START;
			break;
		}
	}
}
