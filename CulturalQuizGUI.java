package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.plaf.basic.BasicIconFactory;
public class CulturalQuizGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton checkButton;
	private JPanel mainPanel;

	private QuizState currentState = QuizState.START;
	private QuizState lastState;

	public CulturalQuizGUI() {
		initializeUI();
		setStartPanel();
	}

	private void initializeUI() {
		setTitle("Cultural Quiz");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);
		setContentPane(mainPanel);
		
	
	}
	public void setStartPanel() {
		mainPanel.removeAll();
		mainPanel.setBackground(new Color(250, 216, 230));

		JLabel titleLabel = new JLabel("Let's see how much you know!");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.BLACK);

		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setOpaque(false);
		titlePanel.add(titleLabel, BorderLayout.CENTER);

		JButton startButton = createButton("Start Quiz");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentState(QuizState.CHOOSE_CATEGORY);
			}
		});
		startButton.setPreferredSize(new Dimension(200, 50));

		addHoverEffect(startButton);

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setOpaque(false);
		centerPanel.add(startButton);

		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1;
		mainPanel.add(titlePanel, gbc);
		gbc.gridy = 1;
		mainPanel.add(centerPanel, gbc);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setQuestionPanel(QuestionModel questionModel) {
		mainPanel.removeAll();
		mainPanel.setBackground(new Color(250, 216, 230));

		JLabel questionLabel = new JLabel(
				"<html><div style='text-align: center;'>" + questionModel.getQuestion() + "</div></html>");
		questionLabel.setFont(new Font("Arial", Font.BOLD, 36));
		questionLabel.setForeground(Color.BLACK);

		JPanel questionPanel = new JPanel(new BorderLayout());
		questionPanel.setBackground(new Color(250, 216, 230));
		questionPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
		questionPanel.add(questionLabel, BorderLayout.CENTER);

		JPanel choicesPanel = new JPanel(new GridLayout(0, 1, 0, 10));
		choicesPanel.setBackground(new Color(250, 216, 230));
		choicesPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
		choicesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		choicesPanel.setOpaque(false);
		ButtonGroup buttonGroup = new ButtonGroup();
		for (int i = 0; i < questionModel.getChoices().size(); i++) {
			JRadioButton choiceButton = new JRadioButton(questionModel.getChoices().get(i));
			choiceButton.setFont(new Font("Arial", Font.PLAIN, 24));
			choiceButton.setForeground(Color.BLACK);
			choiceButton.setActionCommand(String.valueOf(i));
			choiceButton.setBackground(new Color(250, 216, 230));
			choiceButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
			choiceButton.setFocusPainted(false);
			choiceButton.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						checkButton.setEnabled(true);
					}
				}
			});
			buttonGroup.add(choiceButton);
			choicesPanel.add(choiceButton);
		}

		checkButton = createButton("Check Answer");
		checkButton.setEnabled(false);
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedChoiceIndex = Integer.parseInt(buttonGroup.getSelection().getActionCommand());
				lastState = currentState;
				checkAnswer(selectedChoiceIndex, questionModel);

			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(new Color(250, 216, 230));
		buttonPanel.add(checkButton);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(questionPanel, BorderLayout.NORTH);
		mainPanel.add(choicesPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setInformativeMessagePanel(String message, boolean isCorrect) {
		mainPanel.removeAll();
		mainPanel.setBackground(new Color(250, 216, 230));

		JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
		int fontSize = isCorrect ? 48 : 32;
		messageLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Color textColor = isCorrect ? new Color(0, 100, 0) : new Color(255, 102, 102);

		messageLabel.setForeground(textColor);

		JButton okButton = createButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentState != QuizState.WRONG_ANSWER) {
					currentState = lastState;
				}
				moveToTheNextState();
			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(new Color(250, 216, 230));
		buttonPanel.add(okButton);

		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1;
		mainPanel.add(messageLabel, gbc);
		gbc.gridy = 1;
		mainPanel.add(buttonPanel, gbc);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setFinalPanel(String message) {
	    mainPanel.removeAll();
	    mainPanel.setBackground(new Color(250, 216, 230));

	  
	    JLabel finalLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
	    finalLabel.setFont(new Font("Arial", Font.BOLD, 30));
	    finalLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    finalLabel.setForeground(Color.BLACK);

	    JButton restartButton = createButton("Restart Quiz");
	    restartButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            setCurrentState(QuizState.START);
	        }
	    });
	    addHoverEffect(restartButton);

	    JPanel finalPanel = new JPanel(new BorderLayout());
	    finalPanel.setBackground(new Color(250, 216, 230));
	    finalPanel.setBorder(BorderFactory.createCompoundBorder(
	        BorderFactory.createLineBorder(new Color(0, 128, 0), 4),
	        BorderFactory.createEmptyBorder(30, 20, 30, 20)
	    ));

	  
	    finalPanel.setOpaque(false);
	    JPanel gradientPanel = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g;
	            GradientPaint gradient = new GradientPaint(
	                0, 0, new Color(250, 216, 230),
	                0, getHeight(), new Color(255, 228, 225)
	            );
	            g2d.setPaint(gradient);
	            g2d.fillRect(0, 0, getWidth(), getHeight());
	        }
	    };
	    gradientPanel.setLayout(new BorderLayout());
	    gradientPanel.add(finalLabel, BorderLayout.CENTER);

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    buttonPanel.setOpaque(false);
	    buttonPanel.add(restartButton);
	    gradientPanel.add(buttonPanel, BorderLayout.SOUTH);

	    finalPanel.add(gradientPanel, BorderLayout.CENTER);

	    mainPanel.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.weighty = 1;
	    mainPanel.add(finalPanel, gbc);

	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	public void setCategoryPanel() {
		mainPanel.removeAll();
		mainPanel.setBackground(new Color(250, 216, 230));

		JLabel categoryLabel = new JLabel("Choose a category:");
		categoryLabel.setFont(new Font("Arial", Font.BOLD, 36));
		categoryLabel.setForeground(Color.BLACK);

		JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		categoryPanel.setBackground(new Color(250, 216, 230));
		categoryPanel.add(categoryLabel);

		JButton generalKnowledgeButton = createButton("General Knowledge");
		generalKnowledgeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentState(QuizState.QUESTION1);
			}
		});

		JButton historyButton = createButton("History");
		historyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentState(QuizState.QUESTION_H1);
			}
		});

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		buttonPanel.setBackground(new Color(250, 216, 230));
		buttonPanel.add(generalKnowledgeButton);
		buttonPanel.add(historyButton);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(categoryPanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private void moveToTheNextState() {

		switch (currentState) {
		case START:
			currentState = QuizState.CHOOSE_CATEGORY;
		case QUESTION1:
			currentState = QuizState.QUESTION2;
			break;
		case QUESTION2:
			currentState = QuizState.QUESTION3;
			break;
		case QUESTION3:
			currentState = QuizState.QUESTION4;
			break;
		case QUESTION4:
			currentState = QuizState.QUESTION5;
			break;
		case QUESTION5:
			currentState = QuizState.FINAL;
			break;
		case QUESTION_H1:
			currentState = QuizState.QUESTION_H2;
			break;
		case QUESTION_H2:
			currentState = QuizState.QUESTION_H3;
			break;
		case QUESTION_H3:
			currentState = QuizState.QUESTION_H4;
			break;
		case QUESTION_H4:
			currentState = QuizState.QUESTION_H5;
			break;
		case QUESTION_H5:
			currentState = QuizState.FINAL_H;

			break;
		case WRONG_ANSWER:
			currentState = QuizState.START;
			break;
		case FINAL:
			break;
		case FINAL_H:
			break;
		default:
			currentState = QuizState.START;
			break;
		}
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 30));
		button.setBackground(new Color(41, 128, 185));
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
		return button;
	}

	private void addHoverEffect(JButton button) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(78, 205, 196));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(41, 128, 185));
			}
		});
	}

	private void checkAnswer(int selectedChoiceIndex, QuestionModel questionModel) {
		int correctChoiceIndex = questionModel.getCorrectChoiceIndex();
		boolean isCorrect = selectedChoiceIndex == correctChoiceIndex;
		if (isCorrect) {
			currentState = QuizState.CORRECT_ANSWER;
		} else {
			currentState = QuizState.WRONG_ANSWER;
		}

	}

	public QuizState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(QuizState currentState) {
		this.currentState = currentState;
	}

}
