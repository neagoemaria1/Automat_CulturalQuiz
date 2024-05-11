package main;

import javax.swing.SwingUtilities;

public class QuizApp {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CulturalQuizGUI quizGUI = new CulturalQuizGUI();
				quizGUI.setVisible(true);
				CulturalQuizThread quizThread = new CulturalQuizThread(quizGUI);
				quizThread.start();
			}
		});
	}
}
