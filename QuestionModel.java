package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionModel {
	private String question;
	private List<String> choices;
	private int correctChoiceIndex;
	private QuizState state;

	public QuestionModel(String question, List<String> choices, int correctChoiceIndex, QuizState state) {
		this.setQuestion(question);
		this.choices = new ArrayList<>(choices);
		this.correctChoiceIndex = correctChoiceIndex;
		this.setState(state);
	}

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getChoices() {
		return Collections.unmodifiableList(choices);
	}

	public int getCorrectChoiceIndex() {
		return correctChoiceIndex;
	}

	public QuizState getState() {
		return state;
	}

	public void setState(QuizState state) {
		this.state = state;
	}

	
}
